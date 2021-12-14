package com.example.jcherram.appbeacon.controlador;

import android.util.Log;

import com.example.jcherram.appbeacon.ActivityHistorialMediciones;
import com.example.jcherram.appbeacon.fragment.IndiceCalidadAireFragment;
import com.example.jcherram.appbeacon.Utilidades;
import com.example.jcherram.appbeacon.modelo.Medicion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// Fecha: 17/10/2021
// -----------------------------------------------------------------------------------

public class LogicaFake {
    private final String direccionIpServidor;

    public LogicaFake(String direccionIpServidor){
        this.direccionIpServidor = direccionIpServidor;
    }

    /**
     * Realizar peticione de insercion de datos mediante peticion REST
     * @param mediciones lista de mediciones a insetar
     *
     * <Medicion>->insertarMediciones()
     */
    public void insetarMediciones(ArrayList<Medicion> mediciones){
        JSONObject json = new JSONObject();
        try {

            JSONArray array = new JSONArray();
            for (Medicion m : mediciones){
                array.put(m.toJson());
            }

            json.put("mediciones", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("POST",  direccionIpServidor+"insertMedicionJson", json.toString(),
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d(">>>>>>>>>>>>>","Insercion realizada corrrectamente!");
                    }
                }
        );
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo para recoger las mediciones de hoy mediante peticion REST
     * @param fecha pasamos la fecha en la que queremos recoger las mediciones
     * @param beaconsFragment le pasamos el fragment de beacons
     *
     * Texto, BeaconsFragment->getMedicionesHoy()
     */


    public void getMedicionesHoy(String fecha, IndiceCalidadAireFragment beaconsFragment){

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET",  direccionIpServidor+"obtenerTodasLasMediciones","",
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        beaconsFragment.calcularMedia(parsearJsonToArrayMediciones(cuerpo));
                    }
                }
        );

    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para recoger todas las mediciones de la bbdd mediante peticion REST
     * @param activity le pasamos la activity que recibirá las mediciones
     */
    public void getTodasLasMediciones(ActivityHistorialMediciones activity){

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET",  direccionIpServidor+"obtenerTodasLasMediciones","",
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        activity.loadMediciones(parsearJsonToArrayMediciones(cuerpo));
                    }
                }
        );
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para parsear Json a un arraylist de mediciones
     * @param json le pasamos el json que queremos parsear
     * @return devolvemos el nuevo arraylist de mediciones
     *
     * Texto->parsearJsonToArrayMediciones()-><Medicion>
     */
    private ArrayList<Medicion> parsearJsonToArrayMediciones(String json){
        ArrayList<Medicion> arrayListMediciones = new ArrayList<>();
        try {


            JSONObject reader = new JSONObject(json);
            JSONArray mediciones  = reader.getJSONArray("mediciones");
            for(int i= 0; i<mediciones.length();i++){
                JSONObject m = mediciones.getJSONObject(i);

                int id = m.getInt("id");
                float medicion = (float)m.getDouble("medicion");
                Date fecha = Utilidades.stringToDate(m.getString("fecha"));
                Time time = Utilidades.stringToTime(m.getString("hora"));
                float localizacion_lat = (float)m.getDouble("localizacion_lat");
                float localizacion_lon = (float)m.getDouble("localizacion_lon");

                arrayListMediciones.add(new Medicion(id,medicion,fecha,time, localizacion_lat,localizacion_lon));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayListMediciones;
    }


}
