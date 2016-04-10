package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.R;


public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private static final String TAG = "AdapterMain";
    public static ArrayList<Artist> data;
    private static Activity activity;
    Animation animation;
    public static Set<String> favSet = Collections.synchronizedSet(new HashSet<String>());
    private SharedPreferences sp;
    // boolean array for storing
    boolean[] checkBoxState;


    public AdapterMain(Activity activity, ArrayList<Artist> data, Animation animation) {
        AdapterMain.activity = activity;
        AdapterMain.data = data;
        this.animation = animation;
        Log.d(TAG, "Constructor: data.size()  " + data.size());
    }

    @Override
    public AdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        sp = activity.getSharedPreferences("fav", Context.MODE_PRIVATE);
        favSet = sp.getStringSet("favSet", favSet);
        Log.d(TAG, "onCreateViewHolder: favSet.size() " + favSet.size());
        checkBoxState = new boolean[data.size()];
        Log.e(TAG, "onViewAttachedToWindow: " + checkBoxState.length);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterMain.ViewHolder holder, final int position) {


        final Artist item = data.get(position);

        String genres = "";
        final String albums = item.getArtistAlbums() + " " + activity.getString(R.string.albums);
        final String tracks = item.getArtistTracks() + " " + activity.getString(R.string.tracks);
        holder.ArtistName.setText(item.getArtistName());
        for (int i = 0; i < item.getGenre().size(); i++) {
            genres += item.getGenre().get(i);
            if (i != item.getGenre().size() - 1)
                genres += ", ";
        }
        holder.ArtistGenre.setText(genres);
        holder.ArtistAlbums.setText(albums);
        holder.ArtistTracks.setText(tracks);

        Glide.with(activity).load(item.getImgUrl().get("small")).into(holder.ArtistImage);

        Log.d(TAG, "onBindViewHolder: position " + position);

            try {
                for (String s : favSet) {
                    if (item.getArtistName().equals(s)) {
                        Log.e(TAG, "onBindViewHolder: " + s);
                        checkBoxState[position] = true;
                        Log.e(TAG, "onBindViewHolder: " + checkBoxState[position]);
                        holder.fav.setButtonDrawable(R.drawable.ic_checked_fav);
                        break;
                    } else {
                        checkBoxState[position] = false;
                        holder.fav.setButtonDrawable(R.drawable.ic_unchecked_fav);
                    }
                    holder.fav.setChecked(checkBoxState[position]);
                }
            }
            catch (ConcurrentModificationException cme)
            {
                cme.printStackTrace();
            }

            holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!checkBoxState[position]) {
                        checkBoxState[position] = true;
                        buttonView.setButtonDrawable(R.drawable.ic_checked_fav);
                        favSet.add(data.get(position).getArtistName());

                    } else {
                        checkBoxState[position] = false;
                        buttonView.setButtonDrawable(R.drawable.ic_unchecked_fav);
                        favSet.remove(data.get(position).getArtistName());
                    }

                    buttonView.startAnimation(animation);
                    Log.i(TAG, "OnClickListener: " + favSet.size());
                }
            });

        Log.d(TAG, "onBindViewHolder:position " + position + " checkBoxState " + checkBoxState[position]);

    final String finalGenres = genres;
    holder.cardView.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent artist = new Intent(activity, ArtistDescription.class);
        artist.putExtra(Utils.KEY_ARTIST_NAME, item.getArtistName());
        artist.putExtra(Utils.KEY_ARTIST_GENRES, finalGenres);
        artist.putExtra(Utils.KEY_ARTIST_ALBUMS, albums);
        artist.putExtra(Utils.KEY_ARTIST_TRACKS, tracks);
        artist.putExtra(Utils.KEY_ARTIST_DESCRIPTION, item.getDescription());
        artist.putExtra(Utils.KEY_ARTIST_LINK, item.getLink());
        artist.putExtra(Utils.KEY_ARTIST_BIG_IMAGE, item.getImgUrl().get("big"));
        activity.startActivity(artist);
    }
    }

    );

}

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox fav;
        private CardView cardView;
        public ImageView ArtistImage;
        private TextView ArtistName;
        private TextView ArtistGenre;
        private TextView ArtistAlbums;
        private TextView ArtistTracks;

        public ViewHolder(View holderView) {
            super(holderView);
            fav = (CheckBox) holderView.findViewById(R.id.fav);
            cardView = (CardView) holderView.findViewById(R.id.card_view);
            ArtistImage = (ImageView) holderView.findViewById(R.id.artist_image);
            ArtistName = (TextView) holderView.findViewById(R.id.artist_name);
            ArtistGenre = (TextView) holderView.findViewById(R.id.genre);
            ArtistAlbums = (TextView) holderView.findViewById(R.id.artist_albums);
            ArtistTracks = (TextView) holderView.findViewById(R.id.artist_tracks);
        }
    }
}
