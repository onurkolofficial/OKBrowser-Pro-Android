package com.onurkol.app.browser.pro.windows.developer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;
import com.onurkol.app.browser.pro.tools.HTMLSyntax;
import com.onurkol.app.browser.pro.tools.ProcessDelay;

import org.apache.commons.text.StringEscapeUtils;

public class WindowCheckItem {
    // Services
    static ClipboardManager clipboard=(ClipboardManager)ContextManager.getManager().getContext().getSystemService(Context.CLIPBOARD_SERVICE);

    // Bottom Sheet Dialog Window
    public static BottomSheetDialog getWindow(){
        // Get Context
        Context context=ContextManager.getManager().getContext();
        // New Window
        final BottomSheetDialog bottomWindow = new BottomSheetDialog(context);
        // Elements
        EditText itemSource;
        ImageButton copyBtn,applyButton;
        // Set View
        bottomWindow.setContentView(R.layout.window_check_item);
        // Get Elements
        itemSource=bottomWindow.findViewById(R.id.itemCode);
        copyBtn=bottomWindow.findViewById(R.id.codeCopyButton);
        applyButton=bottomWindow.findViewById(R.id.codeApplyButton);
        // Get Classes
        DeveloperManager devManager=DeveloperManager.getManager();
        HTMLSyntax htmlSyntax=HTMLSyntax.getInstance();

        // Default Settings
        applyButton.setVisibility(View.GONE);

        // Button Click Events
        copyBtn.setOnClickListener(view -> {
            // Get Source & Convert HTML.
            String source=devManager.getItemSource();
            String sourceTrim=source.substring(1, source.length()-1);
            String escapeItemCode=StringEscapeUtils.unescapeJava(sourceTrim);
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_item_source_code", escapeItemCode);
            clipboard.setPrimaryClip(clip);
            // Show Message
            Toast.makeText(context, context.getString(R.string.code_copied_text), Toast.LENGTH_SHORT).show();
            // Dismiss Window
            bottomWindow.dismiss();
        });

        applyButton.setOnClickListener(view -> {
            // Replace Code
            devManager.changeTouchedItem(itemSource.getText().toString());
            // Dismiss Window
            bottomWindow.dismiss();
        });

        // Textwatchers
        TextWatcher itemSourceTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Show Save Button
                if(applyButton.getVisibility()==View.GONE)
                    applyButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        };

        // Loading Source Text
        String loadingString=context.getString(R.string.loading_text)+"... "+context.getString(R.string.please_wait_text)+"\n";
        itemSource.setText(loadingString);

        ProcessDelay.Delay(() -> {
            if(devManager.getItemSource()!=null){
                // Get Source and Show Edit Text
                // Convert Source Code
                String sourceConvert=StringEscapeUtils.unescapeJava(devManager.getItemSource());
                // Delete first & last " character.
                String itemCode=sourceConvert.substring(1, sourceConvert.length()-1);
                // Show Source
                itemSource.setText(htmlSyntax.buildMap(itemCode));
                // Add Textwatcher Listener
                itemSource.addTextChangedListener(itemSourceTextWatcher);
            }
        },150);

        return bottomWindow;
    }
}
