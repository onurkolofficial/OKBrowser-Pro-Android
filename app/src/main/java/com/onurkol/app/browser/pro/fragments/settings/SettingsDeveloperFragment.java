package com.onurkol.app.browser.pro.fragments.settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;

public class SettingsDeveloperFragment extends PreferenceFragmentCompat {
    SwitchPreference enableDeveloperPref;
    //Preference name;
    boolean developerMode;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_developer, rootKey);

        // Get Classes
        ContextManager contextManager=ContextManager.getManager();
        AppPreferenceManager prefManager=AppPreferenceManager.getInstance();

        // Get Preferences
        enableDeveloperPref=findPreference("pref_enable_developer");

        // Get Preference Data
        developerMode=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEVELOPER_MODE);

        // Preference Click Events
        // Developer Mode
        enableDeveloperPref.setOnPreferenceClickListener(preference -> {
            // Set Value
            developerMode=(!developerMode);
            // Save Preference
            prefManager.setPreference(BrowserDefaultSettings.KEY_DEVELOPER_MODE, developerMode);
            return false;
        });

        // Check Settings
        enableDeveloperPref.setChecked(developerMode);
    }
}
