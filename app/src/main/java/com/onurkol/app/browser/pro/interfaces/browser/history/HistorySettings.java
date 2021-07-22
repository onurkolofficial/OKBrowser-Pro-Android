package com.onurkol.app.browser.pro.interfaces.browser.history;

import com.onurkol.app.browser.pro.data.browser.HistoryData;

import java.util.ArrayList;

public interface HistorySettings {
    // Preference Keys
    String KEY_HISTORY_PREFERENCE="BROWSER_HISTORY_PREFERENCE";

    // History List
    ArrayList<HistoryData> BROWSER_HISTORY_LIST=new ArrayList<>();
}
