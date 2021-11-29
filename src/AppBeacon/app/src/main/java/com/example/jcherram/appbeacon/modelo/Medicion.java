package com.example.jcherram.appbeacon.modelo;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Time;
import java.util.Locale;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class Medicion {
    private int id;
    private float medicion;
    private Date fecha;
    private Time hora;
    private float localizacion_lat;
    private float localizacion_lon;
    private String valor;
    private String tipoMedicion;
    /**
     * Constructor de la clase Medcion
     * @param id Identificador de la medicion
     * @param medicion Medida
     * @param fecha Fecha cuando se produjo la medidad
     * @param hora Hora cuando se produjo la medida
     * @param localizacion_lat Coordenda latitud donde se produjo la medidaa
     * @param localizacion_lon Coordenda longitud donde se produjo la medidaa
     */
    public Medicion(int id, float medicion, Date fecha, Time hora, float localizacion_lat, float localizacion_lon) {
        this.id = id;
        this.medicion = medicion;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion_lat = localizacion_lat;
        this.localizacion_lon = localizacion_lon;

        if(medicion <=40 ){
            valor="No perjudicial";
        }else if (medicion>40 && medicion<200){
            valor="Perjudicial";
        }else{
            valor = "Muy perjudial";
        }
        tipoMedicion="NO2";
    }

    /**
     * Constructor de la clase Medcion
     * @param medicion Medida
     * @param fecha Fecha cuando se produjo la medidad
     * @param hora Hora cuando se produjo la medida
     * @param localizacion_lat Coordenda latitud donde se produjo la medidaa
     * @param localizacion_lon Coordenda longitud donde se produjo la medidaa
     */
    public Medicion(float medicion, Date fecha, Time hora, float localizacion_lat, float localizacion_lon) {
        this.medicion = medicion;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion_lat = localizacion_lat;
        this.localizacion_lon = localizacion_lon;
        if(medicion <=40 ){
            valor="No perjudicial";
        }else if (medicion>40 && medicion<200){
            valor="Perjudicial";
        }else{
            valor = "Muy perjudial";
        }
        tipoMedicion="NO2";
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
     * Getter de la clase Medicion que devuelve el atributo tipoMedicion de la clase
     * @return valor string resultante
     */
    public String getTipoMedicion() {
        return tipoMedicion;
    }
    /**
     * Setter para modificar el valor tipoMedicion
     * @param tipoMedicion nuevo valor para el atributo que se va ha modificar
     */
    public void setTipoMedicion(String tipoMedicion) {
        this.tipoMedicion = tipoMedicion;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo valor de la clase
     * @return valor string resultante
     */
    public String getValor() {
        return valor;
    }
    /**
     * Setter para modificar el valor 'valor'
     * @param valor nuevo valor para el atributo que se va ha modificar
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Getter de la clase Medicion que devuelve el atributo ID de la clase
     * @return id entero resultante
     */
    public int getId() {
        return id;
    }

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
