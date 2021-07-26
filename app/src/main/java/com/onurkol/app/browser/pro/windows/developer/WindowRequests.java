package com.onurkol.app.browser.pro.windows.developer;

import android.content.Context;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.adapters.developer.RequestListAdapter;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;
import com.onurkol.app.browser.pro.lib.browser.tabs.TabBuilder;
import com.onurkol.app.browser.pro.popups.developer.PopupRequestsDetail;
import com.onurkol.app.browser.pro.webview.OKWebView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowRequests {

    public static WeakReference<ImageButton> requestRefreshButtonStatic;
    public static WeakReference<ListView> requestListViewStatic;
    public static WeakReference<RequestListAdapter> requestListAdapterStatic;

    // Bottom Sheet Dialog Window
    public static BottomSheetDialog getWindow(){
        // Get Context
        Context context=ContextManager.getManager().getContext();
        // New Window
        final BottomSheetDialog bottomWindow = new BottomSheetDialog(context);
        // Elements
        ImageButton requestRefreshButton;
        ListView requestListView;
        // Set View
        bottomWindow.setContentView(R.layout.window_requests);
        // Get Elements
        requestRefreshButton=bottomWindow.findViewById(R.id.requestRefreshButton);
        requestListView=bottomWindow.findViewById(R.id.requestList);
        // Set Static Elements
        requestRefreshButtonStatic=new WeakReference<>(requestRefreshButton);
        requestListViewStatic=new WeakReference<>(requestListView);
        // Get Classes
        DeveloperManager devManager=DeveloperManager.getManager();
        TabBuilder tabBuilder=TabBuilder.Build();

        // Button Click Events
        // Request Refresh Button
        requestRefreshButton.setOnClickListener(view -> {
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
        requestListView.setOnItemClickListener((adapterView, view, i, l) -> {
            // Show Dialog
            PopupRequestsDetail.showPopup(i);
        });

        // Create Adapter
        RequestListAdapter requestListAdapter=new RequestListAdapter(context, requestListView, devManager.getRequestDataList());
        requestListAdapterStatic=new WeakReference<>(requestListAdapter);
        // Set Adapter
        requestListView.setAdapter(requestListAdapter);
        updateRequestList();

        return bottomWindow;
    }

    public static void updateRequestList(){
        Context context=ContextManager.getManager().getContext();
        DeveloperManager devManager=DeveloperManager.getManager();
        // New List
        ArrayList<WebResourceRequest> adaptList=new ArrayList<>();
        if(requestListAdapterStatic!=null && requestListAdapterStatic.get()!=null) {
            // Re-set Adapter
            adaptList.addAll(devManager.getRequestDataList());

            requestListAdapterStatic=new WeakReference<>(new RequestListAdapter(context,requestListViewStatic.get(),adaptList));
            requestListViewStatic.get().setAdapter(requestListAdapterStatic.get());
            requestListAdapterStatic.get().notifyDataSetChanged();
            requestListViewStatic.get().invalidateViews();
        }
    }
}
