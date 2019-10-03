package com.firyal.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firyal.moviecatalogue.model.ResponseTv;

public class TvVm extends ViewModel {
    private MutableLiveData<ResponseTv>responseTvMutableLiveData;
    private  TvRepo tvRepo;

    public void initTv(){
        if (responseTvMutableLiveData != null){
            return;
        }

        tvRepo = TvRepo.getInstance();
        responseTvMutableLiveData = tvRepo.getTv();
    }

    public LiveData<ResponseTv> getTvModel(){
        return responseTvMutableLiveData;
    }
}
