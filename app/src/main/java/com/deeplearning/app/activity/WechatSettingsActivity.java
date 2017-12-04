package com.deeplearning.app.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.util.Log;
import android.preference.PreferenceFragment;
import com.deeplearning.app.widget.CustomSwitchPreference;
import com.deeplearning.app.config.Config;
import com.deeplearning.app.DLApplication;
import com.deeplearning_app.R;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class WechatSettingsActivity extends SettingsActivity {
    private static final String TAG = "WechatSettingsActivity";
    @Override
    public Fragment getSettingsFragment() {
        return new WechatSettingsFragment();
    }

    public static class WechatSettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if(Config.DEBUG) {
                Log.i(TAG, "onCreate");
            }
            getPreferenceManager().setSharedPreferencesName(Config.PREFERENCE_NAME);
            addPreferencesFromResource(R.xml.wechat_settings);
            //微信红包通知声音
            final CustomSwitchPreference notifySound = (CustomSwitchPreference) findPreference(Config.KEY_NOTIFY_SOUND);
            notifySound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    DLApplication.eventStatistics(getActivity(), "notify_sound", String.valueOf(newValue));
                    return true;
                }
            });
            //微信红包震动模式
            final CustomSwitchPreference notifyVibrate = (CustomSwitchPreference) findPreference(Config.KEY_NOTIFY_VIBRATE);
            notifyVibrate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    DLApplication.eventStatistics(getActivity(), "notify_vibrate", String.valueOf(newValue));
                    return true;
                }
            });
            //微信红包夜间模式
            final CustomSwitchPreference notifyNightEnable = (CustomSwitchPreference) findPreference(Config.KEY_NOTIFY_NIGHT_ENABLE);
            notifyNightEnable.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    DLApplication.eventStatistics(getActivity(), "notify_night", String.valueOf(newValue));
                    return true;
                }
            });

            //微信红包模式
            final ListPreference wxMode = (ListPreference) findPreference(Config.KEY_WECHAT_MODE);
            wxMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    int value = Integer.parseInt(String.valueOf(newValue));
                    preference.setSummary(wxMode.getEntries()[value]);
                    DLApplication.eventStatistics(getActivity(), "wx_mode", String.valueOf(newValue));
                    return true;
                }
            });
            wxMode.setSummary(wxMode.getEntries()[Integer.parseInt(wxMode.getValue())]);

            //打开微信红包后
            final ListPreference wxAfterOpenPre = (ListPreference) findPreference(Config.KEY_WECHAT_AFTER_OPEN_HONGBAO);
            wxAfterOpenPre.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    int value = Integer.parseInt(String.valueOf(newValue));
                    preference.setSummary(wxAfterOpenPre.getEntries()[value]);
                    DLApplication.eventStatistics(getActivity(), "wx_after_open", String.valueOf(newValue));
                    return true;
                }
            });
            wxAfterOpenPre.setSummary(wxAfterOpenPre.getEntries()[Integer.parseInt(wxAfterOpenPre.getValue())]);

            //获取微信红包后
            final ListPreference wxAfterGetPre = (ListPreference) findPreference(Config.KEY_WECHAT_AFTER_GET_HONGBAO);
            wxAfterGetPre.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    int value = Integer.parseInt(String.valueOf(newValue));
                    preference.setSummary(wxAfterGetPre.getEntries()[value]);
                    DLApplication.eventStatistics(getActivity(), "wx_after_get", String.valueOf(newValue));
                    return true;
                }
            });
            wxAfterGetPre.setSummary(wxAfterGetPre.getEntries()[Integer.parseInt(wxAfterGetPre.getValue())]);

            final EditTextPreference delayEditTextPre = (EditTextPreference) findPreference(Config.KEY_WECHAT_DELAY_TIME);
            delayEditTextPre.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if("0".equals(String.valueOf(newValue))) {
                        preference.setSummary("");
                    } else {
                        preference.setSummary("已延时" + newValue + "毫秒");
                    }
                    DLApplication.eventStatistics(getActivity(), "wx_delay_time", String.valueOf(newValue));
                    return true;
                }
            });
            String delay = delayEditTextPre.getText();
            if("0".equals(String.valueOf(delay))) {
                delayEditTextPre.setSummary("");
            } else {
                delayEditTextPre.setSummary("已延时" + delay  + "毫秒");
            }
        }
    }
}
