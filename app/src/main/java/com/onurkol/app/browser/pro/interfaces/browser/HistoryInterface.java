package com.onurkol.app.browser.pro.interfaces.browser;

import com.onurkol.app.browser.pro.data.browser.HistoryData;

import java.util.ArrayList;

public interface HistoryInterface {
    String KEY_BROWSER_HISTORY="BROWSER_HISTORY_DATA";

    String HISTORY_NO_DATA="NO_HISTORY";

    ArrayList<HistoryData> HISTORY_LIST=new ArrayList<>();
}
