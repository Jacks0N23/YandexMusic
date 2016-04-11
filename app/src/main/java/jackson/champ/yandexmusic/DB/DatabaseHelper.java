package jackson.champ.yandexmusic.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseHelper.class.getSimpleName();

    DatabaseHelper(Context context) {
        super(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    /**
     * Database creation sql statement
     */
    private final static String DATABASE_CREATE_MAIN =
            "CREATE TABLE if not exists " + Database.DATABASE_TABLE_MAIN +
                    "(" + Database.KEY_ROW_ID + " integer primary key autoincrement, "
                    + Database.KEY_NAME + " TEXT, "
                    + Database.KEY_FAV + " INTEGER"
                    + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_MAIN);
        Log.v("INFO1", "creating db A");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Database.DATABASE_TABLE_MAIN);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "onDowngrade: from ver." + oldVersion + " to ver." + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + Database.DATABASE_TABLE_MAIN);
        onCreate(db);
    }
}