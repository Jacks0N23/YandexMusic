package jackson.champ.yandexmusic.MainScreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.AdapterMain;
import jackson.champ.yandexmusic.Utils.Artist;
import jackson.champ.yandexmusic.Utils.FeedParser;
import jackson.champ.yandexmusic.Utils.OnTaskCompleted;

public class ArtistsList extends android.support.v4.app.Fragment implements OnTaskCompleted {

    String url = "http://cache-default04d.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    private static ArrayList<Artist> sArtists = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AdapterMain adapter;
    private LinearLayoutManager sLinearLayoutManager;
    private CheckBox fav;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ArtistsList = inflater.inflate(R.layout.artists_list, container, false);
        mRecyclerView = (RecyclerView) ArtistsList.findViewById(R.id.feed_recycler_view);

        sLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(sLinearLayoutManager);
        adapter = new AdapterMain(getActivity(), sArtists);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        try {
            new FeedParser(getActivity(), this, sArtists).execute(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ArtistsList;
    }

    @Override
    public synchronized void onTaskCompleted() {
        adapter.notifyDataSetChanged();
    }
}
