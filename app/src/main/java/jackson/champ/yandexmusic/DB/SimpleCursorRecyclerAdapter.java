package jackson.champ.yandexmusic.DB;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.OnTaskCompleted;
import jackson.champ.yandexmusic.Utils.Utils;

/**
 * Created by jackson on 14.10.15.
 */
public class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleCursorRecyclerAdapter.SimpleViewHolder> {

    private static final String TAG = SimpleCursorRecyclerAdapter.class.getSimpleName();

    private Intent i;
    private int mLayout;
    private String[] mFrom;
    private int[] mTo;
    private Activity activity;
    Animation animation;
    // boolean array for storing
    boolean[] checkBoxState;
    public static Database mDb;
    OnTaskCompleted onTaskCompleted;

    public SimpleCursorRecyclerAdapter (Activity activity, int layout, Cursor c, String[] from, int[] to, OnTaskCompleted onTaskCompleted) {
        super(c);
        mLayout = layout;
        mTo = to;
        mFrom = from;
        this.activity = activity;
        this.onTaskCompleted = onTaskCompleted;
        mDb = new Database(activity);
        mDb.open();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        final SimpleViewHolder holder = new SimpleViewHolder(v);
        checkBoxState = new boolean[317];
        Log.e(TAG, "onCreateViewHolder: " + checkBoxState.length);

        animation = AnimationUtils.loadAnimation(activity, R.anim.fav_checking);
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.deleteArtist(holder.ArtistName.getText().toString());
                Log.d(TAG, "onBindViewHolder: onCheckedChanged artist deleted " + holder.ArtistName.getText().toString());
                onTaskCompleted.onTaskCompleted();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolderCursor (final SimpleViewHolder holder, Cursor cursor) {
        final String artistName = cursor.getString(cursor.getColumnIndex(Database.KEY_NAME));
        final String genres = cursor.getString(cursor.getColumnIndex(Database.KEY_GENRES));
        final String albums = cursor.getString(cursor.getColumnIndex(Database.KEY_ALBUMS));
        final String tracks = cursor.getString(cursor.getColumnIndex(Database.KEY_TRACKS));

        holder.ArtistName.setText(artistName);
        holder.ArtistGenre.setText(genres);
        holder.ArtistAlbums.setText(albums);
        holder.ArtistTracks.setText(tracks);

        Glide.with(activity)
                .load(cursor.getString(cursor.getColumnIndex(Database.KEY_SMALL_COVER)))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.ArtistImage);

        Log.d(TAG, "onBindViewHolder: position " + cursor.getPosition());
        holder.fav.setChecked(true);
        holder.fav.setButtonDrawable(R.drawable.ic_checked_fav);
        final String description = cursor.getString(cursor.getColumnIndex(Database.KEY_DESCRIPTION));
        final String link = cursor.getString(cursor.getColumnIndex(Database.KEY_LINK));
        final String big_cover = cursor.getString(cursor.getColumnIndex(Database.KEY_BIG_COVER));
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent artist = new Intent(activity, ArtistDescription.class);
                artist.putExtra(Utils.KEY_ARTIST_NAME, artistName);
                artist.putExtra(Utils.KEY_ARTIST_GENRES, genres);
                artist.putExtra(Utils.KEY_ARTIST_ALBUMS, albums);
                artist.putExtra(Utils.KEY_ARTIST_TRACKS, tracks);
                artist.putExtra(Utils.KEY_ARTIST_DESCRIPTION, description);
                artist.putExtra(Utils.KEY_ARTIST_LINK, link);
                artist.putExtra(Utils.KEY_ARTIST_BIG_IMAGE, big_cover);
                activity.startActivity(artist);
            }
        });
    }

//    @SuppressLint("NewApi")
//    private void magicForLollipop(int versionCode, Intent i)
//    {
//        if (versionCode >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setExitTransition(new Explode());
//            activity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
//        } else {
//
//            activity.startActivity(i);
//        }
//    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        private CheckBox fav;
        private CardView cardView;
        public ImageView ArtistImage;
        private TextView ArtistName;
        private TextView ArtistGenre;
        private TextView ArtistAlbums;
        private TextView ArtistTracks;

        public SimpleViewHolder (View holderView) {
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