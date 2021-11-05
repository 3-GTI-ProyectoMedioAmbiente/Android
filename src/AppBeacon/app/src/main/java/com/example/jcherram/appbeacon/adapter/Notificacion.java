package com.example.jcherram.appbeacon.adapter;


// -----------------------------------------------------------------------------------
// @author: Alberto Valls Martinez
//Fecha: 03/11/2021
// -----------------------------------------------------------------------------------


//Clase notificacion
public class Notificacion {


    private String descripción;
    private String fecha;
    private int foto;

    /**
     *  Constructor Notificacion
     * @param descripción de la notificacion
     * @param fecha de la notificacion
     * @param foto de la notificacion
     *
     * Z,text,text->Notificacion()
     */
    public Notificacion(String descripción, String fecha, int foto) {

        this.descripción=descripción;
        this.fecha=fecha;
        this.foto=foto;


    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Constructor para la descripcion
     * @param descripción
     *
     * text->Notificacion()
     */
    public Notificacion(String descripción){
        this.descripción=descripción;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo getDescripcion
     * @return devolvemos la descripcion
     *
     * getDescripcion()->text
     */
    public String getDescripción() {
        return descripción;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo setDescripcion
     * @param descripción establecemos la descripcion
     *
     * text->setDescripcion()
     */
    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo getFecha
     * @return devolvemos la fecha
     *
     * getFecha()->text
     */
    public String getFecha() {
        return fecha;
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     *  Metodo setFecha
     * @param fecha establecemos la fecha
     *
     * text->setFecha()
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo getFoto
     * @return devolvemos la foto
     *
     * getFoto()->text
     */
    public int getFoto() {
        return foto;
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo setFoto
     * @param foto establecemos la foto
     *
     * Z->setFoto()
     */
    public void setFoto(int foto) {
        this.foto = foto;
    }

    /**
     * Metodo toString para establecer la notificacion
     * @return devolvemos la notificacion con sus parametros
     *
     * toString()->text
     */
    @Override
    public String toString() {
        return "Notificacion{" +
                "descripción='" + descripción + '\'' +
                ", fecha='" + fecha + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}