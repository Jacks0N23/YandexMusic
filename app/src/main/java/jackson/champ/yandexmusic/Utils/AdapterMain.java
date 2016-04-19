package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.DB.Database;
import jackson.champ.yandexmusic.R;


public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private static final String TAG = "AdapterMain";
    public static ArrayList<Artist> data;
    private static Activity activity;
    Animation animation;
    // boolean array for storing
    boolean[] checkBoxState;
    public static Database mDb;


    public AdapterMain(Activity activity, ArrayList<Artist> data) {
        AdapterMain.activity = activity;
        AdapterMain.data = data;

        mDb = new Database(activity);
        mDb.open();
    }

    @Override
    public  AdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        final ViewHolder holder = new ViewHolder(v);
        checkBoxState = new boolean[data.size()];
        Log.e(TAG, "onCreateViewHolder: " + checkBoxState.length);

        animation = AnimationUtils.loadAnimation(activity, R.anim.fav_checking);

        holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( !checkBoxState[holder.getAdapterPosition()]) {
                    checkBoxState[holder.getAdapterPosition()] = true;
                    buttonView.setButtonDrawable(R.drawable.ic_checked_fav);
                    final int position = holder.getAdapterPosition();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            final String artistName = holder.ArtistName.getText().toString();
                            final String genres = holder.ArtistGenre.getText().toString();
                            final String albums = holder.ArtistAlbums.getText().toString();
                            final String tracks = holder.ArtistTracks.getText().toString();
                            final String description = data.get(position).getDescription();
                            final String link = data.get(position).getLink();
                            final String smallCover = data.get(position).getImgUrl().get("small");
                            final String bigCover = data.get(position).getImgUrl().get("big");
                            mDb.Fav(artistName, genres, albums,tracks,description,link,smallCover,bigCover,(byte) 1);
                        }
                    });
                    Log.d(TAG, "onBindViewHolder: onCheckedChanged в mDb.fav добавлено 1");

                } else {
                    checkBoxState[holder.getAdapterPosition()] = false;
                    buttonView.setButtonDrawable(R.drawable.ic_unchecked_fav);
                    /**
                     * можно было бы не удалять, а менять только значение fav, тем самым было бы меньше операций записи,
                     * но, т.к это тестовое приложение, я думаю это не столь важно
                     */
                    mDb.deleteArtist(holder.ArtistName.getText().toString());
                    Log.d(TAG, "onBindViewHolder: onCheckedChanged artist deleted");
                }
                buttonView.startAnimation(animation);
                Log.d(TAG, "onBindViewHolder: onCheckedChanged CLICKED");
            }
        });
        return holder;
    }

    @Override
    public  void onBindViewHolder(final AdapterMain.ViewHolder holder, int position) {

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

        Glide.with(activity)
                .load(item.getImgUrl().get("small"))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.ArtistImage);

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
            ArtistGenre = (TextView) holderView.findViewById(R.id.genres);
            ArtistAlbums = (TextView) holderView.findViewById(R.id.artist_albums);
            ArtistTracks = (TextView) holderView.findViewById(R.id.artist_tracks);
        }
    }
}
