package com.example.jcherram.appbeacon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class VincularDispositivoFragment extends Fragment {


    Button botonvincular;
    TextView txtresultado;


    public VincularDispositivoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeaconsFragment.
     */
    public static VincularDispositivoFragment newInstance(String param1, String param2) {
        VincularDispositivoFragment fragment = new VincularDispositivoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "El escaneo ha sido cancelado", Toast.LENGTH_SHORT).show();
            } else {
                //txtresultado.setText(result.getContents());
                String qr =  result.getContents();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.preferenceIdSensor), qr);
                editor.putString(getString(R.string.preferenceMacSensor),getMacFromQR(qr));
                editor.putString(getString(R.string.preferenceModeloSensor),"Pro Max V9");
                editor.putBoolean(getString(R.string.preferenceEstadoServicio), false);
                editor.apply();

                Toast.makeText(getContext(), "El dispositivo se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setFragment(new BeaconsFragment());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getMacFromQR(String QR){
        int indice = QR.indexOf('_');
        return QR.substring(0, indice);
    }




}
