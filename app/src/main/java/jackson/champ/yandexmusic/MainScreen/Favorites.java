package jackson.champ.yandexmusic.MainScreen;

import android.database.Cursor;
import android.os.Bundle;
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


public class Favorites extends android.support.v4.app.Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>, OnTaskCompleted{

    private static final String TAG = "Favorives" ;
    private RecyclerView mRecyclerView;
    public static SimpleCursorRecyclerAdapter recyclerAdapter;
    public static final int LOADER_ID = 1;
    public Database mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ArtistsFav = inflater.inflate(R.layout.artists_fav, container, false);
        Log.d(TAG, "onCreateView: In onCreateView  Favorites");
        mDb = new Database(getActivity());
        mDb.open();

        /**
         * Создаём LoaderManager
         */
        getActivity().getLoaderManager().initLoader(LOADER_ID,null, this);

        mRecyclerView = (RecyclerView) ArtistsFav.findViewById(R.id.favedArtists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        loadingDataFromDB();
        return ArtistsFav;
    }

    public void loadingDataFromDB()
    {

        /**
         * from - из каких колоннок БД мы забираем данные
         * to  -  в какие места карточки их отправляем
         */
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
        recyclerAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        loader.forceLoad();
    }

    @Override
    public void onTaskCompleted() {
        ArtistsList.adapter.notifyDataSetChanged();
        getActivity().getLoaderManager().getLoader(LOADER_ID).forceLoad();
    }

    @Override
    public void onDestroy() {
        mDb.close();
        super.onDestroy();
    }
}
