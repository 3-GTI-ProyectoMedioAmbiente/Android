package com.example.jcherram.appbeacon.adapter;

public class Notificacion {


    private String descripción;
    private String fecha;
    private int foto;


    public Notificacion(String descripción, String fecha, int foto) {

        this.descripción=descripción;
        this.fecha=fecha;
        this.foto=foto;


    }

    public Notificacion(String descripción){
        this.descripción=descripción;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "descripción='" + descripción + '\'' +
                ", fecha='" + fecha + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}