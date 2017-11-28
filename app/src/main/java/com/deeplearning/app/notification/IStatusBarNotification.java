package com.deeplearning.app.notification;

import android.app.Notification;

/**
 * Created by qq1588518 on 17/12/01.
 */
public interface IStatusBarNotification {

    String getPackageName();
    Notification getNotification();
}
