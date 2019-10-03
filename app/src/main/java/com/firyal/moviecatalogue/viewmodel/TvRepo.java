package com.firyal.moviecatalogue.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.firyal.moviecatalogue.api.ApiInterface;
import com.firyal.moviecatalogue.api.Server;
import com.firyal.moviecatalogue.model.ResponseTv;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvRepo {
    private static TvRepo tvRepo;
    private Context context;

    public static TvRepo getInstance(){
        if (tvRepo == null){
            tvRepo = new TvRepo();
        }
        return tvRepo;
    }
    private ApiInterface apiInterface;
    public TvRepo(){
        apiInterface = Server.getInitRetofit();
    }

    public MutableLiveData<ResponseTv> getTv(){
        final MutableLiveData<ResponseTv> tvData = new MutableLiveData<>();
        apiInterface.getTv().enqueue(new Callback<ResponseTv>() {
            @Override
            public void onResponse(Call<ResponseTv> call, Response<ResponseTv> response) {
                if ((response.body() != null? response.body()
                        .getPage():0)>0){
                    tvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTv> call, Throwable t) {
                tvData.setValue(null);
            }
        });
        return tvData;
    }
}
