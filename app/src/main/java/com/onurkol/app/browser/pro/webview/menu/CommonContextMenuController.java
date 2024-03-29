package com.onurkol.app.browser.pro.webview.menu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.PermissionController;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.DownloadController;
import com.onurkol.app.browser.pro.controller.tabs.TabController;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.CharLimiter;
import com.onurkol.app.browser.pro.libs.JavascriptManager;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

public class CommonContextMenuController implements BrowserDataInterface {
    static ClipboardManager clipboard;
    static JavascriptManager javascriptManager;
    private static boolean showFullUrl=false;

    static WebView.HitTestResult hitResult;
    static String getResultImageURL="",getResultURL="",getResultTITLE="";
    static PopupWindow mPopupWindow;

    static DeveloperManager.ContextMenuActions contextMenuActions;

    public static void loadMenuController(Context context, PopupWindow popupWindow){
        TextView contextMenuShowUrl;
        LinearLayout contextMenuOpenNewTabLayoutButton, contextMenuOpenIncognitoTabLayoutButton,
                contextMenuOpenImageLayoutButton, contextMenuOpenImageNewTabLayoutButton,
                contextMenuOpenImageIncognitoTabLayoutButton, contextMenuDownloadImageLayoutButton,
                contextMenuCopyURLButton, contextMenuCopyTextButton, contextMenuShowUrlFullLayoutButton,
                contextMenuCopyImageURLButton;
        View includeDeveloperMenu;
        // for developer menu
        ImageButton checkItemButton, bypassItemButton, flexboxButton;

        clipboard=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        javascriptManager=JavascriptManager.getManager();
        TabController tabController=TabController.getController(context);
        PreferenceController preferenceController=PreferenceController.getController();
        contextMenuActions=new DeveloperManager.ContextMenuActions();

        mPopupWindow=popupWindow;
        View view=mPopupWindow.getContentView();

        contextMenuShowUrl=view.findViewById(R.id.contextMenuShowUrl);
        contextMenuShowUrlFullLayoutButton=view.findViewById(R.id.contextMenuShowUrlFullLayoutButton);
        contextMenuCopyURLButton=view.findViewById(R.id.contextMenuCopyURLButton);
        contextMenuCopyImageURLButton=view.findViewById(R.id.contextMenuCopyImageURLButton);
        contextMenuCopyTextButton=view.findViewById(R.id.contextMenuCopyTextButton);
        contextMenuOpenNewTabLayoutButton=view.findViewById(R.id.contextMenuOpenNewTabLayoutButton);
        contextMenuOpenIncognitoTabLayoutButton=view.findViewById(R.id.contextMenuOpenIncognitoTabLayoutButton);
        contextMenuOpenImageLayoutButton=view.findViewById(R.id.contextMenuOpenImageLayoutButton);
        contextMenuOpenImageNewTabLayoutButton=view.findViewById(R.id.contextMenuOpenImageNewTabLayoutButton);
        contextMenuOpenImageIncognitoTabLayoutButton=view.findViewById(R.id.contextMenuOpenImageIncognitoTabLayoutButton);
        contextMenuDownloadImageLayoutButton=view.findViewById(R.id.contextMenuDownloadImageLayoutButton);
        includeDeveloperMenu=view.findViewById(R.id.includeDeveloperMenu);
        checkItemButton=view.findViewById(R.id.checkItemButton);
        bypassItemButton=view.findViewById(R.id.bypassItemButton);
        flexboxButton=view.findViewById(R.id.flexboxButton);

        // Get WebView Hit Result
        hitResult = tabController.getCurrentTab().getWebView().getHitTestResult();
        // Get url data
        getResultURL=hitResult.getExtra();
        getResultImageURL=hitResult.getExtra();

        // Callback for getting touch link title
        ValueCallback<String> javascriptValueCallback=string -> {
            // Get Url Title
            getResultTITLE=string.substring(1, (string.length() - 1));

            if(hitResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
                // Get Handler URL
                Handler handler=new Handler();
                Message message=handler.obtainMessage();

                tabController.getCurrentTab().getWebView().requestFocusNodeHref(message);
                getResultURL=message.getData().getString("url");
            }
        };

        // Call JS
        javascriptManager.exec("window._touchtarget?window._touchtarget.innerText:''", javascriptValueCallback);

        // Show Url
        contextMenuShowUrl.setText(CharLimiter.Limit(getResultURL, 55));

        // Check Developer Menu
        if(preferenceController.getBoolean(KEY_DEVELOPER_MODE))
            includeDeveloperMenu.setVisibility(View.VISIBLE);
        else
            includeDeveloperMenu.setVisibility(View.GONE);

        // Click Listeners
        contextMenuShowUrlFullLayoutButton.setOnClickListener(v -> {
            if(!showFullUrl) {
                showFullUrl=true;
                contextMenuShowUrl.setText(getResultURL);
            }
            else{
                showFullUrl=false;
                contextMenuShowUrl.setText(CharLimiter.Limit(getResultURL, 55));
            }
        });
        if(contextMenuCopyURLButton!=null)
            contextMenuCopyURLButton.setOnClickListener(copyURLListener);
        if(contextMenuCopyImageURLButton!=null)
            contextMenuCopyImageURLButton.setOnClickListener(copyImageURLListener);
        if(contextMenuCopyTextButton!=null)
            contextMenuCopyTextButton.setOnClickListener(copyTextListener);
        if(contextMenuOpenNewTabLayoutButton!=null)
            contextMenuOpenNewTabLayoutButton.setOnClickListener(v -> {
                tabController.newTab(getResultURL);
                mPopupWindow.dismiss();
            });
        if(contextMenuOpenIncognitoTabLayoutButton!=null)
            contextMenuOpenIncognitoTabLayoutButton.setOnClickListener(v -> {
                tabController.newIncognitoTab(getResultURL);
                mPopupWindow.dismiss();
            });
        if(contextMenuOpenImageLayoutButton!=null)
            contextMenuOpenImageLayoutButton.setOnClickListener(v -> {
                tabController.getCurrentTab().onStartWeb(getResultImageURL);
                mPopupWindow.dismiss();
            });
        if(contextMenuOpenImageNewTabLayoutButton!=null)
            contextMenuOpenImageNewTabLayoutButton.setOnClickListener(v -> {
                tabController.newTab(getResultImageURL);
                mPopupWindow.dismiss();
            });
        if(contextMenuOpenImageIncognitoTabLayoutButton!=null)
            contextMenuOpenImageIncognitoTabLayoutButton.setOnClickListener(v -> {
                tabController.newIncognitoTab(getResultImageURL);
                mPopupWindow.dismiss();
            });
        if(contextMenuDownloadImageLayoutButton!=null)
            contextMenuDownloadImageLayoutButton.setOnClickListener(downloadImageListener);

        // Developer Buttons
        checkItemButton.setOnClickListener(v -> {
            contextMenuActions.getCheckItemCode(context);
            mPopupWindow.dismiss();
        });
        bypassItemButton.setOnClickListener(v -> {
            contextMenuActions.setBypassOnTouchedItem();
            mPopupWindow.dismiss();
        });
        flexboxButton.setOnClickListener(v -> {
            contextMenuActions.setFlexboxOnTouchedItem();
            mPopupWindow.dismiss();
        });
    }

