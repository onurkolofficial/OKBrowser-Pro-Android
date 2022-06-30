package com.onurkol.app.browser.pro.activity.developer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.installer.InstallerActivity;
import com.onurkol.app.browser.pro.controller.ContextController;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.BrowserDataInitController;
import com.onurkol.app.browser.pro.controller.browser.DownloadController;
import com.onurkol.app.browser.pro.controller.settings.DayNightModeController;
import com.onurkol.app.browser.pro.controller.settings.LanguageController;
import com.onurkol.app.browser.pro.data.developer.ResourceData;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;
import com.onurkol.app.browser.pro.libs.ScreenManager;
import com.onurkol.app.browser.pro.libs.SupportedFileExtension;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ResourceDetailActivity extends AppCompatActivity implements BrowserDataInterface {
    BrowserDataInitController browserDataController;
    PreferenceController preferenceController;
    DayNightModeController dayNightController;
    LanguageController languageController;

    public static boolean isCreated=false;

    DeveloperManager.ResourceManager resourceManager;

    DownloadController downloadController;
    DownloadManager downloadManager;

    static URL urlData=null;
    static Bitmap imageBitmapData = null;
    static String inputData="";

    ImageButton backButton,
            sourceDownloadButton;
    TextView settingName,
            resourceUrlText;
    ImageView sourceImageView;
    EditText sourceEditText;
    LinearLayout loadingTextLayout, sourceImageLayout, sourceImageActionLayout;
    ScrollView sourceTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ContextController.setContext(this);

        downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);

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
        setContentView(R.layout.activity_resource_detail);

        backButton=findViewById(R.id.settingsBackButton);
        settingName=findViewById(R.id.settingsTitle);
        sourceDownloadButton=findViewById(R.id.sourceDownloadButton);
        resourceUrlText=findViewById(R.id.resourceUrlText);
        sourceImageView=findViewById(R.id.sourceImageView);
        sourceEditText=findViewById(R.id.sourceEditText);
        loadingTextLayout=findViewById(R.id.loadingTextLayout);
        sourceImageLayout=findViewById(R.id.sourceImageLayout);
        sourceImageActionLayout=findViewById(R.id.sourceImageActionLayout);
        sourceTextLayout=findViewById(R.id.sourceTextLayout);

        downloadController=DownloadController.getController();
        resourceManager=new DeveloperManager.ResourceManager();

        // Set EditText Layout
        int screenWidth=ScreenManager.getScreenWidth(this)-10;
        // Set Layout Params
        LinearLayout.LayoutParams lp_Wrap=new LinearLayout.LayoutParams(screenWidth, MATCH_PARENT);
        sourceEditText.setLayoutParams(lp_Wrap);
        sourceEditText.setHorizontallyScrolling(false);

        // Default View
        sourceImageLayout.setVisibility(View.GONE);
        sourceImageActionLayout.setVisibility(View.GONE);
        sourceTextLayout.setVisibility(View.GONE);
        loadingTextLayout.setVisibility(View.VISIBLE);

        // Get Data
        int dataPosition=getIntent().getIntExtra(resourceManager.KEY_RESOURCE_INDEX, PreferenceController.INT_NULL);

        settingName.setText(getString(R.string.resource_detail_text));

        if(dataPosition!=PreferenceController.INT_NULL) {
            ResourceData data=resourceManager.getResourceList().get(dataPosition);
            String resourceUrl=data.getResourceUrl();

            resourceUrlText.setText(resourceUrl);

            try {
                urlData = new URL(resourceUrl);
                // Check File Type (image or text)
                if(SupportedFileExtension.isImageFile(resourceUrl)){
                    /* Show Image Sources */
                    if(SupportedFileExtension.isImageSvg(resourceUrl)){
                        // Show SVG Files
                        Uri svgUri=Uri.parse(resourceUrl);
                        GlideToVectorYou.justLoadImage(this, svgUri, sourceImageView);
                    }
                    else if(SupportedFileExtension.isImageGif(resourceUrl)){
                        // Show GIF Files
                        Glide.with(this)
                                .load(resourceUrl).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                .into(sourceImageView);
                    }
                    else{
                        // Show Bitmaps
                        new Thread(() -> {
                            try {
                                // Get Bitmap
                                imageBitmapData = BitmapFactory.decodeStream(urlData.openConnection().getInputStream());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            this.runOnUiThread(() -> {
                                // Set Image Preview
                                sourceImageView.setImageBitmap(imageBitmapData);
                            });
                        }).start();
                    }
                    // Show Image Layout and Download Button
                    sourceImageLayout.setVisibility(View.VISIBLE);
                    sourceImageActionLayout.setVisibility(View.VISIBLE);
                    sourceTextLayout.setVisibility(View.GONE);
                }
                else{
                    /* Show Text Sources */
                    new Thread(() -> {
                        try {
                            // Get Input Data
                            BufferedReader bufferData = new BufferedReader(new InputStreamReader(urlData.openStream()));
                            while (bufferData.readLine()!=null) {
                                inputData+=bufferData.readLine();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        this.runOnUiThread(() -> {
                            // Set Input Data
                            sourceEditText.setText(inputData);
                        });
                    }).start();

                    // Show Text Layout
                    sourceImageLayout.setVisibility(View.GONE);
                    sourceImageActionLayout.setVisibility(View.GONE);
                    sourceTextLayout.setVisibility(View.VISIBLE);
                }
                loadingTextLayout.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }

            sourceDownloadButton.setOnClickListener(view -> {
                String dataDownloadFolder="";
                // Set Folder
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
                    dataDownloadFolder=BROWSER_DOWNLOAD_FOLDER_V30;
                else
                    dataDownloadFolder=BROWSER_DOWNLOAD_FOLDER;
                // Download Image
                downloadController.downloadImage(findViewById(R.id.resourcesLayoutMain),
                        resourceUrl,
                        dataDownloadFolder,
                        downloadManager);
            });
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