package com.firyal.moviecatalogue.data.tvshow;

import com.firyal.moviecatalogue.model.ResultsItemTv;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TvshowDao {
    @Query("SELECT * FROM tb_tv")
    List<ResultsItemTv> getFavoriteTvshow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavTv(ResultsItemTv resultsItemTv);

    @Query("DELETE FROM tb_tv WHERE id=:id")
    int deleteByIdTv(long id);

    @Query("SELECT * FROM tb_tv WHERE id= :id")
    ResultsItemTv selectItemId(String id);
}
