package com.deeplearning.app.fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.deeplearning.app.DLApplication;
import com.deeplearning.app.activity.MainActivity;
import com.deeplearning.app.activity.WechatNotifySettingsActivity;
import com.deeplearning.app.activity.WechatRedenvelopeActivity;
import com.deeplearning.app.activity.WechatSettingsActivity;
import com.deeplearning.app.config.Config;
import com.deeplearning.app.service.BaseAccessibilityService;
import com.deeplearning_app.R;


/**
 * Created by jpeng on 16-11-14.
 */
public class Tab1PagerFragment extends Fragment {
    private static final String TAG = "Tab1PagerFragment";
    //private SwitchPreference notificationPref;
    private boolean notificationChangeByUser = true;

    private Switch mSwitchAssistantSetting;
    private Switch mSwitchNotifySetting;
    private Switch mSwitchWechatSetting;
    private Switch mSwitchWechatNotifySetting;
    private Dialog mTipsDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tab1, container, false);
        //return inflater.inflate(R.layout.tab1,null);
        mSwitchAssistantSetting = (Switch) rootView.findViewById(R.id.ID_ASSISTANT_SETTINGS);
        // 添加监听
        mSwitchAssistantSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(),"开启",Toast.LENGTH_SHORT).show();
                    if(!BaseAccessibilityService.isEnabled()) {
                        showOpenAccessibilityServiceDialog();
                    }
                }else {
                    Toast.makeText(getActivity(),"关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSwitchNotifySetting = (Switch) rootView.findViewById(R.id.ID_NOTIFY_SETTINGS);
        mSwitchNotifySetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!notificationChangeByUser) {
                    notificationChangeByUser = true;
                    return;
                }
                Config.getConfig(getActivity()).setNotificationServiceEnable(isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(),"开启",Toast.LENGTH_SHORT).show();
                    if(!BaseAccessibilityService.isNotificationServiceRunning()) {
                        openNotificationServiceSettings();
                    }
                }else {
                    Toast.makeText(getActivity(),"关闭",Toast.LENGTH_SHORT).show();
                }
                DLApplication.eventStatistics(getActivity(), "notify_service", String.valueOf(isChecked));
            }
        });


        mSwitchWechatSetting = (Switch) rootView.findViewById(R.id.ID_WECHAT_SETTINGS);
        mSwitchWechatSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(),"开启",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), WechatSettingsActivity.class));
                }else {
                    Toast.makeText(getActivity(),"关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSwitchWechatNotifySetting = (Switch) rootView.findViewById(R.id.ID_WECHAT_NOTIFY_SETTINGS);
        mSwitchWechatNotifySetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(),"开启",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), WechatNotifySettingsActivity.class));
                }else {
                    Toast.makeText(getActivity(),"关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Config.DEBUG) {
            Log.i(TAG, "onResume");
        }
        if(BaseAccessibilityService.isEnabled()) {
            dismiss();
        } else {
            showOpenAccessibilityServiceDialog();
        }

        boolean isAgreement = Config.getConfig(getActivity()).isAgreement();
        if(!isAgreement) {
            showAgreementDialog();
        }
        updateNotifyPreference();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Config.DEBUG) {
            Log.i(TAG, "onPause");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Config.DEBUG) {
            Log.i(TAG, "onDestroy");
        }
        mTipsDialog = null;
    }

    public void dismiss() {
        if(mTipsDialog != null) {
            mTipsDialog.dismiss();
        }
    }

    /** 显示未开启辅助服务的对话框*/
    public void showOpenAccessibilityServiceDialog() {
        if(Config.DEBUG) {
            Log.i(TAG, "showOpenAccessibilityServiceDialog");
        }
        if(mTipsDialog != null && mTipsDialog.isShowing()) {
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.dialog_tips_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccessibilityServiceSettings();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.open_service_title);
        builder.setView(view);
        builder.setPositiveButton(R.string.open_service_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAccessibilityServiceSettings();
            }
        });
        mTipsDialog = builder.show();
    }

    /** 前往开启辅助服务的设置界面*/
    public void openAccessibilityServiceSettings() {
        if(Config.DEBUG) {
            Log.i(TAG, "openAccessibilityServiceSettings");
        }
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(getActivity(), R.string.tips, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 打开通知栏设置*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public void openNotificationServiceSettings() {
        if(Config.DEBUG) {
            Log.i(TAG, "openNotificationServiceSettings");
        }
        try {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
            Toast.makeText(getActivity(), R.string.tips, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 更新快速读取通知的设置*/
    public void updateNotifyPreference() {
        if(mSwitchNotifySetting == null) {
            return;
        }
        boolean running = BaseAccessibilityService.isNotificationServiceRunning();
        boolean enable = Config.getConfig(getActivity()).isEnableNotificationService();
        if( enable && running && !mSwitchNotifySetting.isChecked()) {
            DLApplication.eventStatistics(getActivity(), "notify_service", String.valueOf(true));
            notificationChangeByUser = false;
            mSwitchNotifySetting.setChecked(true);
        } else if((!enable || !running) && mSwitchNotifySetting.isChecked()) {
            notificationChangeByUser = false;
            mSwitchNotifySetting.setChecked(false);
        }
    }

    /** 显示免责声明的对话框*/
    public void showAgreementDialog() {
        if(Config.DEBUG) {
            Log.i(TAG, "showAgreementDialog");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(R.string.agreement_title);
        builder.setMessage(getString(R.string.agreement_message, getString(R.string.app_name)));
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Config.getConfig(getContext()).setAgreement(true);
                DLApplication.eventStatistics(getActivity(), "agreement", "true");
            }
        });
        builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Config.getConfig(getContext()).setAgreement(false);
                DLApplication.eventStatistics(getActivity(), "agreement", "false");
                //finish();
            }
        });
        builder.show();
    }
}
