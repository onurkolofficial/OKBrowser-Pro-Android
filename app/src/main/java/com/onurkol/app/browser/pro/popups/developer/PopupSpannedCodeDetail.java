package com.onurkol.app.browser.pro.popups.developer;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.tools.ScreenManager;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class PopupSpannedCodeDetail {
    public static void showPopup(String spannedCode){
        // Get Context
        Context context=ContextManager.getManager().getContext();
        // Get Classes
        AppPreferenceManager prefManager=AppPreferenceManager.getInstance();
        // New Dialog
        final Dialog dialog = new Dialog(context);
        // Elements
        EditText viewCodeEditText;
        Button closeSpannedCodeButton;
        // Set View
        dialog.setContentView(R.layout.popup_developer_spanned_code);
        Window window = dialog.getWindow();

        // Get Elements
        viewCodeEditText=dialog.findViewById(R.id.viewCodeEditText);
        closeSpannedCodeButton=dialog.findViewById(R.id.closeSpannedCodeButton);

        // Screen Width Variables
        int screenWidth=ScreenManager.getScreenWidth();
        int defaultHeight=600;

        // Get Line Wrap Value
        boolean lineWrap=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_WRAP);

        // Line Wrap
        LinearLayout.LayoutParams lp_Default,lp_Wrap;
        lp_Wrap = new LinearLayout.LayoutParams(screenWidth, defaultHeight);
        lp_Default = new LinearLayout.LayoutParams(WRAP_CONTENT, defaultHeight);
        if(lineWrap)
            viewCodeEditText.setLayoutParams(lp_Wrap);
        else
            viewCodeEditText.setLayoutParams(lp_Default);
        viewCodeEditText.setHorizontallyScrolling(!lineWrap);

        // Set Text
        viewCodeEditText.setText(spannedCode);

        // Button Click Events
        closeSpannedCodeButton.setOnClickListener(view -> {
            // Dismiss Popup
            dialog.dismiss();
        });

        // Dialog Settings
        dialog.setCancelable(true);
        // Dialog Width/Height
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // Show Dialog
        dialog.show();
    }
}
