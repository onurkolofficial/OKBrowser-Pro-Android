package com.onurkol.app.browser.pro.fragments.settings;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.browser.pro.BuildConfig;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.browser.SettingsActivity;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;

public class SettingsAboutFragment extends PreferenceFragmentCompat {
    Preference preferenceAppPackage,preferenceAppVersion,preferenceAndroidVersion,preferenceDeveloperWebPage;

    public static boolean isCreated;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_about,rootKey);

        preferenceAppPackage=findPreference("preferenceAppPackage");
        preferenceAppVersion=findPreference("preferenceAppVersion");
        preferenceAndroidVersion=findPreference("preferenceAndroidVersion");
        preferenceDeveloperWebPage=findPreference("preferenceDeveloperWebPage");

        // Data
        String appPackage=requireActivity().getString(R.string.app_package_paid_text)+" -"+ BuildConfig.BUILD_TYPE+"/pro";
        String appVersion=BuildConfig.VERSION_NAME+"."+BuildConfig.VERSION_CODE;
        String androidVersion=Build.VERSION.RELEASE+" - API "+Build.VERSION.SDK_INT;
        String developerWebPage="https://onurkolofficial.cf/en";

        preferenceAppPackage.setSummary(appPackage);
        preferenceAppVersion.setSummary(appVersion);
        preferenceAndroidVersion.setSummary(androidVersion);

        preferenceDeveloperWebPage.setOnPreferenceClickListener(preference -> {
            SettingsActivity.isClosedAndStartMain=true;
            // Set Data
            SettingsActivity.isData=Uri.parse(developerWebPage);

            ActivityActionAnimator.finish(requireActivity());
            return false;
        });

        isCreated=true;
    }
}
