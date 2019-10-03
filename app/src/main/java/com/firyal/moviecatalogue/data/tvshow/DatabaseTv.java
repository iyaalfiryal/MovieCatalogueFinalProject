package com.firyal.moviecatalogue.data.tvshow;

import android.content.Context;

import com.firyal.moviecatalogue.model.ResultsItemTv;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ResultsItemTv.class, version = 1)
public abstract class DatabaseTv extends RoomDatabase {
    public abstract TvshowDao tvshowDao();
    private static DatabaseTv databaseTv;

    public static DatabaseTv getDatabaseTv(Context context){
        synchronized (DatabaseTv.class){
            if (databaseTv == null){
                databaseTv = Room.databaseBuilder(context,
                        DatabaseTv.class, "db_tv").allowMainThreadQueries().build();
            }
        }return databaseTv;
    }
}
