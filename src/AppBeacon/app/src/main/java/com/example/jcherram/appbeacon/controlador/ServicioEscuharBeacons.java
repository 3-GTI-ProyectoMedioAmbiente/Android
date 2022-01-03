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
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.R;
import com.example.jcherram.appbeacon.modelo.TramaIBeacon;
import com.example.jcherram.appbeacon.Utilidades;
import com.example.jcherram.appbeacon.modelo.Medicion;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// Fecha: 17/10/2021
// ServicioEscucharBeacons
// Clase para escuchar los beacons
// -----------------------------------------------------------------------------------
public class ServicioEscuharBeacons extends IntentService {

    private static final String ETIQUETA_LOG = ">>>>";
    private ScanCallback callbackDelEscaneo = null;
    private BluetoothLeScanner elEscanner;
    private boolean seguir = true;
    private ArrayList<Medicion> mediciones;
    private static int MEDICIONES_A_ENVIAR=2;
    private int iteracionesAntesLanzarNotificacion;
    private ClaseLanzarNotificaciones notificaciones;
    private boolean notificacionActiva = false;
    private int idUltimaMedicion = -1;
    /**
     * Constructor del servicio para escuchar IBeacons
     */
    public ServicioEscuharBeacons(  ) {
        super("HelloIntentService");
        mediciones = new ArrayList<>();
        inicializarBlueTooth();
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.constructor: termina");
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

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


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo que se lanzara siempre que se destruya el servicio
     */
    public void onDestroy() {
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onDestroy() " );
        this.parar(); // posiblemente no haga falta, si stopService() ya se carga el servicio y su worker thread
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Funcionamiento del servicio
     * @param intent Intetent donde nos vendran los parametros que queremos enviar
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        long tiempoDeEspera = intent.getLongExtra("tiempoDeEspera",50000);
        String nombreDispositivo = intent.getStringExtra("nombreDispositivo");
        int id_sensor = intent.getIntExtra("usuarioActivoIdSensor",3);
        LogicaFake logicaFake = new LogicaFake();
        notificaciones = new ClaseLanzarNotificaciones(getApplicationContext());
        this.seguir = true;
        // esto lo ejecuta un WORKER THREAD !
        long contador = 1;
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId() );

        try {

            while ( this.seguir ) {
                Thread.sleep(tiempoDeEspera);
                buscarEsteDispositivoBTLE(nombreDispositivo);
                Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador );
                if(mediciones.size() >=MEDICIONES_A_ENVIAR){
                    logicaFake.guardarMediciones(new ArrayList<>(mediciones), id_sensor);
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

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo que inicia la busqueda de dispositivos Blueetoh
     */
    private void buscarEsteDispositivoBTLE(String nombreBluetooth) {
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
        ScanFilter sf = new ScanFilter.Builder().setDeviceName(nombreBluetooth).build();
        List<ScanFilter> filters = new ArrayList<>();
        ScanSettings.Builder scan = new ScanSettings.Builder();
        filters.add(sf);

        this.elEscanner.startScan(filters, scan.build(), callbackDelEscaneo);
        //this.elEscanner.startScan(callbackDelEscaneo);

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + nombreBluetooth);
    }else{
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): Servicio ya iniciado!" );
    }

    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

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

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Mostramos la informacion por el lo y guardamos la informacion de las mediciones en un Array de mediciones
     * @param resultado objeto que contiene la informacion que se ha de mostrar en el LOG
     */
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado) {

        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        TramaIBeacon tib = new TramaIBeacon(bytes);
        //Para evitar leer 2 veces el mismo Beacon los identificamos por el Major
        if(idUltimaMedicion!=Utilidades.bytesToInt(tib.getMajor())){
            int rssi = resultado.getRssi();
            if(bluetoothDevice.getName()!=null){
                Date currentTime = Calendar.getInstance().getTime();

                float dato = Utilidades.bytesToInt(tib.getMinor());
                Medicion medicion = new Medicion(dato, currentTime, new Time(currentTime.getTime()), 38.99698442634084f,-0.1663422921168631f );
                // Nuestro sensor solo mide NO2 por lo que se asignara siempre este valor por defecto
                medicion.setId_tipoMedicion(3);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
                int id_sensor = sharedPreferences.getInt(getString(R.string.usuarioActivoIdSensor), -1);
                medicion.setId_sensor(id_sensor);
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
                Log.d("dato:-------->", "El dispositivo se encuentra a una distancia de rssi("+rssi+") txtPower("+tib.getTxPower()+"):  " + calculateDistance(tib.getTxPower(), rssi));
                sendMessageToActivity(calculateDistance(tib.getTxPower(), rssi));
                idUltimaMedicion=Utilidades.bytesToInt(tib.getMajor());
                if(Utilidades.bytesToInt(tib.getMinor())>252){
                    String currentDateTimeString = new SimpleDateFormat("HH:mm").format(new Date());
                    notificaciones.crearNotificacion("La alerta se ha registrado a las "+currentDateTimeString,"¡Alerta! Aire perjudicial para la salud");
                    notificacionActiva = true;
                }else{
                    if(notificacionActiva){
                        notificaciones.crearNotificacion("Has dejado atras una zona perjudicial para tu saluda. Revisa el historial de notificaciones para mas información.","¡Vuelves ha respirar aire no perjudicial!");
                        notificacionActiva=false;
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Calcula la distancia del iBeacon recibido segun el txPower y el rssi
     * @param txPower
     * @param rssi intesidad de la señal
     * @return distancia entre el movil y el IBeacon
     */
    public double calculateDistance(int txPower,int rssi) {
        // La señal vale el valor absoluto.

        double power = (txPower - rssi) / (10 * 4.0);
        return Math.pow(10, power);

    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------
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

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo que envia  la distancia del sensor y el movil a la activity en la que se mostrara la informacion
     * @param distanciaSensor distancia a evaluar para determinar la distancia del sensor
     */
    private void sendMessageToActivity(double distanciaSensor) {
        Intent intent = new Intent("SettingsActivity");
        // You can also include some extra data.
        intent.putExtra("distancia", distanciaSensor);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

}