package com.firyal.moviecatalogue.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.data.movie.DatabaseMovie;
import com.firyal.moviecatalogue.data.movie.MovieDao;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.firyal.moviecatalogue.Constant.AUTHORITY;

@SuppressLint("Registered")
public class MovieProvider extends ContentProvider {
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, "tb_movie", Constant.CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, "tb_movie" + "/*", Constant.CODE_MOVIE_ITEM);

    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        if (code == Constant.CODE_MOVIE_DIR || code == Constant.CODE_MOVIE_ITEM){
            final Context context = getContext();
            if (context == null){
                return null;
            }

            MovieDao movieDao = DatabaseMovie.getDatabaseMovie(context).movieDao();
            final Cursor cursor;
            if (code == Constant.CODE_MOVIE_DIR){
                cursor =  movieDao.getFavoriteCursor();
            } else {
                cursor = movieDao.selectItem(ContentUris.parseId(uri));
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;

        } else {
            throw  new IllegalArgumentException("unknown URI: " + uri);

        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)){
            case Constant.CODE_MOVIE_DIR:
                return "vnd.android.cursor.dir/" + Constant.AUTHORITY + "." + "tb_movie";
            case Constant.CODE_MOVIE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + "tb_movie";

                default:
                    throw  new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (MATCHER.match(uri)){
            case Constant.CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null){
                    return null;
                }
                final long id = DatabaseMovie.getDatabaseMovie(context).movieDao().insertFav(ResultsItemMovie.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case Constant.CODE_MOVIE_ITEM:
                throw  new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)){
            case Constant.CODE_MOVIE_DIR:
                throw  new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case Constant.CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null){
                    return 0;
                }
                final  int count = DatabaseMovie.getDatabaseMovie(context).movieDao()
                        .deleteByIdMovie(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
                default:
                    throw  new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
