package com.example.jcherram.appbeacon.adapter;

public class Medicion {

    public String valor;
    public String fecha;
    public String tipo;
    public  int medida;


    public Medicion(String valor, String fecha, String tipo, int medida) {
        this.valor = valor;
        this.fecha = fecha;
        this.tipo = tipo;
        this.medida = medida;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }
}
