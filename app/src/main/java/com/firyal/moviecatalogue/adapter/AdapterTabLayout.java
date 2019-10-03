package com.firyal.moviecatalogue.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterTabLayout extends FragmentPagerAdapter {

    private Context context;
    private FragmentManager fragmentManager;
    private final List<Fragment> mfragments = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public AdapterTabLayout(FragmentManager fm) {
        super(fm);
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int i) {
        return mfragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mfragments.add(fragment);
        mFragmentTitleList.add(title);

    }

    public void reapleaceFragment(int i, Fragment fragment, String title) {
        mfragments.remove(i);
        mFragmentTitleList.remove(i);
        addFragment(fragment, title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
