package com.deeplearning.app.service;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by qq1588518 on 17/12/01.
 * 自我防御
 */
public class SelfDefenseAccessibilityService extends BaseAccessibilityService {

    private String mDefenseName = "微信";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        super.onAccessibilityEvent(event);
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getPackageName().equals("com.android.settings")) {
            CharSequence className = event.getClassName();
            if (className.equals("com.android.settings.SubSettings")) {
                AccessibilityNodeInfo nodeInfo = findViewByText("应用程序信息");
                if (nodeInfo != null && findViewByText(mDefenseName) != null) {
                    performBackClick();
                }
            }
        }
    }
}
