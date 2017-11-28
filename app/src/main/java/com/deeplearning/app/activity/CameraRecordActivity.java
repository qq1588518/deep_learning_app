package com.deeplearning.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deeplearning.app.config.Config;
import com.deeplearning.app.core.Packager;
import com.deeplearning.app.rtmp.RESFlvData;
import com.deeplearning.app.rtmp.RESFlvDataCollecter;
import com.deeplearning.app.task.RtmpStreamingSender;
import com.deeplearning_app.R;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class CameraRecordActivity extends BaseActivity implements Camera.PreviewCallback, SurfaceHolder.Callback {

    private static final String TAG = "CameraRecordActivity";
    @BindView(R.id.btn_push_to_rtmp)
    Button btnPushToRtmp;
    @BindView(R.id.et_rtmp_address)
    EditText etRtmpAddress;
    @BindView(R.id.sv_camera)
    SurfaceView svCamera;

    private AtomicBoolean mQuit = new AtomicBoolean(false);
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private RESFlvDataCollecter mDataCollecter;
    private RtmpStreamingSender streamingSender;
    private String rtmpAddr;
    private int mWidth;
    private int mHeight;
    private int mBitRate;
    private int mDpi;
    // parameters for the encoder
    private static final String MIME_TYPE = "video/avc"; // H.264 Advanced Video Coding
    private static final int FRAME_RATE = 30; // 30 fps
    private static final int IFRAME_INTERVAL = 10; // 10 seconds between I-frames
    private static final int TIMEOUT_US = 10000;
    private static final int VIDEO_WIDTH = 1280;
    private static final int VIDEO_HEIGHT = 720;

    private MediaCodec mEncoder;
    private long startTime;
    private VideoEncoderThread encoderThread;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Camera.Parameters mCameraParams;


    public static void launchActivity(Context ctx) {
        if(Config.DEBUG) {
            Log.d(TAG, "launchActivity");
        }
        Intent it = new Intent(ctx, CameraRecordActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(it);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        mSurfaceHolder = svCamera.getHolder();
        mSurfaceHolder.setFixedSize(VIDEO_WIDTH, VIDEO_HEIGHT);
        mSurfaceHolder.addCallback(this);
        try {
            prepareEncoder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        encoderThread = new VideoEncoderThread();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if(Config.DEBUG) {
            Log.d(TAG, "onPreviewFrame");
        }
        dataToCallback(data);
    }

    private void dataToCallback(byte[] data) {
        if(Config.DEBUG) {
            Log.d(TAG, "launchActivity");
        }
        encoderThread.put(data);
    }

    @OnClick(R.id.btn_push_to_rtmp)
    public void onViewClicked() {
        if(Config.DEBUG) {
            Log.d(TAG, "onViewClicked");
        }
        rtmpAddr = etRtmpAddress.getText().toString().trim();
        if (TextUtils.isEmpty(rtmpAddr)) {
            Toast.makeText(this, "rtmp address cannot be null", Toast.LENGTH_SHORT).show();
            return;
        }
        streamingSender = new RtmpStreamingSender();
        streamingSender.sendStart(rtmpAddr);
        mDataCollecter = new RESFlvDataCollecter() {
            @Override
            public void collect(RESFlvData flvData, int type) {
                streamingSender.sendFood(flvData, type);
            }
        };
        encoderThread.start();
    }

    public final boolean getStatus() {
        if(Config.DEBUG) {
            Log.d(TAG, "getStatus");
        }
        return !mQuit.get();
    }


    private void prepareEncoder() throws IOException {
        if(Config.DEBUG) {
            Log.d(TAG, "prepareEncoder");
        }
        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, VIDEO_WIDTH, VIDEO_HEIGHT);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL);
        if(Config.DEBUG) {
            Log.d(TAG, "created video format: " + format);
        }
        mEncoder = MediaCodec.createEncoderByType(MIME_TYPE);
        mEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mEncoder.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(Config.DEBUG) {
            Log.d(TAG, "surfaceCreated");
        }
        openCamera(Camera.CameraInfo.CAMERA_FACING_BACK, holder);
    }

    private void openCamera(int cameraType, SurfaceHolder holder) {
        if(Config.DEBUG) {
            Log.d(TAG, "openCamera");
        }
        releaseCamera();
        try {
            mCamera = Camera.open(cameraType);
        } catch (Exception e) {
            mCamera = null;
            e.printStackTrace();
        }
        if (mCamera == null) {
            Toast.makeText(this, "无法开启摄像头", Toast.LENGTH_SHORT).show();
            return;
        }
        mCameraParams = mCamera.getParameters();
        mCameraParams.setPreviewSize(VIDEO_WIDTH, VIDEO_HEIGHT);
        mCameraParams.setPreviewFormat(ImageFormat.NV21);
        mCamera.setParameters(mCameraParams);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.setPreviewCallback(this);
        mCamera.startPreview();
    }

    private synchronized void releaseCamera() {
        if(Config.DEBUG) {
            Log.d(TAG, "releaseCamera");
        }
        if (mCamera != null) {
            try {
                mCamera.setPreviewCallback(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(Config.DEBUG) {
            Log.d(TAG, "surfaceChanged");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(Config.DEBUG) {
            Log.d(TAG, "surfaceDestroyed");
        }
    }

    private class VideoEncoderThread extends Thread {

        private static final int MAX_QUEUE_COUNT = 5;

        private ConcurrentLinkedQueue<byte[]> rawframes = new ConcurrentLinkedQueue<>();

        public void put(byte[] videoFrame) {
            if(Config.DEBUG) {
                Log.d(TAG, "put");
            }
            if (rawframes.size() <= MAX_QUEUE_COUNT) {
                rawframes.add(videoFrame);
            }
        }

        private byte[] get() {
            if(Config.DEBUG) {
                Log.d(TAG, "get");
            }
            if (!rawframes.isEmpty()) {
                return rawframes.poll();
            } else return null;
        }

        @Override
        public void run() {
            if(Config.DEBUG) {
                Log.d(TAG, "run");
            }
            while (!mQuit.get()) {
                ByteBuffer[] inputBuffers = mEncoder.getInputBuffers();
                byte[] rawFrame = get();
                if (rawFrame != null) {
                    int inputBufferIndex = mEncoder.dequeueInputBuffer(-1);
                    if (inputBufferIndex >= 0) {
                        ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                        inputBuffer.clear();
                        int rotateDegree = 90;
                        long begin = System.currentTimeMillis();
//                        byte[] rotatedFrame;
//                        rotatedFrame = scaleOrRotateData(rawframe.data, rotateDegree, rawframe.isPPTVideoFrame);

                        // color space transform. 下列方法是SRS项目中的格式转换方法，暂时用于调试，效率不一定高, 经过测试，平均 1帧需要12毫秒左右来转换
//                        if (convertedYUVBytes == null || convertedYUVBytes.length != rotatedFrame.length) {
//                            convertedYUVBytes = new byte[rotatedFrame.length];
//                        }
//                        YuvConvertUtil.convertYV12ToSpecifiedYUV420(rotatedFrame, convertedYUVBytes, vColor,
//                                DES_FRAME_WIDTH, DES_FRAME_HEIGHT);
                        inputBuffer.put(rawFrame, 0, rawFrame.length);
                        mEncoder.queueInputBuffer(inputBufferIndex, 0, rawFrame.length, mBufferInfo.presentationTimeUs * 1000, 0);//MS -> US
                    }
                } else continue;

                int eobIndex = mEncoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_US);
                switch (eobIndex) {
                    case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                        Log.i(TAG,"VideoSenderThread,MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED");
                        break;
                    case MediaCodec.INFO_TRY_AGAIN_LATER:
//                    LogTools.d("VideoSenderThread,MediaCodec.INFO_TRY_AGAIN_LATER");
                        break;
                    case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                        Log.i(TAG,"VideoSenderThread,MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:" +
                                mEncoder.getOutputFormat().toString());
                        sendAVCDecoderConfigurationRecord(0, mEncoder.getOutputFormat());
                        break;
                    default:
                        Log.i(TAG,"VideoSenderThread,MediaCode,eobIndex=" + eobIndex);
                        if (startTime == 0) {
                            startTime = mBufferInfo.presentationTimeUs / 1000;
                        }
                        /**
                         * we send sps pps already in INFO_OUTPUT_FORMAT_CHANGED
                         * so we ignore MediaCodec.BUFFER_FLAG_CODEC_CONFIG
                         */
                        if (mBufferInfo.flags != MediaCodec.BUFFER_FLAG_CODEC_CONFIG && mBufferInfo.size != 0) {
                            ByteBuffer realData = mEncoder.getOutputBuffers()[eobIndex];
                            realData.position(mBufferInfo.offset + 4);
                            realData.limit(mBufferInfo.offset + mBufferInfo.size);
                            sendRealData((mBufferInfo.presentationTimeUs / 1000) - startTime, realData);
                        }
                        mEncoder.releaseOutputBuffer(eobIndex, false);
                        break;
                }
            }
        }

        private void sendAVCDecoderConfigurationRecord(long tms, MediaFormat format) {
            if(Config.DEBUG) {
                Log.d(TAG, "sendAVCDecoderConfigurationRecord");
            }
            byte[] AVCDecoderConfigurationRecord = Packager.H264Packager.generateAVCDecoderConfigurationRecord(format);
            int packetLen = Packager.FLVPackager.FLV_VIDEO_TAG_LENGTH +
                    AVCDecoderConfigurationRecord.length;
            byte[] finalBuff = new byte[packetLen];
            Packager.FLVPackager.fillFlvVideoTag(finalBuff,
                    0,
                    true,
                    true,
                    AVCDecoderConfigurationRecord.length);
            System.arraycopy(AVCDecoderConfigurationRecord, 0,
                    finalBuff, Packager.FLVPackager.FLV_VIDEO_TAG_LENGTH, AVCDecoderConfigurationRecord.length);
            RESFlvData resFlvData = new RESFlvData();
            resFlvData.droppable = false;
            resFlvData.byteBuffer = finalBuff;
            resFlvData.size = finalBuff.length;
            resFlvData.dts = (int) tms;
            resFlvData.flvTagType = RESFlvData.FLV_RTMP_PACKET_TYPE_VIDEO;
            resFlvData.videoFrameType = RESFlvData.NALU_TYPE_IDR;
            mDataCollecter.collect(resFlvData, RESFlvData.FLV_RTMP_PACKET_TYPE_VIDEO);
        }

        private void sendRealData(long tms, ByteBuffer realData) {
            if(Config.DEBUG) {
                Log.d(TAG, "sendRealData");
            }
            int realDataLength = realData.remaining();
            int packetLen = Packager.FLVPackager.FLV_VIDEO_TAG_LENGTH +
                    Packager.FLVPackager.NALU_HEADER_LENGTH +
                    realDataLength;
            byte[] finalBuff = new byte[packetLen];
            realData.get(finalBuff, Packager.FLVPackager.FLV_VIDEO_TAG_LENGTH +
                            Packager.FLVPackager.NALU_HEADER_LENGTH,
                    realDataLength);
            int frameType = finalBuff[Packager.FLVPackager.FLV_VIDEO_TAG_LENGTH +
                    Packager.FLVPackager.NALU_HEADER_LENGTH] & 0x1F;
            Packager.FLVPackager.fillFlvVideoTag(finalBuff,
                    0,
                    false,
                    frameType == 5,
                    realDataLength);
            RESFlvData resFlvData = new RESFlvData();
            resFlvData.droppable = true;
            resFlvData.byteBuffer = finalBuff;
            resFlvData.size = finalBuff.length;
            resFlvData.dts = (int) tms;
            resFlvData.flvTagType = RESFlvData.FLV_RTMP_PACKET_TYPE_VIDEO;
            resFlvData.videoFrameType = frameType;
            mDataCollecter.collect(resFlvData, RESFlvData.FLV_RTMP_PACKET_TYPE_VIDEO);
        }
    }
}
