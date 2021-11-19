package com.example.jcherram.appbeacon.adapter;


// -----------------------------------------------------------------------------------
// @author: Alberto Valls Martinez
//Fecha: 03/11/2021
// -----------------------------------------------------------------------------------


//Clase medicion
public class Medicion {

    private String valor;
    private String tipo;
    private String fecha;
    private int medicion;

    /**
     *  Constructor
     */
    public Medicion(String valor,String tipo, String fecha, int medicion) {

        this.valor=valor;
        this.fecha=fecha;
        this.tipo=tipo;
        this.medicion=medicion;


    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    public Medicion(int medicion){
        this.medicion=medicion;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getMedicion() {
        return medicion;
    }

    public void setMedicion(int medicion) {
        this.medicion = medicion;
    }


    @Override
    public String toString() {
        return "Medicion{" +
                "valor='" + valor + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", medicion=" + medicion +
                '}';
    }
}