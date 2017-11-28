package com.deeplearning.app.job;

import android.view.accessibility.AccessibilityEvent;

import com.deeplearning.app.notification.IStatusBarNotification;
import com.deeplearning.app.service.QiangHongBaoService;

/**
 * Created by qq1588518 on 17/12/01.
 */
public interface AccessbilityJob {
    String getTargetPackageName();
    void onCreateJob(QiangHongBaoService service);
    void onReceiveJob(AccessibilityEvent event);
    void onStopJob();
    void onNotificationPosted(IStatusBarNotification service);
    boolean isEnable();
}