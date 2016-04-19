package jackson.champ.yandexmusic.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class Database {
    public static String DATABASE_NAME = "YandexMusic";
    public static String DATABASE_TABLE_MAIN = "Artists";
    public static int DATABASE_VERSION = 2;
    public static String KEY_NAME = "artist";
    public static String KEY_FAV = "fav";
    public static String KEY_ROW_ID = "_id";
    public static String KEY_GENRES = "genres";
    public static String KEY_TRACKS = "tracks";
    public static String KEY_ALBUMS = "albums";
    public static String KEY_SMALL_COVER = "small_cover";
    public static String KEY_BIG_COVER = "big_cover";
    public static String KEY_LINK = "link";
    public static String KEY_DESCRIPTION = "description";
    private static String TAG = "DATABASE";

    private DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;
    private final Context mContext;


    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public Database(Context ctx) {
        this.mContext = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     * initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        try {
            mDb = mDbHelper.getWritableDatabase();
        } catch (Exception sqlE) {
            mDb = mDbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long Fav(String artistName, String genres, String albums, String tracks, String description, String link, String smallCover, String bigCover,byte fav) {
        mDb = mDbHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery("select artist from Artists where artist = '"+artistName+"'", null);
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, artistName);
        initialValues.put(KEY_FAV, fav);
        initialValues.put(KEY_GENRES, genres);
        initialValues.put(KEY_ALBUMS, albums);
        initialValues.put(KEY_TRACKS, tracks);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_LINK, link);
        initialValues.put(KEY_SMALL_COVER, smallCover);
        initialValues.put(KEY_BIG_COVER, bigCover);
        Log.e(TAG, "DataBase: artistName: " + artistName + " fav: " + fav);
        if (cursor.getCount() == 0){
            Log.e(TAG, "DataBase:cursor.getCount() " + cursor.getCount());
            cursor.close();
            return mDb.insert(DATABASE_TABLE_MAIN, null, initialValues);
        }
        else {
            cursor.close();
            Log.e(TAG, "DataBase: ELSE STATEMENT getCount() " + cursor.getCount());
            return mDb.update(DATABASE_TABLE_MAIN, initialValues, "artist = '"+artistName+"'", null);
        }

    }

    public boolean isArtistFaved(String artist) {
        boolean isArtistFaved = false;
        mDb = mDbHelper.getReadableDatabase();
        Cursor c = mDb.rawQuery("select fav from Artists where artist = '"+artist+"'", null);
        if (c.moveToFirst()) {
            do {
                isArtistFaved = c.getInt(c.getColumnIndex(KEY_FAV)) == 1;
                 Log.d(TAG, "isArtistFaved: isArtistFaved c.getInt(c.getColumnIndex(KEY_FAV)) ==  " + c.getInt(c.getColumnIndex(KEY_FAV)));
                Log.d(TAG, "isArtistFaved: isArtistFaved Changed to " + isArtistFaved);
            } while (c.moveToNext());
            c.close();
        }
        close();
        return isArtistFaved;
    }

    /**
     * Delete artist
     *
     * @return true if deleted, false otherwise
     */
    public boolean deleteArtist(String artistName) {
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DATABASE_TABLE_MAIN, "artist = '"+artistName+"'", null);
        return true;
    }


    /**
     * Delete all rows
     *
     * @return true if deleted, false otherwise
     */
    public boolean deleteAll() {
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DATABASE_TABLE_MAIN, null, null);
        mDb.execSQL(DATABASE_TABLE_MAIN);
        return true;
    }



    public Cursor getFavArtists(String artist) {
        String[] all_colomns = new String[]{KEY_FAV};
        String selection = KEY_NAME + " = "+ artist ;
        return mDb.query(true, DATABASE_TABLE_MAIN, all_colomns ,selection , null,
                null, null, null, null);
    }

    public Cursor getAllArtists() {
        String[] all_colomns = new String[]{KEY_ROW_ID,KEY_NAME, KEY_GENRES,KEY_ALBUMS,KEY_TRACKS,KEY_SMALL_COVER, KEY_DESCRIPTION, KEY_LINK, KEY_BIG_COVER};
        return mDb.query(true, DATABASE_TABLE_MAIN, all_colomns ,null , null,
                null, null, null, null);
    }

}