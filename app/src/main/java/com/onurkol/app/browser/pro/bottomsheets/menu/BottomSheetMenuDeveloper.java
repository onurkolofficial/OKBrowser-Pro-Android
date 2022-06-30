package com.onurkol.app.browser.pro.bottomsheets.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.gui.CommonMenuDeveloperController;

public class BottomSheetMenuDeveloper {
    static LayoutInflater inflater;

    public static BottomSheetDialog getMenuBottomSheet(Context context){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottomsheet_toolbar_developer, null);

        bottomSheetDialog.setContentView(view);

        CommonMenuDeveloperController.loadMenuController(context, view, bottomSheetDialog);

        return bottomSheetDialog;
    }
}
