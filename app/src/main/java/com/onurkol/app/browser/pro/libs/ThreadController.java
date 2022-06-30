package com.onurkol.app.browser.pro.libs;

public class ThreadController {
    public static void Run(Runnable runnable, int milliseconds){
        new android.os.Handler().postDelayed(runnable,milliseconds);
    }
}
