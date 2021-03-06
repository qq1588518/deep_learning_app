package com.deeplearning.app.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class Config {

    public static final boolean DEBUG = Boolean.parseBoolean("true");
    public static final String APPLICATION_ID = "com.deeplearning_app";
    public static final String BUILD_TYPE = "debug";
    public static final String FLAVOR = "";
    public static final int VERSION_CODE = 1;
    public static final String VERSION_NAME = "1.0";

    public static final String TEXTVIEW_CLASS_NAME = "android.widget.TextView";
    public static final String IMAGEVIEW_CLASS_NAME = "android.widget.ImageView";
    public static final String BUTTON_CLASS_NAME = "android.widget.Button";

    public static final String WECHAT_PACKAGENAME = "com.tencent.mm";
    public static final String LauncherUI = "com.tencent.mm.ui.LauncherUI";
    public static final String LuckyMoneyReceiveUI = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI";
    public static final String LuckyMoneyDetailUI = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";

    public static final String ACTION_ACCESSBILITY_SERVICE_DISCONNECT = "com.deeplearning_app.ACCESSBILITY_DISCONNECT";
    public static final String ACTION_ACCESSBILITY_SERVICE_CONNECT = "com.deeplearning_app.ACCESSBILITY_CONNECT";

    public static final String ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT = "com.deeplearning_app.NOTIFY_LISTENER_DISCONNECT";
    public static final String ACTION_NOTIFY_LISTENER_SERVICE_CONNECT = "com.deeplearning_app.NOTIFY_LISTENER_CONNECT";

    public static final String KEY_ACCESSIBILITY_SERVICE_ENABLE = "KEY_ACCESSIBILITY_SERVICE_ENABLE";
    public static final String KEY_NOTIFICATION_SERVICE_ENABLE = "KEY_NOTIFICATION_SERVICE_ENABLE";

    public static final String PREFERENCE_NAME = "config";
    public static final String KEY_ENABLE_WECHAT = "KEY_ENABLE_WECHAT";
    public static final String KEY_WECHAT_AFTER_OPEN_LUCKYMONEY = "KEY_WECHAT_AFTER_OPEN_LUCKYMONEY";
    public static final String KEY_WECHAT_DELAY_TIME = "KEY_WECHAT_DELAY_TIME";
    public static final String KEY_WECHAT_AFTER_GET_LUCKYMONEY = "KEY_WECHAT_AFTER_GET_LUCKYMONEY";
    public static final String KEY_WECHAT_MODE = "KEY_WECHAT_MODE";

    public static final String KEY_NOTIFY_SOUND = "KEY_NOTIFY_SOUND";
    public static final String KEY_NOTIFY_VIBRATE = "KEY_NOTIFY_VIBRATE";
    public static final String KEY_NOTIFY_NIGHT_ENABLE = "KEY_NOTIFY_NIGHT_ENABLE";

    private static final String KEY_AGREEMENT = "KEY_AGREEMENT";

    public static final int WX_AFTER_OPEN_LUCKYMONEY = 0;//拆红包
    public static final int WX_AFTER_OPEN_SEE = 1; //看大家手气
    public static final int WX_AFTER_OPEN_NONE = 2; //静静地看着

    public static final int WX_AFTER_GET_GOHOME = 0; //返回桌面
    public static final int WX_AFTER_GET_NONE = 1;

    public static final int WX_MODE_0 = 0;//自动抢
    public static final int WX_MODE_1 = 1;//抢单聊红包,群聊红包只通知
    public static final int WX_MODE_2 = 2;//抢群聊红包,单聊红包只通知
    public static final int WX_MODE_3 = 3;//通知手动抢

    private static Config current;

    public static synchronized Config getConfig(Context context) {
        if (current == null) {
            current = new Config(context.getApplicationContext());
        }
        return current;
    }

    private SharedPreferences preferences;
    private Context mContext;

    private Config(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 是否启动通知栏模式
     */
    public boolean isEnableAccessibilityService() {
        return preferences.getBoolean(KEY_ACCESSIBILITY_SERVICE_ENABLE, false);
    }

    public void setAccessibilityServiceEnable(boolean enable) {
        preferences.edit().putBoolean(KEY_ACCESSIBILITY_SERVICE_ENABLE, enable).apply();
    }

    /**
     * 是否启动通知栏模式
     */
    public boolean isEnableNotificationService() {
        return preferences.getBoolean(KEY_NOTIFICATION_SERVICE_ENABLE, false);
    }

    public void setNotificationServiceEnable(boolean enable) {
        preferences.edit().putBoolean(KEY_NOTIFICATION_SERVICE_ENABLE, enable).apply();
    }

    /**
     * 是否启动微信抢红包
     */
    public boolean isEnableWechat() {
        return preferences.getBoolean(KEY_ENABLE_WECHAT, true);
    }

    /**
     * 是否启动微信抢红包
     */
    public void setEnableWechat(boolean enable) {
        preferences.edit().putBoolean(KEY_ENABLE_WECHAT, enable).apply();
    }

    /**
     * 微信打开红包后的事件
     */
    public int getWechatAfterOpenLuckyMoneyEvent() {
        int defaultValue = 0;
        String result = preferences.getString(KEY_WECHAT_AFTER_OPEN_LUCKYMONEY, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(result);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 微信抢到红包后的事件
     */
    public int getWechatAfterGetLuckyMoneyEvent() {
        int defaultValue = 1;
        String result = preferences.getString(KEY_WECHAT_AFTER_GET_LUCKYMONEY, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(result);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 微信打开红包后延时时间
     */
    public int getWechatOpenDelayTime() {
        int defaultValue = 0;
        String result = preferences.getString(KEY_WECHAT_DELAY_TIME, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(result);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 获取抢微信红包的模式
     */
    public int getWechatMode() {
        int defaultValue = 0;
        String result = preferences.getString(KEY_WECHAT_MODE, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(result);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 是否开启声音
     */
    public boolean isNotifySound() {
        return preferences.getBoolean(KEY_NOTIFY_SOUND, true);
    }

    /**
     * 是否开启震动
     */
    public boolean isNotifyVibrate() {
        return preferences.getBoolean(KEY_NOTIFY_VIBRATE, true);
    }

    /**
     * 是否开启夜间免打扰模式
     */
    public boolean isNotifyNight() {
        return preferences.getBoolean(KEY_NOTIFY_NIGHT_ENABLE, false);
    }

    /**
     * 免费声明
     */
    public boolean isAgreement() {
        return preferences.getBoolean(KEY_AGREEMENT, false);
    }

    /**
     * 设置是否同意
     */
    public void setAgreement(boolean agreement) {
        preferences.edit().putBoolean(KEY_AGREEMENT, agreement).apply();
    }

}
