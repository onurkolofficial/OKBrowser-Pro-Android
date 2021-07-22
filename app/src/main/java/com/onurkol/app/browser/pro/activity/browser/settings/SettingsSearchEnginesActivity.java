package com.onurkol.app.browser.pro.activity.browser.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.adapters.settings.DataCheckboxIconAdapter;
import com.onurkol.app.browser.pro.data.BrowserDataManager;
import com.onurkol.app.browser.pro.data.settings.SettingsPreferenceIconDataInteger;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.settings.SearchEngine;

import java.util.ArrayList;

public class SettingsSearchEnginesActivity extends AppCompatActivity {

    // Elements
    ImageButton backButton;
    TextView settingName;
    ListView settingsSearchEngineList;
    // Classes
    BrowserDataManager dataManager;
    AppPreferenceManager prefManager;

    ArrayList<SettingsPreferenceIconDataInteger> SEARCH_ENGINE_DATA_LIST=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Get Classes
        dataManager=new BrowserDataManager();
        prefManager=AppPreferenceManager.getInstance();
        // Create View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_search_engines);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        settingsSearchEngineList=findViewById(R.id.settingsSearchEngineList);

        // Set Toolbar Title
        settingName.setText(getString(R.string.search_engine_text));

        // Button Click Events
        backButton.setOnClickListener(view -> finish());

        // Set Adapter
        settingsSearchEngineList.setAdapter(new DataCheckboxIconAdapter(this, settingsSearchEngineList, SEARCH_ENGINE_DATA_LIST));

        // Get Data
        ArrayList<String> xmlStringValue=SearchEngine.getInstance().getSearchEngineNameList();
        ArrayList<Integer> xmlIntegerValue=SearchEngine.getInstance().getSearchEngineValueList();
        TypedArray xmlDataIcons = getResources().obtainTypedArray(R.array.search_engines_preference_icons);

        // Add Data
        for(int i=0; i<xmlStringValue.size(); i++){
            SEARCH_ENGINE_DATA_LIST.add(new SettingsPreferenceIconDataInteger(xmlStringValue.get(i),xmlIntegerValue.get(i),xmlDataIcons.getDrawable(i), true, BrowserDefaultSettings.KEY_SEARCH_ENGINE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        // Init Browser Data ( Applying View Settings )
        dataManager.initBrowserPreferenceSettings();
        return super.onCreateView(name, context, attrs);
    }
}