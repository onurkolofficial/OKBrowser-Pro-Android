package com.onurkol.app.browser.pro.dialogs.developer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.developer.RequestDetailActivity;
import com.onurkol.app.browser.pro.data.developer.RequestData;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

public class DialogShowRequest {
    public static Dialog getDialog(Context context, int position){
        ClipboardManager clipboard=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

        TextView requestUrlText;
        ImageButton copyRequestButton;
        Button closeRequestDialogButton, showRequestDetailButton;

        DeveloperManager.RequestManager requestManager=new DeveloperManager.RequestManager();

        final Dialog requestDetailDialog = new Dialog(context);
        requestDetailDialog.setContentView(R.layout.screen_developer_dialog_request);
        requestDetailDialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

        requestUrlText=requestDetailDialog.findViewById(R.id.requestUrlText);
        copyRequestButton=requestDetailDialog.findViewById(R.id.copyRequestButton);
        closeRequestDialogButton=requestDetailDialog.findViewById(R.id.closeRequestDialogButton);
        showRequestDetailButton=requestDetailDialog.findViewById(R.id.showRequestDetailButton);

        // Request Detail Intent
        Intent requestDetailIntent=new Intent(context, RequestDetailActivity.class);

        // Get Data
        RequestData data=requestManager.getRequestList().get(position);
        String requestUrl=data.getRequest().getUrl().toString();

        requestUrlText.setText(requestUrl);

        // Button Click Events
        copyRequestButton.setOnClickListener(v -> {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_request_url", requestUrl);
            clipboard.setPrimaryClip(clip);
            requestDetailDialog.dismiss();
        });
        showRequestDetailButton.setOnClickListener(v -> {
            requestDetailIntent.putExtra(requestManager.KEY_REQUEST_INDEX, position);
            ActivityActionAnimator.startActivity(context, requestDetailIntent);
            requestDetailDialog.dismiss();
        });

        closeRequestDialogButton.setOnClickListener(view -> requestDetailDialog.dismiss());

        return requestDetailDialog;
    }
}
