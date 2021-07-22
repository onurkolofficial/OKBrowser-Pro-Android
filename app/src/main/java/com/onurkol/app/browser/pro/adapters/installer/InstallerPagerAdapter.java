package com.onurkol.app.browser.pro.adapters.installer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.onurkol.app.browser.pro.fragments.installer.InstallerCompletedFragment;
import com.onurkol.app.browser.pro.fragments.installer.InstallerLanguageFragment;
import com.onurkol.app.browser.pro.fragments.installer.InstallerPermissionsFragment;
import com.onurkol.app.browser.pro.fragments.installer.InstallerThemeFragment;
import com.onurkol.app.browser.pro.fragments.installer.InstallerWelcomeFragment;

public class InstallerPagerAdapter extends FragmentStateAdapter {
    public static int pageCount=5;

    public InstallerPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
            default:
                return new InstallerWelcomeFragment(); // Default
            case 1:
                return new InstallerLanguageFragment();
            case 2:
                return new InstallerThemeFragment();
            case 3:
                return new InstallerPermissionsFragment();
            case 4:
                return new InstallerCompletedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return pageCount;
    }
}
