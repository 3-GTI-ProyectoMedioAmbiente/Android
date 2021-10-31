package com.example.jcherram.appbeacon.controlador;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.util.Log;

import com.example.jcherram.appbeacon.modelo.TramaIBeacon;
import com.example.jcherram.appbeacon.Utilidades;
import com.example.jcherram.appbeacon.modelo.Medicion;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
//Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class ServicioEscuharBeacons extends IntentService {

    private static final String ETIQUETA_LOG = ">>>>";
    private static final String NOMBRE_DISPOSITIVO_BL = "gti_jcherram";
    private ScanCallback callbackDelEscaneo = null;
    private BluetoothLeScanner elEscanner;
    private boolean seguir = true;
    private ArrayList<Medicion> mediciones;
    private static int MEDICIONES_A_ENVIAR=2;

    /**
     * Constructor del servicio para escuchar IBeacons
     */
    public ServicioEscuharBeacons(  ) {
        super("HelloIntentService");
        mediciones = new ArrayList<>();
        inicializarBlueTooth();
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.constructor: termina");
    }

    /**
     * Metodo que gestiona la parada del servio
     */
    public void parar () {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() " );


        if ( this.seguir == false ) {
            return;
        }

        this.seguir = false;
        this.stopSelf();

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() : acaba " );

    }

    /**
     * Metodo que se lanzara siempre que se destruya el servicio
     */
    public void onDestroy() {
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onDestroy() " );
        this.parar(); // posiblemente no haga falta, si stopService() ya se carga el servicio y su worker thread
    }

    /**
     * Funcionamiento del servicio
     * @param intent Intetent donde nos vendran los parametros que queremos enviar
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        long tiempoDeEspera = intent.getLongExtra("tiempoDeEspera",50000);
        String direccionIpServidor = intent.getStringExtra("ipServidor");
        LogicaFake logicaFake = new LogicaFake(direccionIpServidor);

        this.seguir = true;
        // esto lo ejecuta un WORKER THREAD !
        long contador = 1;
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId() );

        try {

            while ( this.seguir ) {
                Thread.sleep(tiempoDeEspera);
                buscarEsteDispositivoBTLE();
                Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador );
                if(mediciones.size() >=MEDICIONES_A_ENVIAR){
                    logicaFake.insetarMediciones(new ArrayList<>(mediciones));
                    mediciones= new ArrayList<>();
                }
                contador++;
            }

            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent : tarea terminada ( tras while(true) )" );

        } catch (InterruptedException e) {
            // Restore interrupt status.
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: problema con el thread");

            Thread.currentThread().interrupt();
        }
        detenerBusquedaDispositivosBTLE();
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: termina");

    }

    /**
     * Metodo que inicia la busqueda de dispositivos Blueetoh
     */
    private void buscarEsteDispositivoBTLE() {
    if (callbackDelEscaneo==null){
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");
        // super.onScanResult(ScanSettings.SCAN_MODE_LOW_LATENCY, result); para ahorro de energía
        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult( int callbackType, ScanResult resultado ) {
                super.onScanResult(callbackType, resultado);
                mostrarInformacionDispositivoBTLE( resultado);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        };

        //filtro por nombre de beacon
        ScanFilter sf = new ScanFilter.Builder().setDeviceName(NOMBRE_DISPOSITIVO_BL).build();
        List<ScanFilter> filters = new ArrayList<>();
        ScanSettings.Builder scan = new ScanSettings.Builder();
        filters.add(sf);

        this.elEscanner.startScan(filters, scan.build(), callbackDelEscaneo);
        //this.elEscanner.startScan(callbackDelEscaneo);

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + NOMBRE_DISPOSITIVO_BL );
    }else{
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): Servicio ya iniciado!" );
    }

    }

    /**
     * Metdo para pque gestiona la parada de la busqueda de dispositvos Blueetooth
     */
    private void detenerBusquedaDispositivosBTLE() {

        if ( this.callbackDelEscaneo == null ) {
            return;
        }

        this.elEscanner.stopScan( this.callbackDelEscaneo );
        this.callbackDelEscaneo = null;

    }

    /**
     * Mostramos la informacion por el lo y guardamos la informacion de las mediciones en un Array de mediciones
     * @param resultado objeto que contiene la informacion que se ha de mostrar en el LOG
     */
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado) {



        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();
        if(bluetoothDevice.getName()!=null){
            Date currentTime = Calendar.getInstance().getTime();
            TramaIBeacon tib = new TramaIBeacon(bytes);
            float dato = Utilidades.bytesToInt(tib.getMinor());
            Log.d(ETIQUETA_LOG, "dato------------------->"+Utilidades.bytesToInt(tib.getMinor()));
            Medicion medicion = new Medicion(dato, currentTime, new Time(currentTime.getTime()), 25.6f,35.6f );
            mediciones.add(medicion);

            Log.d(ETIQUETA_LOG, " ****************************************************");
            Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ****************** ");
            Log.d(ETIQUETA_LOG, " ****************************************************");
            Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());

            Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());


            Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
            Log.d(ETIQUETA_LOG, " rssi = " + rssi );

            Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
            Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));



            Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
            Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
            Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
            Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
            Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
            Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
            Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                    + tib.getiBeaconLength() + " ) ");
            Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
            Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
            Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                    + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
            Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                    + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
            Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
            Log.d(ETIQUETA_LOG, " ****************************************************");


        }
    }

    /**
     * Inicializacion para poder buscar dispositivos BL
     */
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");

        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");

        bta.enable();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        this.elEscanner = bta.getBluetoothLeScanner();

        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");
        }
    }
}