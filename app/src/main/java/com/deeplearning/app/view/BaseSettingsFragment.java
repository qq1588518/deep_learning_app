package com.deeplearning.app.view;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.deeplearning.app.config.Config;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class BaseSettingsFragment extends PreferenceFragment {
    private static final String TAG = "BaseSettingsFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(Config.PREFERENCE_NAME);
    }
}
