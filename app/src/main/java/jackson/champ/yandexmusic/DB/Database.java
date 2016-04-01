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
    public static int DATABASE_VERSION = 1;
    public static String KEY_NAME = "parent";
    public static String KEY_GENRES = "orderID";
    public static String KEY_TRACKS = "date_from";
    public static String KEY_ALBUMS = "event_id";
    public static String KEY_SMALL_COVER = "is_date_enabled";
    public static String KEY_BIG_COVER = "date_to";
    public static String KEY_ROW_ID = "_id";
    public static String KEY_LINKS = "title";
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


    public long createPlan(String name, String genres, String tracks, String albums, String links, String small_cover, String description, String big_cover) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_GENRES, genres);
        initialValues.put(KEY_TRACKS, tracks);
        initialValues.put(KEY_ALBUMS, albums);
        initialValues.put(KEY_LINKS, links);
        initialValues.put(KEY_SMALL_COVER, small_cover);
        initialValues.put(KEY_BIG_COVER, big_cover);
        initialValues.put(KEY_DESCRIPTION, description);


        return mDb.insert(DATABASE_TABLE_MAIN, null, initialValues);
    }


    /**
     * Delete all rows
     *
     * @return true if deleted, false otherwise
     */
    public boolean deleteAll(long parentId) {
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DATABASE_TABLE_MAIN, null, null);
        mDb.execSQL(DATABASE_TABLE_MAIN);
        return true;
    }

    /**
     * Return a Cursor that matches all rows
     *
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */


    public Cursor getAllArtists() {
        String[] all_colomns = new String[]{KEY_ROW_ID, KEY_NAME, KEY_GENRES, KEY_TRACKS, KEY_ALBUMS, KEY_LINKS, KEY_SMALL_COVER, KEY_BIG_COVER, KEY_DESCRIPTION};
        return mDb.query(true, DATABASE_TABLE_MAIN, all_colomns ,null , null,
                null, null, null, null);
    }


    public static int getArtistsCount() {
        int count = 0;
        Cursor cursor = mDb.query(true, DATABASE_TABLE_MAIN, new String[]{KEY_ROW_ID}, KEY_ROW_ID + " = ?", null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d(TAG, "getTaskIdCount: " + Long.toString(count));

        return count;
    }

}