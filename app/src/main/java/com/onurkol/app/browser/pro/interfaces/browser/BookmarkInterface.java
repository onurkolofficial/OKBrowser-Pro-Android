package com.onurkol.app.browser.pro.interfaces.browser;

import com.onurkol.app.browser.pro.data.browser.BookmarkData;

import java.util.ArrayList;

public interface BookmarkInterface {
    String KEY_BROWSER_BOOKMARK="BROWSER_BOOKMARK_DATA";

    String BOOKMARK_NO_DATA="NO_BOOKMARK";

    ArrayList<BookmarkData> BOOKMARK_LIST=new ArrayList<>();
}
