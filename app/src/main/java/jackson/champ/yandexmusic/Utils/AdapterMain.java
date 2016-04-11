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

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.DB.Database;
import jackson.champ.yandexmusic.R;


public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private static final String TAG = "AdapterMain";
    public static ArrayList<Artist> data;
    private static Activity activity;
    Animation animation;
    private SharedPreferences sp;
    // boolean array for storing
    boolean[] checkBoxState;
    public static Database mDb;


    public AdapterMain(Activity activity, ArrayList<Artist> data, Animation animation) {
        AdapterMain.activity = activity;
        AdapterMain.data = data;
        this.animation = animation;
        Log.d(TAG, "Constructor: data.size()  " + data.size());

        mDb = new Database(activity);
        mDb.open();
    }

    @Override
    public AdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        sp = activity.getSharedPreferences("fav", Context.MODE_PRIVATE);
        checkBoxState = new boolean[data.size()];
        Log.e(TAG, "onCreateViewHolder: " + checkBoxState.length);



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterMain.ViewHolder holder, int position) {


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


                if (mDb.isArtistFaved(item.getArtistName()))
                {
                    Log.e(TAG, "onBindViewHolder: " + item.getArtistName());
                    checkBoxState[position] = true;
                    Log.e(TAG, "onBindViewHolder: " + checkBoxState[position]);
                    holder.fav.setButtonDrawable(R.drawable.ic_checked_fav);
                }
                else {
                    checkBoxState[position] = false;
                    holder.fav.setButtonDrawable(R.drawable.ic_unchecked_fav);
                    }
                    holder.fav.setChecked(checkBoxState[position]);


            holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && !checkBoxState[holder.getAdapterPosition()]) {
                        checkBoxState[holder.getAdapterPosition()] = true;
                        buttonView.setButtonDrawable(R.drawable.ic_checked_fav);
                        mDb.Fav(item.getArtistName(),(byte) 1);

                    } else {
                        checkBoxState[holder.getAdapterPosition()] = false;
                        buttonView.setButtonDrawable(R.drawable.ic_unchecked_fav);
                        mDb.Fav(item.getArtistName(), (byte) 0);
                    }

                    buttonView.startAnimation(animation);
                    Log.d(TAG, "onBindViewHolder: onCheckedChanged " + mDb.getArtistsCount());
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
