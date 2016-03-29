package jackson.champ.yandexmusic.Utils;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artist {
    private static final String TAG = "Artist";

    public Artist() {
    }
//
//    public String name;
//    public String genres;
//    public String tracks;
//    public String albums;

    @SerializedName("name")
    private String mArtistName;
    @SerializedName("genres")
    private List<String> mGenre;
    @SerializedName("albums")
    private String mArtistAlbums;
    @SerializedName("tracks")
    private String mArtistTracks;
    private String mUrl;
    private String mArtistImage;

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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getImgUrl() {
        return mArtistImage;
    }

    public void setImgUrl(String imgUrl) {
        mArtistImage = imgUrl;
    }

    public String getArtistAlbums() { return  mArtistAlbums; }

    public void setArtistAlbums(String ArtistAlbums) { mArtistAlbums = ArtistAlbums; }

    public String getArtistTracks() { return  mArtistTracks; }

    public void setArtistTracks(String ArtistTracks) { mArtistTracks = ArtistTracks;}
}