    // Listeners
    static View.OnClickListener downloadImageListener=v -> {
        PermissionController permissionController=PermissionController.getController();
        DownloadManager downloadManager=(DownloadManager)v.getContext().getSystemService(DOWNLOAD_SERVICE);
        DownloadController downloadController=DownloadController.getController();

        View snackbarRoot=((Activity)v.getContext()).findViewById(R.id.browserCoordinatorLayout);

        String dataDownloadFolder="";
        // Set Folder
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
            dataDownloadFolder=BROWSER_DOWNLOAD_FOLDER_V30;
        else
            dataDownloadFolder=BROWSER_DOWNLOAD_FOLDER;

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            // API 29 and above
            downloadController.downloadImage(snackbarRoot, getResultImageURL, dataDownloadFolder, downloadManager);
        }
        else{
            // API 28 and below
            if(permissionController.getStoragePermission(v.getContext()))
                downloadController.downloadImage(snackbarRoot, getResultImageURL, dataDownloadFolder, downloadManager);
            else
                Snackbar.make(snackbarRoot, v.getContext().getString(R.string.require_file_permission_text),
                        Snackbar.LENGTH_SHORT)
                        .setAction(v.getContext().getString(R.string.ok_text),view -> {})
                        .show();
        }
        // Dismiss Popup
        mPopupWindow.dismiss();
    };

    static View.OnClickListener copyURLListener=v -> {
        // Copy Data
        ClipData clip = ClipData.newPlainText("copy_link", getResultURL);
        clipboard.setPrimaryClip(clip);
        // Dismiss Popup
        mPopupWindow.dismiss();
    };
    static View.OnClickListener copyImageURLListener=v -> {
        // Copy Data
        ClipData clip = ClipData.newPlainText("copy_link", getResultImageURL);
        clipboard.setPrimaryClip(clip);
        // Dismiss Popup
        mPopupWindow.dismiss();
    };
    static View.OnClickListener copyTextListener=v -> {
        // Copy Data
        ClipData clip = ClipData.newPlainText("copy_title", getResultTITLE);
        clipboard.setPrimaryClip(clip);
        // Dismiss Popup
        mPopupWindow.dismiss();
    };
}
