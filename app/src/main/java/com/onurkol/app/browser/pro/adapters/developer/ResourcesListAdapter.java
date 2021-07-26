package com.onurkol.app.browser.pro.adapters.developer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.tools.CharLimiter;

import java.util.ArrayList;

public class ResourcesListAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static ArrayList<String> resourceList;
    private final ListView resourcesListView;

    public ResourcesListAdapter(Context context, ListView listView, ArrayList<String> data){
        super(context, 0, data);
        resourcesListView=listView;
        resourceList=data;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resourceList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.item_developer_resources, null);
            holder=new ViewHolder();
            holder.requestUrl=convertView.findViewById(R.id.resourceUrl);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        // Get Data
        String webResourceUrl=resourceList.get(position);
        // Char Limits
        String getUrl=CharLimiter.Limit(webResourceUrl,70);
        // Set Request Url
        holder.requestUrl.setText(getUrl);

        return convertView;
    }

    //View Holder
    private static class ViewHolder {
        TextView requestUrl, requestType;
    }
}
