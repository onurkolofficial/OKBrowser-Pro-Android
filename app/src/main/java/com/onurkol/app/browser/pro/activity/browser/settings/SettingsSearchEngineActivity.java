package com.onurkol.app.browser.pro.activity.browser.settings;

import static com.onurkol.app.browser.pro.data.settings.xml.SearchEngineXMLToList.getSearchEngineIconList;
import static com.onurkol.app.browser.pro.data.settings.xml.SearchEngineXMLToList.getSearchEngineNameList;
import static com.onurkol.app.browser.pro.data.settings.xml.SearchEngineXMLToList.getSearchEngineValueList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
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

public class SettingsSearchEngineActivity extends AppCompatActivity implements BrowserDataInterface {
    BrowserDataInitController browserDataController;
    PreferenceController preferenceController;
    DayNightModeController dayNightController;
    LanguageController languageController;

    public static boolean isCreated;

    ImageButton backButton;
    TextView settingName;
    ListView settingsSearchEngineList;

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
        setContentView(R.layout.activity_settings_search_engine);

        backButton=findViewById(R.id.settingsBackButton);
        settingName=findViewById(R.id.settingsTitle);
        settingsSearchEngineList=findViewById(R.id.settingsSearchEngineList);

        settingName.setText(getString(R.string.search_engine_text));

        backButton.setOnClickListener(view -> ActivityActionAnimator.finish(this));

        // Set ListView Adapter
        settingsSearchEngineList.setAdapter(new SettingsPreferenceListWithIconAdapter(this, DATA_LIST, true));

        // Get Data
        ArrayList<String> xmlStringValue=getSearchEngineNameList(this);
        ArrayList<Integer> xmlIntegerValue=getSearchEngineValueList(this);
        TypedArray xmlDataIcons=getSearchEngineIconList(this);

        // Add Data
        for(int i=0; i<xmlStringValue.size(); i++)
            DATA_LIST.add(new SettingXMLDataWithIcon(xmlStringValue.get(i), xmlIntegerValue.get(i), xmlDataIcons.getDrawable(i), KEY_SEARCH_ENGINE));

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