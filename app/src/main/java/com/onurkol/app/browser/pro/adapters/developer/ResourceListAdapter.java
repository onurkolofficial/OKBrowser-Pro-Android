package com.onurkol.app.browser.pro.adapters.developer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.data.developer.ResourceData;
import com.onurkol.app.browser.pro.libs.CharLimiter;

import java.util.ArrayList;

public class ResourceListAdapter extends ArrayAdapter<ResourceData> {
    private final LayoutInflater inflater;
    private final Context mContext;
    private ViewHolder holder;
    private ArrayList<ResourceData> mResourceData;

    public ResourceListAdapter(@NonNull Context context, ArrayList<ResourceData> resourceDataList){
        super(context, 0, resourceDataList);
        inflater=LayoutInflater.from(context);
        mContext=context;
        mResourceData=resourceDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_developer_resource_data, null);
            holder = new ViewHolder();
            holder.resourceUrl=convertView.findViewById(R.id.resourceUrl);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        // Get Data
        ResourceData data=mResourceData.get(position);

        String getUrl=CharLimiter.Limit(data.getResourceUrl(),70);
        holder.resourceUrl.setText(getUrl);

        return convertView;
    }

    private static class ViewHolder {
        TextView resourceUrl;
    }
}
