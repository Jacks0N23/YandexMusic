package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.R;

/**
 * Created by jackson on 29.03.16.
 */
public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    public static ArrayList<Artist> data;
    private static Activity activity;

    public AdapterMain(Activity activity, ArrayList<Artist> data) {
        AdapterMain.activity = activity;
        AdapterMain.data = data;
    }

    @Override
    public AdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterMain.ViewHolder holder, int position) {
        final Artist item = data.get(position);
        String genre = "";
        String albums = item.getArtistAlbums() + " " + activity.getString(R.string.albums);
        String tracks =item.getArtistTracks() + " " + activity.getString(R.string.tracks);
        holder.ArtistName.setText(item.getArtistName());
        for(int i = 0; i < item.getGenre().size(); i++)
        {
            genre+=item.getGenre().get(i);
            if(i != item.getGenre().size()-1)
            genre+=", ";
        }
        holder.ArtistGenre.setText(genre);
        holder.ArtistAlbums.setText(albums);
        holder.ArtistTracks.setText(tracks);

        Glide.with(activity).load(item.getImgUrl().getSmall()).into(holder.ArtistImage);

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
