package com.deeplearning.app.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import java.io.File;

import com.deeplearning.app.config.Config;
import com.deeplearning_app.R;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class AutoInstallActivity extends BaseActivity {
    private static final String TAG = "AutoInstallActivity";
    private PackageManager mPackageManager;
    private String[] mPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        setContentView(R.layout.activity_main1);
        mPackageManager = this.getPackageManager();
        mPackages = new String[]{"com.tencent.mm"};
    }

    /**
     * 前往开启辅助服务界面
     */
    public void goAccess(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "goAccess");
        }
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goApp(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "goApp");
        }
        Intent intent = mPackageManager.getLaunchIntentForPackage("com.tencent.mm");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void cleanProcess(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "cleanProcess");
        }
        for (String mPackage : mPackages) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mPackage, null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    public void autoInstall(View view) {
        if(Config.DEBUG) {
            Log.d(TAG, "autoInstall");
        }
        String apkPath = Environment.getExternalStorageDirectory() + "/deeplearning_app.apk";
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }
}
