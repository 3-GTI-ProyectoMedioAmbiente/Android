package com.example.jcherram.appbeacon.modelo;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Time;
import java.util.Locale;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
//Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class Medicion {
    private int id;
    private float medicion;
    private Date fecha;
    private Time hora;
    private float localizacion_lat;
    private float localizacion_lon;

    public Medicion(int id, float medicion, Date fecha, Time hora, float localizacion_lat, float localizacion_lon) {
        this.id = id;
        this.medicion = medicion;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion_lat = localizacion_lat;
        this.localizacion_lon = localizacion_lon;
    }
    public Medicion(float medicion, Date fecha, Time hora, float localizacion_lat, float localizacion_lon) {
        this.medicion = medicion;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion_lat = localizacion_lat;
        this.localizacion_lon = localizacion_lon;


    }

    /**
     * transforma el objeto actual a JSON
     * @return
     * @throws JSONException
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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public float getMedicion() {
        return medicion;
    }

    public void setMedicion(float medicion) {
        this.medicion = medicion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public float getLocalizacion_lat() {
        return localizacion_lat;
    }

    public void setLocalizacion_lat(float localizacion_lat) {
        this.localizacion_lat = localizacion_lat;
    }

    public float getLocalizacion_lon() {
        return localizacion_lon;
    }

    public void setLocalizacion_lon(float localizacion_lon) {
        this.localizacion_lon = localizacion_lon;
    }
}
