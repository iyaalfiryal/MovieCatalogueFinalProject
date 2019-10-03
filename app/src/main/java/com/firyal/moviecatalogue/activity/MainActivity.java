package com.firyal.moviecatalogue.activity;

import android.content.Intent;
import android.os.Bundle;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.favorite.FavoriteFragment;
import com.firyal.moviecatalogue.fragment.MovieFragment;
import com.firyal.moviecatalogue.fragment.TvFragment;
import com.firyal.moviecatalogue.reminder.ReminderActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,new MovieFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,new TvFragment()).commit();
                    return true;
                case R.id.nav_fav:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new FavoriteFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.reminder:
                Intent in = new Intent(this, ReminderActivity.class);
                startActivity(in);
                break;

    }
        return super.onOptionsItemSelected(item);
    }


}
