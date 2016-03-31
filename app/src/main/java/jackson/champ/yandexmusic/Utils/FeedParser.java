package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackson on 29.03.16.
 */
public class FeedParser extends AsyncTask<URL, Void, JSONObject> {

    private Activity activity;
    private List<Artist> mArtists = new ArrayList<>();
    private String TAG = "FeedParser";
    private JSONObject jsonRoot;

    OnTaskCompleted onTaskCompleted;

    public FeedParser(Activity activity, OnTaskCompleted onTaskCompleted, ArrayList<Artist> data) {
        this.activity = activity;
        this.mArtists = data;
        this.onTaskCompleted = onTaskCompleted;
    }

    private void enableHttpResponseCache() {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(activity.getCacheDir(), "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
            Log.d(TAG, "CACHEDIR: " + activity.getCacheDir());
        } catch (Exception httpResponseCacheNotAvailable) {
            Log.d(TAG, "HTTP response cache is unavailable.");

        }
    }


        @Override
    protected JSONObject doInBackground(URL... params) {
        try {
            //обработчик плохой связи сделать
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setUseCaches(true);
            conn.setDefaultUseCaches(true);
            enableHttpResponseCache();
            InputStream input = conn.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
            reader.beginArray();
            while (reader.hasNext()) {
                    Artist message = new GsonBuilder().setLenient().create().fromJson(reader, Artist.class);
                    mArtists.add(message);
                    Log.d(TAG, "doInBackground: WAS HERE");
                    Log.d(TAG, "doInBackground: " + message.getImgUrl().getSmall());
            }

            reader.endArray();
            reader.close();

//            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
//            InputStream input = conn.getInputStream();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            copy(input, baos);
//            jsonRoot = new JSONObject(baos.toString());

//            String json = IOUtils.toString(params[0]);
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(json);
//            Gson gson = new Gson();
//
//            if (element.getAsJsonObject() == element.getAsJsonObject().getAsJsonObject("cover")) {
//                Artist.ImageLoader img = gson.fromJson(element, Artist.ImageLoader.class);
//                mArtistImage.add(img);
//            }
//            else
//            {
//                Artist artist = new Gson().fromJson(element, Artist.class);
//                mArtists.add(artist);
//                Log.d(TAG, "doInBackground: WAS HERE");
//                Log.d(TAG, "doInBackground: " + artist.getArtistName());
//
//            }

        } catch (IOException jse) {
            jse.printStackTrace();
            //выводить сообщения как в дроидере
        }
        return jsonRoot;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        onTaskCompleted.onTaskCompleted();
    }
}
