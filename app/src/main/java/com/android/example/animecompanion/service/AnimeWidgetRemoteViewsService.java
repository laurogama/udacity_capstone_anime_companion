package com.android.example.animecompanion.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.android.example.animecompanion.ui.widget.AnimeWidgetRemoteViewsFactory;

public class AnimeWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AnimeWidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
