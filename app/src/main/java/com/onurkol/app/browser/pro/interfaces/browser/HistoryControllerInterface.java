package com.onurkol.app.browser.pro.interfaces.browser;

import com.onurkol.app.browser.pro.data.browser.HistoryData;

import java.util.ArrayList;

public interface HistoryControllerInterface {
    void newHistory(String historyTitle, String historyUrl);
    void deleteHistory(int position);
    void deleteAllHistory();
    void updateHistory(int position, HistoryData historyData);

    void syncHistoryData();

    void saveHistoryDataPreference();

    ArrayList<HistoryData> getHistoryList();
}
