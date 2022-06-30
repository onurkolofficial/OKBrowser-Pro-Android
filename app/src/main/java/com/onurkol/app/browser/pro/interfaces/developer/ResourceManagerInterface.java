package com.onurkol.app.browser.pro.interfaces.developer;

import android.view.View;

import com.onurkol.app.browser.pro.data.developer.ResourceData;

import java.util.ArrayList;

public interface ResourceManagerInterface {
    String KEY_RESOURCE_INDEX="RESOURCE_DATA_INDEX";

    void newResourceData(ResourceData resourceData);
    void clearResourceList();

    void updateView(View view);

    ArrayList<ResourceData> getResourceList();
}
