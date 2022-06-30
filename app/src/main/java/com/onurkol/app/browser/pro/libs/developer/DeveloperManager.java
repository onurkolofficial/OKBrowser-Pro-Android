package com.onurkol.app.browser.pro.libs.developer;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;

import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetCheckItem;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetJavascriptConsole;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetRequestList;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetResourceList;
import com.onurkol.app.browser.pro.bottomsheets.developer.BottomSheetViewSource;
import com.onurkol.app.browser.pro.data.developer.RequestData;
import com.onurkol.app.browser.pro.data.developer.ResourceData;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;
import com.onurkol.app.browser.pro.interfaces.developer.ContextMenuActionsInterface;
import com.onurkol.app.browser.pro.interfaces.developer.JavascriptConsoleInterface;
import com.onurkol.app.browser.pro.interfaces.developer.RequestManagerInterface;
import com.onurkol.app.browser.pro.interfaces.developer.ResourceManagerInterface;
import com.onurkol.app.browser.pro.interfaces.developer.ViewSourceInterface;
import com.onurkol.app.browser.pro.libs.JavascriptManager;

import java.util.ArrayList;

public class DeveloperManager implements BrowserDataInterface {
    // Variables (JavascriptConsole)
    private static String JS_CONSOLE_ALL_LOG="",
            JS_CONSOLE_COMMAND_LOG="";
    private static Spannable JS_CONSOLE_ALL_LOG_SPANNABLE=new SpannableString("");

    // Variables (ViewSource)
    private static String VIEW_SOURCE_CODE="";

    // Variables (Requests)
    private static final ArrayList<RequestData> REQUEST_DATA_LIST=new ArrayList<>();

    // Variables (Resources)
    private static final ArrayList<ResourceData> RESOURCE_DATA_LIST=new ArrayList<>();

    private static JavascriptManager javascriptManager;

    // Javascript Console Class
    public static class JavascriptConsole implements JavascriptConsoleInterface {
        public JavascriptConsole(){
            javascriptManager=JavascriptManager.getManager();
        }

        @Override
        public void execJavascript(String javascript) {
            javascriptManager.exec(javascript, execLog -> {
                if (execLog.length() > 2)
                    execLog = execLog.substring(1, execLog.length() - 1);
                // Convert 'ul' & 'null' to 'undefined'
                if (execLog.equals("ul") || execLog.equals("null"))
                    execLog = "";
                setExecCommandLog(execLog);
            });
        }
        @Override
        public void execJavascript(View updateView, String javascript) {
            javascriptManager.exec(javascript, execLog -> {
                if (execLog.length() > 2)
                    execLog = execLog.substring(1, execLog.length() - 1);
                // Convert 'ul' & 'null' to 'undefined'
                if (execLog.equals("ul") || execLog.equals("null"))
                    execLog = "";
                setExecCommandLog(execLog);
                BottomSheetJavascriptConsole.updateConsoleLog(updateView);
            });
        }
        @Override
        public void saveConsoleLog(String consoleLog) {
            JS_CONSOLE_ALL_LOG=consoleLog;
        }
        @Override
        public void saveConsoleLogWithSpannable(Editable consoleLog) {
            JS_CONSOLE_ALL_LOG_SPANNABLE=consoleLog;
            saveConsoleLog(consoleLog.toString());
        }
        @Override
        public void clearConsoleLog() {
            JS_CONSOLE_ALL_LOG="";
            JS_CONSOLE_ALL_LOG_SPANNABLE=new SpannableString("");
            JS_CONSOLE_COMMAND_LOG="";
        }
        @Override
        public String getConsoleLog() {
            return JS_CONSOLE_ALL_LOG;
        }
        @Override
        public Spannable getConsoleLogSpannable() {
            return JS_CONSOLE_ALL_LOG_SPANNABLE;
        }
        @Override
        public void setExecCommandLog(String executeLog) {
            JS_CONSOLE_COMMAND_LOG=executeLog;
        }
        @Override
        public String getExecCommandLog() {
            return JS_CONSOLE_COMMAND_LOG;
        }
    }
    // View Source Class
    public static class ViewSource implements ViewSourceInterface {
        public ViewSource() {
            javascriptManager=JavascriptManager.getManager();
        }
        @Override
        public void updateViewSource() {
            javascriptManager.exec("(function(){ return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                    htmlViewSource -> VIEW_SOURCE_CODE=htmlViewSource);
        }
        @Override
        public void updateViewSource(View view) {
            javascriptManager.exec("(function(){ return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                    htmlViewSource -> {
                VIEW_SOURCE_CODE=htmlViewSource;
                BottomSheetViewSource.updateViewSource(view);
            });
        }
        @Override
        public String getViewSource() {
            return VIEW_SOURCE_CODE;
        }
    }
    // Request Class
    public static class RequestManager implements RequestManagerInterface {
        @Override
        public void newRequestData(RequestData requestData) {
            REQUEST_DATA_LIST.add(requestData);
        }
        @Override
        public void clearRequestList() {
            REQUEST_DATA_LIST.clear();
        }
        @Override
        public void updateView(View view) {
            BottomSheetRequestList.updateRequests(view);
        }
        @Override
        public ArrayList<RequestData> getRequestList() {
            return REQUEST_DATA_LIST;
        }
    }
    // Resource Class
    public static class ResourceManager implements ResourceManagerInterface {
        @Override
        public void newResourceData(ResourceData resourceData) {
            RESOURCE_DATA_LIST.add(resourceData);
        }
        @Override
        public void clearResourceList() {
            RESOURCE_DATA_LIST.clear();
        }
        @Override
        public void updateView(View view) {
            BottomSheetResourceList.updateResources(view);
        }
        @Override
        public ArrayList<ResourceData> getResourceList() {
            return RESOURCE_DATA_LIST;
        }
    }
    // WebView Context Menu Class
    public static class ContextMenuActions implements ContextMenuActionsInterface {
        public ContextMenuActions(){
            javascriptManager=JavascriptManager.getManager();
        }
        @Override
        public void getCheckItemCode(Context context) {
            javascriptManager.exec("window._touchtarget?window._touchtarget.outerHTML:''", itemCode -> {
                // Open Dialog
                BottomSheetCheckItem.getBottomSheet(context, itemCode).show();
            });
        }
        @Override
        public void setEditOnTouchedItem(String newCode) {
            javascriptManager.exec("var source=document.getElementsByTagName('html')[0].innerHTML;"
                    + "var itemSource=window._touchtarget?window._touchtarget.outerHTML:'';"
                    + "var newSource=source.replace(itemSource,'"+newCode+"');"
                    + "document.getElementsByTagName('html')[0].innerHTML=newSource;");
        }
        @Override
        public void setFlexboxOnTouchedItem() {
            javascriptManager.exec("var item=window._touchtarget?window._touchtarget:'';"
                    + "var colorR=Math.round(Math.random()*255);"
                    + "var colorG=Math.round(Math.random()*255);"
                    + "var colorB=Math.round(Math.random()*255);"
                    + "item.style.border='1.7px dotted rgb('+colorR+','+colorG+','+colorB+')';");
        }
        @Override
        public void setBypassOnTouchedItem() {
            javascriptManager.exec("var item=window._touchtarget?window._touchtarget:'';"
                    + "item.remove();");
        }
    }
}
