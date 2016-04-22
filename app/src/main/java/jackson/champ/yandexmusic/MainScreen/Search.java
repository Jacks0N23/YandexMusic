package jackson.champ.yandexmusic.MainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.OnTaskCompleted;
import jackson.champ.yandexmusic.Utils.SearchAdapter;

/**
 * Created by jackson on 22.04.16.
 */
public class Search extends android.support.v4.app.Fragment implements OnTaskCompleted {

    private RecyclerView searched;
    private SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.search, container, false);

        searched = (RecyclerView)searchView.findViewById(R.id.search_recycler_view);

        searched.setLayoutManager(new LinearLayoutManager(getActivity()));
        searched.setHasFixedSize(true);


        adapter = new SearchAdapter(getActivity(), ArtistsList.sArtists, MainActivity.searchEditText, this);
        return searchView;
    }

    @Override
    public void onTaskCompleted() {
        adapter.notifyDataSetChanged();
    }
}
