package com.firyal.moviecatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.adapter.AdapterMovie;
import com.firyal.moviecatalogue.api.ApiInterface;
import com.firyal.moviecatalogue.api.Server;
import com.firyal.moviecatalogue.model.ResponseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.ArrayList;
import java.util.List;


public class SearchMovie extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView searchMovie;
    RecyclerView rv;
    ProgressBar pb;
    private AdapterMovie adapterMovie;
    List<ResultsItemMovie> resultsItemMoviesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchMovie = findViewById(R.id.searchMovie);
        rv = findViewById(R.id.rvSearch);
        pb = findViewById(R.id.pbSearch);

        searchMovie.setQueryHint("Cari Film");
        searchMovie.setOnQueryTextListener(this);
        searchMovie.setIconified(false);
        searchMovie.clearFocus();

        setRv();
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        rv.setVisibility(View.GONE);
        showLoading(true);

        Server.getInitRetofit().searchMovie(s).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                showLoading(false);
                rv.setVisibility(View.VISIBLE);
                if((response.body()!= null? response.body().getResults(): null) != null){
                    ResponseMovie responseMovie = response.body();
                    resultsItemMoviesArray = responseMovie.getResults();
                    adapterMovie = new AdapterMovie(SearchMovie.this, resultsItemMoviesArray);
                    rv.setAdapter(adapterMovie);
                }else {
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

    private void setRv() {
        if (adapterMovie == null){
            adapterMovie = new AdapterMovie(this, resultsItemMoviesArray);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapterMovie);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setNestedScrollingEnabled(true);
        }else{
            adapterMovie.notifyDataSetChanged();
        }
    }

    private void showLoading(boolean b) {
        if (b){
            pb.setVisibility(View.VISIBLE);
        }else {
            pb.setVisibility(View.GONE);
        }
    }
}
