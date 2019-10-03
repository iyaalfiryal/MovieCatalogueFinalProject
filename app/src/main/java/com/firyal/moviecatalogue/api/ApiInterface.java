package com.firyal.moviecatalogue.api;

import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.model.ResponseMovie;
import com.firyal.moviecatalogue.model.ResponseTv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("discover/movie?api_key=" + Constant.APIKEY + "&language=en_US")
    Call<ResponseMovie> getMovie();

    @GET("discover/tv?api_key=" + Constant.APIKEY + "&language=en_US")
    Call<ResponseTv> getTv();

    @GET("search/movie?api_key=" + Constant.APIKEY + "&language=en_US")
    Call<ResponseMovie> searchMovie(@Query("query") String movie);

    @GET("search/movie?api_key=" + Constant.APIKEY + "&language=en_US")
    Call<ResponseTv> searchTv(@Query("query") String tvshow);

    @GET("discover/movie?api_key=" + Constant.APIKEY)
    Call<ResponseMovie>getReleaseToday(@Query("primary_release_date.gte") String tanggal1,
                                       @Query("primary_release_date.lte") String tanggal2);
}
