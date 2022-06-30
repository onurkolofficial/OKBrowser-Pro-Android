package com.onurkol.app.browser.pro.interfaces.developer;

import android.text.Editable;
import android.text.Spannable;
import android.view.View;

public interface JavascriptConsoleInterface {
    void execJavascript(String javascript);
    void execJavascript(View updateView, String javascript);

    void saveConsoleLog(String consoleLog);
    void saveConsoleLogWithSpannable(Editable consoleLog);

    void clearConsoleLog();
    String getConsoleLog();
    Spannable getConsoleLogSpannable();

    void setExecCommandLog(String executeLog);
    String getExecCommandLog();
}
