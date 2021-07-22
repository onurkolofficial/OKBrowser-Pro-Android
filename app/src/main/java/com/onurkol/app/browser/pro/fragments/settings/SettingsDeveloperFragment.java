package com.onurkol.app.browser.pro.fragments.settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.lib.ContextManager;

public class SettingsDeveloperFragment extends PreferenceFragmentCompat {
    SwitchPreference enableDeveloperPref;
    //Preference name;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_developer, rootKey);

        // Get Classes
        ContextManager contextManager = ContextManager.getManager();

        // Get Preferences
        enableDeveloperPref=findPreference("pref_enable_developer");
    }
}
