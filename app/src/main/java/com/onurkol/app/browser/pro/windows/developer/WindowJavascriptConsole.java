package com.onurkol.app.browser.pro.windows.developer;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.DeveloperManager;
import com.onurkol.app.browser.pro.tools.KeyboardController;
import com.onurkol.app.browser.pro.tools.ProcessDelay;

public class WindowJavascriptConsole {

    // Bottom Sheet Dialog Window
    public static BottomSheetDialog getWindow(){
        // Get Context
        Context context=ContextManager.getManager().getContext();
        // New Window
        final BottomSheetDialog bottomWindow=new BottomSheetDialog(context);
        // Elements
        EditText javascriptLog, javascriptCommand;
        ImageButton clearConsoleLog;
        // Set View
        bottomWindow.setContentView(R.layout.window_javascript_console);
        // Get Elements
        javascriptLog=bottomWindow.findViewById(R.id.javascriptLog);
        javascriptCommand=bottomWindow.findViewById(R.id.javascriptCommand);
        clearConsoleLog=bottomWindow.findViewById(R.id.clearConsoleLog);
        // Get Classes
        DeveloperManager devManager=DeveloperManager.getManager();
        // Variables
        int consoleLogColor=ContextCompat.getColor(context, R.color.console_log_color);

        // Print Log History
        javascriptLog.setText(devManager.getJavascriptLogMessagesSpannable());
        javascriptLog.requestFocus();

        // Button Click Events
        clearConsoleLog.setOnClickListener(view -> {
            devManager.clearJavascriptLogMessages();
            javascriptLog.setText(devManager.getJavascriptLogMessages());
        });

        javascriptCommand.setOnKeyListener((view, i, keyEvent) -> {
            // Check on Enter
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                KeyboardController.hideKeyboard(view);
                // Get Command
                String command = javascriptCommand.getText().toString();
                // Check String
                command=(command.startsWith(" ") ? command.substring(1) : command);
                // Exec Javascript
                devManager.execJavascript("javascript:try{ "+command+" } catch(m){ m.message; }");
                // Out Log
                ProcessDelay.Delay(() -> {
                    // Print Log
                    javascriptLog.append(">> "+javascriptCommand.getText()+"\n");
                    // Log Spanned
                    Spannable spannedCode;
                    if(devManager.getJavascriptCommandOutput()!=null) {
                        spannedCode = new SpannableString(devManager.getJavascriptCommandOutput());
                        spannedCode.setSpan(new ForegroundColorSpan(consoleLogColor), 0, devManager.getJavascriptCommandOutput().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    else
                        spannedCode=new SpannableString("");
                    // Print Spannable Log
                    javascriptLog.append(spannedCode);
                    javascriptLog.append("\n");
                    // Clear Command
                    javascriptCommand.setText("");

                    // Clear History
                    devManager.clearJavascriptLogMessages();
                    // Save History
                    devManager.setJavascriptLogMessageWithSpannable(javascriptLog.getText());
                    // Reset Command Output
                    devManager.clearJavascriptCommandOutput();
                }, 150);

                // Fixed Scroll Down on Enter Command
                javascriptLog.requestFocus();
            }
            return false;
        });
        // Focus Command
        javascriptCommand.requestFocus();

        return bottomWindow;
    }
}
