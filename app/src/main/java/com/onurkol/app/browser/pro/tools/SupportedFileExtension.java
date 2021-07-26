package com.onurkol.app.browser.pro.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupportedFileExtension {

    static String[] imageExtensionsList={
            ".png",".jpg",".jpeg",".bmp",".gif",".webp",".raw",".psd",".ico",".tif",".tiff"
    };

    static String[] extensionList={
            ".mp3",".mp4",".ogg",".zip",".rar",".tar",".gz",".sh",".rgb",".iso",".rtf",".ai",".pdf",".avi",
            ".swf",".m3u",".gtar",".movie",".m1v",".txt",".json",".doc",".docx",".ppt",".pptx",".xls",".xlsx",
            ".html",".htm",".csv",".bat",".mid",".midi",".svg",".cfg",".xml"
    };

    public static boolean isSupportFileExtension(String fileName){
        // Merge List
        List<String> list = new ArrayList<>(Arrays.asList(imageExtensionsList));
        list.addAll(Arrays.asList(extensionList));
        Object[] mergeList = list.toArray();
        // Check
        String fileExtension=fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        for (Object listExtension : mergeList) {
            if (fileExtension.equals(listExtension.toString()))
                return true;
        }
        return false;
    }

    public static boolean isImageFile(String fileName){
        // Check
        String fileExtension=fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        for (String listExtension : imageExtensionsList) {
            if (fileExtension.equals(listExtension))
                return true;
        }
        return false;
    }
}
