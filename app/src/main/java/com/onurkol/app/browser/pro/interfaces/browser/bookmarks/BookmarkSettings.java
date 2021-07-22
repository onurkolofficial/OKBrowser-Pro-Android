package com.onurkol.app.browser.pro.interfaces.browser.bookmarks;

import com.onurkol.app.browser.pro.data.browser.BookmarkData;

import java.util.ArrayList;

public interface BookmarkSettings {
    // Preference Keys
    String KEY_BOOKMARK_PREFERENCE="BROWSER_BOOKMARK_PREFERENCE";

    // Bookmark List
    ArrayList<BookmarkData> BROWSER_BOOKMARK_LIST=new ArrayList<>();
}
