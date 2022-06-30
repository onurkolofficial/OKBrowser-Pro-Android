package com.onurkol.app.browser.pro.bottomsheets.developer;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.edittext.OKDeveloperEditText;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ScreenManager;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;
import com.onurkol.app.browser.pro.libs.developer.HTMLSyntaxColorant;

import org.apache.commons.text.StringEscapeUtils;

public class BottomSheetViewSource implements BrowserDataInterface {
    static LayoutInflater inflater;
    static DeveloperManager.ViewSource viewSource;

    public static BottomSheetDialog getBottomSheet(Context context) {
        ClipboardManager clipboard=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        PreferenceController preferenceController=PreferenceController.getController();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        ImageButton sourceCopyButton;
        OKDeveloperEditText viewSourceCode;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottomsheet_developer_view_source, null);
        bottomSheetDialog.setContentView(view);

        sourceCopyButton=view.findViewById(R.id.sourceCopyButton);
        viewSourceCode=view.findViewById(R.id.viewSourceCode);

        // Get Developer Class
        viewSource=new DeveloperManager.ViewSource();

        // Dialog Variables
        int screenWidth=ScreenManager.getScreenWidth(context);

        LinearLayout.LayoutParams
                lp_Default = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT),
                lp_Wrap = new LinearLayout.LayoutParams(screenWidth, WRAP_CONTENT);
        // Get & Set LineWrap Setting
        boolean lineWrap=preferenceController.getBoolean(KEY_DEVELOPER_VS_LINE_WRAP);
        if(lineWrap)
            viewSourceCode.setLayoutParams(lp_Wrap);
        else
            viewSourceCode.setLayoutParams(lp_Default);
        viewSourceCode.setHorizontallyScrolling(!lineWrap);

        sourceCopyButton.setOnClickListener(v -> {
            // Get Source & Convert HTML.
            String source=viewSource.getViewSource();
            String sourceTrim=source.substring(1, source.length()-1);
            String escapeSourceCode=StringEscapeUtils.unescapeJava(sourceTrim);
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_view_source", escapeSourceCode);
            clipboard.setPrimaryClip(clip);
            // Show Snackbar
            Snackbar.make(((Activity) context).findViewById(R.id.browserCoordinatorLayout),
                            context.getString(R.string.code_copied_text),
                            Snackbar.LENGTH_SHORT)
                    .setAction(context.getString(R.string.ok_text), vw -> {})
                    .show();
            bottomSheetDialog.dismiss();
        });

        // Update Data
        viewSource.updateViewSource(view);

        // Loading Source Text
        String loadingString=context.getString(R.string.loading_text)+"... "+context.getString(R.string.please_wait_text);
        viewSourceCode.setText(loadingString);

        return bottomSheetDialog;
    }

    public static void updateViewSource(View view){
        EditText viewSourceCode=view.findViewById(R.id.viewSourceCode);

        HTMLSyntaxColorant htmlSyntaxColorant=HTMLSyntaxColorant.getInstance(view.getContext());
        // Get Source
        if(viewSource.getViewSource()!=null){
            String source=viewSource.getViewSource();
            String sourceTrim=source.substring(1, source.length()-1);
            // Convert HTML Codes.
            String escapeSourceCode=StringEscapeUtils.unescapeJava(sourceTrim);
            // Fixed '<..><..>' to '<..>\n<..>'
            String sourceHtml=escapeSourceCode.replace("><",">\n<");
            // Set Syntax Colors
            viewSourceCode.setText(htmlSyntaxColorant.buildMap(sourceHtml));
            viewSourceCode.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else{
            viewSourceCode.setText(view.getContext().getString(R.string.no_code_text));
        }
    }
}
