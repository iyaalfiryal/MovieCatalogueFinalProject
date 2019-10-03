package com.firyal.moviecatalogue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.adapter.AdapterMovie;
import com.firyal.moviecatalogue.api.Server;
import com.firyal.moviecatalogue.fragment.MovieFragment;
import com.firyal.moviecatalogue.fragment.TvFragment;
import com.firyal.moviecatalogue.model.ResponseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTv extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView rvtv;
    ProgressBar pb;
    SearchView sv;
    private TvFragment tvFragment;

    private AdapterMovie adapterMovie;
    List<ResultsItemMovie> resultsItemMovieArrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        sv = findViewById(R.id.searchTv);
        rvtv = findViewById(R.id.rvSearch);
        pb = findViewById(R.id.pbSearch);

        sv.setQueryHint("Cari Film");
        sv.setOnQueryTextListener(this);
        sv.setIconified(false);
        sv.clearFocus();

        setRvTv();
    }

    private void showLoading(Boolean state) {
        if (state) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    private void setRvTv() {
        if (adapterMovie == null) {
        adapterMovie = new AdapterMovie(this, resultsItemMovieArrayList);
        rvtv.setLayoutManager(new LinearLayoutManager(this));
        rvtv.setAdapter(adapterMovie);
        rvtv.setItemAnimator(new DefaultItemAnimator());
        rvtv.setNestedScrollingEnabled(true);
    } else {
        adapterMovie.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        rvtv.setVisibility(View.GONE);
        showLoading(true);

        Server.getInitRetofit().searchMovie(newText).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                showLoading(false);
                rvtv.setVisibility(View.VISIBLE);
                if ((response.body() != null ? response.body().getResults() : null) != null) {
                    ResponseMovie responseMovie = response.body();
                    resultsItemMovieArrayList = responseMovie.getResults();
                    adapterMovie = new AdapterMovie(SearchTv.this, resultsItemMovieArrayList);
                    rvtv.setAdapter(adapterMovie);
                } else {
                    showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                showLoading(false);
            }
        });
        return true;
    }
}
