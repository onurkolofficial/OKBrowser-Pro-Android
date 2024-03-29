package com.onurkol.app.browser.pro.adapters.settings;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.activity.MainActivity;
import com.onurkol.app.browser.pro.activity.browser.SettingsActivity;
import com.onurkol.app.browser.pro.controller.settings.DayNightModeController;
import com.onurkol.app.browser.pro.controller.settings.LanguageController;
import com.onurkol.app.browser.pro.controller.PreferenceController;
import com.onurkol.app.browser.pro.data.settings.SettingXMLDataWithIcon;
import com.onurkol.app.browser.pro.interfaces.BrowserDataInterface;

import java.util.ArrayList;

public class SettingsPreferenceListWithIconAdapter extends ArrayAdapter<SettingXMLDataWithIcon> implements BrowserDataInterface {
    private final LayoutInflater inflater;
    private final Context getContext;
    private ViewHolder holder;
    private static ArrayList<SettingXMLDataWithIcon> dataTypeList;

    // Change Settings and disabled all checkbox.
    ArrayList<CheckBox> checkBoxes=new ArrayList<>();

    // Variables
    String PREF_DATA_KEY=null;
    int preferenceData=0;
    boolean mSettingsPreference;

    PreferenceController preferenceController;
    LanguageController languageController;
    DayNightModeController dayNightModeController;

    public SettingsPreferenceListWithIconAdapter(@NonNull Context context, ArrayList<SettingXMLDataWithIcon> getDataList, boolean isSettingsPreference) {
        super(context, 0, getDataList);
        dataTypeList=getDataList;
        inflater=LayoutInflater.from(context);
        preferenceController=PreferenceController.getController();
        languageController=LanguageController.getController();
        dayNightModeController=DayNightModeController.getController();
        getContext=context;
        mSettingsPreference=isSettingsPreference;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            if(mSettingsPreference)
                convertView = inflater.inflate(R.layout.item_preferences_checkbox_icon_data, null);
            else
                convertView = inflater.inflate(R.layout.item_checkbox_icon_data, null);
            holder = new ViewHolder();
            holder.dataItemNameView = convertView.findViewById(R.id.dataItemNameView);
            holder.dataItemSelectLayoutButton = convertView.findViewById(R.id.dataItemSelectLayoutButton);
            holder.dataItemCheckbox = convertView.findViewById(R.id.dataItemCheckbox);
            holder.dataItemIcon = convertView.findViewById(R.id.dataItemIcon);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        // Add Checkboxes List
        checkBoxes.add(holder.dataItemCheckbox);

        // Get Data
        SettingXMLDataWithIcon dataType=dataTypeList.get(position);

        String getXmlDataString=dataType.getStringData();
        int getXmlDataInteger=dataType.getIntegerData();
        Drawable getXmlDataIcon=dataType.getIconData();

        // Write Data
        holder.dataItemNameView.setText(getXmlDataString);
        holder.dataItemIcon.setImageDrawable(getXmlDataIcon);

        // Check Preference
        if(dataType.getPreferenceKey()!=null){
            // Get Preference Key
            PREF_DATA_KEY=dataType.getPreferenceKey();
            // Get Preference Data (only Integer)
            preferenceData=preferenceController.getInt(PREF_DATA_KEY);

            // Check Checkbox
            if(preferenceData==getXmlDataInteger){
                if(!holder.dataItemCheckbox.isChecked())
                    holder.dataItemCheckbox.setChecked(true);
            }
            else
                holder.dataItemCheckbox.setChecked(false);
        }

        // Change Settings
        holder.dataItemSelectLayoutButton.setOnClickListener(view -> {
            // Unselect Checked Checkboxes
            for(int i=0; i<checkBoxes.size(); i++){
                if(checkBoxes.get(i).isChecked()) {
                    checkBoxes.get(i).setChecked(false);
                    break;
                }
            }
            // Select Current Checkbox
            ((CheckBox)view.findViewById(R.id.dataItemCheckbox)).setChecked(true);

            // Check Data, Save Preference
            if(PREF_DATA_KEY!=null) {
                preferenceController.setPreference(PREF_DATA_KEY, getXmlDataInteger);
                // Update Theme & Language
                if(!PREF_DATA_KEY.equals(KEY_SEARCH_ENGINE)) {
                    ((Activity)getContext).recreate();
                    setSettingState();
                }

                /*
                 *
                // Update Theme & Language (Alternative)
                switch (PREF_DATA_KEY) {
                    case KEY_DAY_NIGHT_MODE:
                        dayNightModeController.setDayNightMode(getXmlDataInteger);
                        ((Activity)getContext).recreate();
                        break;
                    case KEY_LANGUAGE:
                        languageController.setLanguage(getContext(), getXmlDataInteger);
                        ((Activity)getContext).recreate();
                        break;
                }
                 *
                 */
            }
        });
        return convertView;
    }

    private void setSettingState(){
        SettingsActivity.isSettingChanged=true;
        MainActivity.isSettingChanged=true;
    }

    @Override
    public int getCount() {
        return dataTypeList.size();
    }

    private static class ViewHolder {
        TextView dataItemNameView;
        LinearLayoutCompat dataItemSelectLayoutButton;
        CheckBox dataItemCheckbox;
        ImageView dataItemIcon;
    }
}
