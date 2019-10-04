package com.firyal.moviecatalogue.data.movie;

import android.database.Cursor;

import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM tb_movie")
    List<ResultsItemMovie> getFavoriteMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFav(ResultsItemMovie resultsItemMovie);

    @Query("DELETE FROM tb_movie WHERE id=:id")
    int deleteByIdMovie(long id);

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    ResultsItemMovie selectItemId(String id);

    //cursor
    @Query("SELECT * FROM tb_movie WHERE id = :id")
    Cursor selectItem(long id);

    @Query("SELECT * FROM tb_movie")
    Cursor getFavoriteCursor();




}
