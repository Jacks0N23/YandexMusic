package jackson.champ.yandexmusic.ArtistSreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jackson.champ.yandexmusic.R;
import jackson.champ.yandexmusic.Utils.Utils;

/**
 * Created by jackson on 29.03.16.
 */
public class ArtistDescription extends AppCompatActivity {

    private static final String TAG = "ArtistDescription" ;


    private ImageView ArtistImage;
    private TextView ArtistGenres;
    private TextView ArtistAlbums;
    private TextView ArtistTracks;
    private TextView ArtistsDescription;
    private TextView ArtistsLink;
    private Toolbar toolbar;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_description);

        ArtistImage = (ImageView)findViewById(R.id.artist_image);
        ArtistGenres = (TextView)findViewById(R.id.genres);
        ArtistAlbums = (TextView)findViewById(R.id.artist_albums);
        ArtistTracks = (TextView)findViewById(R.id.artist_tracks);
        ArtistsDescription = (TextView)findViewById(R.id.artist_description);
        ArtistsLink = (TextView)findViewById(R.id.artist_link);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(getIntent().getStringExtra(Utils.KEY_ARTIST_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        extras = getIntent().getExtras();

        Log.d(TAG, "onCreate: " + extras.getString(Utils.KEY_ARTIST_BIG_IMAGE));
        Glide.with(this).load(extras.getString(Utils.KEY_ARTIST_BIG_IMAGE)).into(ArtistImage);
        ArtistGenres.setText(extras.getString(Utils.KEY_ARTIST_GENRES));
        ArtistAlbums.setText(extras.getString(Utils.KEY_ARTIST_ALBUMS));
        ArtistTracks.setText(extras.getString(Utils.KEY_ARTIST_TRACKS));
        ArtistsDescription.setText(extras.getString(Utils.KEY_ARTIST_DESCRIPTION));
        ArtistsLink.setText((extras.getString(Utils.KEY_ARTIST_LINK) == null) ? "" : extras.getString(Utils.KEY_ARTIST_LINK));
    }
}
