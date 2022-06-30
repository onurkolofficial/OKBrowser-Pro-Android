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
import com.onurkol.app.browser.pro.activity.developer.ResourceDetailActivity;
import com.onurkol.app.browser.pro.data.developer.ResourceData;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

public class DialogShowResource {
    public static Dialog getDialog(Context context, int position){
        ClipboardManager clipboard=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

        TextView resourceUrlText;
        ImageButton copyResourceButton;
        Button closeResourceDialogButton, showResourceDetailButton;

        DeveloperManager.ResourceManager resourceManager=new DeveloperManager.ResourceManager();

        final Dialog resourceDetailDialog = new Dialog(context);
        resourceDetailDialog.setContentView(R.layout.screen_developer_dialog_resource);
        resourceDetailDialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

        resourceUrlText=resourceDetailDialog.findViewById(R.id.resourceUrlText);
        copyResourceButton=resourceDetailDialog.findViewById(R.id.copyResourceButton);
        closeResourceDialogButton=resourceDetailDialog.findViewById(R.id.closeResourceDialogButton);
        showResourceDetailButton=resourceDetailDialog.findViewById(R.id.showResourceDetailButton);

        // Resource Detail Intent
        Intent resourceDetailIntent=new Intent(context, ResourceDetailActivity.class);

        // Get Data
        ResourceData data=resourceManager.getResourceList().get(position);
        String resourceUrl=data.getResourceUrl();

        resourceUrlText.setText(resourceUrl);

        // Button Click Events
        copyResourceButton.setOnClickListener(v -> {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_resource_url", resourceUrl);
            clipboard.setPrimaryClip(clip);
            resourceDetailDialog.dismiss();
        });
        showResourceDetailButton.setOnClickListener(v -> {
            resourceDetailIntent.putExtra(resourceManager.KEY_RESOURCE_INDEX, position);
            ActivityActionAnimator.startActivity(context, resourceDetailIntent);
            resourceDetailDialog.dismiss();
        });

        closeResourceDialogButton.setOnClickListener(view -> resourceDetailDialog.dismiss());

        return resourceDetailDialog;
    }
}
