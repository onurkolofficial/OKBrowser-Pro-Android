package com.onurkol.app.browser.pro.activity.browser.settings;

import static com.onurkol.app.browser.pro.data.settings.xml.DayNightXMLToList.getDayNightIconList;
import static com.onurkol.app.browser.pro.data.settings.xml.DayNightXMLToList.getDayNightNameList;
import static com.onurkol.app.browser.pro.data.settings.xml.DayNightXMLToList.getDayNightValueList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.installer.InstallerActivity;
import com.onurkol.app.browser.pro.adapters.settings.SettingsPreferenceListWithIconAdapter;
import com.onurkol.app.browser.pro.controller.ContextController;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.BrowserDataInitController;
import com.onurkol.app.browser.pro.controller.settings.DayNightModeController;
import com.onurkol.app.browser.pro.controller.settings.LanguageController;
import com.onurkol.app.browser.pro.data.settings.SettingXMLDataWithIcon;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;

import java.util.ArrayList;

public class SettingsDayNightActivity extends AppCompatActivity implements BrowserDataInterface {
    BrowserDataInitController browserDataController;
    PreferenceController preferenceController;
    DayNightModeController dayNightController;
    LanguageController languageController;

    public static boolean isCreated;

    ImageButton backButton;
    TextView settingName;
    ListView settingsThemeList;

    ArrayList<SettingXMLDataWithIcon> DATA_LIST=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ContextController.setContext(this);

        preferenceController=PreferenceController.getController();
        browserDataController=BrowserDataInitController.getController();
        browserDataController.init();

        dayNightController=DayNightModeController.getController();
        languageController=LanguageController.getController();

        if(!browserDataController.isInstallerCompleted()){
            startActivity(new Intent(this, InstallerActivity.class));
            finish();
        }

        // Set Theme|Language
        dayNightController.setDayNightMode(this, preferenceController.getInt(KEY_DAY_NIGHT_MODE));
        languageController.setLanguage(this, preferenceController.getInt(KEY_LANGUAGE));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_day_night);

        backButton=findViewById(R.id.settingsBackButton);
        settingName=findViewById(R.id.settingsTitle);
        settingsThemeList=findViewById(R.id.settingsThemeList);

        settingName.setText(getString(R.string.day_night_mode_text));

        backButton.setOnClickListener(view -> ActivityActionAnimator.finish(this));

        // Set ListView Adapter
        settingsThemeList.setAdapter(new SettingsPreferenceListWithIconAdapter(this, DATA_LIST, true));

        // Get Data
        ArrayList<String> xmlStringValue=getDayNightNameList(this);
        ArrayList<Integer> xmlIntegerValue=getDayNightValueList(this);
        TypedArray xmlDataIcons=getDayNightIconList(this);

        // Add Data
        for(int i=0; i<xmlStringValue.size(); i++) {
            // API 29 and and oldest versions not supported System Theme.
            // Not add this option for API 29 and oldest.
            if (xmlIntegerValue.get(i)==2){
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
                    DATA_LIST.add(new SettingXMLDataWithIcon(xmlStringValue.get(i), xmlIntegerValue.get(i), xmlDataIcons.getDrawable(i), KEY_DAY_NIGHT_MODE));
            }
            else
                DATA_LIST.add(new SettingXMLDataWithIcon(xmlStringValue.get(i), xmlIntegerValue.get(i), xmlDataIcons.getDrawable(i), KEY_DAY_NIGHT_MODE));
        }

        isCreated=true;
    }

    @Override
    public void onBackPressed() {
        ActivityActionAnimator.finish(this);
    }

    @Override
    protected void onDestroy() {
        isCreated=false;
        super.onDestroy();
    }
}