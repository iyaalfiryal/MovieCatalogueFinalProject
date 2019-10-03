package com.firyal.moviecatalogue.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.firyal.moviecatalogue.api.ApiInterface;
import com.firyal.moviecatalogue.api.Server;
import com.firyal.moviecatalogue.model.ResponseMovie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepo {
    private static MovieRepo movieRepo;
    private Context context;

    public static MovieRepo getInstance(){
        if (movieRepo == null){
            movieRepo = new MovieRepo();
        }

        return movieRepo;
    }

    private ApiInterface apiInterface;

    public MovieRepo(){
        apiInterface = Server.getInitRetofit();
    }

    public MutableLiveData<ResponseMovie> getMovies(){
        final MutableLiveData<ResponseMovie> movieData = new MutableLiveData<>();
        apiInterface.getMovie().enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if ((response.body() != null ? response.body().getPage():0)>0){
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }
}
