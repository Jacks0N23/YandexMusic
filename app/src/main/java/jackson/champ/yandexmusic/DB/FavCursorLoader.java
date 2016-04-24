package jackson.champ.yandexmusic.DB;

import android.content.Context;
import android.database.Cursor;



public class FavCursorLoader extends android.content.CursorLoader {

    Database mDb;
    public FavCursorLoader(Context context, Database mDb) {
        super(context);
        this.mDb = mDb;
    }

    @Override
    public Cursor loadInBackground() {
        return mDb.getAllArtists();
    }
}
