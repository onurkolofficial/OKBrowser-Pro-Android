package com.onurkol.app.browser.pro.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.lib.AppPreferenceManager;

public class DeveloperEditText extends androidx.appcompat.widget.AppCompatEditText {
    private final Context context;
    private Rect rect;
    private Paint paint;

    // Classes
    AppPreferenceManager prefManager;

    public DeveloperEditText(Context context) {
        super(context);
        this.context = context;
        prefManager=AppPreferenceManager.getInstance();
        init();
    }

    public DeveloperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DeveloperEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    private void init(){
        int textSize=37;

        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.MONOSPACE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(prefManager==null)
            prefManager=AppPreferenceManager.getInstance();

        boolean devLineNumberSettings=prefManager.getBoolean(BrowserDefaultSettings.KEY_DEV_EDIT_TEXT_LINE_NUMBERS);
        if(devLineNumberSettings) {
            int baseline;
            int lineCount = getLineCount();
            int lineNumber = 1;

            for (int i = 0; i < lineCount; ++i) {
                baseline = getLineBounds(i, null);
                if (i == 0) {
                    canvas.drawText("" + lineNumber, rect.left, baseline, paint);
                    ++lineNumber;
                } else if (getText().charAt(getLayout().getLineStart(i) - 1) == '\n') {
                    canvas.drawText("" + lineNumber, rect.left, baseline, paint);
                    ++lineNumber;
                }
            }

            int paddingLeft=86,paddingRight=28;
            setPadding(paddingLeft, getPaddingTop(), paddingRight, getPaddingBottom());
            /*
            if (lineCount < 100)
                setPadding(20, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            else if (lineCount > 99 && lineCount < 1000)
                setPadding(30, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            else if (lineCount > 999 && lineCount < 10000)
                setPadding(40, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            else if (lineCount > 9999 && lineCount < 100000)
                setPadding(50, getPaddingTop(), getPaddingRight(), getPaddingBottom());
             */
        }
        super.onDraw(canvas);
    }
}

