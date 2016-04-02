package jackson.champ.yandexmusic.MainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jackson.champ.yandexmusic.R;

/**
 * Created by jackson on 29.03.16.
 */
public class Favorives extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ArtistsFav = inflater.inflate(R.layout.artists_fav, container, false);

        return ArtistsFav;
    }
}
