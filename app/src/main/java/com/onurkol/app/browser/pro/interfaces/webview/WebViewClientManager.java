package com.onurkol.app.browser.pro.interfaces.webview;

import com.onurkol.app.browser.pro.webview.OKWebViewChromeClient;
import com.onurkol.app.browser.pro.webview.OKWebViewClient;

public interface WebViewClientManager {
    // Client
    void setOKWebViewClient(OKWebViewClient webViewClient);
    OKWebViewClient getOKWebViewClient();

    // Chrome Client
    void setOKWebViewChromeClient(OKWebViewChromeClient webViewClient);
    OKWebViewChromeClient getOKWebViewChromeClient();
}
