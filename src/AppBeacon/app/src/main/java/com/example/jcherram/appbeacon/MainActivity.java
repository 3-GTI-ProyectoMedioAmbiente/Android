
package com.example.jcherram.appbeacon;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jcherram.appbeacon.adapter.Notificacion;
import com.example.jcherram.appbeacon.adapter.NotificacionesAdapter;
import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.controlador.ServicioEscuharBeacons;
import com.example.jcherram.appbeacon.modelo.Medicion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity  {

    private static final String ETIQUETA_LOG = ">>>>";
    private static final String DIRECCION_SERVIDOR = "http://192.168.78.31:8080/";
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private Intent elIntentDelServicio = null;
    private LogicaFake logicaFake;
    BottomNavigationView navigationView;
    Dialog mydialog;
    TextView txtclose;

    /**
     * Constructor de vista principal
     * @param savedInstanceState instancia del main activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        comprobarPermisosBlueetooth();
        logicaFake = new LogicaFake(DIRECCION_SERVIDOR);


        setContentView(R.layout.activity_main);


        navigationView = findViewById(R.id.bottom_navigation);

        setFragment(new MedicionesFragment());

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.nav_user:
                        fragment = new UserFragment();
                        break;

                    case R.id.nav_notificaciones:
                        fragment = new NotificacionesFragment();
                        break;

                    case R.id.nav_mediciones:
                        fragment = new MedicionesFragment();
                        break;


                    case R.id.nav_beacons:
                        fragment = new BeaconsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });


    }





    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    /**
     * Contola la solicitud de permisos necesarios para iniciar el servicio Blueetooth
     */
    private void comprobarPermisosBlueetooth(){
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
     * Gestion de la solicitud de permisos
     * @param requestCode codigo de peticion
     * @param permissions lista con los permisos que se can a solicitar
     * @param grantResults lista con los resultados obtenidos en cada permiso
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
     * Metodo que gestiona la parada del servicio
     * @param v View desde donde se llama al metodo
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
     * Metodo que gestiona la inicializacion del servicio
     * @param v View desde donde se llama al metodo
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
     * Crea una medida segun el dato introducido en por el Usuario
     * @param v View desde donde se llama al metodo
     */

    /**
    public void hacerPeticionRest(View v){
        Date currentTime = Calendar.getInstance().getTime();
        float dato = Float.parseFloat(((EditText)findViewById(R.id.edtiTextMedicion)).getText().toString());
        Medicion medicion = new Medicion(dato, currentTime, new Time(currentTime.getTime()), 25.6f,35.6f );
        ArrayList<Medicion> mediciones = new ArrayList<>();
        mediciones.add(medicion);
        logicaFake.insetarMediciones(mediciones);
    }
     */
}

