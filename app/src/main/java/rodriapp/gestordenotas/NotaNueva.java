package rodriapp.gestordenotas;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotaNueva extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_nueva);

        Bundle extra = this.getIntent().getExtras();

        final EditText titulo = (EditText)findViewById(R.id.nota_titulo_detalle);
        final EditText descripcion = (EditText)findViewById(R.id.nota_descripcion_detalle);
        final EditText latitud = (EditText)findViewById(R.id.nota_latitud_detalle);
        final EditText longitud = (EditText)findViewById(R.id.nota_longitud_detalle);
        final EditText fecha = (EditText)findViewById(R.id.nota_fecha_detalle);

        Button btn_insertar_nota = (Button)findViewById(R.id.btn_insertar_nota);
        btn_insertar_nota.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Insertar", "Botón insertar clicado");
                Uri uri = Uri.parse("content://rodriapp.gestordenotas");
                ContentValues values = new ContentValues();
                values.put("titulo", titulo.getText().toString());
                values.put("descripcion", descripcion.getText().toString());
                values.put("latitud", latitud.getText().toString());
                values.put("longitud", longitud.getText().toString());
                values.put("fecha", fecha.getText().toString());
                //values.put("imagen", imagen.toString());
                getContentResolver().insert(uri, values);

                Toast.makeText(getApplicationContext(), R.string.nota_insertada, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
        //Toast.makeText(this, "Se ejecuta el método onStop en Nota Detalle", Toast.LENGTH_LONG).show();
    }

    protected void cerrar(View view) {
        finish();
    }
}
