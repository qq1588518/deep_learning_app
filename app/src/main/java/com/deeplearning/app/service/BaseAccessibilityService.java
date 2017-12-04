package com.deeplearning.app.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.deeplearning.app.config.Config;
import com.deeplearning.app.job.AccessbilityJob;
import com.deeplearning.app.job.WechatAccessbilityJob;
import com.deeplearning.app.notification.IStatusBarNotification;
import com.deeplearning.app.util.PrintUtils;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class BaseAccessibilityService extends AccessibilityService {
    private static final String TAG = "AccessibilityService";
    private static BaseAccessibilityService mInstance;
    private List<AccessbilityJob> mAccessbilityJobs;
    private HashMap<String, AccessbilityJob> mPkgAccessbilityJobMap;
    private boolean isConnect = false;

    private static final Class[] ACCESSBILITY_JOBS= {
            WechatAccessbilityJob.class,
    };

    public static BaseAccessibilityService getInstance() {
        if (mInstance == null) {
            mInstance = new BaseAccessibilityService();
        }
        return mInstance;
    }

    public Config getConfig() {
        return Config.getConfig(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Config.DEBUG) {
            Log.d(TAG, " onCreate");
        }
        mInstance = this;
        mAccessbilityJobs = new ArrayList<>();
        mPkgAccessbilityJobMap = new HashMap<>();

        //初始化辅助插件工作
        for(Class clazz : ACCESSBILITY_JOBS) {
            try {
                Object object = clazz.newInstance();
                if(object instanceof AccessbilityJob) {
                    AccessbilityJob job = (AccessbilityJob) object;
                    job.onCreateJob(mInstance);
                    mAccessbilityJobs.add(job);
                    mPkgAccessbilityJobMap.put(job.getTargetPackageName(), job);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Config.DEBUG) {
            Log.d(TAG, " onDestroy");
        }
        if(mPkgAccessbilityJobMap != null) {
            mPkgAccessbilityJobMap.clear();
        }
        if(mAccessbilityJobs != null && !mAccessbilityJobs.isEmpty()) {
            for (AccessbilityJob job : mAccessbilityJobs) {
                job.onStopJob();
            }
            mAccessbilityJobs.clear();
        }

        isConnect = false;
        mAccessbilityJobs = null;
        mPkgAccessbilityJobMap = null;
        mInstance = null;
        //发送广播，已经断开辅助服务
        Intent intent = new Intent(com.deeplearning.app.config.Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT);
        sendBroadcast(intent);
    }

    @Override
    public void onInterrupt() {
        if(Config.DEBUG) {
            Log.d(TAG, " onInterrupt");
        }
        Toast.makeText(this, "中断抢红包服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        if(Config.DEBUG) {
            Log.d(TAG, " onServiceConnected");
        }
        isConnect = true;
        //发送广播，已经连接上了
        Intent intent = new Intent(com.deeplearning.app.config.Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT);
        sendBroadcast(intent);
        Toast.makeText(this, "已连接抢红包服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(Config.DEBUG) {
            Log.d(TAG, " onAccessibilityEvent 事件--->" + event );
        }
        String pkn = String.valueOf(event.getPackageName());
        if(mAccessbilityJobs != null && !mAccessbilityJobs.isEmpty()) {
            if(!getConfig().isAgreement()) {
                return;
            }
            for (AccessbilityJob job : mAccessbilityJobs) {
                if(pkn.equals(job.getTargetPackageName()) && job.isEnable()) {
                    job.onReceiveJob(event);
                }
            }
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getPackageName().equals("com.android.packageinstaller")) {
            AccessibilityNodeInfo nodeInfo = findViewByText("安装", true);
            if (nodeInfo != null) {
                performViewClick(nodeInfo);
            }
            PrintUtils.printEvent(event);
            findAndPerformActionButton("继续");
            findAndPerformActionTextView("下一步");
            findAndPerformActionTextView("安装");
        }

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


    /** 接收通知栏事件*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void handeNotificationPosted(IStatusBarNotification notificationService) {
        if(notificationService == null) {
            return;
        }
        if(mInstance == null || mInstance.mPkgAccessbilityJobMap == null) {
            return;
        }
        if(Config.DEBUG) {
            Log.d(TAG, " handeNotificationPosted 事件--->" + notificationService.getPackageName() );
        }
        String pack = notificationService.getPackageName();
        AccessbilityJob job = mInstance.mPkgAccessbilityJobMap.get(pack);
        if(job == null) {
            return;
        }
        job.onNotificationPosted(notificationService);
    }

    /**
     * 判断当前服务是否启用
     * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isEnabled() {
        if(Config.DEBUG) {
            Log.d(TAG, " isEnabled");
        }
        if((mInstance == null)||(mInstance.isConnect == false)){
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) mInstance.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityServiceInfo info = mInstance.getServiceInfo();
        if(info == null) {
            return false;
        }
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        Iterator<AccessibilityServiceInfo> iterator = list.iterator();

        boolean connect = false;
        while (iterator.hasNext()) {
            AccessibilityServiceInfo i = iterator.next();
            if(i.getId().equals(info.getId())) {
                connect = true;
                break;
            }
        }
        if(!connect) {
            return false;
        }
        return true;
    }

    /** 快速读取通知栏服务是否启动*/
    public static boolean isNotificationServiceRunning() {
        if(Config.DEBUG) {
            Log.d(TAG, " isNotificationServiceRunning");
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        //部份手机没有NotificationService服务
        try {
            return BaseNotificationService.isEnabled();
        } catch (Throwable t) {}
        return false;
    }
    /**
     * 模拟点击事件
     *
     * @param nodeInfo nodeInfo
     */
    public void performViewClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
    }

    /**
     * 模拟返回操作
     */
    public void performBackClick() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    /**
     * 模拟下滑操作
     */
    public void performScrollBackward() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }

    /**
     * 模拟上滑操作
     */
    public void performScrollForward() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    /**
     * 查找对应文本的View
     *
     * @param text text
     * @return View
     */
    public AccessibilityNodeInfo findViewByText(String text) {
        return findViewByText(text, false);
    }

    /**
     * 查找对应文本的View
     *
     * @param text      text
     * @param clickable 该View是否可以点击
     * @return View
     */
    public AccessibilityNodeInfo findViewByText(String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    /**
     * 查找对应ID的View
     *
     * @param id id
     * @return View
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public AccessibilityNodeInfo findViewByID(String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    public void clickTextViewByText(String text) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void clickTextViewByID(String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
    }

    /**
     * 模拟输入
     *
     * @param nodeInfo nodeInfo
     * @param text     text
     */
    public void inputText(AccessibilityNodeInfo nodeInfo, String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", text);
            clipboard.setPrimaryClip(clip);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
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

}
