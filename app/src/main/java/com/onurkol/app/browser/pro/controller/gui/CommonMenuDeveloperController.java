package com.onurkol.app.browser.pro.controller.gui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetJavascriptConsole;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetRequestList;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetResourceList;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetViewSource;

public class CommonMenuDeveloperController {
    public static void loadMenuController(Context context, View view, PopupWindow popupWindow){
        loadMenuControllerClass(context, view, popupWindow, null);
    }

    public static void loadMenuController(Context context, View view, BottomSheetDialog bottomSheetDialog){
        loadMenuControllerClass(context, view, null, bottomSheetDialog);
    }

    private static void loadMenuControllerClass(Context context, View view, PopupWindow popupWindow, BottomSheetDialog bottomSheetDialog){
        LinearLayout javascriptConsoleLayoutButton, viewSourceLayoutButton, requestsLayoutButton, resourcesLayoutButton;

        javascriptConsoleLayoutButton=view.findViewById(R.id.javascriptConsoleLayoutButton);
        viewSourceLayoutButton=view.findViewById(R.id.viewSourceLayoutButton);
        requestsLayoutButton=view.findViewById(R.id.requestsLayoutButton);
        resourcesLayoutButton=view.findViewById(R.id.resourcesLayoutButton);

        javascriptConsoleLayoutButton.setOnClickListener(v -> {
            BottomSheetJavascriptConsole.getBottomSheet(context).show();
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        viewSourceLayoutButton.setOnClickListener(v -> {
            BottomSheetViewSource.getBottomSheet(context).show();
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        requestsLayoutButton.setOnClickListener(v -> {
            BottomSheetRequestList.getBottomSheet(context).show();
            dismissDialog(popupWindow, bottomSheetDialog);
        });
        resourcesLayoutButton.setOnClickListener(v -> {
            BottomSheetResourceList.getBottomSheet(context).show();
            dismissDialog(popupWindow, bottomSheetDialog);
        });
    }

    private static void dismissDialog(PopupWindow popupWindow, BottomSheetDialog bottomSheetDialog){
        if(popupWindow!=null)
            popupWindow.dismiss();
        if(bottomSheetDialog!=null)
            bottomSheetDialog.dismiss();
    }
}
