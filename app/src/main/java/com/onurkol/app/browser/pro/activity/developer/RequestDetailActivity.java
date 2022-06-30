package com.onurkol.app.browser.pro.activity.developer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.installer.InstallerActivity;
import com.onurkol.app.browser.pro.controller.ContextController;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.BrowserDataInitController;
import com.onurkol.app.browser.pro.controller.settings.DayNightModeController;
import com.onurkol.app.browser.pro.controller.settings.LanguageController;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

public class RequestDetailActivity extends AppCompatActivity implements BrowserDataInterface {
    BrowserDataInitController browserDataController;
    PreferenceController preferenceController;
    DayNightModeController dayNightController;
    LanguageController languageController;

    public static boolean isCreated=false;

    DeveloperManager.RequestManager requestManager;

    ImageButton backButton;
    TextView settingName,
            requestGetUrl, requestGetType, requestGetHost, requestGetScheme, requestGetQuery, requestGetPaths;

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
        setContentView(R.layout.activity_request_detail);

        backButton=findViewById(R.id.settingsBackButton);
        settingName=findViewById(R.id.settingsTitle);
        requestGetUrl=findViewById(R.id.requestGetUrl);
        requestGetType=findViewById(R.id.requestGetType);
        requestGetHost=findViewById(R.id.requestGetHost);
        requestGetQuery=findViewById(R.id.requestGetQuery);
        requestGetScheme=findViewById(R.id.requestGetScheme);
        requestGetPaths=findViewById(R.id.requestGetPaths);

        requestManager=new DeveloperManager.RequestManager();

        // Get Data
        int dataPosition=getIntent().getIntExtra(requestManager.KEY_REQUEST_INDEX, PreferenceController.INT_NULL);

        settingName.setText(getString(R.string.request_detail_text));

        if(dataPosition!=PreferenceController.INT_NULL) {
            // Get Data
            WebResourceRequest request = requestManager.getRequestList().get(dataPosition).getRequest();

            // Write Data
            requestGetUrl.setText(request.getUrl().toString());
            requestGetType.setText(request.getMethod());
            requestGetHost.setText(request.getUrl().getHost());
            requestGetScheme.setText(request.getUrl().getScheme());
            requestGetQuery.setText(request.getUrl().getQuery());
            requestGetPaths.setText(request.getUrl().getPath());
        }

        backButton.setOnClickListener(view -> ActivityActionAnimator.finish(this));

        isCreated=true;
    }

    @Override
    protected void onDestroy() {
        isCreated=false;
        super.onDestroy();
    }
}