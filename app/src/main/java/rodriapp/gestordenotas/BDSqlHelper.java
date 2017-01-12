package rodriapp.gestordenotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rodrigoaguna on 18/11/16.
 */

class BDSqlHelper extends SQLiteOpenHelper {
    private static String DB_NAME= "gestor_notas.db";
    private static int DB_VERSION = 1;

    BDSqlHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        if (db.isReadOnly()) {
            db = getWritableDatabase();
        }

        db.execSQL("CREATE TABLE notas (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT, imagen TEXT, latitud LONG, longitud LONG, fecha DATE);");

        /*ContentValues values = new ContentValues();
        values.put("titulo", "Nota prueba");
        values.put("descripcion", "Esta es mi primera nota" );
        db.insert("notas", "", values);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cuando haya cambios en la estructura deberemos
        // incluir el código
        // SQL necesario para actualizar la base de datos

        /*if(db.isReadOnly())
        {
            db=getWritableDatabase();
        }

        if(oldVersion != newVersion)
        {
            db.execSQL("UPDATE TABLE notas WHERE (_ID INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT, imagen TEXT, latitud LONG, longitud LONG);");
        }*/
    }
}