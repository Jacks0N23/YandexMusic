package jackson.champ.yandexmusic.MainScreen;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jackson.champ.yandexmusic.DB.Database;
import jackson.champ.yandexmusic.DB.FavCursorLoader;
import jackson.champ.yandexmusic.DB.SimpleCursorRecyclerAdapter;
import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.OnTaskCompleted;

/**
 * Created by jackson on 29.03.16.
 */
public class Favorives extends android.support.v4.app.Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>, OnTaskCompleted{

    private static final String TAG = "Favorives" ;
    private RecyclerView mRecyclerView;
    public static SimpleCursorRecyclerAdapter recyclerAdapter;
    public static final int LOADER_ID = 1;
    public Database mDb;
    public static SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ArtistsFav = inflater.inflate(R.layout.artists_fav, container, false);
        Log.d(TAG, "onCreateView: In onCreateView  Favorives");
        mDb = new Database(getActivity());
        mDb.open();
        getActivity().getLoaderManager().initLoader(LOADER_ID,null, this);

        swipeRefreshLayout = (SwipeRefreshLayout)ArtistsFav.findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onTaskCompleted();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView = (RecyclerView) ArtistsFav.findViewById(R.id.favedArtists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        loadingDataFromDB();
        return ArtistsFav;
    }

    public void loadingDataFromDB()
    {

        String[] from = new String[] {Database.KEY_NAME, Database.KEY_GENRES, Database.KEY_ALBUMS, Database.KEY_TRACKS, Database.KEY_SMALL_COVER};
        int[] to = new int[] {R.id.artist_name,R.id.genres, R.id.artist_albums, R.id.artist_tracks, R.id.artist_image};

        recyclerAdapter = new SimpleCursorRecyclerAdapter(getActivity(),R.layout.card, null, from,to, this);
        mRecyclerView.setAdapter(recyclerAdapter);
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new FavCursorLoader(getActivity(),mDb);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        recyclerAdapter.changeCursor(data);

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        loader.forceLoad();
    }

    @Override
    public void onTaskCompleted() {
        getActivity().getLoaderManager().getLoader(LOADER_ID).onContentChanged();
    }
}
