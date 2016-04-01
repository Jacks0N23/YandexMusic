package jackson.champ.yandexmusic.DB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.Artist;

/**
 * Created by Jackson on 02.04.2016.
 */
public class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleCursorRecyclerAdapter.SimpleViewHolder> {

    private static final String TAG = SimpleCursorRecyclerAdapter.class.getSimpleName();

    private Intent i;
    private int mLayout;
    private int[] mFrom;
    private int[] mTo;
    private String[] mOriginalFrom;
    private Activity activity;
    private static long position;
    public static long parentID;

    public SimpleCursorRecyclerAdapter (Activity context, int layout, Cursor c, String[] from, int[] to) {
        super(c);
        mLayout = layout;
        mTo = to;
        mOriginalFrom = from;
        findColumns(c, from);
        activity = context;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder (final SimpleViewHolder holder, Cursor cursor) {
        final int[] from = mFrom;


        String albums = cursor.getString(from[3]) + " " + activity.getString(R.string.albums);
        String tracks =cursor.getString(from[2]) + " " + activity.getString(R.string.tracks);
        holder.ArtistName.setText(cursor.getString(from[0]));
        holder.ArtistGenre.setText(cursor.getString(from[1]));
        holder.ArtistAlbums.setText(albums);
        holder.ArtistTracks.setText(tracks);

        Glide.with(activity).load(cursor.getString(from[4])).into(holder.ArtistImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, ArtistDescription.class));
            }
        });

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.fav.isChecked())
                    holder.fav.setButtonDrawable(R.drawable.ic_checked_fav);
                else
                    holder.fav.setButtonDrawable(R.drawable.ic_unchecked_fav);
            }
        });
    }

    @SuppressLint("NewApi")
    private void magicForLollipop(int versionCode, Intent i) {
        if (versionCode >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setExitTransition(new Explode());
            activity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(i);
        }
    }


    /**
     * Create a map from an array of strings to an array of column-id integers in cursor c.
     * If c is null, the array will be discarded.
     *
     * @param c the cursor to find the columns from
     * @param from the Strings naming the columns of interest
     */
    private void findColumns(Cursor c, String[] from) {
        if (c != null) {
            int i;
            int count = from.length;
            if (mFrom == null || mFrom.length != count) {
                mFrom = new int[count];
            }
            for (i = 0; i < count; i++) {
                mFrom[i] = c.getColumnIndexOrThrow(from[i]);
            }
        } else {
            mFrom = null;
        }
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        private CheckBox fav;
        private CardView cardView;
        public ImageView ArtistImage;
        private TextView ArtistName;
        private TextView ArtistGenre;
        private TextView ArtistAlbums;
        private TextView ArtistTracks;

        public SimpleViewHolder(View holderView) {
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
