package com.deeplearning.app.util;

import java.io.Serializable;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class FieldData implements Serializable{
    private static final String TAG = "FieldData";

    public String type;
    public String id;

    FieldData(String type, String id){
        this.type = type;
        this.id = id;
    }
}
