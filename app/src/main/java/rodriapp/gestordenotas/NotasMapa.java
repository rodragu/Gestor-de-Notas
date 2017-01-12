package rodriapp.gestordenotas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class NotasMapa extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_mapa);
    }



    protected void cerrar(View view) {
        finish();
    }
}
