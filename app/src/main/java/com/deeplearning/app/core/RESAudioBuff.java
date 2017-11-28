package com.deeplearning.app.core;

import android.util.Log;

import com.deeplearning.app.config.Config;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class RESAudioBuff {
    private static final String TAG = "RESAudioBuff";
    public boolean isReadyToFill;
    public int audioFormat = -1;
    public byte[] buff;

    public RESAudioBuff(int audioFormat, int size) {
        if(Config.DEBUG) {
            Log.i(TAG, "RESAudioBuff");
        }
        isReadyToFill = true;
        this.audioFormat = audioFormat;
        buff = new byte[size];
    }
}