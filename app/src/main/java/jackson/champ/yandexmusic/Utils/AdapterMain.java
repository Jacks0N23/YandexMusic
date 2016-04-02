package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jackson.champ.yandexmusic.ArtistSreen.ArtistDescription;
import jackson.champ.yandexmusic.R;


public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    public static ArrayList<Artist> data;
    private static Activity activity;
    Animation animation;


    public AdapterMain(Activity activity, ArrayList<Artist> data, Animation animation) {
        AdapterMain.activity = activity;
        AdapterMain.data = data;
        this.animation = animation;
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
        String genres = "";
        final String albums = item.getArtistAlbums() + " " + activity.getString(R.string.albums);
        final String tracks =item.getArtistTracks() + " " + activity.getString(R.string.tracks);
        holder.ArtistName.setText(item.getArtistName());
        for(int i = 0; i < item.getGenre().size(); i++)
        {
            genres+=item.getGenre().get(i);
            if(i != item.getGenre().size()-1)
            genres+=", ";
        }
        holder.ArtistGenre.setText(genres);
        holder.ArtistAlbums.setText(albums);
        holder.ArtistTracks.setText(tracks);

        Glide.with(activity).load(item.getImgUrl().getSmall()).into(holder.ArtistImage);

        final String finalGenres = genres;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent artist = new Intent(activity, ArtistDescription.class);
                artist.putExtra(Utils.KEY_ARTIST_NAME, item.getArtistName());
                artist.putExtra(Utils.KEY_ARTIST_GENRES, finalGenres);
                artist.putExtra(Utils.KEY_ARTIST_ALBUMS, albums);
                artist.putExtra(Utils.KEY_ARTIST_TRACKS, tracks);
                artist.putExtra(Utils.KEY_ARTIST_DESCRIPTION, item.getmDescription());
                artist.putExtra(Utils.KEY_ARTIST_LINK, item.getLink());
                artist.putExtra(Utils.KEY_ARTIST_BIG_IMAGE, item.getImgUrl().getBig());
                activity.startActivity(artist);
            }
        });

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                    buttonView.setButtonDrawable(R.drawable.ic_unchecked_fav);
//                else
//                    buttonView.setButtonDrawable(R.drawable.ic_checked_fav);
//
//                buttonView.startAnimation(animation);
//                buttonView.refreshDrawableState();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton fav;
        private CardView cardView;
        public ImageView ArtistImage;
        private TextView ArtistName;
        private TextView ArtistGenre;
        private TextView ArtistAlbums;
        private TextView ArtistTracks;

        public ViewHolder(View holderView) {
            super(holderView);
            fav = (ImageButton) holderView.findViewById(R.id.fav);
            cardView = (CardView) holderView.findViewById(R.id.card_view);
            ArtistImage = (ImageView) holderView.findViewById(R.id.artist_image);
            ArtistName = (TextView) holderView.findViewById(R.id.artist_name);
            ArtistGenre = (TextView) holderView.findViewById(R.id.genre);
            ArtistAlbums = (TextView) holderView.findViewById(R.id.artist_albums);
            ArtistTracks = (TextView) holderView.findViewById(R.id.artist_tracks);
        }
    }
}
