package com.onurkol.app.browser.pro.interfaces.browser.downloads;

import com.onurkol.app.browser.pro.data.browser.DownloadsData;

import java.util.ArrayList;

public interface DownloadsManagers {
    void newDownload(DownloadsData downloadsData);
    void saveDownloadsListPreference(ArrayList<DownloadsData> downloadsData);
    ArrayList<DownloadsData> getSavedDownloadsData();
    void syncSavedDownloadsData();
}
