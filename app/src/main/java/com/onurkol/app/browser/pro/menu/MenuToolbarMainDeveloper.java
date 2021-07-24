package com.onurkol.app.browser.pro.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.windows.developer.WindowJavascriptConsole;
import com.onurkol.app.browser.pro.windows.developer.WindowRequests;
import com.onurkol.app.browser.pro.windows.developer.WindowResources;
import com.onurkol.app.browser.pro.windows.developer.WindowViewSource;

public class MenuToolbarMainDeveloper {
    static PopupWindow popupWindow;

    public static PopupWindow getMenu() {
        // Init Context
        ContextManager contextManager=ContextManager.getManager();
        Context context=contextManager.getContext();

        // Elements
        LinearLayout javascriptConsoleLayoutButton,viewSourceLayoutButton,requestsLayoutButton,resourcesLayoutButton;

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
        javascriptConsoleLayoutButton=view.findViewById(R.id.javascriptConsoleLayoutButton);
        viewSourceLayoutButton=view.findViewById(R.id.viewSourceLayoutButton);
        requestsLayoutButton=view.findViewById(R.id.requestsLayoutButton);
        resourcesLayoutButton=view.findViewById(R.id.resourcesLayoutButton);

        // Set Listeners
        javascriptConsoleLayoutButton.setOnClickListener(itemView -> {
            WindowJavascriptConsole.getWindow().show();
            popupWindow.dismiss();
        });
        viewSourceLayoutButton.setOnClickListener(itemView -> {
            WindowViewSource.getWindow().show();
            popupWindow.dismiss();
        });
        requestsLayoutButton.setOnClickListener(itemView -> {
            WindowRequests.getWindow().show();
            popupWindow.dismiss();
        });
        resourcesLayoutButton.setOnClickListener(itemView -> {
            WindowResources.getWindow().show();
            popupWindow.dismiss();
        });

        return popupWindow;
    }
}
