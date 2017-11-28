package com.deeplearning.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.deeplearning.app.DLApplication;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLApplication.activityCreateStatistics(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DLApplication.activityResumeStatistics(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DLApplication.activityPauseStatistics(this);
    }
}
