package com.deeplearning.app.util;

import java.io.Serializable;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class FieldData implements Serializable{

    public String type;
    public String id;

    FieldData(String type, String id){
        this.type = type;
        this.id = id;
    }
}
