package com.onurkol.app.browser.pro.interfaces;

import android.os.Environment;

import com.onurkol.app.browser.pro.interfaces.browser.BookmarkInterface;
import com.onurkol.app.browser.pro.interfaces.browser.DeveloperInterface;
import com.onurkol.app.browser.pro.interfaces.browser.DownloadInterface;
import com.onurkol.app.browser.pro.interfaces.browser.HistoryInterface;
import com.onurkol.app.browser.pro.interfaces.tabs.RecentSearchInterface;
import com.onurkol.app.browser.pro.interfaces.tabs.TabDataInterface;
import com.onurkol.app.browser.pro.interfaces.tabs.UserWebCollectionInterface;
import com.onurkol.app.browser.pro.interfaces.values.DayNightValueInterface;
import com.onurkol.app.browser.pro.interfaces.values.GuiModeValueInteface;
import com.onurkol.app.browser.pro.interfaces.values.LanguageValueInterface;

public interface BrowserDataInterface extends DayNightValueInterface, LanguageValueInterface, GuiModeValueInteface,
        TabDataInterface, UserWebCollectionInterface, RecentSearchInterface, HistoryInterface, BookmarkInterface,
        DownloadInterface, DeveloperInterface {
    String BROWSER_PREFERENCE_NAME="OKBROWSER_PREFERENCES";

    String KEY_BROWSER_INSTALLER_COMPLETE="BROWSER_INSTALLER_COMPLETE",
            KEY_JAVASCRIPT_MODE="BROWSER_JAVASCRIPT_MODE",
            KEY_GEO_LOCATION="BROWSER_GEO_LOCATION",
            KEY_POPUPS="BROWSER_POPUPS",
            KEY_DOM_STORAGE="BROWSER_DOM_STORAGE",
            KEY_ZOOM="BROWSER_ZOOM",
            KEY_ZOOM_BUTTONS="BROWSER_ZOOM_BUTTONS",
            KEY_APP_CACHE="BROWSER_APP_CACHE",
            KEY_SAVE_FORMS="BROWSER_SAVE_FORMS",
            KEY_SEARCH_ENGINE="BROWSER_SEARCH_ENGINE",
            KEY_LANGUAGE="APP_LANGUAGE",
            KEY_DAY_NIGHT_MODE="ANDROID_DAY_NIGHT_MODE",
            KEY_GUI_MODE="BROWSER_GUI_MODE";

    boolean DEFAULT_JAVASCRIPT_MODE=true,
            DEFAULT_GEO_LOCATION=false,
            DEFAULT_POPUPS=false,
            DEFAULT_DOM_STORAGE=true,
            DEFAULT_ZOOM=true,
            DEFAULT_ZOOM_BUTTONS=false,
            DEFAULT_APP_CACHE=true,
            DEFAULT_SAVE_FORMS=true;

    String BROWSER_DOWNLOAD_FOLDER=Environment.DIRECTORY_DOWNLOADS+"/OKDownloads";
    String BROWSER_DOWNLOAD_FOLDER_V30=Environment.DIRECTORY_DOWNLOADS;
    String BROWSER_STORAGE_FOLDER=Environment.getExternalStorageDirectory().getPath();

    // 'res/browser/values/search_engines.xml'
    int DEFAULT_SEARCH_ENGINE=0;

    int DEFAULT_LANGUAGE=LANGUAGE_AUTO;

    int DEFAULT_NIGHT_MODE=DAY_NIGHT_MODE_DAY;
    int DEFAULT_NIGHT_MODE_V30=DAY_NIGHT_MODE_AUTO;

    int DEFAULT_GUI_MODE=GUI_MODE_SIMPLE;
}
