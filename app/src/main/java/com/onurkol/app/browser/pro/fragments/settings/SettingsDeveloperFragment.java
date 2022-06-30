package com.onurkol.app.browser.pro.fragments.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;

public class SettingsDeveloperFragment extends PreferenceFragmentCompat implements BrowserDataInterface {

    SwitchPreference developerModePreference;
    CheckBoxPreference developerVSLineNumbersPreference,
            developerVSLineWrapPreference,
            developerGNRefreshPreference;
    boolean developerMode;
    boolean developerVSLineNumbers,
            developerVSLineWrap,
            developerGNRefresh;

    PreferenceController preferenceController;

    public static boolean isCreated;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_developer,rootKey);

        preferenceController=PreferenceController.getController();

        developerModePreference=findPreference("developerModePreference");
        developerVSLineNumbersPreference=findPreference("developerVSLineNumbersPreference");
        developerVSLineWrapPreference=findPreference("developerVSLineWrapPreference");
        developerGNRefreshPreference=findPreference("developerGNRefreshPreference");

        developerMode=preferenceController.getBoolean(KEY_DEVELOPER_MODE);
        developerVSLineNumbers=preferenceController.getBoolean(KEY_DEVELOPER_VS_LINE_NUMBERS);
        developerVSLineWrap=preferenceController.getBoolean(KEY_DEVELOPER_VS_LINE_WRAP);
        developerGNRefresh=preferenceController.getBoolean(KEY_DEVELOPER_GN_REFRESH);

        developerModePreference.setOnPreferenceClickListener(preference -> {
            developerMode=!developerMode;
            preferenceController.setPreference(KEY_DEVELOPER_MODE, developerMode);
            updateView();
            return false;
        });
        developerVSLineNumbersPreference.setOnPreferenceClickListener(preference -> {
            developerVSLineNumbers=!developerVSLineNumbers;
            preferenceController.setPreference(KEY_DEVELOPER_VS_LINE_NUMBERS, developerVSLineNumbers);
            updateView();
            return false;
        });
        developerVSLineWrapPreference.setOnPreferenceClickListener(preference -> {
            developerVSLineWrap=!developerVSLineWrap;
            preferenceController.setPreference(KEY_DEVELOPER_VS_LINE_WRAP, developerVSLineWrap);
            updateView();
            return false;
        });
        developerGNRefreshPreference.setOnPreferenceClickListener(preference -> {
            developerGNRefresh=!developerGNRefresh;
            preferenceController.setPreference(KEY_DEVELOPER_GN_REFRESH, developerGNRefresh);
            updateView();
            return false;
        });

        updateView();
        isCreated=true;
    }

    private void updateView(){
        developerModePreference.setChecked(developerMode);
        // Check Developer Mode is Enabled
        developerVSLineNumbersPreference.setEnabled(developerMode);
        developerVSLineWrapPreference.setEnabled(developerMode);
        developerGNRefreshPreference.setEnabled(developerMode);
        // Set Values
        developerVSLineNumbersPreference.setChecked(developerVSLineNumbers);
        developerVSLineWrapPreference.setChecked(developerVSLineWrap);
        developerGNRefreshPreference.setChecked(developerGNRefresh);
    }
}
