package jackson.champ.yandexmusic.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;


public class Utils {

    final public static String KEY_ARTIST_NAME = "name";
    final public static String KEY_ARTIST_GENRES = "genres";
    final public static String KEY_ARTIST_ALBUMS = "albums";
    final public static String KEY_ARTIST_TRACKS = "tracks";
    final public static String KEY_ARTIST_BIG_IMAGE = "big_image";
    final public static String KEY_ARTIST_DESCRIPTION = "description";
    final public static String KEY_ARTIST_LINK = "link";



    public static boolean isOnline(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void initInternetConnectionDialog(final Context context) {

        new AlertDialog.Builder(context).setTitle("Соединение прервано").setMessage("Проверьте своё соединение с интернетом")
                .setNeutralButton("Включить Wi-Fi?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enableWiFi(context, true);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
    }

    private static void enableWiFi(Context c, boolean wifi) {
        WifiManager wifiConfiguration = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        wifiConfiguration.setWifiEnabled(wifi);
    }
}
