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
import com.onurkol.app.browser.pro.data.developer.RequestData;
import com.onurkol.app.browser.pro.libs.CharLimiter;

import java.util.ArrayList;

public class RequestListAdapter extends ArrayAdapter<RequestData> {
    private final LayoutInflater inflater;
    private final Context mContext;
    private ViewHolder holder;
    private ArrayList<RequestData> mRequestData;

    public RequestListAdapter(@NonNull Context context, ArrayList<RequestData> requestDataList){
        super(context, 0, requestDataList);
        inflater=LayoutInflater.from(context);
        mContext=context;
        mRequestData=requestDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_developer_request_data, null);
            holder = new ViewHolder();
            holder.requestUrl=convertView.findViewById(R.id.requestUrl);
            holder.requestType=convertView.findViewById(R.id.requestType);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        // Get Data
        RequestData data=mRequestData.get(position);

        String getUrl=CharLimiter.Limit(data.getRequest().getUrl().toString(),70);
        String getType=data.getRequest().getMethod();
        holder.requestUrl.setText(getUrl);
        holder.requestType.setText(getType);

        return convertView;
    }

    private static class ViewHolder {
        TextView requestUrl, requestType;
    }
}
