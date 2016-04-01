package jackson.champ.yandexmusic.MainScreen;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import jackson.champ.yandexmusic.DB.Database;
import jackson.champ.yandexmusic.DB.SimpleCursorRecyclerAdapter;
import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.AdapterMain;
import jackson.champ.yandexmusic.Utils.Artist;
import jackson.champ.yandexmusic.Utils.FeedParser;
import jackson.champ.yandexmusic.Utils.OnTaskCompleted;

public class ArtistsList extends android.support.v4.app.Fragment implements OnTaskCompleted {

    private static final String TAG = "ArtistsList";
    String url = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    private static ArrayList<Artist> sArtists = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AdapterMain adapter;
    private LinearLayoutManager sLinearLayoutManager;
    private Database mDb;
    private ListView listView;
    SimpleCursorAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = new Database(getContext());
        mDb.open();

        View ArtistsList = inflater.inflate(R.layout.artists_list, container, false);

        mRecyclerView = (RecyclerView) ArtistsList.findViewById(R.id.feed_recycler_view);

        sLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(sLinearLayoutManager);
        adapter = new AdapterMain(getActivity(), sArtists);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);

//        fillData();
        try {
            new FeedParser(getActivity(), this, sArtists).execute(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ArtistsList;
    }


    private void fillData() {
        // сделать через лоадеры
        Cursor notesCursor = mDb.getAllArtists();
        getActivity().startManagingCursor(notesCursor);

        String[] from = new String[]{Database.KEY_NAME, Database.KEY_GENRES,Database.KEY_TRACKS, Database.KEY_ALBUMS, Database.KEY_SMALL_COVER};
        int[] to = new int[]{R.id.artist_name, R.id.genre, R.id.artist_tracks, R.id.artist_albums, R.id.artist_image};

        SimpleCursorRecyclerAdapter listAdapter = new SimpleCursorRecyclerAdapter(getActivity(), R.layout.card, notesCursor, from, to);
        mRecyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public synchronized void onTaskCompleted() {
        adapter.notifyDataSetChanged();
//        listAdapter.notifyDataSetChanged();
    }
}
