package com.deeplearning.app.service;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class CleanBackgroundProcessAccessibilityService extends BaseAccessibilityService {
    private static final String TAG = "CleanBackgroundProcessAccessibilityService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getPackageName().equals("com.android.settings")) {
            CharSequence className = event.getClassName();
            if (className.equals("com.android.settings.applications.InstalledAppDetailsTop")) {
                AccessibilityNodeInfo info = findViewByText("强行停止");
                if (info.isEnabled()) {
                    performViewClick(info);
                } else {
                    performBackClick();
                }
            }
            if (className.equals("android.app.AlertDialog")) {
                clickTextViewByText("确定");
                performBackClick();
            }
        }
    }
}
