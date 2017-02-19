package rodriapp.gestordenotas;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


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
        final ImageView imagen = (ImageView) findViewById(R.id.imagen);

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
                values.put("imagen", imagen.toString());
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

        //Seleccionar una imagen haciendo click en la zona de la imagen

        ImageView img = (ImageView) findViewById(R.id.nota_imagen_detalle);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPickImage(v);
            }
        });
    }

    private static final int PICK_IMAGE_ID = R.id.nota_imagen_detalle; // the number doesn't matter

    public void onPickImage(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                // TODO use bitmap
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    protected void cerrar(View view) {
        finish();
    }
}
