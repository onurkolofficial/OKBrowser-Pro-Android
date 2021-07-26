package com.onurkol.app.browser.pro.windows.developer;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.adapters.developer.ResourcesListAdapter;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;
import com.onurkol.app.browser.pro.lib.browser.tabs.TabBuilder;
import com.onurkol.app.browser.pro.popups.developer.PopupResourcesDetail;
import com.onurkol.app.browser.pro.webview.OKWebView;

public class WindowResources {

    // Bottom Sheet Dialog Window
    public static BottomSheetDialog getWindow(){
        // Get Context
        Context context=ContextManager.getManager().getContext();
        // New Window
        final BottomSheetDialog bottomWindow = new BottomSheetDialog(context);
        // Elements
        ImageButton resourcesRefreshButton;
        ListView resourcesListView;
        // Set View
        bottomWindow.setContentView(R.layout.window_resources);
        // Get Elements
        resourcesRefreshButton=bottomWindow.findViewById(R.id.resourcesRefreshButton);
        resourcesListView=bottomWindow.findViewById(R.id.resourcesList);
        // Get Classes
        DeveloperManager devManager=DeveloperManager.getManager();
        TabBuilder tabBuilder=TabBuilder.Build();

        // Button Click Events
        // Resource Refresh Button
        resourcesRefreshButton.setOnClickListener(view -> {
            // Get WebView
            OKWebView webView;
            if(tabBuilder.getActiveTabFragment()!=null)
                webView=tabBuilder.getActiveTabFragment().getWebView();
            else
                webView=tabBuilder.getActiveIncognitoFragment().getWebView();

            // Refresh WebView
            webView.reload();
            // Dismiss Window
            bottomWindow.dismiss();
        });

        // ListView Listener
        resourcesListView.setOnItemClickListener((adapterView, view, i, l) -> {
            // Show Dialog
            PopupResourcesDetail.showPopup(i);
        });

        // Create Adapter
        ResourcesListAdapter resourceListAdapter=new ResourcesListAdapter(context, resourcesListView, devManager.getResourcesDataList());
        // Set Adapter
        resourcesListView.setAdapter(resourceListAdapter);
        // Refresh
        resourceListAdapter.notifyDataSetChanged();
        resourcesListView.invalidateViews();

        return bottomWindow;
    }
}
