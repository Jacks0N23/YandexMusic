package jackson.champ.yandexmusic.MainScreen;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private AdapterMain adapter;
    Animation animation;
    private SharedPreferences sp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View ArtistsList = inflater.inflate(R.layout.artists_list, container, false);

        if (!Utils.isOnline(getActivity()))
            Utils.initInternetConnectionDialog(getActivity());
        else
        {
            mRecyclerView = (RecyclerView) ArtistsList.findViewById(R.id.feed_recycler_view);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setHasFixedSize(true);

            LoadingData();
        }

        sp = getActivity().getSharedPreferences("fav", Context.MODE_PRIVATE);

        return ArtistsList;
    }

    private void LoadingData()
    {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fav_checking);

        adapter = new AdapterMain(getActivity(), sArtists, animation);
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
    }

    @Override
    public void onPause() {
        Log.d(TAG, "ArtistsList: " + AdapterMain.favSet);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putStringSet("favSet", AdapterMain.favSet);
        ed.apply();
        super.onPause();
    }

}
