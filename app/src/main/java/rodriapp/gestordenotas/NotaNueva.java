package rodriapp.gestordenotas;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class NotaNueva extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PETICION_PERMISO_LOCALIZACION = 0;
    GoogleApiClient mGoogleApiClient;
    final EditText titulo = (EditText) findViewById(R.id.nota_titulo_detalle);
    final EditText descripcion = (EditText) findViewById(R.id.nota_descripcion_detalle);
    final EditText latitud = (EditText) findViewById(R.id.nota_latitud_detalle);
    final EditText longitud = (EditText) findViewById(R.id.nota_longitud_detalle);
    final EditText fecha = (EditText) findViewById(R.id.nota_fecha_detalle);
    final ImageView imagen = (ImageView) findViewById(R.id.nota_imagen_detalle);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_nueva);

        Bundle extra = this.getIntent().getExtras();

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int monthDay = today.monthDay;
        int month = today.month + 1;
        int year = today.year;

        fecha.setText(monthDay + "-" + month + "-" + year);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

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
                //values.put("imagen", imagen.getImageAlpha());
                getContentResolver().insert(uri, values);

                Toast.makeText(getApplicationContext(), R.string.nota_insertada, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        // Aquí deberíamos leer los datos de la última sesión para seguir la // aplicación donde la dejó el usuario
        //Toast.makeText(this, "Se ejecuta el método onStart en Nota Detalle", Toast.LENGTH_LONG).show();
    }

    // Definimos el evento callback onStop de la Actividad
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        //Toast.makeText(this, "Se ejecuta el método onStop en Nota Detalle", Toast.LENGTH_LONG).show();
    }

    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            getCoordinates();

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e("LOGTAG", "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e("LOGTAG", "Se ha interrumpido la conexión con Google Play Services");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                getCoordinates();

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e("LOGCAT", "Permiso denegado");
            }
        }
    }

    public void getCoordinates()
    {
        @SuppressWarnings("MissingPermission")
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (lastLocation != null) {
            latitud.setText(String.valueOf(lastLocation.getLatitude()));
            longitud.setText(String.valueOf(lastLocation.getLongitude()));
            //Toast.makeText(this, String.valueOf(lastLocation.getLatitude()) + " - " + String.valueOf(lastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        } else {
            latitud.setText(R.string.no_latitud);
            longitud.setText(R.string.no_longitud);
        }
    }

    protected void cerrar(View view) {
        finish();
    }
}
