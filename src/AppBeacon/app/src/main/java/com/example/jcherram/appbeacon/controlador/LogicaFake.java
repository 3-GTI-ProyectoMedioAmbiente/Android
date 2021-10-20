package com.example.jcherram.appbeacon.controlador;

import android.content.Context;
import android.widget.Toast;
import com.example.jcherram.appbeacon.modelo.Medicion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
//Fecha: 17/10/2021
// -----------------------------------------------------------------------------------

public class LogicaFake {
    private final String direccionIpServidor;

    public LogicaFake(String direccionIpServidor){
        this.direccionIpServidor = direccionIpServidor; }

    /**
     * Realizar peticione de insercion de datos mediante peticion REST
     * @param mediciones
     * @param context
     */
    public void insetarMediciones(ArrayList<Medicion> mediciones, Context context){
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
                        Toast.makeText(context, "La medicion se ha insertado correctamente!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


}
