package com.firyal.moviecatalogue.reminder;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.api.Server;
import com.firyal.moviecatalogue.model.ResponseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    Switch sRelease, sDaily;
    private DailyReminder dailyReminder;
    private TodayReminder todayReminder;

    List<ResultsItemMovie>resultsItemMovies = new ArrayList<>();
    String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        sRelease = findViewById(R.id.swicRealese);
        sDaily = findViewById(R.id.swicDaily);

        if (Hawk.contains("daily")){
            sDaily.setChecked(true);
        }else {
            sDaily.setChecked(false);
        }

        if (Hawk.contains("release")){
            sRelease.setChecked(true);
        }else {
            sRelease.setChecked(false);
        }

        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        formatDate = dateFormat.format(date);

        dailyReminder = new DailyReminder();
        todayReminder = new TodayReminder();

        getReleaseToday();
        setDailyReminder();
        setSwichDaily();
    }

    private void setSwichDaily() {
        sDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    dailyReminder.setRepeatingAlrm(ReminderActivity.this);
                    Hawk.put("daily", "");
                }else {
                    dailyReminder.cancelAlarm(ReminderActivity.this);
                    Hawk.delete("daily");
                }
            }
        });
    }

    private void setDailyReminder() {
        sRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    todayReminder.setRepeatingAlarm(ReminderActivity.this, resultsItemMovies);
                    Hawk.put("release", "");

                }else {
                    todayReminder.cancelAlarm(ReminderActivity.this);
                    Hawk.delete("release");
                }
            }
        });
    }

    private void getReleaseToday() {
        Server.getInitRetofit().getReleaseToday(formatDate, formatDate).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if (response.isSuccessful()){
                    ResponseMovie responseMovie = response.body();
                    resultsItemMovies = responseMovie.getResults();
                }else {
                    Toast.makeText(ReminderActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                Toast.makeText(ReminderActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
