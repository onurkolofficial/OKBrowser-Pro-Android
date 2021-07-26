package com.onurkol.app.browser.pro.tools;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.edittext.DeveloperEditText;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.popups.developer.PopupSpannedCodeDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLSyntax {
    private static HTMLSyntax instance=null;

    // Colors
    int ColorElements,ColorElementParameters, ColorNumbers, ColorMinifieds;

    // Script Codes
    List<String> ScriptCodes=new ArrayList<>();
    // Style Codes
    List<String> StyleCodes=new ArrayList<>();

    private HTMLSyntax(){
        Context context=ContextManager.getManager().getContext();
        // Colors
        ColorElements=ContextCompat.getColor(context, R.color.color_html_elements);
        ColorElementParameters=ContextCompat.getColor(context, R.color.color_html_elementParams);
        ColorNumbers=ContextCompat.getColor(context, R.color.color_numbers);
        ColorMinifieds=ContextCompat.getColor(context, R.color.background_minified_codes);
    }

    public static synchronized HTMLSyntax getInstance(){
        if(instance==null)
            instance=new HTMLSyntax();
        return instance;
    }

    public Spannable buildMap(String HTMLSource){
        // Get String
        Spannable stringSpan;
        Spannable getElementSpanned,getElParamSpanned,getNumberSpanned,getScriptTagCodeSpan,getStyleTagCodeSpan;

        // Get Minify Tool
        MinifyTool mf=new MinifyTool();

        // Minify code elements. (script, style, ...)
        String mfScriptString=mf.minifyHTMLTag(HTMLSource,"script",ScriptCodes);
        String mfSyleString=mf.minifyHTMLTag(mfScriptString,"style",StyleCodes);

        // String Span
        stringSpan=new SpannableString(mfSyleString);

        // HTML Elements
        getElementSpanned=setColorForStringRegex(stringSpan,new String[]{
                "<[a-zA-Z]+", "</[a-zA-Z]+", "[>\\\"]+"
        }, ColorElements);
        // HTML Element Parameters
        getElParamSpanned=setColorForStringRegex(getElementSpanned,new String[]{
                "[a-zA-Z]+=\\\"","[a-zA-Z]+-[a-zA-Z]+=\\\""
        }, ColorElementParameters);
        // NUMBERS
        getNumberSpanned=setColorForStringRegex(getElParamSpanned,new String[]{
                "^\\d+$"
        }, ColorNumbers);
        // Clickable Spans
        getScriptTagCodeSpan=setClickForCodeViewSpan(getNumberSpanned,"script",ScriptCodes,new String[]{
                "<script> ... </script>"
        },ColorMinifieds);
        getStyleTagCodeSpan=setClickForCodeViewSpan(getScriptTagCodeSpan,"style",StyleCodes,new String[]{
                "<style> ... </style>"
        },ColorMinifieds);

        return getStyleTagCodeSpan;
    }

    private static class MinifyTool{
        public String minifyHTMLTag(String HTMLSource, String HTMLTag, List<String> HTMLCodes){
            Pattern pScript1, pScript2;
            Matcher mScript1, mScript2;

            String minifyNormal="<"+HTMLTag+">([\\s\\S]*?)</"+HTMLTag+">";
            String minifyParams="<"+HTMLTag+" [^.*]*>([\\s\\S]*?)</"+HTMLTag+">";
            String replaceCode="<"+HTMLTag+"> ... </"+HTMLTag+">";

            // Replace <ELEMENT params=".."> ... </ELEMENT>
            pScript1 = Pattern.compile(minifyNormal);
            mScript1 = pScript1.matcher(HTMLSource);
            while (mScript1.find())
                HTMLCodes.add(mScript1.group(1));
            // Clear Codes
            String ConvertHTMLSource=HTMLSource.replaceAll(minifyNormal,replaceCode);

            // Replace <ELEMENT params=".."> ... </ELEMENT>
            pScript2 = Pattern.compile(minifyParams);
            mScript2 = pScript2.matcher(ConvertHTMLSource);
            while (mScript2.find())
                HTMLCodes.add(mScript2.group(1));
            String newHTMLSource=ConvertHTMLSource.replaceAll(minifyParams,replaceCode);

            return newHTMLSource;
        }
    }

    // Set Color String Regex
    private Spannable setColorForStringRegex(Spannable spannable, String[] paths, int color) {
        for (String path : paths) {
            Pattern p = Pattern.compile(path);
            Matcher m = p.matcher(spannable.toString());
            // Print Colors
            while (m.find())
                spannable.setSpan(new ForegroundColorSpan(color), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    // Clickable Span For
    // <ELEMENT>...</ELEMENT>
    private Spannable setClickForCodeViewSpan(Spannable spannable, String HTMLTag, List<String> list, String[] paths, int color) {
        // On Click Span
        for (String path : paths) {
            Pattern p = Pattern.compile(path);
            Matcher m = p.matcher(spannable.toString());
            int index=0;
            while (m.find()){
                // Get Index & Code Positions
                int getCodeIndex=index, getStart=m.start(), getEnd=m.end();
                // Set On Click Event
                ClickableSpan clickableSpan=new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        // Click to open Dialog (Method 1)
                        // Get Code
                        String getCode="";
                        if(getCodeIndex<list.size())
                            getCode=list.get(getCodeIndex);
                        // Show Dialog
                        PopupSpannedCodeDetail.showPopup(getCode);
                        /*
                        // Click to replace in EditText Code. (Method 2)
                        // <BUG> Clicked span but dont updating other span index. 'start,end'
                        // Get Code
                        String getCode="";
                        if(getCodeIndex<list.size())
                            getCode=list.get(getCodeIndex);
                        // Get Element
                        ViewSourceEditText vsedit=(ViewSourceEditText)view;
                        // Replace String
                        Editable getEditable=vsedit.getText();
                        getEditable=getEditable.replace(getStart,getEnd,"<"+HTMLTag+">"+getCode+"</"+HTMLTag+">");
                        // Replace Code
                        vsedit.setText(getEditable);
                         */
                    }
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.bgColor=color;
                    }
                };
                // Set Span
                spannable.setSpan(clickableSpan, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                index++;
            }
        }
        return spannable;
    }
}
