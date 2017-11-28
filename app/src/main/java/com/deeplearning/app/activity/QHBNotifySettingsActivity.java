package com.deeplearning.app.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;

import com.deeplearning.app.config.Config;
import com.deeplearning.app.view.BaseSettingsFragment;
import com.deeplearning.app.DLApplication;
import com.deeplearning_app.R;
/**
 * Created by qq1588518 on 17/12/01.
 */
public class QHBNotifySettingsActivity extends QHBSettingsActivity {
    private static final String TAG = "NotifySettingsFragment";
    @Override
    public Fragment getSettingsFragment() {
        return new NotifySettingsFragment();
    }

    public static class NotifySettingsFragment extends BaseSettingsFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(Config.DEBUG) {
                Log.i(TAG, "onCreate");
            }
            addPreferencesFromResource(R.xml.notify_settings);

            findPreference(Config.KEY_NOTIFY_SOUND).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    DLApplication.eventStatistics(getActivity(), "notify_sound", String.valueOf(newValue));
                    return true;
                }
            });

            findPreference(Config.KEY_NOTIFY_VIBRATE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    DLApplication.eventStatistics(getActivity(), "notify_vibrate", String.valueOf(newValue));
                    return true;
                }
            });

            findPreference(Config.KEY_NOTIFY_NIGHT_ENABLE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    DLApplication.eventStatistics(getActivity(), "notify_night", String.valueOf(newValue));
                    return true;
                }
            });
        }
    }
}
