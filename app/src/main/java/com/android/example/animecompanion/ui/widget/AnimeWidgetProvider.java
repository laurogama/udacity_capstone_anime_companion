package com.android.example.animecompanion.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.service.AnimeWidgetRemoteViewsService;
import com.android.example.animecompanion.ui.detail.DetailActivity;

import static com.android.example.animecompanion.ui.widget.AnimeWidgetRemoteViewsFactory.MY_ON_CLICK_TAG;

public class AnimeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.anime_widget);
            remoteViews.setRemoteAdapter(R.id.rv_widget_anime,
                    new Intent(context, AnimeWidgetRemoteViewsService.class));

            remoteViews.setPendingIntentTemplate(R.id.rv_widget_anime,
                    getPendingSelfIntent(context));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.setAction(MY_ON_CLICK_TAG);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}
