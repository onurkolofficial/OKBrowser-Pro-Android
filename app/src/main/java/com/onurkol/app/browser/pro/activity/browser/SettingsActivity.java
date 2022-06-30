package com.onurkol.app.browser.pro.activity.browser;

import static com.onurkol.app.browser.pro.libs.ActivityActionAnimator.finishAndStartActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.MainActivity;
import com.onurkol.app.browser.pro.activity.installer.InstallerActivity;
import com.onurkol.app.browser.pro.controller.ContextController;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.BrowserDataInitController;
import com.onurkol.app.browser.pro.controller.settings.DayNightModeController;
import com.onurkol.app.browser.pro.controller.settings.LanguageController;
import com.onurkol.app.browser.pro.fragments.settings.SettingsHomeFragment;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;

public class SettingsActivity extends AppCompatActivity implements BrowserDataInterface {
    BrowserDataInitController browserDataController;
    PreferenceController preferenceController;
    DayNightModeController dayNightController;
    LanguageController languageController;

    public static boolean isCreated=false,
            isSettingChanged=false,
            isClosed=false,
            isClosedAndStartMain=false;

    public static Uri isData;

    ImageButton backButton;
    TextView settingName;

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
        setContentView(R.layout.activity_settings);

        backButton=findViewById(R.id.settingsBackButton);
        settingName=findViewById(R.id.settingsTitle);

        settingName.setText(getString(R.string.settings_text));

        backButton.setOnClickListener(view -> ActivityActionAnimator.finish(this));

        // for uiMode Change
        if(!SettingsHomeFragment.isCreated)
            getSupportFragmentManager().beginTransaction().add(R.id.settingsFragmentView, new SettingsHomeFragment()).commit();

        isCreated=true;
    }

    @Override
    protected void onResume() {
        if(isSettingChanged) {
            isSettingChanged=false;
            SettingsHomeFragment.isCreated=false;
            finishAndStartActivity(this, getIntent());
        }
        if(isClosed) {
            isClosed=false;
            SettingsHomeFragment.isCreated=false;
            ActivityActionAnimator.finish(this);
        }
        if(isClosedAndStartMain){
            isClosedAndStartMain=false;
            SettingsHomeFragment.isCreated=false;

            Intent mainActivityIntent=new Intent(this, MainActivity.class);
            mainActivityIntent.setData(isData);
            // Check MainActivity is Start Status.
            if(ContextController.getController().getBaseContext()==null)
                finishAndStartActivity(this, mainActivityIntent);
            else {
                ContextController.getController().getBaseContextActivity().getIntent().setData(isData);
                ActivityActionAnimator.finish(this);
            }

            isData=null;
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        ActivityActionAnimator.finish(this);
    }

    @Override
    protected void onDestroy() {
        isCreated=false;
        SettingsHomeFragment.isCreated=false;
        super.onDestroy();
    }
}