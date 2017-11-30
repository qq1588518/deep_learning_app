package com.deeplearning.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import com.deeplearning.app.activity.ScreenRecordActivity;
import com.deeplearning.app.model.DanmakuBean;
import com.deeplearning.app.manager.ScreenRecordWindowManager;
import com.deeplearning.app.IScreenRecorderAidlInterface;
import com.deeplearning_app.R;


/**
 * Created by qq1588518 on 17/12/01.
 */
public class ScreenRecordListenerService extends Service {
    private static final String TAG = "ScreenRecordListenerService";

    public static final int PENDING_REQUEST_CODE = 0x01;
    private static final int NOTIFICATION_ID = 3;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
        if (!ScreenRecordWindowManager.isWindowShowing()) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    ScreenRecordWindowManager.createSmallWindow(getApplicationContext());
//                }
//            });
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initNotification() {
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("您正在录制视频内容哦")
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_VIBRATE);

        Intent backIntent = new Intent(this, ScreenRecordActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, PENDING_REQUEST_CODE, backIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_ID);
        }
        ScreenRecordWindowManager.removeSmallWindow(getApplicationContext());
    }

    private IScreenRecorderAidlInterface.Stub mBinder = new IScreenRecorderAidlInterface.Stub() {

        @Override
        public void sendDanmaku(List<DanmakuBean> danmakuBean) throws RemoteException {
            if (ScreenRecordWindowManager.isWindowShowing()) {
                ScreenRecordWindowManager.getSmallWindow().setDataToList(danmakuBean);
            }
        }

        @Override
        public void startScreenRecord(Intent bundleData) throws RemoteException {

        }
    };

}
