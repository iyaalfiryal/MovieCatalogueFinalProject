package com.firyal.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firyal.moviecatalogue.model.ResponseMovie;

public class MovieVM extends ViewModel {
    private MutableLiveData<ResponseMovie> responseMovieMutableLiveData;
    private MovieRepo movieRepo;

    public void initMovie(){
        if (responseMovieMutableLiveData != null){
            return;
        }

        movieRepo = MovieRepo.getInstance();
        responseMovieMutableLiveData = movieRepo.getMovies();

    }

    public LiveData<ResponseMovie> getMovieModel(){
        return responseMovieMutableLiveData;
    }
}
