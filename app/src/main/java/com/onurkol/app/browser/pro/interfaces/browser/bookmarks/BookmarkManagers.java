package com.onurkol.app.browser.pro.interfaces.browser.bookmarks;

import com.onurkol.app.browser.pro.data.browser.BookmarkData;

import java.util.ArrayList;

public interface BookmarkManagers {
    void newBookmark(BookmarkData bookmarkData);
    void saveBookmarkListPreference(ArrayList<BookmarkData> bookmarkData);
    ArrayList<BookmarkData> getSavedBookmarkData();
    void syncSavedBookmarkData();
}
