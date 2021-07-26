package com.onurkol.app.browser.pro.activity.browser.developer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.data.BrowserDataManager;
import com.onurkol.app.browser.pro.interfaces.AppPreferenceSettings;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;

public class RequestsDetailActivity extends AppCompatActivity {
    // Elements
    ImageButton backButton;
    TextView settingName,
            requestGetUrl, requestGetType, requestGetHost, requestGetScheme, requestGetQuery, requestGetPaths;
    // Classes
    BrowserDataManager dataManager;
    DeveloperManager devManager;
    // Variables
    public static boolean isCreated=false,isCreatedView=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Get Classes
        dataManager=new BrowserDataManager();
        devManager=DeveloperManager.getManager();
        // Create View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_detail);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        requestGetUrl=findViewById(R.id.requestGetUrl);
        requestGetType=findViewById(R.id.requestGetType);
        requestGetHost=findViewById(R.id.requestGetHost);
        requestGetQuery=findViewById(R.id.requestGetQuery);
        requestGetScheme=findViewById(R.id.requestGetScheme);
        requestGetPaths=findViewById(R.id.requestGetPaths);

        // Check is Created (for Theme bug)
        if(!isCreated) {
            // Set Toolbar Title
            settingName.setText(getString(R.string.request_detail_text));

            // Get Intent Data
            int dataIndex=getIntent().getIntExtra(DeveloperManager.KEY_REQUEST_ITEM_INDEX,AppPreferenceManager.INTEGER_NULL);

            if(dataIndex!=AppPreferenceManager.INTEGER_NULL) {
                // Get Data
                WebResourceRequest request = devManager.getRequestDataList().get(dataIndex);

                // Write Data
                requestGetUrl.setText(request.getUrl().toString());
                requestGetType.setText(request.getMethod());
                requestGetHost.setText(request.getUrl().getHost());
                requestGetScheme.setText(request.getUrl().getScheme());
                requestGetQuery.setText(request.getUrl().getQuery());
                requestGetPaths.setText(request.getUrl().getPath());
            }

            // Button Click Events
            backButton.setOnClickListener(view -> {
                // Close This Activity
                finish();
            });
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