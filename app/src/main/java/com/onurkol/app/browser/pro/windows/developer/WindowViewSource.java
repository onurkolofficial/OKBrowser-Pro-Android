package com.onurkol.app.browser.pro.windows.developer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.edittext.DeveloperEditText;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;
import com.onurkol.app.browser.pro.tools.HTMLSyntax;
import com.onurkol.app.browser.pro.tools.ProcessDelay;
import com.onurkol.app.browser.pro.tools.ScreenManager;

import org.apache.commons.text.StringEscapeUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class WindowViewSource {
    // Services
    static ClipboardManager clipboard=(ClipboardManager)ContextManager.getManager().getContext().getSystemService(Context.CLIPBOARD_SERVICE);

    // Bottom Sheet Dialog Window
    public static BottomSheetDialog getWindow(){
        // Get Context
        Context context=ContextManager.getManager().getContext();
        // New Window
        final BottomSheetDialog bottomWindow = new BottomSheetDialog(context);
        // Elements
        DeveloperEditText webSource;
        ImageButton copyBtn;
        // Set View
        bottomWindow.setContentView(R.layout.window_view_source);
        // Get Elements
        webSource=bottomWindow.findViewById(R.id.viewSourceCode);
        copyBtn=bottomWindow.findViewById(R.id.sourceCopyButton);
        // Get Classes
        DeveloperManager devManager=DeveloperManager.getManager();
        AppPreferenceManager prefManager=AppPreferenceManager.getInstance();
        HTMLSyntax htmlSyntax=HTMLSyntax.getInstance();

        // Update Source
        devManager.updateWebSource();

        // Variables
        boolean lineWrap=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_WRAP);
        int screenWidth=ScreenManager.getScreenWidth();

        // Line Wrap
        LinearLayout.LayoutParams lp_Default,lp_Wrap;
        lp_Wrap = new LinearLayout.LayoutParams(screenWidth, WRAP_CONTENT);
        lp_Default = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        if(lineWrap)
            webSource.setLayoutParams(lp_Wrap);
        else
            webSource.setLayoutParams(lp_Default);
        webSource.setHorizontallyScrolling(!lineWrap);

        // Button Click Events
        copyBtn.setOnClickListener(view -> {
            // Get Source & Convert HTML.
            String source=devManager.getWebSource();
            String sourceTrim=source.substring(1, source.length()-1);
            String escapeSourceCode=StringEscapeUtils.unescapeJava(sourceTrim);
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_source_code", escapeSourceCode);
            clipboard.setPrimaryClip(clip);
            // Show Message
            Toast.makeText(context, context.getString(R.string.code_copied_text), Toast.LENGTH_SHORT).show();
            // Dismiss Window
            bottomWindow.dismiss();
        });

        // Loading Source Text
        String loadingString=context.getString(R.string.loading_text)+"... "+context.getString(R.string.please_wait_text)+"\n";
        webSource.setText(loadingString);

        ProcessDelay.Delay(() -> {
            if(devManager.getWebSource()!=null){
                // Get Source and Show Edit Text
                String source=devManager.getWebSource();
                // Delete first & last " character.
                String sourceTrim=source.substring(1, source.length()-1);
                // Convert HTML Codes.
                String escapeSourceCode=StringEscapeUtils.unescapeJava(sourceTrim);
                // Fixed '<..><..>' to '<..>\n<..>'
                String sourceHtml=escapeSourceCode.replace("><",">\n<");
                // Set Syntax Colors
                webSource.setText(htmlSyntax.buildMap(sourceHtml));
                webSource.setMovementMethod(LinkMovementMethod.getInstance());
            }
        },150);

        return bottomWindow;
    }
}
