package com.deeplearning.app.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.deeplearning.app.config.Config;
import com.deeplearning.app.notification.IStatusBarNotification;

/**
 * Created by qq1588518 on 17/12/01.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BaseNotificationService extends NotificationListenerService {
    private static final String TAG = "NotificationService";
    private PackageManager mPackageManager;
    private String[] mPackages;
    private static BaseNotificationService mInstance;
    private boolean isConnect = false;

    private Config getConfig() {
        return Config.getConfig(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if(Config.DEBUG) {
            Log.i(TAG, "onCreate");
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            onListenerConnected();
        }
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        if(Config.DEBUG) {
            Log.i(TAG, "onNotificationPosted");
        }
        if(!getConfig().isAgreement()) {
            return;
        }
        if(!getConfig().isEnableNotificationService()) {
            return;
        }
        BaseAccessibilityService.handeNotificationPosted(new IStatusBarNotification() {
            @Override
            public String getPackageName() {
                return sbn.getPackageName();
            }

            @Override
            public Notification getNotification() {
                return sbn.getNotification();
            }
        });
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onNotificationRemoved(sbn);
        }
        if(Config.DEBUG) {
            Log.i(TAG, "onNotificationRemoved");
        }
    }

    @Override
    public void onListenerConnected() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onListenerConnected();
        }
        if(Config.DEBUG) {
            Log.i(TAG, " onListenerConnected");
        }
        isConnect = true;
        //发送广播，已经连接上了
        Intent intent = new Intent(Config.ACTION_NOTIFY_LISTENER_SERVICE_CONNECT);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Config.DEBUG) {
            Log.i(TAG, " onDestroy");
        }
        isConnect = false;
        mInstance = null;
        //发送广播，已经连接上了
        Intent intent = new Intent(Config.ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT);
        sendBroadcast(intent);
    }

    /** 是否启动通知栏监听*/
    public static boolean isEnabled() {
        if(Config.DEBUG) {
            Log.d(TAG, " isEnabled");
        }
        if((mInstance == null)||(mInstance.isConnect == false)){
            return false;
        }
        return true;
    }

}
