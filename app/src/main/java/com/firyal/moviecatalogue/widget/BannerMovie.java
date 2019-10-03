package com.firyal.moviecatalogue.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.R;

public class BannerMovie extends AppWidgetProvider {

    private static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWdgetId){
        Intent i = new Intent(context, StackWidget.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWdgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.banner_movie);
        remoteViews.setRemoteAdapter(R.id.stackViewWidget, i);
        remoteViews.setEmptyView(R.id.stackViewWidget, R.id.emptyWidget);

        Intent toastIn = new Intent( context, BannerMovie.class);
        toastIn.setAction(Constant.TOAST_ACTION);
        toastIn.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWdgetId);

        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, toastIn, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.stackViewWidget, pendingIntent);

        appWidgetManager.updateAppWidget(appWdgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds){
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Constant.TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(Constant.EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
