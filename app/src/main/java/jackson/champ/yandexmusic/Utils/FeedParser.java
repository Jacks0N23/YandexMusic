package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.IOUtils.copy;

/**
 * Created by jackson on 29.03.16.
 */
public class FeedParser extends AsyncTask<URL, Void, JSONObject> {

    private Activity activity;
    private List<Artist> mArtists = new ArrayList<>();
    private List<String> mArtistImage = new ArrayList<>();
    private List<String> mArtistName = new ArrayList<>();
    private List<String> mGenres = new ArrayList<>();
    private List<String> mArtistAlbums = new ArrayList<>();
    private List<String> mArtistTracks = new ArrayList<>();
    private String TAG = "FeedParser";
    private JSONObject jsonRoot;

    OnTaskCompleted onTaskCompleted;

    public FeedParser(Activity activity, OnTaskCompleted onTaskCompleted, ArrayList<Artist> data) {
        this.activity = activity;
        this.mArtists = data;
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected JSONObject doInBackground(URL... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            InputStream input = conn.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
            reader.beginArray();
            while (reader.hasNext()) {
                Artist message = new Gson().fromJson(reader, Artist.class);
                message.getArtistName();
                mArtists.add(message);
                Log.d(TAG, "doInBackground: WAS HERE");
                Log.d(TAG, "doInBackground: " + message.getArtistName());

            }

            reader.endArray();
            reader.close();

//            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
//            InputStream input = conn.getInputStream();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            copy(input, baos);
//            jsonRoot = new JSONObject(baos.toString());

//            String json = IOUtils.toString(new URL(params[0]));
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(json);
//            mArtists = new Gson().fromJson(element, ArrayList.class);
//            Log.d(TAG, "doInBackground: WAS HERE" );
//            if (element.isJsonObject()) {
//                JsonObject artistName = element.getAsJsonObject();
//                mArtistName.add(artistName.get("name").getAsString());
//                Log.d(TAG, "doInBackground: " + artistName.get("name").getAsString());
//                JsonObject genres = element.getAsJsonObject();
//                mGenres.add(artistName.get("genres").getAsString());
//
//                JsonObject artistAlbums = element.getAsJsonObject();
//                mArtistAlbums.add(artistName.get("albums").getAsString());
//
//                JsonObject artistTracks = element.getAsJsonObject();
//                mArtistTracks.add(artistName.get("tracks").getAsString());

//                JsonObject artistImage = element.getAsJsonObject();
//                mArtistImage.add(artistName.get("cover").getAsString());
//            }

        } catch (IOException jse) {
            jse.printStackTrace();
        }
        return jsonRoot;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        onTaskCompleted.onTaskCompleted();
//        for (int i = 0; i < mArtists.size(); i++) {
//            Artist feedItem = new Artist();
//
//            feedItem.setArtistName(mArtistName.get(i));
//            feedItem.setGenre(mGenres.get(i));
//            feedItem.setArtistAlbums(mArtistAlbums.get(i));
//            feedItem.setArtistTracks(mArtistTracks.get(i));
//            mArtists.add(feedItem);
////        }
////    }
    }

        //    @Override
//    protected void onPostExecute(Void aVoid) {
//        for (int i = 0; i < mArtistName.size(); i++) {
//            Artist feedItem = new Artist();
//
//            feedItem.setArtistName(mArtistName.get(i));
//            feedItem.setGenre(mGenres.get(i));
//            feedItem.setArtistAlbums(mArtistAlbums.get(i));
//            feedItem.setArtistTracks(mArtistTracks.get(i));
//            mArtists.add(feedItem);
//    }
// }
}
