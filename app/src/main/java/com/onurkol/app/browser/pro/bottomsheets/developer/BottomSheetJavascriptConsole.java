package com.onurkol.app.browser.pro.bottomsheets.developer;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.controller.KeyboardController;
import com.onurkol.app.browser.pro.libs.developer.DeveloperManager;

public class BottomSheetJavascriptConsole {
    static LayoutInflater inflater;
    static DeveloperManager.JavascriptConsole javascriptConsole;

    public static BottomSheetDialog getBottomSheet(Context context){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);

        ImageButton clearConsoleButton;
        EditText javascriptLog, javascriptCommand;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottomsheet_developer_javascript_console, null);
        bottomSheetDialog.setContentView(view);

        clearConsoleButton=view.findViewById(R.id.clearConsoleButton);
        javascriptLog=view.findViewById(R.id.javascriptLog);
        javascriptCommand=view.findViewById(R.id.javascriptCommand);

        // Get Developer Class
        javascriptConsole=new DeveloperManager.JavascriptConsole();

        // Print Log History
        javascriptLog.setText(javascriptConsole.getConsoleLogSpannable());

        clearConsoleButton.setOnClickListener(v -> {
            javascriptConsole.clearConsoleLog();
            javascriptLog.setText(javascriptConsole.getConsoleLog());
        });
        javascriptCommand.setOnKeyListener((v, i, keyEvent) -> {
            // Check on Enter
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                KeyboardController.hideKeyboard(context, v);
                // Check Command
                String command = javascriptCommand.getText().toString();
                command=(command.startsWith(" ") ? command.substring(1) : command);
                // Exec Command ('.execJavascript(...)' returning to '.getExecCommandLog()'
                // Note! '.execJavascript(view, ...)' is calling this 'updateConsoleLog(view)' method.
                javascriptConsole.execJavascript(view, "javascript:try{ "+command+" } catch(m){ m.message; }");
            }
            return false;
        });

        javascriptCommand.requestFocus();
        return bottomSheetDialog;
    }

    public static void updateConsoleLog(View view){
        // Variables
        int consoleLogColor=ContextCompat.getColor(view.getContext(), R.color.console_log_color);

        EditText javascriptLog=view.findViewById(R.id.javascriptLog),
                javascriptCommand=view.findViewById(R.id.javascriptCommand);
        ScrollView javascriptLogScrollView=view.findViewById(R.id.javascriptLogScrollView);

        javascriptLog.append(">> "+javascriptCommand.getText()+"\n");
        // Get Log with Spannable
        Spannable spannedLog;
        if(javascriptConsole.getExecCommandLog()!=null){
            spannedLog = new SpannableString(javascriptConsole.getExecCommandLog());
            spannedLog.setSpan(new ForegroundColorSpan(consoleLogColor),
                    0,
                    javascriptConsole.getExecCommandLog().length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else
            spannedLog = new SpannableString("");
        // Print Spannable Log
        javascriptLog.append(spannedLog);
        javascriptLog.append("\n");
        // Clear Command
        javascriptCommand.setText("");
        // Save Log
        javascriptConsole.saveConsoleLogWithSpannable(javascriptLog.getText());
        // Scroll Down on Enter Command
        javascriptLogScrollView.post(() -> javascriptLogScrollView.fullScroll(View.FOCUS_DOWN));
    }
}
