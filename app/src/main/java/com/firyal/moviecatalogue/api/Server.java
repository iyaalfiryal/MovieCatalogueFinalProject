package com.firyal.moviecatalogue.api;

import com.firyal.moviecatalogue.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    private static Retrofit setInitRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return  new Retrofit.Builder()
                .baseUrl(Constant.URLMOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    public static ApiInterface getInitRetofit(){
        return setInitRetrofit().create(ApiInterface.class);
    }
}
