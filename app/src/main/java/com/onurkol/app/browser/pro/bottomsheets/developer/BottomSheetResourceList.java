package com.onurkol.app.browser.pro.bottomsheets.developer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.adapters.developer.ResourceListAdapter;
import com.onurkol.app.browser.pro.controller.tabs.TabController;
import com.onurkol.app.browser.pro.data.developer.ResourceData;
import com.onurkol.app.browser.pro.dialogs.developer.DialogShowResource;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BottomSheetResourceList {
    static LayoutInflater inflater;
    static DeveloperManager.ResourceManager resourceManager;

    public static WeakReference<ResourceListAdapter> resourceListAdapterStatic;

    public static BottomSheetDialog getBottomSheet(Context context) {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);

        ImageButton resourcesRefreshButton;
        ListView resourceListView;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottomsheet_developer_resources, null);
        bottomSheetDialog.setContentView(view);

        TabController tabController=TabController.getController(context);
        tabController.getCurrentTab().setResourceDialog(bottomSheetDialog);

        resourcesRefreshButton=view.findViewById(R.id.resourcesRefreshButton);
        resourceListView=view.findViewById(R.id.resourceListView);

        // Get Developer Class
        resourceManager=new DeveloperManager.ResourceManager();

        // Set List Adapter
        resourceListAdapterStatic=new WeakReference<>(new ResourceListAdapter(context, resourceManager.getResourceList()));
        resourceListView.setAdapter(resourceListAdapterStatic.get());

        resourcesRefreshButton.setOnClickListener(v -> {
            if(!tabController.getCurrentTab().isTabHome())
                tabController.getCurrentTab().getWebView().reload();
        });

        // Listeners
        bottomSheetDialog.setOnDismissListener(dialog -> tabController.getCurrentTab().removeResourceDialog());
        resourceListView.setOnItemClickListener((parent, view1, position, id) -> {
            // Show Dialog
            DialogShowResource.getDialog(context, position).show();
        });

        updateResources(view);
        return bottomSheetDialog;
    }

    public static void updateResources(View view){
        ListView resourceListView=view.findViewById(R.id.resourceListView);

        ArrayList<ResourceData> updatedList=new ArrayList<>(resourceManager.getResourceList());
        ResourceListAdapter updatedAdapter=new ResourceListAdapter(view.getContext(), updatedList);

        resourceListAdapterStatic=new WeakReference<>(updatedAdapter);
        resourceListView.setAdapter(updatedAdapter);
        updatedAdapter.notifyDataSetChanged();
        resourceListView.invalidateViews();
    }
}
