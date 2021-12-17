package com.example.jcherram.appbeacon.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcherram.appbeacon.ActivityHistorialMediciones;
import com.example.jcherram.appbeacon.R;
import com.example.jcherram.appbeacon.SettingsActivity;
import com.example.jcherram.appbeacon.Utilidades;
import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.modelo.Medicion;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndiceCalidadAireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndiceCalidadAireFragment extends Fragment {

    private TextView textViewMedia;
    private TextView textViewTextoMedia;

    private TextView textViewValorUltima;
    private TextView textViewHoraUltima;
    private TextView textViewMedicionUltima;

    private Medicion ultimaMedicion;
    private LogicaFake logicaFake;
    public IndiceCalidadAireFragment() {
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
    public static IndiceCalidadAireFragment newInstance(String param1, String param2) {
        IndiceCalidadAireFragment fragment = new IndiceCalidadAireFragment();

        return fragment;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Se llama cuando onCreate el Fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logicaFake = new LogicaFake();

        logicaFake.obtenerMedicionesUltimas24h("2021-12-17",this);
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
        View v = inflater.inflate(R.layout.fragment_beacons2, container, false);
        Button buttonVerHistorial = v.findViewById(R.id.buttonVerHistorial);
        ImageView buttonConfigurarNodo = v.findViewById(R.id.imageViewConfigurarNodo);
        textViewMedia = v.findViewById(R.id.textViewMediaCalidadAire);
        textViewTextoMedia = v.findViewById(R.id.textViewTextoMediaCalidadAire);

        textViewHoraUltima = v.findViewById(R.id.textViewHoraUltima);
        textViewValorUltima = v.findViewById(R.id.textViewValorUtima);
        textViewMedicionUltima = v.findViewById(R.id.textViewMedicionUltima);

        buttonConfigurarNodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarNodo();
            }
        });

        buttonVerHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verHistorial();
            }
        });
        return v;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para abrir la activity de settings
     * configurarNodo()
     */
    private void configurarNodo(){
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para ver el historial de mediciones
     * verHistorial()
     */
    private void verHistorial(){
        Intent intent = new Intent(getContext(), ActivityHistorialMediciones.class);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para calcular la media de mediciones
     * @param list recibimos la lista de mediciones
     *
     * <medicion>->calcularMedia()
     */

    public void calcularMedia(ArrayList<Medicion> list){
        float res = 0;
        for (int i = 0; i<list.size();i++){
            res+=list.get(i).getMedicion();
        }
        res= res/list.size();
        ultimaMedicion = list.get(list.size()-1);

        textViewMedia.setText(String.format("%.02f", res));
        textViewTextoMedia.setText(ultimaMedicion.getValor());

        textViewValorUltima.setText(ultimaMedicion.getValor());
        textViewHoraUltima.setText(Utilidades.TimeToString(ultimaMedicion.getHora()));
        textViewMedicionUltima.setText(String.format("%.02f", ultimaMedicion.getMedicion())+" µg/m3");
    }

}