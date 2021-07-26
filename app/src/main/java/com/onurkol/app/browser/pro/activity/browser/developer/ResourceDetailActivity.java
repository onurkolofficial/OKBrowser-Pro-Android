package com.onurkol.app.browser.pro.activity.browser.developer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
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
import com.onurkol.app.browser.pro.data.BrowserDataManager;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;
import com.onurkol.app.browser.pro.tools.ScreenManager;
import com.onurkol.app.browser.pro.tools.SupportedFileExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ResourceDetailActivity extends AppCompatActivity {
    // Elements
    ImageButton backButton;
    ImageView sourcePreviewImage;
    ScrollView sourcePreviewEditTextLayout;
    TextView settingName,resourceGetUrl;
    EditText sourcePreviewEditText;
    LinearLayout loadingTextLayout, sourcePreviewImageLayout;
    // Classes
    BrowserDataManager dataManager;
    DeveloperManager devManager;
    // Variables
    public static boolean isCreated=false,isCreatedView=false;
    static URL urlData=null;
    static Bitmap imageBitmapData = null;
    static String inputData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Get Classes
        dataManager=new BrowserDataManager();
        devManager=DeveloperManager.getManager();
        // Create View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        resourceGetUrl=findViewById(R.id.resourceGetUrl);
        sourcePreviewImage=findViewById(R.id.sourcePreviewImage);
        sourcePreviewEditTextLayout=findViewById(R.id.sourcePreviewEditTextLayout);
        sourcePreviewEditText=findViewById(R.id.sourcePreviewEditText);
        loadingTextLayout=findViewById(R.id.loadingTextLayout);
        sourcePreviewImageLayout=findViewById(R.id.sourcePreviewImageLayout);

        // Variables
        int screenWidth=ScreenManager.getScreenWidth()-10;
        // Set Layout Params
        LinearLayout.LayoutParams lp_Wrap=new LinearLayout.LayoutParams(screenWidth, MATCH_PARENT);
        sourcePreviewEditText.setLayoutParams(lp_Wrap);
        sourcePreviewEditText.setHorizontallyScrolling(false);

        // Default
        sourcePreviewImageLayout.setVisibility(View.GONE);
        sourcePreviewEditTextLayout.setVisibility(View.GONE);
        loadingTextLayout.setVisibility(View.VISIBLE);

        sourcePreviewEditText.setText("");

        // Check is Created (for Theme bug)
        if(!isCreated) {
            // Set Toolbar Title
            settingName.setText(getString(R.string.resource_detail_text));

            // Get Intent Data
            int dataIndex=getIntent().getIntExtra(DeveloperManager.KEY_RESOURCE_ITEM_INDEX, AppPreferenceManager.INTEGER_NULL);

            if(dataIndex!=AppPreferenceManager.INTEGER_NULL) {
                // Get Data
                String resource=devManager.getResourcesDataList().get(dataIndex);
                // Set Text
                resourceGetUrl.setText(resource);
                try {
                    urlData = new URL(resource);
                    // Check File Type
                    if(SupportedFileExtension.isImageFile(resource)){
                        // Check Gif, Svg and other image type.
                        if(SupportedFileExtension.isImageSvg(resource)){
                            // Load SVG Image
                            Uri svgUri=Uri.parse(resource);
                            GlideToVectorYou.justLoadImage(this,svgUri,sourcePreviewImage);
                            // Show Image View
                            sourcePreviewImageLayout.setVisibility(View.VISIBLE);
                            sourcePreviewEditTextLayout.setVisibility(View.GONE);
                            loadingTextLayout.setVisibility(View.GONE);
                        }
                        else if(SupportedFileExtension.isImageGif(resource)){
                            // Load Gif Image
                            Glide.with(this)
                                    .load(resource).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                    .into(sourcePreviewImage);
                            // Show Image View
                            sourcePreviewImageLayout.setVisibility(View.VISIBLE);
                            sourcePreviewEditTextLayout.setVisibility(View.GONE);
                            loadingTextLayout.setVisibility(View.GONE);
                        }
                        else{
                            // Load Bitmap Images
                            new Thread(() -> {
                                try {
                                    // Get Bitmap
                                    imageBitmapData = BitmapFactory.decodeStream(urlData.openConnection().getInputStream());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                this.runOnUiThread(() -> {
                                    // Set Image Preview
                                    sourcePreviewImage.setImageBitmap(imageBitmapData);
                                    // Show Image View
                                    sourcePreviewImageLayout.setVisibility(View.VISIBLE);
                                    sourcePreviewEditTextLayout.setVisibility(View.GONE);
                                    loadingTextLayout.setVisibility(View.GONE);
                                });
                            }).start();
                        }
                    }
                    else{
                        // Start Thread
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
                                sourcePreviewEditText.setText(inputData);
                                // Show Image View
                                sourcePreviewImageLayout.setVisibility(View.GONE);
                                sourcePreviewEditTextLayout.setVisibility(View.VISIBLE);
                                loadingTextLayout.setVisibility(View.GONE);
                            });
                        }).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlData.openStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    fullString += line;
                }
                reader.close()
                */

                /*
                if(SupportedFileExtension.isImageFile(resource)){
                    // Show ImageView
                    sourcePreviewImage.setVisibility(View.VISIBLE);
                    sourcePreviewEditTextLayout.setVisibility(View.GONE);
                    // Get Image & Show Bitmap
                    URL newurl=null;
                    try {
                        newurl = new URL(resource);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if(newurl!=null) {
                        Bitmap imageBitmap = null;
                        try {
                            imageBitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(imageBitmap!=null)
                            sourcePreviewImage.setImageBitmap(imageBitmap);
                    }
                }
                else{
                    // Show TextView
                    sourcePreviewImage.setVisibility(View.GONE);
                    sourcePreviewEditTextLayout.setVisibility(View.VISIBLE);
                    // Get Source
                    URL urlData = null;
                    try {
                        urlData = new URL(resource);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if(urlData!=null) {
                        BufferedReader bufferData=null;
                        try {
                            bufferData = new BufferedReader(new InputStreamReader(urlData.openStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(bufferData!=null) {
                            String inputData = "";
                            try {
                                while (bufferData.readLine() != null) {
                                    inputData += bufferData.readLine();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sourcePreviewEditText.setText(inputData);
                        }
                    }
                }

                 */
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