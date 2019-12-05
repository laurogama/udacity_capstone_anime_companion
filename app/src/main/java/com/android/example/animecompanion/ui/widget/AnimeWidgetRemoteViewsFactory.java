package com.android.example.animecompanion.ui.widget;

import android.app.Application;
import android.content.Context;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;

import java.util.ArrayList;
import java.util.Objects;

public class AnimeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Anime> mAnimeList = new ArrayList<>();

    public AnimeWidgetRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        mAnimeList = (ArrayList<Anime>) Objects.requireNonNull(
                Repository.getInstance((Application) mContext.getApplicationContext())
                        .getMyAnimeList().getValue());
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        mAnimeList = null;
    }

    @Override
    public int getCount() {
        return mAnimeList != null ? mAnimeList.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mAnimeList.isEmpty()) {
            return null;
        }
        try {
            Anime anime = mAnimeList.get(position);
            RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_anime_list_item);
            rv.setTextViewText(R.id.tv_widget_item, anime.getTitle());
            return rv;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
