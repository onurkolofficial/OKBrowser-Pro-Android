package com.onurkol.app.browser.pro.menu.toolbars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.gui.CommonMenuNoTabController;

public class MenuToolbarNoTab {
    static PopupWindow popupWindow;
    static LayoutInflater inflater;

    public static PopupWindow getMenuWindow(Context context){
        popupWindow=new PopupWindow();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_toolbar_no_tab, null);

        // Popup Window Settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(640);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setElevation(20);

        CommonMenuNoTabController.loadMenuController(context, view, popupWindow);

        return popupWindow;
    }
}
