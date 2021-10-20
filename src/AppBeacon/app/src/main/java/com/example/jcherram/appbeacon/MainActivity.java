
package com.example.jcherram.appbeacon;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.controlador.ServicioEscuharBeacons;
import com.example.jcherram.appbeacon.modelo.Medicion;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
//Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity {

    private static final String ETIQUETA_LOG = ">>>>";
    private static final String DIRECCION_SERVIDOR = "http://192.168.1.14:8080/";
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private Intent elIntentDelServicio = null;
    private LogicaFake logicaFake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comprobarPermisosBlueetooth();
        logicaFake = new LogicaFake(DIRECCION_SERVIDOR);
    }

    public void comprobarPermisosBlueetooth(){
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Comprobar permisos Bluetooth");
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
        }
        else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");
        }
    }

    /**
     * controlar la solicitud de permisos para uso de Blueetooth
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODIGO_PETICION_PERMISOS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): permisos concedidos  !!!!");
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): Socorro: permisos NO concedidos  !!!!");
                }
                return;
        }
    }

    /**
     *
     * @param v
     */
    public void botonDetenerServicioPulsado( View v ) {
        if ( this.elIntentDelServicio == null ) {
            // no estaba arrancado
            return;
        }
        stopService( this.elIntentDelServicio );
        this.elIntentDelServicio = null;
        Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado" );
    }

    /**
     * @param v
     */
    public void botonArrancarServicioPulsado( View v ) {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado" );
        if ( this.elIntentDelServicio != null ) {
            // ya estaba arrancado
            return;
        }
        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscuharBeacons.class);
        this.elIntentDelServicio.putExtra("ipServidor", DIRECCION_SERVIDOR);
        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        startService( this.elIntentDelServicio );
    }

    /**
     *
     * @param v
     */
    public void hacerPeticionRest(View v){
        Date currentTime = Calendar.getInstance().getTime();
        float dato = Float.parseFloat(((EditText)findViewById(R.id.edtiTextMedicion)).getText().toString());
        Medicion medicion = new Medicion(dato, currentTime, new Time(currentTime.getTime()), 25.6f,35.6f );
        ArrayList<Medicion> mediciones = new ArrayList<>();
        mediciones.add(medicion);
        logicaFake.insetarMediciones(mediciones, getApplicationContext());
    }
}

