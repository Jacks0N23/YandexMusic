package jackson.champ.yandexmusic.Utils;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artist {
    private static final String TAG = "Artist";

    public Artist() {
    }

    @SerializedName("name")
    private String mArtistName;
    @SerializedName("genres")
    private List<String> mGenre;
    @SerializedName("albums")
    private String mArtistAlbums;
    @SerializedName("tracks")
    private String mArtistTracks;
    @SerializedName("link")
    private String mLink;
    @SerializedName("cover")
    private Imgs mArtistImage;
    @SerializedName("description")
    private String mDescription;

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String ArtistName) {
        mArtistName = ArtistName;
    }

    public List<String> getGenre() {
        return mGenre;
    }

    public void setGenre(List<String> ArtistGenre) {
        mGenre = ArtistGenre;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String url) {
        mLink = url;
    }

    public Imgs getImgUrl() {
        return mArtistImage;
    }

    public void setImgUrl(Imgs imgUrl) {
        mArtistImage = imgUrl;
    }

    public String getArtistAlbums() { return  mArtistAlbums; }

    public void setArtistAlbums(String ArtistAlbums) { mArtistAlbums = ArtistAlbums; }

    public String getArtistTracks() { return  mArtistTracks; }

    public void setArtistTracks(String ArtistTracks) { mArtistTracks = ArtistTracks;}

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
