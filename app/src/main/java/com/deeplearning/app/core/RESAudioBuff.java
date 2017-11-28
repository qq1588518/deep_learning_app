package com.deeplearning.app.core;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class RESAudioBuff {
    public boolean isReadyToFill;
    public int audioFormat = -1;
    public byte[] buff;

    public RESAudioBuff(int audioFormat, int size) {
        isReadyToFill = true;
        this.audioFormat = audioFormat;
        buff = new byte[size];
    }
}
