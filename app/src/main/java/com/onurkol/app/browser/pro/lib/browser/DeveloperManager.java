package com.onurkol.app.browser.pro.lib.browser;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;

import com.onurkol.app.browser.pro.data.browser.developer.RequestData;
import com.onurkol.app.browser.pro.lib.browser.tabs.TabBuilder;
import com.onurkol.app.browser.pro.tools.JavascriptManager;
import com.onurkol.app.browser.pro.webview.OKWebView;

import java.util.ArrayList;

public class DeveloperManager {
    private static DeveloperManager instance;
    // Javascript Console
    private String JAVASCRIPT_LOG_STRING,JAVASCRIPT_COMMAND_OUTPUT;
    private Spannable JAVASCRIPT_LOG_SPANNABLE=new SpannableString("");
    // View Source
    private String WEB_PAGE_SOURCE;
    // Item Source
    private String CHECK_ITEM_SOURCE;

    // Lists
    ArrayList<RequestData> REQUESTS_DATA_LIST=new ArrayList<>();

    // Classes
    JavascriptManager jsManager;
    TabBuilder tabBuilder;

    public static synchronized DeveloperManager getManager(){
        if(instance==null)
            instance=new DeveloperManager();
        return instance;
    }
    // Init Configs
    private void initConfig(){
        // Get Classes
        jsManager=JavascriptManager.getManager();
        tabBuilder=TabBuilder.Build();
        // Check WebView
        OKWebView webView;
        if(jsManager.getWebView()==null){
            if(tabBuilder.getActiveTabFragment()!=null)
                webView=tabBuilder.getActiveTabFragment().getWebView();
            else
                webView=tabBuilder.getActiveIncognitoFragment().getWebView();
            jsManager.setWebView(webView);
        }
    }

    // Javascript Log Messages
    public void execJavascript(String javascript){
        initConfig();
        // Exec
        jsManager.exec(javascript,commandLog -> {
            // Delete first & last " character.
            if (commandLog.length() > 2)
                commandLog = commandLog.substring(1, commandLog.length() - 1);
            // Convert 'ul'<bug> & 'null' to 'undefined'
            if (commandLog.equals("ul") || commandLog.equals("null"))
                commandLog = "";

            setJavascriptCommandOutput(commandLog);
        });
    }
    public void setJavascriptLogMessage(String message){
        JAVASCRIPT_LOG_STRING+=message;
    }
    public void setJavascriptLogMessage(Editable message){
        JAVASCRIPT_LOG_STRING+=message.toString();
    }
    public void setJavascriptLogMessageWithSpannable(Editable message){
        JAVASCRIPT_LOG_STRING+=message.toString();
        JAVASCRIPT_LOG_SPANNABLE=message;
    }
    public String getJavascriptLogMessages(){
        return JAVASCRIPT_LOG_STRING;
    }
    public Spannable getJavascriptLogMessagesSpannable(){
        return JAVASCRIPT_LOG_SPANNABLE;
    }
    public void clearJavascriptLogMessages(){
        JAVASCRIPT_LOG_STRING="";
    }
    public void setJavascriptCommandOutput(String commandOutput){
        JAVASCRIPT_COMMAND_OUTPUT=commandOutput;
    }
    public String getJavascriptCommandOutput(){
        return JAVASCRIPT_COMMAND_OUTPUT;
    }
    public void clearJavascriptCommandOutput(){
        JAVASCRIPT_COMMAND_OUTPUT="";
    }

    // View Source
    public void updateWebSource(){
        initConfig();
        // Exec
        jsManager.exec("(function(){ return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();", sourceHTML -> {
            WEB_PAGE_SOURCE=sourceHTML;
        });
    }
    public String getWebSource(){
        return WEB_PAGE_SOURCE;
    }

    // Check Item
    public void updateTouchedItemSourceCode(){
        initConfig();
        // See: JavascriptManager:58
        jsManager.exec("window._touchtarget?window._touchtarget.outerHTML:''", sourceHTML -> {
            CHECK_ITEM_SOURCE=sourceHTML;
        });
    }
    public void changeTouchedItem(String newSource){
        initConfig();
        // Exec
        jsManager.exec("var source=document.getElementsByTagName('html')[0].innerHTML;" +
                "var itemSource=window._touchtarget?window._touchtarget.outerHTML:'';"+
                "var newSource=source.replace(itemSource,'"+newSource+"');" +
                "document.getElementsByTagName('html')[0].innerHTML=newSource;");
    }
    public String getItemSource(){
        return CHECK_ITEM_SOURCE;
    }

    // Bypass Item
    public void bypassTouchItem(){
        initConfig();
        // Exec
        jsManager.exec("var item=window._touchtarget?window._touchtarget:'';"+
                "item.remove();");
    }

    // Flexbox Item
    public void flexboxTouchItem(){
        initConfig();
        // Exec
        jsManager.exec("var item=window._touchtarget?window._touchtarget:'';"+
                "var colorR=Math.round(Math.random()*255);"+
                "var colorG=Math.round(Math.random()*255);"+
                "var colorB=Math.round(Math.random()*255);"+
                "item.style.border='1px dotted rgb('+colorR+','+colorG+','+colorB+')';");
    }
}
