package com.onurkol.app.browser.pro.adapters.developer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.tools.CharLimiter;

import java.util.ArrayList;

public class RequestListAdapter extends ArrayAdapter<WebResourceRequest> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static ArrayList<WebResourceRequest> requestList;
    private final ListView requestListView;

    public RequestListAdapter(Context context, ListView listView, ArrayList<WebResourceRequest> data){
        super(context, 0, data);
        requestListView=listView;
        requestList=data;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.item_developer_requests, null);
            holder=new ViewHolder();
            holder.requestUrl=convertView.findViewById(R.id.requestUrl);
            holder.requestType=convertView.findViewById(R.id.requestType);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        // Get Data
        WebResourceRequest webRequest=requestList.get(position);

        // Char Limits
        String getUrl=CharLimiter.Limit(webRequest.getUrl().toString(),70);
        String getType=webRequest.getMethod();

        // Set Request Url
        holder.requestUrl.setText(getUrl);
        holder.requestType.setText(getType);

        return convertView;
    }

    //View Holder
    private static class ViewHolder {
        TextView requestUrl, requestType;
    }
}
