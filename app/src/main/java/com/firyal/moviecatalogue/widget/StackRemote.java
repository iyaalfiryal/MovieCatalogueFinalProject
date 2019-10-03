package com.firyal.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.data.movie.DatabaseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemote implements RemoteViewsService.RemoteViewsFactory {
    
    private List<ResultsItemMovie> resultsItemMovies = new ArrayList<>();
    private final Context mcontext;

    StackRemote( Context mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    public void onCreate() {
        
    }

    @Override
    public void onDataSetChanged() {
        DatabaseMovie databaseMovie = DatabaseMovie.getDatabaseMovie(mcontext);
        resultsItemMovies = databaseMovie.movieDao().getFavoriteMovie();

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int getCount() {
        return resultsItemMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mcontext.getPackageName(), R.layout.widget_item);

        try {
            Bitmap bitmap = Glide.with(mcontext)
                    .asBitmap()
                    .load(Constant.URLIMAGE + resultsItemMovies.get(i)
                            .getPosterPath())
                    .apply(new RequestOptions().fitCenter()).submit().get();

            remoteViews.setImageViewBitmap(R.id.imgWidget, bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(Constant.EXTRA_ITEM, i);
        Intent in = new Intent();
        in.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imgWidget, in);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
