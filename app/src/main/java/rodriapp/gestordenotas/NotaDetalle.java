package rodriapp.gestordenotas;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;

public class NotaDetalle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_detalle);

        final Bundle extra = this.getIntent().getExtras();
        final String[] _id = new String[1];
        final EditText titulo = (EditText)findViewById(R.id.nota_titulo_detalle);
        final EditText descripcion = (EditText)findViewById(R.id.nota_descripcion_detalle);
        final EditText latitud = (EditText)findViewById(R.id.nota_latitud_detalle);
        final EditText longitud = (EditText)findViewById(R.id.nota_longitud_detalle);
        final EditText fecha = (EditText)findViewById(R.id.nota_fecha_detalle);


        _id[0] = extra.getString("_id");
        titulo.setText(extra.getString("titulo"));
        descripcion.setText(extra.getString("descripcion"));
        latitud.setText(String.valueOf(extra.getLong("latitud")));
        longitud.setText(String.valueOf(extra.getLong("longitud")));
        fecha.setText(extra.getString("fecha"));

        final String selection = "_id = ?";
        final String[] selectionArgs = {_id[0]};

        Button btn_editar_nota = (Button)findViewById(R.id.btn_editar_nota);
        btn_editar_nota.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("content://rodriapp.gestordenotas");
                Log.i("UPDATED", "Inicio actualizar nota _id " + _id[0]);
                ContentValues values = new ContentValues();
                values.put("titulo", titulo.getText().toString());
                values.put("descripcion", descripcion.getText().toString());
                values.put("latitud", latitud.getText().toString());
                values.put("longitud", longitud.getText().toString());
                values.put("fecha", fecha.getText().toString());
                getContentResolver().update(uri, values, selection, selectionArgs);
                Log.i("UPDATED", "Nota Actualizada");
                Toast.makeText(getApplicationContext(), R.string.nota_editada, Toast.LENGTH_LONG).show();
                Intent iMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iMain);
            }
        });

        Button btn_eliminar_nota = (Button)findViewById(R.id.btn_eliminar_nota);
        btn_eliminar_nota.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("content://rodriapp.gestordenotas");
                getContentResolver().delete(uri, selection, selectionArgs);
                Toast.makeText(getApplicationContext(), R.string.nota_eliminada, Toast.LENGTH_LONG).show();
                Intent iMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iMain);
            }
        });
    }

    // Definimos el evento callback onPause de la Actividad
    @Override
    protected void onPause() {
        super.onPause();
        // Aquí deberíamos guardar la información para la siguiente sesión Toast.makeText(this, "Se ejecuta el método onPause", 1).show();
        //Toast.makeText(this, "Se ejecuta el método onPause en Nota Detalle", Toast.LENGTH_LONG).show();
    }
    // Definimos el evento callback onRestart de la Actividad
    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "Se ejecuta el método onRestart en Nota Detalle", Toast.LENGTH_LONG).show();
    }
    // Definimos el evento callback onResume de la Actividad
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "Se ejecuta el método onResume en Nota Detalle", Toast.LENGTH_LONG).show();
    }
    // Definimos el evento callback onStart de la Actividad
    @Override
    protected void onStart() {
        super.onStart();
        // Aquí deberíamos leer los datos de la última sesión para seguir la // aplicación donde la dejó el usuario
        //Toast.makeText(this, "Se ejecuta el método onStart en Nota Detalle", Toast.LENGTH_LONG).show();
    }
    // Definimos el evento callback onDestroy de la Actividad
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Se ejecuta el método onDestroy en Nota Detalle", Toast.LENGTH_LONG).show();
    }
    // Definimos el evento callback onStop de la Actividad
    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeTfaext(this, "Se ejecuta el método onStop en Nota Detalle", Toast.LENGTH_LONG).show();
    }

    protected void cerrar(View view) {
        finish();
    }
}
