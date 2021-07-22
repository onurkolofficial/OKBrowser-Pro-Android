package com.onurkol.app.browser.pro.interfaces.browser.history;

import com.onurkol.app.browser.pro.data.browser.HistoryData;

import java.util.ArrayList;

public interface HistoryManagers {
    void newHistory(HistoryData historyData);
    void deleteAllHistory();
    void saveHistoryListPreference(ArrayList<HistoryData> historyData);
    ArrayList<HistoryData> getSavedHistoryData();
    void syncSavedHistoryData();
}
