package com.firyal.moviecatalogue.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.activity.SearchMovie;
import com.firyal.moviecatalogue.activity.SearchTv;
import com.firyal.moviecatalogue.adapter.AdapterTv;
import com.firyal.moviecatalogue.model.ResponseTv;
import com.firyal.moviecatalogue.model.ResultsItemTv;
import com.firyal.moviecatalogue.viewmodel.TvVm;

import java.util.ArrayList;
import java.util.List;

public class TvFragment extends Fragment {

    private RecyclerView rv2;
    private AdapterTv adapterTv;
    ArrayList<ResultsItemTv>resultsItemTvArrayList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private ProgressDialog pb;
    TvVm tvVm;
    private SearchView svTv;

    public void setTvFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static TvFragment newInstance(FragmentManager fragmentManager){
        TvFragment fragment = new TvFragment();
        return fragment;
    }
    
    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        rv2 = v.findViewById(R.id.rv2);
        svTv = v.findViewById(R.id.searchTv);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        pb = new ProgressDialog(getActivity());
        pb.setMessage(getString(R.string.loading));
        pb.show();

        svTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchTv.class));
            }
        });

        tvVm = ViewModelProviders.of(this).get(TvVm.class);
        tvVm.initTv();
        tvVm.getTvModel().observe(this, new Observer<ResponseTv>() {
            @Override
            public void onChanged(ResponseTv responseTv) {
                pb.dismiss();
                List<ResultsItemTv>resultsItemTvs = responseTv.getResults();
                resultsItemTvArrayList.addAll(resultsItemTvs);
                adapterTv.notifyDataSetChanged();
            }
        });
        
        setUpRv2();
    }

    private void setUpRv2() {
        
        if (adapterTv == null){
            adapterTv = new AdapterTv(getActivity(), resultsItemTvArrayList);
            rv2.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv2.setAdapter(adapterTv);
            rv2.setItemAnimator(new DefaultItemAnimator());
            rv2.setNestedScrollingEnabled(true);
        }else {
            adapterTv.notifyDataSetChanged();
        }
    }
}
