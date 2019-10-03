package com.firyal.moviecatalogue.activity.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.data.tvshow.DatabaseTv;
import com.firyal.moviecatalogue.model.ResultsItemTv;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

public class DetailTv extends AppCompatActivity {
    ImageView img2;
    TextView title2, time2, desc2;
    private ProgressDialog pb2;

    MaterialFavoriteButton materialFavoriteButton;
    private DatabaseTv databaseTv;
    private ResultsItemTv resultsItemTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        materialFavoriteButton = findViewById(R.id.btnFavTv);
        resultsItemTv = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
        databaseTv = DatabaseTv.getDatabaseTv(this);
        if (databaseTv.tvshowDao().selectItemId(String.valueOf(resultsItemTv.getId()))!= null){
            materialFavoriteButton.setFavorite(true);
        }

        setInit();
        pb2 = new ProgressDialog(this);
        pb2.setMessage(getString(R.string.loading));
        pb2.show();

        if(savedInstanceState != null){
            String result = savedInstanceState.getString(Constant.EXTRA_RESULT);
            Glide.with(this).load(result).into(img2);
            desc2.setText(result);
            time2.setText(result);
            title2.setText(result);
            pb2.dismiss();
        }

        setUpDelay();
        setFavChangeListener();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        ResultsItemTv resultsItemTv = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
        outState.putString(Constant.EXTRA_RESULT, resultsItemTv.getBackdropPath());
        outState.putString(Constant.EXTRA_RESULT, resultsItemTv.getName());
        outState.putString(Constant.EXTRA_RESULT, resultsItemTv.getPosterPath());
        outState.putString(Constant.EXTRA_RESULT, resultsItemTv.getOverview());
    }

    private void setUpDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb2.dismiss();
                ResultsItemTv resultsItemTv = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
                getSupportActionBar().setTitle(" " + resultsItemTv.getName());
                Glide.with(DetailTv.this).load(Constant.URLIMAGE + resultsItemTv.getPosterPath()).into(img2);
                title2.setText(resultsItemTv.getName());
                time2.setText(resultsItemTv.getFirstAirDate());
                desc2.setText(resultsItemTv.getOverview());

            }
        }, 2000);
    }

    private void setInit() {
        img2 = findViewById(R.id.img2);
        title2 = findViewById(R.id.title2);
        time2 = findViewById(R.id.time2);
        desc2 = findViewById(R.id.desc2);
    }

    //fav
    private void setFavChangeListener(){
        materialFavoriteButton.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite){
                            resultsItemTv = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
                            databaseTv = DatabaseTv.getDatabaseTv(getApplicationContext());
                            databaseTv.tvshowDao().insertFavTv(resultsItemTv);
                            Toast.makeText(DetailTv.this, getString(R.string.sukses), Toast.LENGTH_SHORT).show();
                        }else {
                            databaseTv = DatabaseTv.getDatabaseTv(getApplicationContext());
                            databaseTv.tvshowDao().deleteByIdTv(resultsItemTv.getId());
                            Toast.makeText(DetailTv.this, getString(R.string.gagal), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }
}
