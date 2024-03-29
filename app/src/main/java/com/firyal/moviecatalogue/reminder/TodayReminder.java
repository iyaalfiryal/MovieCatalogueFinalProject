package com.firyal.moviecatalogue.reminder;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;

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

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayReminder extends BroadcastReceiver {

    private final int REQUEST_CODE_RELEASE = 12;

    private List<ResultsItemMovie> releaseResultsList = new ArrayList<>();


    @Override
    public void onReceive(final Context context, Intent intent) {

        Date cal = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToday = dateFormat.format(cal);

        Server.getInitRetofit().getReleaseToday(dateToday, dateToday).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if (response.body() != null) {
                    releaseResultsList.addAll(response.body().getResults());
                    for (ResultsItemMovie r : releaseResultsList) {
                        String date = r.getReleaseDate();
                        if (date.equals(dateToday)) {
                            showAlarmNotification(context, context.getString(R.string.pengingat_rilis_film), r.getTitle(), 8);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                t.printStackTrace();
                Log.e("Ada Error", t.getMessage());
            }
        });

    }


    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Release channel";
        NotificationCompat.Builder builder;

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        android.app.Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public void setRepeatingAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TodayReminder.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }


    public void stopReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TodayReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

}
