package com.onurkol.app.browser.pro.interfaces.browser;

import com.onurkol.app.browser.pro.data.browser.BookmarkData;

import java.util.ArrayList;

public interface BookmarkControllerInterface {
    void newBookmark(String bookmarkTitle, String bookmarkUrl);
    void deleteBookmark(int position);
    void deleteAllBookmarks();
    void updateBookmark(int position, BookmarkData bookmarkData);

    void syncBookmarkData();

    void saveBookmarkDataPreference();

    ArrayList<BookmarkData> getBookmarkList();
}
