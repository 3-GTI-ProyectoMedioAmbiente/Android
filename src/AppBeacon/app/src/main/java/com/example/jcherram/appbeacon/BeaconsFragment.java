package com.example.jcherram.appbeacon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeaconsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeaconsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int id;
    private float medicion;
    private Date fecha;
    private Time hora;
    private float localizacion_lat;
    private float localizacion_lon;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BeaconsFragment() {
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
    // TODO: Rename and change types and number of parameters
    public static BeaconsFragment newInstance(String param1, String param2) {
        BeaconsFragment fragment = new BeaconsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beacons2, container, false);
    }

    /**
     * Constructor de la clase Medcion
     * @param id Identificador de la medicion
     * @param medicion Medida
     * @param fecha Fecha cuando se produjo la medidad
     * @param hora Hora cuando se produjo la medida
     * @param localizacion_lat Coordenda latitud donde se produjo la medidaa
     * @param localizacion_lon Coordenda longitud donde se produjo la medidaa
     */
    public BeaconsFragment(int id, float medicion, Date fecha, Time hora, float localizacion_lat, float localizacion_lon) {
        this.id = id;
        this.medicion = medicion;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion_lat = localizacion_lat;
        this.localizacion_lon = localizacion_lon;
    }

    /**
     * Constructor de la clase Medcion
     * @param medicion Medida
     * @param fecha Fecha cuando se produjo la medidad
     * @param hora Hora cuando se produjo la medida
     * @param localizacion_lat Coordenda latitud donde se produjo la medidaa
     * @param localizacion_lon Coordenda longitud donde se produjo la medidaa
     */
    public BeaconsFragment(float medicion, Date fecha, Time hora, float localizacion_lat, float localizacion_lon) {
        this.medicion = medicion;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion_lat = localizacion_lat;
        this.localizacion_lon = localizacion_lon;
    }

    /**
     * Transforma el objeto actual a JSON
     * @return JSONObject medicion en formato JSOn
     * @throws JSONException excepcion en que se puede producir al crear el objeto JSON
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA);

        json.put("medicion", medicion);
        json.put("fecha",dateFormat.format(fecha).replace('/','-'));
        json.put("hora", hora);
        json.put("localizacion_lat", localizacion_lat);
        json.put("localizacion_lon", localizacion_lon);
        return  json;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo ID de la clase
     * @return id entero resultante
     */

    //public int getId() { return id; }

    /**
     * Setter para modificar el valor hora
     * @param id nuevo valor para el atributo que se va ha modificar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo medicion de la clase
     * @return float numero resultante
     */
    public float getMedicion() {
        return medicion;
    }

    /**
     * Setter para modificar el valor medicion
     * @param medicion nuevo valor para el atributo que se va ha modificar
     */
    public void setMedicion(float medicion) {
        this.medicion = medicion;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo feche de la clase
     * @return fecha resultante
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Setter para modificar el valor hora
     * @param fecha nuevo valor para el atributo que se va ha modificar
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo hora de la clase
     * @return Time hora resultante
     */
    public Time getHora() {
        return hora;
    }

    /**
     * Setter para modificar el valor hora
     * @param hora nuevo valor para el atributo que se va ha modificar
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo localizacion_lat de la clase
     * @return float numero resultante
     */
    public float getLocalizacion_lat() {
        return localizacion_lat;
    }

    /**
     * Setter para modificar el valor localizacion_lat
     * @param localizacion_lat nuevo valor para el atributo que se va ha modificar
     */
    public void setLocalizacion_lat(float localizacion_lat) {
        this.localizacion_lat = localizacion_lat;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo localizacion_lon de la clase
     * @return float numero resultante
     */
    public float getLocalizacion_lon() {
        return localizacion_lon;
    }

    /**
     * Setter para modificar el valor Localizacion_Lon
     * @param localizacion_lon nuevo valor para el atributo que se va ha modificar
     */
    public void setLocalizacion_lon(float localizacion_lon) {
        this.localizacion_lon = localizacion_lon;
    }
}