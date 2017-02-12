package rodriapp.gestordenotas;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.id.list;
import static android.view.View.OnClickListener;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_nueva_nota = (Button) findViewById(R.id.btnNuevaNota);
        Button btn_mapas_nota = (Button) findViewById(R.id.btnNotasMapa);
        final ListView lv = (ListView) findViewById(list);

        Uri uri = Uri.parse("content://rodriapp.gestordenotas");

        final String[] columnas = new String[]{
                NotasProvider.TITULO,
                NotasProvider.DESCRIPCION,
                NotasProvider.LATITUD,
                NotasProvider.LONGITUD,
                NotasProvider.FECHA,
                //NotasProvider.IMAGEN
                NotasProvider._ID,
        };

        final Cursor cursor = managedQuery(uri, columnas, null, null, null);

        String[] camposDb = new String[]{
                NotasProvider.TITULO,
                NotasProvider.DESCRIPCION,
                NotasProvider.LATITUD,
                NotasProvider.LONGITUD,
                NotasProvider.FECHA,
                //NotasProvider.IMAGEN
                NotasProvider._ID,
        };

        final int[] camposView = new int[]{android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                camposDb,
                camposView);
        lv.setAdapter(adapter);

        //Establecemos los escuchadores de eventos

        //Botón Nueva Nota

        btn_nueva_nota.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NotaNueva.class);
                startActivity(i);
            }
        });

        //Botón Notas Mapa

        btn_mapas_nota.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NotasMapa.class);
                startActivity(i);
            }
        });

        //onItemClickListener

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent item = new Intent(getApplicationContext(), NotaDetalle.class);
                Log.e("Item Seleccionado", "Pos : " + position + " / id : " + id);
                cursor.moveToPosition(position);
                String titulo = cursor.getString(0);
                String descripcion = cursor.getString(1);
                long latitud = cursor.getLong(2);
                long longitud = cursor.getLong(3);
                //Date fecha = new Date();
                String fecha = cursor.getString(4);
                Log.i("Datos: ", cursor.getString(5));
                item.putExtra("_id", cursor.getString(5));
                item.putExtra("titulo", titulo);
                item.putExtra("descripcion", descripcion);
                item.putExtra("latitud", latitud);
                item.putExtra("longitud", longitud);
                //item.putExtra("fecha", fecha);
                item.putExtra("fecha", fecha);
                startActivity(item);
                //cursor.close();
            }
        });
    }

    // Definimos el evento callback onPause de la Actividad
    @Override
    protected void onPause() {
        super.onPause();
        // Aquí deberíamos guardar la información para la siguiente sesión Toast.makeText(this, "Se ejecuta el método onPause", 1).show();
        //Toast.makeText(this, "Se ejecuta el método onPause", Toast.LENGTH_LONG).show();
    }

    // Definimos el evento callback onRestart de la Actividad
    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "Se ejecuta el método onRestart", Toast.LENGTH_LONG).show();
    }

    // Definimos el evento callback onResume de la Actividad
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "Se ejecuta el método onResume", Toast.LENGTH_LONG).show();
    }

    // Definimos el evento callback onStart de la Actividad
    @Override
    protected void onStart() {
        super.onStart();
        // Aquí deberíamos leer los datos de la última sesión para seguir la // aplicación donde la dejó el usuario
        //Toast.makeText(this, "Se ejecuta el método onStart", Toast.LENGTH_LONG).show();
    }

    // Definimos el evento callback onDestroy de la Actividad
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Se ejecuta el método onDestroy", Toast.LENGTH_LONG).show();
    }

    // Definimos el evento callback onStop de la Actividad
    @Override
    protected void onStop() {
        super.onStop();
    }
}
