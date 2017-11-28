package com.deeplearning.app.service;

import android.util.Log;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

import com.deeplearning.app.util.PrintUtils;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class AutoInstallService extends BaseAccessibilityService {
    private static final String TAG = "AutoInstallService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        PrintUtils.printEvent(event);
        findAndPerformActionButton("继续");
        findAndPerformActionTextView("下一步");
        findAndPerformActionTextView("安装");
    }


    private void findAndPerformActionButton(String text) {
        if (getRootInActiveWindow() == null)//取得当前激活窗体的根节点
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    private void findAndPerformActionTextView(String text) {
        if (getRootInActiveWindow() == null)
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            if (node.getClassName().equals("android.widget.TextView") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i(TAG,"onServiceConnected");
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG,"onInterrupt");
    }


}
