package com.onurkol.app.browser.pro.tools;

public class ProcessDelay {
    public static void Delay(Runnable runnable, int milliseconds){
        new android.os.Handler().postDelayed(runnable,milliseconds);
    }
}
