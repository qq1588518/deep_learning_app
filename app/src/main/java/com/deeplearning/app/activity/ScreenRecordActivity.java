package com.deeplearning.app.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.deeplearning.app.DLApplication;
import com.deeplearning.app.config.Config;
import com.deeplearning.app.rtmp.RESFlvData;
import com.deeplearning.app.IScreenRecorderAidlInterface;
import com.deeplearning.app.core.RESAudioClient;
import com.deeplearning.app.core.RESCoreParameters;
import com.deeplearning.app.model.DanmakuBean;
import com.deeplearning.app.rtmp.RESFlvDataCollecter;
import com.deeplearning.app.service.ScreenRecordListenerService;
import com.deeplearning.app.task.RtmpStreamingSender;
import com.deeplearning.app.task.ScreenRecorderThread;
import com.deeplearning_app.R;


/**
 * Created by qq1588518 on 17/12/01.
 */
public class ScreenRecordActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ScreenRecordActivity";
    private PackageManager mPackageManager;
    private String[] mPackages;
    private static final int REQUEST_CODE = 1;
    private Button mButton;
    private EditText mRtmpAddET;
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorderThread mVideoRecorder;
    private RESAudioClient audioClient;
    private RtmpStreamingSender streamingSender;
    private ExecutorService executorService;
    private List<DanmakuBean> danmakuBeanList = new ArrayList<>();
    private String rtmpAddr;
    private boolean isRecording;
    private RESCoreParameters coreParameters;

    public static void launchActivity(Context ctx) {
        if(Config.DEBUG) {
            Log.i(TAG, "launchActivity");
        }
        Intent it = new Intent(ctx, ScreenRecordActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(it);
    }

    private IScreenRecorderAidlInterface recorderAidlInterface;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            recorderAidlInterface = IScreenRecorderAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            recorderAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.DEBUG) {
            Log.i(TAG, "onCreate");
        }
        setContentView(R.layout.activity_screen);
        mPackageManager = this.getPackageManager();
        mPackages = new String[]{Config.WECHAT_PACKAGENAME};
        mButton = (Button) findViewById(R.id.button);
        mRtmpAddET = (EditText) findViewById(R.id.et_rtmp_address);
        mButton.setOnClickListener(this);
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Config.DEBUG) {
            Log.i(TAG, "onActivityResult");
        }
        MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
        if (mediaProjection == null) {
            Log.e("@@", "media projection is null");
            return;
        }
        rtmpAddr = mRtmpAddET.getText().toString().trim();
        if (TextUtils.isEmpty(rtmpAddr)) {
            Toast.makeText(this, "rtmp address cannot be null", Toast.LENGTH_SHORT).show();
            return;
        }
        streamingSender = new RtmpStreamingSender();
        streamingSender.sendStart(rtmpAddr);
        RESFlvDataCollecter collecter = new RESFlvDataCollecter() {
            @Override
            public void collect(RESFlvData flvData, int type) {
                streamingSender.sendFood(flvData, type);
            }
        };
        coreParameters = new RESCoreParameters();

        audioClient = new RESAudioClient(coreParameters);

        if (!audioClient.prepare()) {
            Log.i(TAG,"!!!!!audioClient.prepare()failed");
            return;
        }

        mVideoRecorder = new ScreenRecorderThread(collecter, RESFlvData.VIDEO_WIDTH, RESFlvData.VIDEO_HEIGHT, RESFlvData.VIDEO_BITRATE, 1, mediaProjection);
        mVideoRecorder.start();
        audioClient.start(collecter);

        executorService = Executors.newCachedThreadPool();
        executorService.execute(streamingSender);

        mButton.setText("Stop Recorder");
        Toast.makeText(this, "Screen recorder is running...", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }


    @Override
    public void onClick(View v) {
        if(Config.DEBUG) {
            Log.i(TAG, "onClick");
        }
        if (mVideoRecorder != null) {
            stopScreenRecord();
        } else {
            createScreenCapture();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Config.DEBUG) {
            Log.i(TAG, "onDestroy");
        }
        if (mVideoRecorder != null) {
            stopScreenRecord();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Config.DEBUG) {
            Log.i(TAG, "onResume");
        }
        if (isRecording) stopScreenRecordService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Config.DEBUG) {
            Log.i(TAG, "onStop");
        }
        if (isRecording) startScreenRecordService();
    }

    private void startScreenRecordService() {
        if(Config.DEBUG) {
            Log.i(TAG, "startScreenRecordService");
        }
        if (mVideoRecorder != null && mVideoRecorder.getStatus()) {
            Intent runningServiceIT = new Intent(this, ScreenRecordListenerService.class);
            bindService(runningServiceIT, connection, BIND_AUTO_CREATE);
            startService(runningServiceIT);
            startAutoSendDanmaku();
        }
    }

    private void startAutoSendDanmaku() {
        if(Config.DEBUG) {
            Log.i(TAG, "startAutoSendDanmaku");
        }
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (true) {
                    DanmakuBean danmakuBean = new DanmakuBean();
                    danmakuBean.setMessage(String.valueOf(index++));
                    danmakuBean.setName("little girl");
                    danmakuBeanList.add(danmakuBean);
                    try {
                        if (recorderAidlInterface != null) {
                            recorderAidlInterface.sendDanmaku(danmakuBeanList);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void stopScreenRecordService() {
        if(Config.DEBUG) {
            Log.i(TAG, "stopScreenRecordService");
        }
        Intent runningServiceIT = new Intent(this, ScreenRecordListenerService.class);
        stopService(runningServiceIT);
        if (mVideoRecorder != null && mVideoRecorder.getStatus()) {
            Toast.makeText(this, "现在正在进行录屏直播哦", Toast.LENGTH_SHORT).show();
        }
    }

    private void createScreenCapture() {
        if(Config.DEBUG) {
            Log.i(TAG, "createScreenCapture");
        }
        isRecording = true;
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, REQUEST_CODE);
    }

    private void stopScreenRecord() {
        if(Config.DEBUG) {
            Log.i(TAG, "stopScreenRecord");
        }
        mVideoRecorder.quit();
        mVideoRecorder = null;
        if (streamingSender != null) {
            streamingSender.sendStop();
            streamingSender.quit();
            streamingSender = null;
        }
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
        mButton.setText("Restart recorder");
    }

    public void goApp(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "goApp");
        }
        Intent intent = mPackageManager.getLaunchIntentForPackage(Config.WECHAT_PACKAGENAME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void cleanProcess(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "cleanProcess");
        }
        for (String mPackage : mPackages) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mPackage, null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    public void autoInstall(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "autoInstall");
        }
        String apkPath = Environment.getExternalStorageDirectory() + "/deeplearning_app.apk";
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }
}
