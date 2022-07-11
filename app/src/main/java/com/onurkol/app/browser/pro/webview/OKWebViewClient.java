package com.onurkol.app.browser.pro.webview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetRequestList;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetResourceList;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.controller.browser.HistoryController;
import com.onurkol.app.browser.pro.controller.settings.GUIController;
import com.onurkol.app.browser.pro.controller.tabs.TabController;
import com.onurkol.app.browser.pro.data.developer.RequestData;
import com.onurkol.app.browser.pro.data.developer.ResourceData;
import com.onurkol.app.browser.pro.data.tabs.TabData;
import com.onurkol.app.browser.pro.fragments.tabs.TabFragment;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.libs.JavascriptManager;
import com.onurkol.app.browser.pro.libs.ScreenManager;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

public class OKWebViewClient extends WebViewClient implements BrowserDataInterface {
    TabController tabController;
    GUIController guiController;
    HistoryController historyController;
    Context wcContext;

    PreferenceController preferenceController;
    JavascriptManager javascriptManager;

    // Developer Class
    DeveloperManager.RequestManager requestManager;
    DeveloperManager.ResourceManager resourceManager;

    TabData currentTabData;

    boolean isPageFinished=false;
    boolean isDeveloperMode;

    public OKWebViewClient(Context context){
        tabController=TabController.getController(context);
        guiController=GUIController.getController();
        historyController=HistoryController.getController();
        javascriptManager=JavascriptManager.getManager();
        preferenceController=PreferenceController.getController();
        wcContext=context;
        currentTabData=tabController.getCurrentTabData();
        // Developer Tools
        requestManager=new DeveloperManager.RequestManager();
        resourceManager=new DeveloperManager.ResourceManager();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        updateTabData(view);
        // Update View Data
        EditText browserToolbarSearchInput=((Activity)wcContext).findViewById(R.id.browserToolbarSearchInput);
        browserToolbarSearchInput.setText(url);
        // Check GUI Mode and update button state (DENSE MODE BOTTOM MENU)
        if(view.canGoForward())
            tabController.getCurrentTab().setBackForwardState(TabFragment.MENU_UI_CAN_FORWARD_BACK_STATE);
        else
            tabController.getCurrentTab().setBackForwardState(TabFragment.MENU_UI_CAN_BACK_NO_FORWARD_STATE);
        tabController.getCurrentTab().setMenuUIStateUpdate();
        // Developer Manager
        isDeveloperMode=preferenceController.getBoolean(KEY_DEVELOPER_MODE);
        if(isDeveloperMode) {
            requestManager.clearRequestList();
            resourceManager.clearResourceList();
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        updateTabData(view);

        // <BUG-Fixed> WebView not scrolled with nested scroll.
        checkWebViewHeight((OKWebView)view);

        if(!isPageFinished){
            // Called onPageFinish 1 time in this block.
            javascriptManager.exec("window.scrollTo(0,0);");

            // Sync and Save History
            if(!tabController.getCurrentTab().isIncognito()) {
                historyController.syncHistoryData();
                historyController.newHistory(view.getTitle(), url); // Note! History add index always '0'.
            }
            isPageFinished=true;
        }
        else
            isPageFinished=false;

        // Update Views for Developer Mode
        isDeveloperMode=preferenceController.getBoolean(KEY_DEVELOPER_MODE);
        if(isDeveloperMode){
            if(tabController.getCurrentTab().getRequestDialog() != null) {
                BottomSheetRequestList.updateRequests(
                        tabController.getCurrentTab().getRequestDialog().getWindow().getDecorView());
            }
            if(tabController.getCurrentTab().getResourceDialog() != null) {
                BottomSheetResourceList.updateResources(
                        tabController.getCurrentTab().getResourceDialog().getWindow().getDecorView());
            }
        }

        // For get touch title
        javascriptManager.exec("var w=window;"
                + "function wrappedOnDownFunc(e){"
                + "  w._touchtarget = e.touches[0].target;"
                + "}"
                + "w.addEventListener('touchstart',wrappedOnDownFunc);");

        super.onPageFinished(view, url);
    }

    private void checkWebViewHeight(OKWebView webView){
        // <BUG> Clicked youtube menu button, webview height set to :wrap
        int getScreenHeigth = ScreenManager.getScreenHeight(wcContext);
        int getContentHeight = webView.getContentHeight();
        // Params
        LinearLayout.LayoutParams heightWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams heightMatch = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // Check View Layout Params
        if (getScreenHeigth >= getContentHeight || getContentHeight==0)
            webView.setLayoutParams(heightMatch);
        else
            webView.setLayoutParams(heightWrap);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        updateTabData(view);
        view.loadUrl(url);
        return true;
    }

    private void updateTabData(WebView view){
        // Update Tab Data (Finish)
        currentTabData.setUrl(view.getUrl());
        currentTabData.setTitle(view.getTitle());
        if(currentTabData.getTabFragment().isIncognito())
            tabController.replaceIncognitoTabData(currentTabData.getTabIndex(), currentTabData);
        else
            tabController.replaceTabData(currentTabData.getTabIndex(), currentTabData);
        // Save Preference (is not incognito)
        if(currentTabData.getTabFragment()!=null &&
                !currentTabData.getTabFragment().isIncognito() &&
                view.getUrl()!=null)
            tabController.saveTabDataPreference();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if(error.getErrorCode()==ERROR_CONNECT)
            tabController.getCurrentTab().setUIStateError();
        super.onReceivedError(view, request, error);
    }

    // Get Requests
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        isDeveloperMode=preferenceController.getBoolean(KEY_DEVELOPER_MODE);
        // Add Request
        if(isDeveloperMode)
            requestManager.newRequestData(new RequestData(request));
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        // Add Resources
        isDeveloperMode=preferenceController.getBoolean(KEY_DEVELOPER_MODE);
        if(isDeveloperMode)
            resourceManager.newResourceData(new ResourceData(url));
    }
}
