package com.deeplearning.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.deeplearning.app.DLApplication;
import com.deeplearning.app.config.Config;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        DLApplication.activityCreateStatistics(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Config.DEBUG) {
            Log.d(TAG, "onResume");
        }
        DLApplication.activityResumeStatistics(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Config.DEBUG) {
            Log.d(TAG, "onPause");
        }
        DLApplication.activityPauseStatistics(this);
    }
}
