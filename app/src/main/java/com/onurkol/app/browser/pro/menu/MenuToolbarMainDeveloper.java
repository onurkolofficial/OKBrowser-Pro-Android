package com.onurkol.app.browser.pro.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.lib.ContextManager;

public class MenuToolbarMainDeveloper {
    static PopupWindow popupWindow;

    public static PopupWindow getMenu() {
        // Init Context
        ContextManager contextManager=ContextManager.getManager();
        Context context=contextManager.getContext();

        // Elements
        // ...

        // Get Popup Window
        popupWindow=new PopupWindow(context);

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_toolbar_main_developer, null);

        // Popup Window Settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(640);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setElevation(20);

        // Get Elements
        // ...

        return popupWindow;
    }
}
