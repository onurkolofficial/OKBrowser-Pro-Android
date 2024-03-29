package com.onurkol.app.browser.pro.menu.webview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.webview.menu.CommonContextMenuController;

public class ContextMenuImage {
    static PopupWindow popupWindow;
    static LayoutInflater inflater;

    public static PopupWindow getContextMenuWindow(Context context){
        popupWindow=new PopupWindow();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_webview_image, null);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        CommonContextMenuController.loadMenuController(context, popupWindow);

        return popupWindow;
    }
}
