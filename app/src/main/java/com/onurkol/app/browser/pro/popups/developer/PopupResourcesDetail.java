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
import com.onurkol.app.browser.pro.activity.browser.developer.ResourceDetailActivity;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;

public class PopupResourcesDetail {
    // Services
    static ClipboardManager clipboard=(ClipboardManager) ContextManager.getManager().getContext().getSystemService(Context.CLIPBOARD_SERVICE);

    // Classes
    static DeveloperManager devManager;
    // Intents
    static Intent resourceDetailIntent;

    public static void showPopup(int resourcePosition){
        // Get Context
        Context context=ContextManager.getManager().getContext();

        // Get Classes
        devManager=DeveloperManager.getManager();

        // Intents
        resourceDetailIntent=new Intent(context, ResourceDetailActivity.class);
        // New Dialog
        final Dialog dialog = new Dialog(context);
        // Elements
        TextView getResourceUrl;
        Button cancelButton, showResourceButton;
        ImageButton copyResourceUrlButton;
        // Set View
        dialog.setContentView(R.layout.popup_developer_resource_detail);
        Window window = dialog.getWindow();

        // Get Elements
        getResourceUrl = dialog.findViewById(R.id.getResourceUrl);
        cancelButton = dialog.findViewById(R.id.closeResourceButton);
        showResourceButton = dialog.findViewById(R.id.showResourceButton);
        copyResourceUrlButton = dialog.findViewById(R.id.copyResourceUrlButton);

        // Get Data
        String resourceData=devManager.getResourcesDataList().get(resourcePosition);

        // Write Data
        getResourceUrl.setText(resourceData);

        // Button Click Events
        // Cancel Button
        cancelButton.setOnClickListener(view -> {
            // Dismiss Popup
            dialog.dismiss();
        });
        // Copy Url Button
        copyResourceUrlButton.setOnClickListener(view -> {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_request_url", resourceData);
            clipboard.setPrimaryClip(clip);
            // Show Message
            Toast.makeText(context, context.getString(R.string.copied_text), Toast.LENGTH_SHORT).show();
            // Dismiss Popup
            dialog.dismiss();
        });
        // Show Request Button
        showResourceButton.setOnClickListener(view -> {
            // Send Request Data
            resourceDetailIntent.putExtra(DeveloperManager.KEY_RESOURCE_ITEM_INDEX,resourcePosition);
            // Start Request Activity
            context.startActivity(resourceDetailIntent);
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
