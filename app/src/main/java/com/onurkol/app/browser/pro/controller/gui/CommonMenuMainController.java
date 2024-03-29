package com.onurkol.app.browser.pro.controller.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.MainActivity;
import com.onurkol.app.browser.pro.activity.browser.BookmarkActivity;
import com.onurkol.app.browser.pro.activity.browser.DownloadsActivity;
import com.onurkol.app.browser.pro.activity.browser.HistoryActivity;
import com.onurkol.app.browser.pro.activity.browser.SettingsActivity;
import com.onurkol.app.browser.pro.bottomsheets.menu.BottomSheetMenuDeveloper;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.BookmarkController;
import com.onurkol.app.browser.pro.controller.settings.GUIController;
import com.onurkol.app.browser.pro.controller.tabs.TabController;
import com.onurkol.app.browser.pro.controller.tabs.TabCounterController;
import com.onurkol.app.browser.pro.controller.tabs.UserWebCollectionController;
import com.onurkol.app.browser.pro.fragments.tabs.TabFragment;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.ActivityActionAnimator;
import com.onurkol.app.browser.pro.menu.developer.MenuDeveloper;

public class CommonMenuMainController implements BrowserDataInterface {
    // This class is a continuation of 'MenuToolbarSimple' and 'BottomSheetToolbarDense'.
    // 'java/menu/toolbars/MenuToolbarSimple' , 'java/bottomsheets/toolbars/BottomSheetToolbarDense'
    public static void loadMenuController(Context context, View view, PopupWindow popupWindow){
        loadMenuControllerClass(context, view, popupWindow, null);
    }

    public static void loadMenuController(Context context, View view, BottomSheetDialog bottomSheetDialog){
        loadMenuControllerClass(context, view, null, bottomSheetDialog);
    }

    // Variables
    static int bookmarkIndex;

