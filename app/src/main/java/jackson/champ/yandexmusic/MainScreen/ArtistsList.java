package jackson.champ.yandexmusic.MainScreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.AdapterMain;
import jackson.champ.yandexmusic.Utils.Artist;
import jackson.champ.yandexmusic.Utils.FeedParser;
import jackson.champ.yandexmusic.Utils.OnTaskCompleted;
import jackson.champ.yandexmusic.Utils.Utils;

public class ArtistsList extends android.support.v4.app.Fragment implements OnTaskCompleted {

    private static final String TAG = "ArtistsList";
    String url = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    public static ArrayList<Artist> sArtists = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public static AdapterMain adapter;
    public static SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View ArtistsList = inflater.inflate(R.layout.artists_list, container, false);



        if (!Utils.isOnline(getActivity()))
            Utils.initInternetConnectionDialog(getActivity());
        else
        {
            swipeRefreshLayout = (SwipeRefreshLayout)ArtistsList.findViewById(R.id.refresher);
            swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    LoadingData();
                    onTaskCompleted();

                }
            });
            mRecyclerView = (RecyclerView) ArtistsList.findViewById(R.id.feed_recycler_view);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setHasFixedSize(true);

            LoadingData();
        }


        return ArtistsList;
    }

    private void LoadingData()
    {
        swipeRefreshLayout.setRefreshing(true);
        adapter = new AdapterMain(getActivity(), sArtists, this);
        mRecyclerView.setAdapter(adapter);
        try {

            new FeedParser(getActivity(), this, sArtists).execute(new URL(url));

        } catch (IOException e) {

            e.printStackTrace();

            Toast.makeText(getActivity(), getString(R.string.conn_problems),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public synchronized void onTaskCompleted() {

        adapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().getLoaderManager().getLoader(Favorits.LOADER_ID).forceLoad();
            }
        }).start();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onPause() {
        Log.e(TAG, "onPause: sArtists.size() " + sArtists.size());
        super.onPause();
    }
}
