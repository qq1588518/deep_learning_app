package com.deeplearning.app.task;

import android.text.TextUtils;
import android.util.Log;

import com.deeplearning.app.rtmp.RESFlvData;
import com.deeplearning.app.rtmp.RtmpClient;
import com.deeplearning.app.core.RESCoreParameters;
import com.deeplearning.app.rtmp.FLvMetaData;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class RtmpStreamingSender implements Runnable {
    private static final String TAG = "RtmpStreamingSender";
    private static final int MAX_QUEUE_CAPACITY = 50;
    private AtomicBoolean mQuit = new AtomicBoolean(false);
    private LinkedBlockingDeque<RESFlvData> frameQueue = new LinkedBlockingDeque<>(MAX_QUEUE_CAPACITY);
    private final Object syncWriteMsgNum = new Object();
    private FLvMetaData fLvMetaData;
    private RESCoreParameters coreParameters;
    private volatile int state;

    private long jniRtmpPointer = 0;
    private int maxQueueLength = 50;
    private int writeMsgNum = 0;
    private String rtmpAddr = null;

    private static class STATE {
        private static final int START = 0;
        private static final int RUNNING = 1;
        private static final int STOPPED = 2;
    }

    public RtmpStreamingSender() {
        coreParameters = new RESCoreParameters();
        coreParameters.mediacodecAACBitRate = RESFlvData.AAC_BITRATE;
        coreParameters.mediacodecAACSampleRate = RESFlvData.AAC_SAMPLE_RATE;
        coreParameters.mediacodecAVCFrameRate = RESFlvData.FPS;
        coreParameters.videoWidth = RESFlvData.VIDEO_WIDTH;
        coreParameters.videoHeight = RESFlvData.VIDEO_HEIGHT;

        fLvMetaData = new FLvMetaData(coreParameters);
    }

    @Override
    public void run() {
        while (!mQuit.get()) {
            if (frameQueue.size() > 0) {
                switch (state) {
                    case STATE.START:
                        Log.i(TAG,"RESRtmpSender,WorkHandler,tid=" + Thread.currentThread().getId());
                        if (TextUtils.isEmpty(rtmpAddr)) {
                            Log.e(TAG,"rtmp address is null!");
                            break;
                        }
                        jniRtmpPointer = RtmpClient.open(rtmpAddr, true);
                        final int openR = jniRtmpPointer == 0 ? 1 : 0;
                        String serverIpAddr = null;
                        if (openR == 0) {
                            serverIpAddr = RtmpClient.getIpAddr(jniRtmpPointer);
                            Log.i(TAG,"server ip address = " + serverIpAddr);
                        }
                        if (jniRtmpPointer == 0) {
                            break;
                        } else {
                            byte[] MetaData = fLvMetaData.getMetaData();
                            RtmpClient.write(jniRtmpPointer,
                                    MetaData,
                                    MetaData.length,
                                    RESFlvData.FLV_RTMP_PACKET_TYPE_INFO, 0);
                            state = STATE.RUNNING;
                        }
                        break;
                    case STATE.RUNNING:
                        synchronized (syncWriteMsgNum) {
                            --writeMsgNum;
                        }
                        if (state != STATE.RUNNING) {
                            break;
                        }
                        RESFlvData flvData = frameQueue.pop();
                        if (writeMsgNum >= (maxQueueLength * 2 / 3) && flvData.flvTagType == RESFlvData.FLV_RTMP_PACKET_TYPE_VIDEO && flvData.droppable) {
                            Log.i(TAG,"senderQueue is crowded,abandon video");
                            break;
                        }
                        final int res = RtmpClient.write(jniRtmpPointer, flvData.byteBuffer, flvData.byteBuffer.length, flvData.flvTagType, flvData.dts);
                        if (res == 0) {
                            if (flvData.flvTagType == RESFlvData.FLV_RTMP_PACKET_TYPE_VIDEO) {
                                Log.i(TAG,"video frame sent = " + flvData.size);
                            } else {
                                Log.i(TAG,"audio frame sent = " + flvData.size);
                            }
                        } else {
                            Log.i(TAG,"writeError = " + res);
                        }

                        break;
                    case STATE.STOPPED:
                        if (state == STATE.STOPPED || jniRtmpPointer == 0) {
                            break;
                        }
                        final int closeR = RtmpClient.close(jniRtmpPointer);
                        serverIpAddr = null;
                        Log.i(TAG,"close result = " + closeR);
                        break;
                }

            }

        }
        final int closeR = RtmpClient.close(jniRtmpPointer);
        Log.i(TAG,"close result = " + closeR);
    }

    public void sendStart(String rtmpAddr) {
        synchronized (syncWriteMsgNum) {
            writeMsgNum = 0;
        }
        this.rtmpAddr = rtmpAddr;
        state = STATE.START;
    }

    public void sendStop() {
        synchronized (syncWriteMsgNum) {
            writeMsgNum = 0;
        }
        state = STATE.STOPPED;
    }

    public void sendFood(RESFlvData flvData, int type) {
        synchronized (syncWriteMsgNum) {
            //LAKETODO optimize
            if (writeMsgNum <= maxQueueLength) {
                frameQueue.add(flvData);
                ++writeMsgNum;
            } else {
                Log.i(TAG,"senderQueue is full,abandon");
            }
        }
    }


    public final void quit() {
        mQuit.set(true);
    }
}
