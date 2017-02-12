package rodriapp.gestordenotas;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by rodrigoaguna on 19/11/16.
 */

public class NotasProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "rodriapp.gestordenotas";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/notas");
    public static final String _ID = "_id";
    public static final String TITULO = "titulo";
    public static final String DESCRIPCION = "descripcion";
    public static final String IMAGEN = "imagen";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String FECHA = "fecha";
    private static final String DATABASE_TABLE = "notas";
    private static final int NOTAS = 1;
    private static final int NOTA_ID = 2;
    private static final UriMatcher uriMatcher = null;

    /*static {
        UriMatcher Matcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "notas", NOTAS);
        uriMatcher.addURI(PROVIDER_NAME, "notas/#", NOTA_ID);
    }*/

    private SQLiteDatabase notasDB;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        BDSqlHelper dbHelper = new BDSqlHelper(context);
        notasDB = dbHelper.getWritableDatabase();
        return (notasDB == null)? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(DATABASE_TABLE);

        if ((sortOrder == null) || sortOrder.equals("")) {
            sortOrder = FECHA;
        }

        Cursor c = sqlBuilder.query(
                notasDB, projection, selection,
                selectionArgs, null, null, sortOrder
        );

        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NOTAS:
                return "vnd.android.cursor.dir/vnd.rodriapp.notas"; // para un solo jugador
            case NOTA_ID:
                return "vnd.android.cursor.item/vnd.rodriapp.nota_id";
            default:throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // añade una nueva nota
        long rowID = notasDB.insert(DATABASE_TABLE, "", contentValues); // si todo ha ido ok devolvemos su Uri
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        long rowID = notasDB.update(DATABASE_TABLE, contentValues, selection, selectionArgs); // si todo ha ido ok devolvemos 1
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return 1;
        }
        throw new SQLException("Failed to update row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long rowID = notasDB.delete(DATABASE_TABLE, selection, selectionArgs); // si todo ha ido ok devolvemos 0
        Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(_uri, null);
        return 0;
    }

    /*public void getCoordinates()
    {
        Location location = new Location()
    }*/
}
