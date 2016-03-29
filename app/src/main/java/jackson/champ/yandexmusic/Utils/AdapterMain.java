package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
    public void onBindViewHolder(AdapterMain.ViewHolder holder, int position) {
        final Artist item = data.get(position);
        holder.ArtistName.setText(item.getArtistName());
//        holder.ArtistGenre.setText(item.getGenre());
        holder.ArtistAlbums.setText(item.getArtistAlbums());
        holder.ArtistTracks.setText(item.getArtistTracks());
//        holder.ArtistImage.setImageURI(Uri.parse(item.getImgUrl()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        public ImageView ArtistImage;
        private TextView ArtistName;
        private TextView ArtistGenre;
        private TextView ArtistAlbums;
        private TextView ArtistTracks;

        public ViewHolder(View holderView) {
            super(holderView);
            cardView = (CardView) holderView.findViewById(R.id.card_view);
            ArtistImage = (ImageView) holderView.findViewById(R.id.artist_image);
            ArtistName = (TextView) holderView.findViewById(R.id.artist_name);
            ArtistGenre = (TextView) holderView.findViewById(R.id.genre);
            ArtistAlbums = (TextView) holderView.findViewById(R.id.artist_albums);
            ArtistTracks = (TextView) holderView.findViewById(R.id.artist_tracks);
        }
    }
}
