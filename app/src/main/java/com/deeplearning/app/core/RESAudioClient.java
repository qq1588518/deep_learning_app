package com.deeplearning.app.core;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.deeplearning.app.config.Config;
import com.deeplearning.app.rtmp.RESFlvDataCollecter;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class RESAudioClient {
    private static final String TAG = "RESAudioClient";
    RESCoreParameters resCoreParameters;
    private final Object syncOp = new Object();
    private AudioRecordThread audioRecordThread;
    private AudioRecord audioRecord;
    private byte[] audioBuffer;
    private RESSoftAudioCore softAudioCore;

    public RESAudioClient(RESCoreParameters parameters) {
        resCoreParameters = parameters;
    }

    public boolean prepare() {
        synchronized (syncOp) {
            if(Config.DEBUG) {
                Log.d(TAG, "prepare");
            }
            resCoreParameters.audioBufferQueueNum = 5;
            softAudioCore = new RESSoftAudioCore(resCoreParameters);
            if (!softAudioCore.prepare()) {
                if(Config.DEBUG) {
                    Log.e(TAG, "not prepare");
                }
                return false;
            }
            resCoreParameters.audioRecoderFormat = AudioFormat.ENCODING_PCM_16BIT;
            resCoreParameters.audioRecoderChannelConfig = AudioFormat.CHANNEL_IN_MONO;
            resCoreParameters.audioRecoderSliceSize = resCoreParameters.mediacodecAACSampleRate / 10;
            resCoreParameters.audioRecoderBufferSize = resCoreParameters.audioRecoderSliceSize * 2;
            resCoreParameters.audioRecoderSource = MediaRecorder.AudioSource.DEFAULT;
            resCoreParameters.audioRecoderSampleRate = resCoreParameters.mediacodecAACSampleRate;
            prepareAudio();
            return true;
        }
    }

    public boolean start(RESFlvDataCollecter flvDataCollecter) {
        synchronized (syncOp) {
            if(Config.DEBUG) {
                Log.d(TAG, "start");
            }
            softAudioCore.start(flvDataCollecter);
            audioRecord.startRecording();
            audioRecordThread = new AudioRecordThread();
            audioRecordThread.start();
            Log.d(TAG,"RESAudioClient,start()");
            return true;
        }
    }

    public boolean stop() {
        synchronized (syncOp) {
            if(Config.DEBUG) {
                Log.d(TAG, "stop");
            }
            audioRecordThread.quit();
            try {
                audioRecordThread.join();
            } catch (InterruptedException ignored) {
            }
            softAudioCore.stop();
            audioRecordThread = null;
            audioRecord.stop();
            return true;
        }
    }

    public boolean destroy() {
        synchronized (syncOp) {
            if(Config.DEBUG) {
                Log.d(TAG, "destroy");
            }
            audioRecord.release();
            return true;
        }
    }
    public void setSoftAudioFilter(BaseSoftAudioFilter baseSoftAudioFilter) {
        softAudioCore.setAudioFilter(baseSoftAudioFilter);
    }
    public BaseSoftAudioFilter acquireSoftAudioFilter() {
        return softAudioCore.acquireAudioFilter();
    }

    public void releaseSoftAudioFilter() {
        softAudioCore.releaseAudioFilter();
    }

    private boolean prepareAudio() {
        if(Config.DEBUG) {
            Log.d(TAG, "prepareAudio");
        }
        int minBufferSize = AudioRecord.getMinBufferSize(resCoreParameters.audioRecoderSampleRate,
                resCoreParameters.audioRecoderChannelConfig,
                resCoreParameters.audioRecoderFormat);
        audioRecord = new AudioRecord(resCoreParameters.audioRecoderSource,
                resCoreParameters.audioRecoderSampleRate,
                resCoreParameters.audioRecoderChannelConfig,
                resCoreParameters.audioRecoderFormat,
                minBufferSize * 5);
        audioBuffer = new byte[resCoreParameters.audioRecoderBufferSize];
        if (AudioRecord.STATE_INITIALIZED != audioRecord.getState()) {
            Log.e(TAG,"audioRecord.getState()!=AudioRecord.STATE_INITIALIZED!");
            return false;
        }
        if (AudioRecord.SUCCESS != audioRecord.setPositionNotificationPeriod(resCoreParameters.audioRecoderSliceSize)) {
            Log.e(TAG,"AudioRecord.SUCCESS != audioRecord.setPositionNotificationPeriod(" + resCoreParameters.audioRecoderSliceSize + ")");
            return false;
        }
        return true;
    }

    class AudioRecordThread extends Thread {
        private boolean isRunning = true;

        AudioRecordThread() {
            isRunning = true;
        }

        public void quit() {
            isRunning = false;
        }

        @Override
        public void run() {
            if(Config.DEBUG) {
                Log.d(TAG, "tid=" + Thread.currentThread().getId());
            }
            while (isRunning) {
                int size = audioRecord.read(audioBuffer, 0, audioBuffer.length);
                if (isRunning && softAudioCore != null && size > 0) {
                    softAudioCore.queueAudio(audioBuffer);
                }
            }
        }
    }
}
