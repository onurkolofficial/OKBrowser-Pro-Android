package com.onurkol.app.browser.pro.data.developer;

import android.webkit.WebResourceRequest;

public class RequestData {
    private final WebResourceRequest webRequest;

    public RequestData(WebResourceRequest request){
        webRequest=request;
    }

    public WebResourceRequest getRequest() {
        return webRequest;
    }
}
