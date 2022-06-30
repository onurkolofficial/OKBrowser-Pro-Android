package com.onurkol.app.browser.pro.interfaces.webview;

import androidx.annotation.NonNull;

import com.onurkol.app.browser.pro.webview.OKWebViewChromeClient;
import com.onurkol.app.browser.pro.webview.OKWebViewClient;

public interface OKWebViewInterface {
    void setOKWebViewClient(@NonNull OKWebViewClient webViewClient);
    void setOKWebViewChromeClient(@NonNull OKWebViewChromeClient webViewChromeClient);
    OKWebViewClient getOKWebViewClient();
    OKWebViewChromeClient getOKWebViewChromeClient();
}
