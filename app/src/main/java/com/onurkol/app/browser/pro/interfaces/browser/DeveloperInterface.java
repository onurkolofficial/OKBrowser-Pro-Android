package com.onurkol.app.browser.pro.interfaces.browser;

public interface DeveloperInterface {
    String KEY_DEVELOPER_MODE="BROWSER_DEVELOPER_MODE";

    boolean DEFAULT_DEVELOPER_MODE=false;

    // Developer Settings
    // Keys
    String KEY_DEVELOPER_VS_LINE_NUMBERS="DEVELOPER_VIEW_SOURCE_LINE_NUMBERS",
            KEY_DEVELOPER_VS_LINE_WRAP="DEVELOPER_VIEW_SOURCE_LINE_WRAP",
            KEY_DEVELOPER_GN_REFRESH="DEVELOPER_GENERAL_STOP_REFRESH";

    // Values
    boolean DEFAULT_DEVELOPER_VS_LINE_NUMBERS=true,
            DEFAULT_DEVELOPER_VS_LINE_WRAP=false,
            DEFAULT_DEVELOPER_GN_REFRESH=false;
}
