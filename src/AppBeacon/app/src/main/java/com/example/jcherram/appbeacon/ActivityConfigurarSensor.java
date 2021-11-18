package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.controlador.ServicioEscuharBeacons;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityConfigurarSensor extends AppCompatActivity {


    private static final String ETIQUETA_LOG = ">>>>";
    private static final String DIRECCION_SERVIDOR = "http://192.168.78.31:8080/";
    private Intent elIntentDelServicio = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_nodo);

        findViewById(R.id.botonArrancarServicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonArrancarServicioPulsado();
            }
        });

        findViewById(R.id.botonDetenerServicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonDetenerServicioPulsado();
            }
        });

    }

    /**
     * Metodo que gestiona la parada del servicio
     * */
    public void botonDetenerServicioPulsado() {
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
     */
    public void botonArrancarServicioPulsado() {
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
}