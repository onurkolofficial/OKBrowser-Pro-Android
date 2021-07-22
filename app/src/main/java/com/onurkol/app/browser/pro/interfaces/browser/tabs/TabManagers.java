package com.onurkol.app.browser.pro.interfaces.browser.tabs;

import androidx.fragment.app.Fragment;

import com.onurkol.app.browser.pro.data.browser.tabs.ClassesTabData;
import com.onurkol.app.browser.pro.data.browser.tabs.IncognitoTabData;
import com.onurkol.app.browser.pro.data.browser.tabs.TabData;
import com.onurkol.app.browser.pro.fragments.browser.tabs.IncognitoTabFragment;
import com.onurkol.app.browser.pro.fragments.browser.tabs.TabFragment;

import java.util.ArrayList;

public interface TabManagers {
    // Normal Tabs
    void createNewTab();
    void createNewTab(String Url);
    void changeTab(int tabIndex);
    // Data
    void setActiveTabFragment(TabFragment TabFragment);
    TabFragment getActiveTabFragment();
    // Datas
    ArrayList<TabFragment> getTabFragmentList();
    ArrayList<TabData> getTabDataList();
    ArrayList<ClassesTabData> getClassesTabDataList();
    void recreateTabIndex();
    // Saved Tabs
    void saveTabListPreference(ArrayList<TabData> tabDataList);
    ArrayList<TabData> getSavedTabList();
    void syncSavedTabs();
    void updateSyncTabData(int position, TabData updateData, ClassesTabData updateClassesData);

    // Incognito Tabs
    void createNewIncognitoTab();
    void createNewIncognitoTab(String Url);
    void changeIncognitoTab(int tabIndex);
    // Data
    void setActiveIncognitoFragment(IncognitoTabFragment IncognitoFragment);
    IncognitoTabFragment getActiveIncognitoFragment();
    // Datas
    ArrayList<IncognitoTabFragment> getIncognitoTabFragmentList();
    ArrayList<IncognitoTabData> getIncognitoTabDataList();
    void recreateIncognitoTabIndex();
    // Fragments Views
    void addFragmentView(int viewId, Fragment fragment);
    void removeFragment(Fragment fragment);
    void showFragment(Fragment fragment);
    void hideFragment(Fragment fragment);
    void attachFragment(Fragment fragment);
    void detachFragment(Fragment fragment);
    boolean isShowingFragment(Fragment fragment);
    boolean isHiddenFragment(Fragment fragment);
    boolean isAttachedFragment(Fragment fragment);
    boolean isDetachedFragment(Fragment fragment);
}
