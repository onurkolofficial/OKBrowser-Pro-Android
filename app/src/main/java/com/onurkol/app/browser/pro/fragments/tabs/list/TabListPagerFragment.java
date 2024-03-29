package com.onurkol.app.browser.pro.fragments.tabs.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.onurkol.app.browser.pro.R;
import com.onurkol.app.browser.pro.adapters.tabs.TabListAdapter;
import com.onurkol.app.browser.pro.controller.tabs.TabController;

public class TabListPagerFragment extends Fragment {
    TabController tabController;

    GridView tabListGridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tabController=TabController.getController(requireActivity());

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.screen_tab_list, container, false);

        tabListGridView=view.findViewById(R.id.tabListGridView);

        tabListGridView.setAdapter(new TabListAdapter(requireActivity(), tabController.getTabList()));

        return view;
    }
}
