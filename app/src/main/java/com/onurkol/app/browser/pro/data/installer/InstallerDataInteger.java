package com.onurkol.app.browser.pro.data.installer;

public class InstallerDataInteger {
    /**
     * WARNING!
     * Only <String> and <Integer>
     * Look at 'res/browser/values/app_*.xml' And 'java/.../lib/browser/App*.java'
     * ! If set preference data to isPreferenceData is true;
     **/
    private String stringData;
    private Integer integerData;
    private boolean isPreferenceData;
    private String preferenceKey;

    public InstallerDataInteger(String getStringData, Integer getIntegerData, boolean getIsPreferenceData, String getPreferenceKey){
        stringData=getStringData;
        integerData=getIntegerData;
        isPreferenceData=getIsPreferenceData;
        preferenceKey=getPreferenceKey;
    }

    public String getStringData(){
        return stringData;
    }
    public Integer getIntegerData(){
        return integerData;
    }
    public boolean getIsPreferenceData(){
        return isPreferenceData;
    }
    public String getPreferenceKey(){
        return preferenceKey;
    }
}
