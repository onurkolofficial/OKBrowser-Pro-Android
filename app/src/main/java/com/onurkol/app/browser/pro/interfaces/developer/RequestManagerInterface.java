package com.onurkol.app.browser.pro.interfaces.developer;

import android.view.View;

import com.onurkol.app.browser.pro.data.developer.RequestData;

import java.util.ArrayList;

public interface RequestManagerInterface {
    String KEY_REQUEST_INDEX="REQUEST_DATA_INDEX";

    void newRequestData(RequestData requestData);
    void clearRequestList();

    void updateView(View view);

    ArrayList<RequestData> getRequestList();
}
