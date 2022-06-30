package com.onurkol.app.browser.pro.bottomsheets.developer;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;
import com.onurkol.app.browser.pro.libs.developer.HTMLSyntaxColorant;

import org.apache.commons.text.StringEscapeUtils;

public class BottomSheetCheckItem {
    static LayoutInflater inflater;
    static DeveloperManager.ContextMenuActions contextMenuActions;

    public static BottomSheetDialog getBottomSheet(Context context, String getItemCode) {
        ClipboardManager clipboard=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        HTMLSyntaxColorant syntaxColorant=HTMLSyntaxColorant.getInstance(context);

        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);

        ImageButton codeCopyButton, codeApplyButton;
        EditText itemCode;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottomsheet_developer_check_item, null);
        bottomSheetDialog.setContentView(view);

        codeCopyButton=view.findViewById(R.id.codeCopyButton);
        codeApplyButton=view.findViewById(R.id.codeApplyButton);
        itemCode=view.findViewById(R.id.itemCode);

        // Get Developer Class
        contextMenuActions=new DeveloperManager.ContextMenuActions();

        // Get Code
        String itemSourceConvert=StringEscapeUtils.unescapeJava(getItemCode);
        String itemSourceCode=itemSourceConvert.substring(1, itemSourceConvert.length()-1);
        // Set Spannable Code
        itemCode.setText(syntaxColorant.buildMap(itemSourceCode));

        // Button Click Events
        codeCopyButton.setOnClickListener(v -> {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_item_source", itemSourceCode);
            clipboard.setPrimaryClip(clip);
            // Show Snackbar
            Snackbar.make(((Activity) context).findViewById(R.id.browserCoordinatorLayout),
                            context.getString(R.string.code_copied_text),
                            Snackbar.LENGTH_SHORT)
                    .setAction(context.getString(R.string.ok_text), vw -> {})
                    .show();
            bottomSheetDialog.dismiss();
        });
        codeApplyButton.setOnClickListener(v -> {
            contextMenuActions.setEditOnTouchedItem(itemCode.getText().toString());
            bottomSheetDialog.dismiss();
        });


        return bottomSheetDialog;
    }
}
