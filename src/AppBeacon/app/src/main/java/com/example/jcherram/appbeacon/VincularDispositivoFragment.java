package com.example.jcherram.appbeacon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.fragment.IndiceCalidadAireFragment;
import com.example.jcherram.appbeacon.modelo.Usuario;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * @Author: Alberto Valls Martinez
 * Fecha: 23/11/21
 * VincularDispositivoFragment
 * Clase que recibe la MAC de un sensor por medio de escanear un QR
 */

//----------------------------------------------------------------
//----------------------------------------------------------------

/**
 * Clase VincularDispositivoFragment
 */
public class VincularDispositivoFragment extends Fragment {


    Button botonvincular;
    TextView txtresultado;


    public VincularDispositivoFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment BeaconsFragment.
     */
    public static VincularDispositivoFragment newInstance() {
        VincularDispositivoFragment fragment = new VincularDispositivoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vincular_nodo,
                container, false);


        botonvincular=view.findViewById(R.id.buttonvincular);
        txtresultado=view.findViewById(R.id.txtResultado);

        botonvincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });


        return view;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo que sirve para escanear un codigo QR
     * escanear()
     */
    public void escanear() {

        IntentIntegrator intent = IntentIntegrator.forSupportFragment(VincularDispositivoFragment.this);
        //IntentIntegrator intent = new IntentIntegrator(getActivity());
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("Escanea el sensor");
        intent.setCameraId(0);
        intent.setOrientationLocked(false);
        intent.setBeepEnabled(false);
        intent.setCaptureActivity(QrActivity.class);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();

    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para registrar un dispositivo en la cuenta de usuario
     * @param requestCode recibimos el codigo de solicitud
     * @param resultCode recibimos el codigo de resultado
     * @param data recibimos la informacion del dispositivo
     *
     * Z,Z,intent->onActivityResult()
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "El escaneo ha sido cancelado", Toast.LENGTH_SHORT).show();
            } else {
                //txtresultado.setText(result.getContents());
                String qr =  result.getContents();
                LogicaFake logicaFake = new LogicaFake();
                logicaFake.obtenerIdSensorMedianteMac(qr,getMacFromQR(qr),this);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void vincularNodo(int id_sensor, String qr){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.preferenceIdSensor), qr);
        editor.putString(getString(R.string.preferenceMacSensor),getMacFromQR(qr));
        editor.putString(getString(R.string.preferenceModeloSensor),"Pro Max V9");
        editor.putBoolean(getString(R.string.preferenceEstadoServicio), false);
        editor.putInt(getString(R.string.usuarioActivoIdSensor), id_sensor);
        editor.apply();

        //Actualizamos en la BD
        Usuario user = getCurrentUser();
        user.setId_sensor(id_sensor);

        LogicaFake logicaFake = new LogicaFake();
        logicaFake.editarUsuario(user, null);
        Toast.makeText(getContext(), "El dispositivo se ha registrado correctamente", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).setFragment(new IndiceCalidadAireFragment());
    }

    private Usuario getCurrentUser(){
        //int id, String mail, String nombre, String apellidos, Boolean isAutobusero, int edad, String matricula, String telefono, String password, int id_sensor
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int id = sharedPreferences.getInt(getString(R.string.usuarioActivoId), -1);
        String mail = sharedPreferences.getString(getString(R.string.usuarioActivoMail), "");
        String nomre = sharedPreferences.getString(getString(R.string.usuarioActivoNombre), "");
        String apellidos = sharedPreferences.getString(getString(R.string.usuarioActivoNombre), "");
        Boolean isAutobusero = sharedPreferences.getBoolean(getString(R.string.usuarioActivoIsAutobusero), false);
        String edad = sharedPreferences.getString(getString(R.string.usuarioActivoFechaNacimiento), "");
        String matricula = sharedPreferences.getString(getString(R.string.usuarioActivoMatricula), "");
        String telefono = sharedPreferences.getString(getString(R.string.usuarioActivoTelefono), "");
        String password = sharedPreferences.getString(getString(R.string.usuarioActivoPassword), "");
        int id_sensor = sharedPreferences.getInt(getString(R.string.usuarioActivoIdSensor), -1);

        return new Usuario(id, mail, nomre,apellidos,isAutobusero,edad,matricula,telefono,password,id_sensor);



    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo que coge la Mac del QR escaneado
     * @param QR recibimos el texto escaneado del QR
     * @return devolvemos la MAC
     *
     * Texto->getMacFromQr()->Texto
     */

    private String getMacFromQR(String QR){
        int indice = QR.indexOf('_');
        return QR.substring(0, indice);
    }




}
