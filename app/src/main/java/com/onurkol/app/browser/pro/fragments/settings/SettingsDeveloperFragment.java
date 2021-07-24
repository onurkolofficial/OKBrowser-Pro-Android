package com.onurkol.app.browser.pro.fragments.settings;

import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;

public class SettingsDeveloperFragment extends PreferenceFragmentCompat {
    SwitchPreference enableDeveloperPref;
    CheckBoxPreference enableLineNumberPref,enableLineWrapPref,enableStopRefreshPref;
    boolean developerMode,lineNumbers,lineWrap,stopRefresh;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_developer, rootKey);

        // Get Classes
        ContextManager contextManager=ContextManager.getManager();
        AppPreferenceManager prefManager=AppPreferenceManager.getInstance();

        // Get Preferences
        enableDeveloperPref=findPreference("pref_enable_developer");
        enableLineNumberPref=findPreference("pref_enable_line_number");
        enableLineWrapPref=findPreference("pref_enable_line_wrap");
        enableStopRefreshPref=findPreference("pref_enable_stop_refresh");

        // Get Preference Data
        developerMode=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEVELOPER_MODE);
        lineNumbers=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_NUMBERS);
        lineWrap=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_WRAP);
        stopRefresh=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEV_STOP_REFRESH);

        // Preference Click Events
        // Developer Mode
        enableDeveloperPref.setOnPreferenceClickListener(preference -> {
            // Set Value
            developerMode=(!developerMode);
            // Save Preference
            prefManager.setPreference(BrowserDefaultSettings.KEY_DEVELOPER_MODE, developerMode);
            // Check Enabled
            checkEnabled(developerMode);
            return false;
        });
        // Line Numbers
        enableLineNumberPref.setOnPreferenceClickListener(preference -> {
            // Set Value
            lineNumbers=(!lineNumbers);
            // Save Preference
            prefManager.setPreference(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_NUMBERS, lineNumbers);
            return false;
        });
        // Line Wrap
        enableLineWrapPref.setOnPreferenceClickListener(preference -> {
            // Set Value
            lineWrap=(!lineWrap);
            // Save Preference
            prefManager.setPreference(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_WRAP, lineWrap);
            return false;
        });

        // Stop Refresh
        enableStopRefreshPref.setOnPreferenceClickListener(preference -> {
            // Set Value
            stopRefresh=(!stopRefresh);
            // Save Preference
            prefManager.setPreference(BrowserDefaultSettings.KEY_DEV_STOP_REFRESH, stopRefresh);
            return false;
        });

        // Check Settings
        enableDeveloperPref.setChecked(developerMode);
        enableLineNumberPref.setChecked(lineNumbers);
        enableLineWrapPref.setChecked(lineWrap);
        enableStopRefreshPref.setChecked(stopRefresh);

        // Check Enabled
        checkEnabled(developerMode);
    }

    private void checkEnabled(boolean mode){
        enableLineNumberPref.setEnabled(mode);
        enableLineWrapPref.setEnabled(mode);
        enableStopRefreshPref.setEnabled(mode);
    }
}
