package com.onurkol.app.browser.pro.popups.developer;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.browser.developer.RequestsDetailActivity;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;

public class PopupRequestsDetail {
    // Services
    static ClipboardManager clipboard = (ClipboardManager)ContextManager.getManager().getContext().getSystemService(Context.CLIPBOARD_SERVICE);

    // Classes
    static DeveloperManager devManager;
    // Intents
    static Intent requestDetailIntent;

    public static void showPopup(int requestPosition){
        // Get Context
        Context context=ContextManager.getManager().getContext();

        // Get Classes
        devManager=DeveloperManager.getManager();

        // Intents
        requestDetailIntent=new Intent(context, RequestsDetailActivity.class);
        // New Dialog
        final Dialog dialog = new Dialog(context);
        // Elements
        TextView getRequestUrl;
        Button cancelButton, showRequestButton;
        ImageButton copyRequestUrlButton;
        // Set View
        dialog.setContentView(R.layout.popup_developer_request_detail);
        Window window = dialog.getWindow();

        // Get Elements
        getRequestUrl = dialog.findViewById(R.id.getRequestUrl);
        cancelButton = dialog.findViewById(R.id.closeRequestButton);
        showRequestButton = dialog.findViewById(R.id.showRequestButton);
        copyRequestUrlButton = dialog.findViewById(R.id.copyRequestUrlButton);

        // Get Data
        WebResourceRequest data=devManager.getRequestDataList().get(requestPosition);
        // Get URL
        String requestUrl=data.getUrl().toString();

        // Write Data
        getRequestUrl.setText(requestUrl);

        // Button Click Events
        // Cancel Button
        cancelButton.setOnClickListener(view -> {
            // Dismiss Popup
            dialog.dismiss();
        });
        // Copy Url Button
        copyRequestUrlButton.setOnClickListener(view -> {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_request_url", requestUrl);
            clipboard.setPrimaryClip(clip);
            // Show Message
            Toast.makeText(context, context.getString(R.string.copied_text), Toast.LENGTH_SHORT).show();
            // Dismiss Popup
            dialog.dismiss();
        });
        // Show Request Button
        showRequestButton.setOnClickListener(view -> {
            // Send Request Data
            requestDetailIntent.putExtra(DeveloperManager.KEY_REQUEST_ITEM_INDEX,requestPosition);
            // Start Request Activity
            context.startActivity(requestDetailIntent);
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
