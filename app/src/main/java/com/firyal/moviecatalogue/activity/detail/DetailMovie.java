package com.firyal.moviecatalogue.activity.detail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.data.movie.DatabaseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

public class DetailMovie extends AppCompatActivity {
    ImageView img;
    TextView title, time, desc;
    private ProgressDialog pb;


    //fav
    MaterialFavoriteButton materialFavoriteButton;

    private DatabaseMovie databaseMovie;
    private ResultsItemMovie resultsItemMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //fav
        materialFavoriteButton = findViewById(R.id.btnFavMovie);
        resultsItemMovie = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
        databaseMovie = DatabaseMovie.getDatabaseMovie(this);
        if (databaseMovie.movieDao().selectItemId(String.valueOf(resultsItemMovie.getId()))!= null){
            materialFavoriteButton.setFavorite(true);
        }

        setInitMovie();
        pb = new ProgressDialog(this);
        pb.setMessage(getString(R.string.loading));
        pb.show();

        if (savedInstanceState != null){
            String result = savedInstanceState.getString(Constant.EXTRA_RESULT);
            Glide.with(this).load(result).into(img);
            desc.setText(result);
            time.setText(result);
            title.setText(result);
            pb.dismiss();
        }

        setUpDelay();
        setFavChangeListener();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        ResultsItemMovie resultsItemMovie = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
        outState.putString(Constant.EXTRA_RESULT, resultsItemMovie.getBackdropPath());
        outState.putString(Constant.EXTRA_RESULT, resultsItemMovie.getTitle());
        outState.putString(Constant.EXTRA_RESULT, resultsItemMovie.getReleaseDate());
        outState.putString(Constant.EXTRA_RESULT, resultsItemMovie.getOverview());


    }

    private void setUpDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb.dismiss();
                ResultsItemMovie resultsItemMovie = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
                getSupportActionBar().setTitle("" + resultsItemMovie.getTitle() );
                Glide.with(DetailMovie.this).load(Constant.URLIMAGE + resultsItemMovie.getPosterPath()).into(img);
                title.setText(resultsItemMovie.getTitle());
                time.setText(resultsItemMovie.getReleaseDate());
                desc.setText(resultsItemMovie.getOverview());
            }
        }, 2000);
    }

    private void setInitMovie() {
        img = findViewById(R.id.img);
        time = findViewById(R.id.time) ;
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
    }

    //fav
    private void setFavChangeListener(){
        materialFavoriteButton.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite){
                            resultsItemMovie = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
                            databaseMovie = DatabaseMovie.getDatabaseMovie(getApplicationContext());
                            databaseMovie.movieDao().insertFav(resultsItemMovie);
                            Toast.makeText(DetailMovie.this, getString(R.string.sukses), Toast.LENGTH_SHORT).show();
                        }else {
                            databaseMovie = DatabaseMovie.getDatabaseMovie(getApplicationContext());
                            databaseMovie.movieDao().deleteByIdMovie(resultsItemMovie.getId());
                            Toast.makeText(DetailMovie.this, getString(R.string.gagal), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

}
