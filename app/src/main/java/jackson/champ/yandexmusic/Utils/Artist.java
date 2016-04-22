package jackson.champ.yandexmusic.Utils;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

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
    private Map<String, String> mArtistImage;
    @SerializedName("description")
    private String mDescription;

    public String getArtistName() {
        return mArtistName;
    }

    public void setmArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public void setmGenre(List<String> mGenre) {
        this.mGenre = mGenre;
    }

    public void setmArtistAlbums(String mArtistAlbums) {
        this.mArtistAlbums = mArtistAlbums;
    }

    public void setmArtistTracks(String mArtistTracks) {
        this.mArtistTracks = mArtistTracks;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public void setmArtistImage(Map<String, String> mArtistImage) {
        this.mArtistImage = mArtistImage;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public List<String> getGenre() {
        return mGenre;
    }

    public String getLink() {
        return mLink;
    }

    public Map<String, String> getImgUrl() {
        return mArtistImage;
    }

    public String getArtistAlbums() { return  mArtistAlbums; }

    public String getArtistTracks() { return  mArtistTracks; }

    public String getDescription() {
        return mDescription;
    }
}