    private static void loadMenuControllerClass(Context context, View view, PopupWindow popupWindow, BottomSheetDialog bottomSheetDialog){
        ImageButton menuForwardButton, menuRefreshButton, addBookmarkButton, downloadsButton, historyButton;
        LinearLayout newTabLayoutButton, newIncognitoTabLayoutButton, desktopModeLayoutCheckbox,
                findInPageLayoutButton, bookmarksLayoutButton, settingsLayoutButton, closeAllTabsLayoutButton,
                exitBrowserLayoutButton, addCollectionLayoutButton, developerMenuLayoutButton;
        CheckBox desktopModeCheckBox;
        TextView desktopModeTextView;

        TabController tabController=TabController.getController(context);
        BookmarkController bookmarkController=BookmarkController.getController();
        PreferenceController preferenceController=PreferenceController.getController();
        UserWebCollectionController webCollectionController=UserWebCollectionController.getController();
        GUIController guiController=GUIController.getController();

        exitBrowserLayoutButton=view.findViewById(R.id.exitBrowserLayoutButton);
        settingsLayoutButton=view.findViewById(R.id.settingsLayoutButton);
        menuForwardButton=view.findViewById(R.id.menuForwardButton);
        downloadsButton=view.findViewById(R.id.downloadsButton);
        historyButton=view.findViewById(R.id.historyButton);
        bookmarksLayoutButton=view.findViewById(R.id.bookmarksLayoutButton);
        newTabLayoutButton=view.findViewById(R.id.newTabLayoutButton);
        closeAllTabsLayoutButton=view.findViewById(R.id.closeAllTabsLayoutButton);
        newIncognitoTabLayoutButton=view.findViewById(R.id.newIncognitoTabLayoutButton);
        menuRefreshButton=view.findViewById(R.id.menuRefreshButton);
        desktopModeLayoutCheckbox=view.findViewById(R.id.desktopModeLayoutCheckbox);
        desktopModeCheckBox=view.findViewById(R.id.desktopModeCheckBox);
        desktopModeTextView=view.findViewById(R.id.desktopModeTextView);
        findInPageLayoutButton=view.findViewById(R.id.findInPageLayoutButton);
        addBookmarkButton=view.findViewById(R.id.addBookmarkButton);
        addCollectionLayoutButton=view.findViewById(R.id.addCollectionLayoutButton);
        developerMenuLayoutButton=view.findViewById(R.id.developerMenuLayoutButton);

        settingsLayoutButton.setOnClickListener(v -> {
            ActivityActionAnimator.startActivity(context, new Intent(context, SettingsActivity.class));
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        exitBrowserLayoutButton.setOnClickListener(v -> System.exit(0));
        downloadsButton.setOnClickListener(v -> {
            ActivityActionAnimator.startActivity(context, new Intent(context, DownloadsActivity.class));
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        historyButton.setOnClickListener(v -> {
            ActivityActionAnimator.startActivity(context, new Intent(context, HistoryActivity.class));
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        bookmarksLayoutButton.setOnClickListener(v -> {
            ActivityActionAnimator.startActivity(context, new Intent(context, BookmarkActivity.class));
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        newTabLayoutButton.setOnClickListener(v -> {
            MainActivity.isNewTab=true;
            // for Tablet
            if(MainActivity.isTabletView)
                ((MainActivity)context).onTabUpdateWithTablet();
            // Re-setting tab count for settings changed.
            TabCounterController.setMenuTabCounterButton(context, tabController.getTabList().size());
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        closeAllTabsLayoutButton.setOnClickListener(v -> {
            MainActivity.isAllTabsClosed=true;
            // Re-setting tab count for settings changed.
            TabCounterController.setMenuTabCounterButton(context, tabController.getTabList().size());
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        newIncognitoTabLayoutButton.setOnClickListener(v -> {
            MainActivity.isNewIncognitoTab=true;
            // for Tablet
            if(MainActivity.isTabletView)
                ((MainActivity)context).onTabUpdateWithTablet();
            // Re-setting tab count for settings changed.
            TabCounterController.setMenuTabCounterButton(context, tabController.getIncognitoTabList().size());
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        menuForwardButton.setOnClickListener(v -> {
            tabController.getCurrentTab().goForward();
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        menuRefreshButton.setOnClickListener(v -> {
            if(tabController.getCurrentTab().isTabError())
                tabController.getCurrentTab().setUIStateSearchBreak();
            if(!preferenceController.getBoolean(KEY_DEVELOPER_GN_REFRESH))
                tabController.getCurrentTab().getWebView().reload();
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        desktopModeLayoutCheckbox.setOnClickListener(v -> {
            boolean getDesktopMode=tabController.getCurrentTab().isDesktopMode();
            tabController.getCurrentTab().setDesktopMode(!getDesktopMode);
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        findInPageLayoutButton.setOnClickListener(v -> {
            if(!tabController.getCurrentTab().isTabHome())
                MainActivity.setFindMode(context, true);
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        addBookmarkButton.setOnClickListener(v -> {
            if(!tabController.getCurrentTab().isTabHome()){
                if(checkIsBookmarkExist(tabController, bookmarkController)){
                    // Remove Bookmark
                    bookmarkController.deleteBookmark(bookmarkIndex);
                    bookmarkIndex=0;
                }
                else{
                    // Add Bookmark
                    bookmarkController.newBookmark(
                            tabController.getCurrentTabData().getTitle(),
                            tabController.getCurrentTabData().getUrl());
                    // Show Snackbar
                    Snackbar.make(((Activity)context).findViewById(R.id.browserCoordinatorLayout),
                                    context.getString(R.string.bookmark_added_text),
                                    Snackbar.LENGTH_SHORT)
                            .setAction(context.getString(R.string.ok_text),vw -> {})
                            .show();
                }
            }
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        addCollectionLayoutButton.setOnClickListener(v -> {
            if(!tabController.getCurrentTab().isTabHome()) {
                String collectionTitle = tabController.getCurrentTabData().getTitle();
                String collectionUrl = tabController.getCurrentTabData().getUrl();
                webCollectionController.newWebCollection(collectionTitle, collectionUrl);
                // Show Snackbar
                Snackbar.make(((Activity) context).findViewById(R.id.browserCoordinatorLayout),
                                context.getString(R.string.collection_added_text),
                                Snackbar.LENGTH_SHORT)
                        .setAction(context.getString(R.string.ok_text), vw -> {})
                        .show();
                // Update View
                tabController.getCurrentTab().updateWebCollectionView();
            }
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        developerMenuLayoutButton.setOnClickListener(v -> {
            if(guiController.isDenseMode())
                BottomSheetMenuDeveloper.getMenuBottomSheet(context).show();
            else if(guiController.isSimpleMode())
                MenuDeveloper.getMenuWindow(context).showAsDropDown(((Activity)context).findViewById(R.id.browserToolbarMenuButton));
            dismissDialog(popupWindow, bottomSheetDialog);
        });

        // Check Forward Buttons
        int tabBFState=tabController.getCurrentTab().getBackForwardState();
        menuForwardButton.setEnabled(tabBFState==TabFragment.MENU_UI_CAN_FORWARD_STATE ||
                tabBFState==TabFragment.MENU_UI_CAN_FORWARD_BACK_STATE ||
                tabBFState==TabFragment.MENU_UI_CAN_FORWARD_NO_BACK_STATE);

        // Check Desktop Mode Buttons
        boolean desktopMode=tabController.getCurrentTab().isDesktopMode();
        if(GUIController.getController().isDenseMode()){
            if(desktopMode){
                // Set enabled color.
                for (Drawable drawable : desktopModeTextView.getCompoundDrawablesRelative()) {
                    if (drawable!=null)
                        DrawableCompat.setTint(drawable, context.getColor(R.color.primary));
                }
            }
            else{
                // Reset default color.
                for (Drawable drawable : desktopModeTextView.getCompoundDrawablesRelative()) {
                    if (drawable!=null)
                        drawable.setColorFilter(null);
                }
            }
        }
        else if(GUIController.getController().isSimpleMode())
            desktopModeCheckBox.setChecked(desktopMode);

        // Check Bookmark Button
        if(checkIsBookmarkExist(tabController, bookmarkController)){
            addBookmarkButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_star_fill_circle));
            addBookmarkButton.setColorFilter(context.getColor(R.color.primary));
        }
        else{
            addBookmarkButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_star_circle));
            addBookmarkButton.setColorFilter(null);
        }

        // Check Developer Mode
        if(preferenceController.getBoolean(KEY_DEVELOPER_MODE)) {
            developerMenuLayoutButton.setVisibility(View.VISIBLE);
        }
        else {
            if(guiController.isDenseMode())
                hideDeveloperButton(developerMenuLayoutButton);
            else
                developerMenuLayoutButton.setVisibility(View.GONE);
        }
    }

    private static boolean checkIsBookmarkExist(TabController tc, BookmarkController bc){
        // sync data
        bc.syncBookmarkData();
        boolean isExist=false;
        // Check Bookmark Data
        int size=bc.getBookmarkList().size();
        for(int i=0; i<size; i++) {
            String bookmarkUrl=bc.getBookmarkList().get(i).getUrl();
            String webViewUrl=tc.getCurrentTabData().getUrl();
            if(webViewUrl.equals(bookmarkUrl)) {
                bookmarkIndex=i;
                isExist=true;
                break;
            }
        }
        return isExist;
    }

    // This methods call on using 'Dense' UI Mode.
    private static void hideDeveloperButton(View view) {
        GridLayout gridLayout = (GridLayout) view.getParent();
        // GridLayout item:4 = Developer Menu Button for 'res/layouts/bottomsheets/bottomsheet_toolbar_main_dense.xml'
        gridLayout.removeViewAt(4);
    }

    private static void dismissDialog(PopupWindow popupWindow, BottomSheetDialog bottomSheetDialog){
        if(popupWindow!=null)
            popupWindow.dismiss();
        if(bottomSheetDialog!=null)
            bottomSheetDialog.dismiss();
    }
}
