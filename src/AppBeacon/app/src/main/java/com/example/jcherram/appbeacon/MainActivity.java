
package com.example.jcherram.appbeacon;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.fragment.IndiceCalidadAireFragment;
import com.example.jcherram.appbeacon.fragment.MapaFragment;
import com.example.jcherram.appbeacon.fragment.NotificacionesFragment;
import com.example.jcherram.appbeacon.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity  {

    private static final String ETIQUETA_LOG = ">>>>";
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private BottomNavigationView navigationView;
    public long horaAlEntrar ;
    /**
     * Constructor de vista principal
     * @param savedInstanceState instancia del main activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comprobarPermisosBlueetooth();
        setContentView(R.layout.activity_main);
        setFragment(new MapaFragment());



        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String res = sharedPref.getString(getString(R.string.usuarioActivoNombre), "noNombre");
        Log.d("entroAMain",res);

        //Obtener la hora a la que inicia sesión
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("horaAlEntrar",System.currentTimeMillis());
        editor.apply();



        navigationView = findViewById(R.id.bottom_navigation);

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
                        fragment = new MapaFragment();
                        break;


                    case R.id.nav_beacons:
                        if( comprobarSiExisteSensorVinculado()){
                            fragment = new IndiceCalidadAireFragment();
                        }else{
                            fragment = new VincularDispositivoFragment();
                        }

                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });


    }

    /**
     * Comprueba si tenemos algún sensor vinculado
     * @return devuelve el sensor
     *
     * comprobarSiExisteSensorVinculado()->V/F
     */

    private boolean comprobarSiExisteSensorVinculado(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int res = sharedPref.getInt(getString(R.string.usuarioActivoIdSensor), -1);
        boolean sensorVinculado = false;
        if(res!=-1){
            sensorVinculado=true;
        }
        return sensorVinculado;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para establecer un fragment como vista
     * @param fragment recibimos el fragment para la vista
     */

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Contola la solicitud de permisos necesarios para iniciar el servicio Blueetooth
     * comprobarPermisosBlueetooth()
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

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Gestion de la solicitud de permisos
     * @param requestCode codigo de peticion
     * @param permissions lista con los permisos que se can a solicitar
     * @param grantResults lista con los resultados obtenidos en cada permiso
     * Z, Texto,Z->onrequestPermissionsResult()
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


}

