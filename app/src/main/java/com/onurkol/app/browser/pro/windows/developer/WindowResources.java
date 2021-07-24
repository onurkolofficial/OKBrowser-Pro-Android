package com.onurkol.app.browser.pro.windows.developer;

import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.lib.ContextManager;

public class WindowResources {

    // Bottom Sheet Dialog Window
    public static BottomSheetDialog getWindow(){
        // Get Context
        Context context= ContextManager.getManager().getContext();
        // New Window
        final BottomSheetDialog bottomWindow = new BottomSheetDialog(context);
        // Elements
        // ...
        // Set View
        bottomWindow.setContentView(R.layout.window_resources);
        // Get Elements
        // ...
        // Get Classes
        // ...

        return bottomWindow;
    }
}
