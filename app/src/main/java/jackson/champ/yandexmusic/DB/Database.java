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


    public long Fav(String artistName, int fav) {
        mDb = mDbHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery("select artist from Artists where artist = '"+artistName+"'", null);
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, artistName);
        initialValues.put(KEY_FAV, fav);
        if (cursor.getCount() == 0){
            cursor.close();
            return mDb.insert(DATABASE_TABLE_MAIN, null, initialValues);
        }
        else {
            cursor.close();
            return mDb.update(DATABASE_TABLE_MAIN, initialValues, "artist = '"+artistName+"'", null);
        }

    }

    public boolean isArtistFaved(String artist) {
        boolean isArtistFaved = false;
        mDb = mDbHelper.getReadableDatabase();
        Cursor c = mDb.rawQuery("select fav from Artists where artist = '"+artist+"'", null);
        if (c.moveToFirst()) {
            do {
                isArtistFaved = c.getInt(c.getColumnIndex(KEY_FAV)) == 0;
                Log.d(TAG, "isArtistFaved: isArtistFaved Changed to " + isArtistFaved);
            } while (c.moveToNext());
            c.close();
        }

        return isArtistFaved;
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

    public Cursor getFavArtists(String artist) {
        String[] all_colomns = new String[]{KEY_FAV};
        String selection = KEY_NAME + " = "+ artist ;
        return mDb.query(true, DATABASE_TABLE_MAIN, all_colomns ,selection , null,
                null, null, null, null);
    }

    public Cursor getAllArtists() {
        String[] all_colomns = new String[]{KEY_ROW_ID, KEY_NAME, KEY_FAV};
        return mDb.query(true, DATABASE_TABLE_MAIN, all_colomns ,null , null,
                null, null, null, null);
    }

}