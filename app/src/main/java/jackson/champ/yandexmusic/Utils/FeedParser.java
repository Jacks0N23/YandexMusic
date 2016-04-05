package jackson.champ.yandexmusic.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

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
public class FeedParser extends AsyncTask<URL, Void, Void> {

    private Activity activity;
    private List<Artist> mArtists = new ArrayList<>();
    private String TAG = "FeedParser";
    public static int hash;
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
    protected Void doInBackground(URL... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(30000);
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
                    Log.d(TAG, "doInBackground: " + message.getImgUrl().get("small"));
            }

            reader.endArray();
            reader.close();


        } catch (IOException jse) {
            jse.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void obj) {
        onTaskCompleted.onTaskCompleted();
    }
}
