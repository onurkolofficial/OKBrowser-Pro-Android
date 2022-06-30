package com.onurkol.app.browser.pro.interfaces.tabs;

import androidx.annotation.NonNull;

import com.onurkol.app.browser.pro.data.tabs.UserWebCollectionData;

import java.util.ArrayList;

public interface UserWebCollectionControllerInterface {
    void newWebCollection(@NonNull String webTitle, @NonNull String webUrl);
    void updateWebCollection(int position, UserWebCollectionData updatedWebCollectionData);
    void deleteWebCollection(int position);
    void deleteAllWebCollectionData();

    void syncCollectionData();

    void saveCollectionDataPreference();

    ArrayList<UserWebCollectionData> getWebCollectionList();
}
