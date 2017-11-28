package com.deeplearning.app.service;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by qq1588518 on 17/12/01.
 * 自动安装
 */
public class AutoInstallAccessibilityService extends BaseAccessibilityService {
    private static final String TAG = "AutoInstallAccessibilityService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        super.onAccessibilityEvent(event);
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getPackageName().equals("com.android.packageinstaller")) {
            AccessibilityNodeInfo nodeInfo = findViewByText("安装", true);
            if (nodeInfo != null) {
                performViewClick(nodeInfo);
            }
        }
    }
}
