package com.firyal.moviecatalogue.favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.adapter.AdapterTabLayout;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private FragmentManager fragmentManager;


    public FavoriteFragment() {
        // Ruequired empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPager(view);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static FavoriteFragment newInstance(FragmentManager fragmentManager) {
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setFragmentManager(fragmentManager);
        return fragment;
    }

    private void setPager(View view) {
        ViewPager viewPager = view.findViewById(R.id.vp_containerELearning);
        setupViewPager(viewPager);

        TabLayout tabs = view.findViewById(R.id.tl_tabsAbsence);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterTabLayout tabLayoutAdapter = new AdapterTabLayout(getChildFragmentManager());
        tabLayoutAdapter.addFragment(FavMovieFragment.newInstance(fragmentManager), getString(R.string.movie));
        tabLayoutAdapter.addFragment(FavTvshowFragment.newInstance(fragmentManager), getString(R.string.tv));
        viewPager.setAdapter(tabLayoutAdapter);
    }

}
