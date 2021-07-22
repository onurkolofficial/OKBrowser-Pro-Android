package com.onurkol.app.browser.pro.activity.browser.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.browser.installer.InstallerActivity;
import com.onurkol.app.browser.pro.adapters.browser.DownloadListAdapter;
import com.onurkol.app.browser.pro.data.BrowserDataManager;
import com.onurkol.app.browser.pro.interfaces.browser.downloads.DownloadsSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.downloads.DownloadsManager;

public class DownloadsActivity extends AppCompatActivity implements DownloadsSettings {

    // Elements
    ImageButton backButton;
    TextView settingName;
    ListView downloadsListView;
    LinearLayout noDownloadLayout;
    // Classes
    BrowserDataManager dataManager;
    AppPreferenceManager prefManager;
    // Intents
    Intent installerIntent;
    // Variables
    public static boolean isCreated=false,isCreatedView=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Get Classes
        dataManager=new BrowserDataManager();
        prefManager=AppPreferenceManager.getInstance();
        // Create View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);
        // Get Intents
        installerIntent=new Intent(this, InstallerActivity.class);

        // Check Installer Activity
        if(dataManager.startInstallerActivity){
            // Start Welcome Activity
            startActivity(installerIntent);
            // Finish Current Activity
            finish();
        }
        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        downloadsListView=findViewById(R.id.downloadsListView);
        noDownloadLayout=findViewById(R.id.noDownloadLayout);

        // Set Toolbar Title
        settingName.setText(getString(R.string.downloads_text));

        // Button Click Events
        backButton.setOnClickListener(view -> finish());

        // Check is Created (for Theme bug)
        if(!isCreated) {
            // Set Adapter
            downloadsListView.setAdapter(new DownloadListAdapter(this, downloadsListView, BROWSER_DOWNLOAD_LIST));

            // Get Saved Data
            DownloadsManager.getInstance().syncSavedDownloadsData();

            if(BROWSER_DOWNLOAD_LIST.size()<=0){
                // Show No Downloads Layout
                noDownloadLayout.setVisibility(View.VISIBLE);
                downloadsListView.setVisibility(View.GONE);
            }
            else{
                // Hide No Downloads Layout
                noDownloadLayout.setVisibility(View.GONE);
                downloadsListView.setVisibility(View.VISIBLE);
            }
        }
        isCreated = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        // Init Browser Data ( Applying View Settings )
        if(!dataManager.startInstallerActivity){
            dataManager.initBrowserPreferenceSettings();
            /*
            if(!isCreatedView) {
                //...
            }
             */
            isCreatedView=true;
        }
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCreated=false;
    }
}