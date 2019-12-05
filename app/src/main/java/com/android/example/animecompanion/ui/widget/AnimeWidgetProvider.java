package com.android.example.animecompanion.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.service.AnimeWidgetRemoteViewsService;

public class AnimeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.anime_widget);

            remoteViews.setRemoteAdapter(R.id.rv_widget_anime,
                    new Intent(context, AnimeWidgetRemoteViewsService.class));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}
