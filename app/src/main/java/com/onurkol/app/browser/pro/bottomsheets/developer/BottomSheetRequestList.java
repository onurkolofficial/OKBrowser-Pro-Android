package com.onurkol.app.browser.pro.bottomsheets.developer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.adapters.developer.RequestListAdapter;
import com.onurkol.app.browser.pro.controller.tabs.TabController;
import com.onurkol.app.browser.pro.data.developer.RequestData;
import com.onurkol.app.browser.pro.dialogs.developer.DialogShowRequest;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BottomSheetRequestList {
    static LayoutInflater inflater;
    static DeveloperManager.RequestManager requestManager;

    public static WeakReference<RequestListAdapter> requestListAdapterStatic;

    public static BottomSheetDialog getBottomSheet(Context context) {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);

        ImageButton requestRefreshButton;
        ListView requestListView;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottomsheet_developer_requests, null);
        bottomSheetDialog.setContentView(view);

        TabController tabController=TabController.getController(context);
        tabController.getCurrentTab().setRequestDialog(bottomSheetDialog);

        requestRefreshButton=view.findViewById(R.id.requestRefreshButton);
        requestListView=view.findViewById(R.id.requestListView);

        // Get Developer Class
        requestManager=new DeveloperManager.RequestManager();

        // Set List Adapter
        requestListAdapterStatic=new WeakReference<>(new RequestListAdapter(context, requestManager.getRequestList()));
        requestListView.setAdapter(requestListAdapterStatic.get());

        requestRefreshButton.setOnClickListener(v -> {
            if(!tabController.getCurrentTab().isTabHome())
                tabController.getCurrentTab().getWebView().reload();
        });

        // Listeners
        bottomSheetDialog.setOnDismissListener(dialog -> tabController.getCurrentTab().removeRequestDialog());
        requestListView.setOnItemClickListener((parent, view1, position, id) -> {
            // Show Dialog
            DialogShowRequest.getDialog(context, position).show();
        });

        updateRequests(view);
        return bottomSheetDialog;
    }

    public static void updateRequests(View view){
        ListView requestListView=view.findViewById(R.id.requestListView);

        ArrayList<RequestData> updatedList=new ArrayList<>(requestManager.getRequestList());
        RequestListAdapter updatedAdapter=new RequestListAdapter(view.getContext(), updatedList);

        requestListAdapterStatic=new WeakReference<>(updatedAdapter);
        requestListView.setAdapter(updatedAdapter);
        updatedAdapter.notifyDataSetChanged();
        requestListView.invalidateViews();
    }
}
