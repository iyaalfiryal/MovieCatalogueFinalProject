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
import com.firyal.moviecatalogue.adapter.AdapterMovie;
import com.firyal.moviecatalogue.model.ResponseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;
import com.firyal.moviecatalogue.viewmodel.MovieVM;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rv1;
    private AdapterMovie adapterMovie;
    ArrayList<ResultsItemMovie> resultsItemMoviesArray = new ArrayList<>();
    MovieVM movieVM;
    private FragmentManager fragmentManager;
    private ProgressDialog pb;
    private SearchView sv;


    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public static MovieFragment newInstance(FragmentManager fragmentManager){
        MovieFragment fragment = new MovieFragment();
        fragment.setFragmentManager(fragmentManager);
        return fragment;
    }

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        rv1 = v.findViewById(R.id.rv1);
        sv = v.findViewById(R.id.searchMovi);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchMovie.class));
            }
        });

        pb = new ProgressDialog(getActivity());
        pb.setMessage(getString(R.string.loading));
        pb.show();

        movieVM = ViewModelProviders.of(this).get(MovieVM.class);
        movieVM.initMovie();
        movieVM.getMovieModel().observe(this, new Observer<ResponseMovie>() {
            @Override
            public void onChanged(ResponseMovie responseMovie) {
                pb.dismiss();
                List<ResultsItemMovie> resultsItemMovies = responseMovie.getResults();
                resultsItemMoviesArray.addAll(resultsItemMovies);
                adapterMovie.notifyDataSetChanged();
            }
        });

        setupRv1();
    }

    private void setupRv1() {
        if (adapterMovie == null){
            adapterMovie = new AdapterMovie(getActivity(), resultsItemMoviesArray);
            rv1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv1.setAdapter(adapterMovie);
            rv1.setItemAnimator(new DefaultItemAnimator());
            rv1.setNestedScrollingEnabled(true);
        }else {
            adapterMovie.notifyDataSetChanged();
        }
    }
}
