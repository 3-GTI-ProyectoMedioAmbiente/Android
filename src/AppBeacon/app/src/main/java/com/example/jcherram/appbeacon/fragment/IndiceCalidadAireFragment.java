package com.example.jcherram.appbeacon.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

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

    private TextView textViewMediaCalidadAire;
    private TextView textViewTextoMedia;

    private TextView textViewValorUltima;
    private TextView textViewHoraUltima;
    private TextView textViewMedicionUltima;

    private ImageView imageViewConsejo1;
    private ImageView imageViewConsejo2;
    private ImageView imageViewICA;

    private TextView textViewIndiceHoy;
    private TextView textViewConsejo1;
    private TextView textViewConsejo2;


    //Fondo de las CardViews
    private ConstraintLayout constraintFondo;
    private ConstraintLayout constraintFondoFuerte;


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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        int res = sharedPref.getInt(getString(R.string.usuarioActivoId), 1);
        logicaFake.obtenerMedicionesUltimas24h("dia",res,this);
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
        textViewMediaCalidadAire = v.findViewById(R.id.textViewMediaCalidadAire);
        textViewIndiceHoy = v.findViewById(R.id.textViewIndiceHoy);
        textViewTextoMedia = v.findViewById(R.id.textViewTextoMediaCalidadAire);

        textViewHoraUltima = v.findViewById(R.id.textViewHoraUltima);
        textViewValorUltima = v.findViewById(R.id.textViewValorUtima);
        textViewMedicionUltima = v.findViewById(R.id.textViewMedicionUltima);
        textViewConsejo1 = v.findViewById(R.id.textViewConsejo1);
        textViewConsejo2 = v.findViewById(R.id.textViewConsejo2);
        constraintFondo = v.findViewById(R.id.constraitFonfoDebil);
        constraintFondoFuerte = v.findViewById(R.id.constraintFondoFuerte);

        imageViewConsejo1 = v.findViewById(R.id.imageViewConsejo1);
        imageViewConsejo2 = v.findViewById(R.id.imageViewConsejo2);
        imageViewICA = v.findViewById(R.id.imageViewICA);
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
        if (!list.isEmpty()){
            float res = 0;
            for (int i = 0; i<list.size();i++){
                res+=list.get(i).getMedicion();
            }
            res= res/list.size();
            actualizarConsesjosSegunMedia(res);
            ultimaMedicion = list.get(list.size()-1);
            textViewMediaCalidadAire.setText(String.format("%.0f", res));


            if(ultimaMedicion.getMedicion()>40 && ultimaMedicion.getMedicion()<100){
                textViewValorUltima.setTextColor(getResources().getColor(R.color.naranjaICA, null));
            }else if(ultimaMedicion.getMedicion()>=100){
                textViewValorUltima.setTextColor(getResources().getColor(R.color.rojoICA, null));
            }
            textViewValorUltima.setText(ultimaMedicion.getValor());
            textViewHoraUltima.setText(Utilidades.TimeToString(ultimaMedicion.getHora()));
            textViewMedicionUltima.setText(String.format("%.02f", ultimaMedicion.getMedicion())+" µg/m3");
        }


    }

    /**
     * actualzizar los colores y la informacion segun su ICA
     * @param media
     */
    private void actualizarConsesjosSegunMedia(float media){
        int colorFuerte=R.color.verdeICAFuerte;
        int colorDebil=R.color.verdeICA;
        int colorLetra = R.color.verdeLetraICA;
        int image = R.drawable.ic_cool;
        int imageConsejo1 = R.drawable.ic_open_window;
        int imageConsejo2 = R.drawable.ic_soccer;
        int textoConsejo1 = R.string.consejoAireSaludable1;
        int textoConsejo2 = R.string.consejoAireSaludable2;
        String textoCalidaAire = "No perjudicial";
        if(media>40 && media <100){
            //Indice de calidad Perjudicial
            colorDebil = R.color.naranjaICA;
            colorFuerte = R.color.naranjaICAFuerte;
            colorLetra = R.color.naranjaLetraICA;
            image = R.drawable.ic_confused;
            imageConsejo1 = R.drawable.ic_ic_health_window_red;
            textoConsejo1 = R.string.consejoAirePerjudicial1;
            textoConsejo2 = R.string.consejoAirePerjudicial2;
            textoCalidaAire="Perjudicial";
        }else if(media>100){
            //Indice de calidad Muy perjudidicial
           colorDebil = R.color.rojoICA;
           colorFuerte = R.color.rojoICAFuerte;
           colorLetra = R.color.rojoLetraICA;
           image = R.drawable.ic_face_red;
           imageConsejo1 = R.drawable.ic_ic_health_window_red;
           imageConsejo2 = R.drawable.ic_health_mask_red;
           textoConsejo1 = R.string.consejoAireMuyPerjudicial1;
           textoConsejo2 = R.string.consejoAireMuyPerjudicial2;
           textoCalidaAire="Muy perjudicial";
        }
        textViewTextoMedia.setText(textoCalidaAire);
        imageViewICA.setImageDrawable(getResources().getDrawable(image,null));
        if (media > 40) {
            //El color se cambia solo en estado perjudicial y muy perjudicail.
            imageViewICA.setColorFilter(getResources().getColor(colorLetra,null));
        }


        imageViewConsejo1.setImageDrawable(getResources().getDrawable(imageConsejo1,null));
        imageViewConsejo1.setColorFilter(getResources().getColor(colorDebil,null));

        imageViewConsejo2.setImageDrawable(getResources().getDrawable(imageConsejo2,null));
        imageViewConsejo2.setColorFilter(getResources().getColor(colorDebil,null));

        textViewConsejo1.setText(getResources().getString(textoConsejo1));
        textViewConsejo2.setText(getResources().getString(textoConsejo2));

        textViewTextoMedia.setTextColor(getResources().getColor(colorLetra, null));
        textViewIndiceHoy.setTextColor(getResources().getColor(colorLetra, null));
        constraintFondoFuerte.setBackgroundColor(getResources().getColor(colorFuerte, null));
        constraintFondo.setBackgroundColor(getResources().getColor(colorDebil, null));
    }

}