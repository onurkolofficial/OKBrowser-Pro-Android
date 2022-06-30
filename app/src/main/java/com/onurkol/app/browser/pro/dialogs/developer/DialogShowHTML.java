package com.onurkol.app.browser.pro.dialogs.developer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.edittext.OKDeveloperEditText;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ScreenManager;

public class DialogShowHTML implements BrowserDataInterface {
    public static Dialog getDialog(Context context, String HTML) {
        Button closeShowHTMLDialogButton;
        OKDeveloperEditText viewHTMLCodeEditText;

        PreferenceController preferenceController=PreferenceController.getController();

        final Dialog collectionNewPageDialog = new Dialog(context);
        collectionNewPageDialog.setContentView(R.layout.screen_developer_dialog_show_html);
        collectionNewPageDialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

        closeShowHTMLDialogButton=collectionNewPageDialog.findViewById(R.id.closeShowHTMLDialogButton);
        viewHTMLCodeEditText=collectionNewPageDialog.findViewById(R.id.viewHTMLCodeEditText);

        // Dialog Variables
        int screenWidth=ScreenManager.getScreenWidth(context);
        int defaultHeight=600; // for Dialog

        LinearLayout.LayoutParams
                lp_Default = new LinearLayout.LayoutParams(WRAP_CONTENT, defaultHeight),
                lp_Wrap = new LinearLayout.LayoutParams(screenWidth, defaultHeight);
        // Get & Set LineWrap Setting
        boolean lineWrap=preferenceController.getBoolean(KEY_DEVELOPER_VS_LINE_WRAP);
        if(lineWrap)
            viewHTMLCodeEditText.setLayoutParams(lp_Wrap);
        else
            viewHTMLCodeEditText.setLayoutParams(lp_Default);
        viewHTMLCodeEditText.setHorizontallyScrolling(!lineWrap);

        // Set Code
        viewHTMLCodeEditText.setText(HTML);

        // Button Click Events
        closeShowHTMLDialogButton.setOnClickListener(view -> collectionNewPageDialog.dismiss());

        return collectionNewPageDialog;
    }
}
