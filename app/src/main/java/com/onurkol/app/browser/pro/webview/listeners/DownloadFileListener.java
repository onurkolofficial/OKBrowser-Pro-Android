package com.onurkol.app.browser.pro.webview.listeners;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.webkit.DownloadListener;
import android.widget.Toast;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.data.browser.DownloadsData;
import com.onurkol.app.browser.pro.interfaces.BrowserDefaultSettings;
import com.onurkol.app.browser.pro.interfaces.browser.downloads.DownloadsSettings;
import com.onurkol.app.browser.pro.lib.ContextManager;
import com.onurkol.app.browser.pro.lib.browser.downloads.DownloadsHelper;
import com.onurkol.app.browser.pro.lib.core.PermissionManager;
import com.onurkol.app.browser.pro.tools.DateManager;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadFileListener implements DownloadListener, DownloadsSettings {
    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        // Get Context
        Context context= ContextManager.getManager().getContext();
        // Get Classes
        PermissionManager permissionManager=PermissionManager.getInstance();
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);

        if(permissionManager.getStoragePermission()){
            // Get File Info
            Uri fileUri=Uri.parse(url);
            File file=new File(String.valueOf(fileUri));
            // Set Download Manager Request
            DownloadManager.Request request = new DownloadManager.Request(fileUri);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            // Get Strings
            String downloading_file_text=context.getString(R.string.downloading_file_text);

            // Get File Name
            String fileName=file.getName();
            // Get Folder
            String downloadFolder=BrowserDefaultSettings.BROWSER_DOWNLOAD_FOLDER;
            String downloadFolderApi30Up=BrowserDefaultSettings.BROWSER_DOWNLOAD_FOLDER_API30_UP;
            // Get Download Date
            String downloadDate=DateManager.getDate();
            // Set Folder
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
                request.setDestinationInExternalPublicDir(downloadFolderApi30Up,fileName);
            else
                request.setDestinationInExternalPublicDir(downloadFolder,fileName);

            // Create Data
            DownloadsHelper.downloadsData=new DownloadsData(fileName, downloadFolder, downloadDate);
            // Enqueue
            downloadManager.enqueue(request);
            // Show Message
            Toast.makeText(context, downloading_file_text, Toast.LENGTH_LONG).show();
        }
        else{
            permissionManager.setStoragePermission();
        }
    }
}
