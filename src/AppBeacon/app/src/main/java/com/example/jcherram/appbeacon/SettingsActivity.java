package com.example.jcherram.appbeacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.jcherram.appbeacon.controlador.ServicioEscuharBeacons;


// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// Fecha: 17/10/2021
// SettingsActivity
// Clase para gestionar el funcionamiento del sensor
// -----------------------------------------------------------------------------------
public class SettingsActivity extends AppCompatActivity {

    private static final String ETIQUETA_LOG = ">>>>";
    private TextView textViewDistancia;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            double distance = intent.getDoubleExtra("distancia", -1.0);

            if (distance == -1.0d) {
                textViewDistancia.setText("Desconocida");
            }else{
                if(distance >0 && distance<0.75){
                    textViewDistancia.setText("Cerca (0-0.75m)");
                }else if (distance >=0.75 && distance<=3){
                    textViewDistancia.setText("Distancia intermedia (0.75-3m)");
                }else{
                    textViewDistancia.setText("Lejos (>3m)");
                }
            }
        }
    };

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     *  Constructor de la Acticity de configuracion del sensor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        textViewDistancia = findViewById(R.id.textViewDistancia);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("SettingsActivity"));
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private Intent elIntentDelServicio = null;
        /**
         * Constructor del fragmente de configurarcion
         * @param savedInstanceState
         * @param rootKey
         * Bundle savedInstanceState, String rootKey ->onCreatePreferences
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            //SwitchPreference
            configurarSwitchPreference(getPreferenceScreen().findPreference(getString(R.string.preferenceEstadoServicio)), getString(R.string.preferenceEstadoServicio));

        }

        // -----------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------

        /**
         * Vincular el SwitchPreference con su preferencia en la aplicacion
         * @param switchPreference Preferencia a la que se le anyadira el listener
         * @param preferenceName nombre de la preferencia a modificar
         * SwitchPreferenceCompat switchPreference, Texto preferenceName->configurarSwitchPreference
         */
        private void configurarSwitchPreference(SwitchPreferenceCompat switchPreference, String preferenceName ){
            switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    if(switchPreference.isChecked()){
                        Toast.makeText(getContext(),"Servicio detenido",Toast.LENGTH_SHORT).show();
                        editor.putBoolean(preferenceName, false);
                        botonDetenerServicioPulsado();
                        switchPreference.setChecked(false);
                    }else {
                        Toast.makeText(getContext(),"Servicio iniciado",Toast.LENGTH_SHORT).show();
                        editor.putBoolean(preferenceName, true);
                        botonArrancarServicioPulsado();
                        switchPreference.setChecked(true);
                    }
                    editor.apply();
                    return false;
                }
            });
        }

        // -----------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------

        /**
         * Metodo que gestiona la parada del servicio
         * botonDetenerServicioPulsado()
         * */
        public void botonDetenerServicioPulsado() {
            if ( this.elIntentDelServicio == null ) {
                // no estaba arrancado
                return;
            }
            getActivity().stopService( this.elIntentDelServicio );
            this.elIntentDelServicio = null;
            Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado" );
        }

        // -----------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------

        /**
         * Metodo que gestiona la inicializacion del servicio
         * botonArrancarServicioPulsado()
         */
        public void botonArrancarServicioPulsado() {
            Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado" );
            if ( this.elIntentDelServicio != null ) {
                // ya estaba arrancado
                return;
            }
            Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String nombreSensor = sharedPreferences.getString(getString(R.string.preferenceIdSensor), "noId");
            String ipServidor = sharedPreferences.getString(getString(R.string.preferenceIpServidor), "noIp");
            //int id_sensor = sharedPreferences.getInt(getString(R.string.usuarioActivoIdSensor), 3);
            this.elIntentDelServicio = new Intent(getContext(), ServicioEscuharBeacons.class);
            this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
            this.elIntentDelServicio.putExtra("ipServidor", ipServidor);
            this.elIntentDelServicio.putExtra("nombreDispositivo", nombreSensor);
            this.elIntentDelServicio.putExtra("usuarioActivoIdSensor", 1);
            getActivity().startService( this.elIntentDelServicio );
        }

    }
}