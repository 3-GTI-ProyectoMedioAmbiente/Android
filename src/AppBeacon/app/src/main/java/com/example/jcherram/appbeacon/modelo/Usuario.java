package com.example.jcherram.appbeacon.modelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Locale;

/**
 *
 * Usuario.java
 * @author Sergi Sirvent Sempere
 * Fecha: 14/12/21
 * Clase que contiene el modelo del objeto usuario
 *
 */

public class Usuario {

    //atributos privados de la clase usuario
    private int id;
    private String mail;
    private String nombre;
    private String apellidos;
    private Boolean isAutobusero;
    private int edad;
    private String matricula;
    private String telefono;
    private String password;


    /**
     * Constructor para un autobusero, el cual tiene en cuenta todos los atributos de la clase
     *
     * @param id
     * @param mail
     * @param nombre
     * @param apellidos
     * @param isAutobusero
     * @param edad
     * @param matricula
     * @param telefono
     * @param password
     *
     * id:Z,mail:Texto,nombre:Texto,apellidos:Texto,isAutobusero:VoF,edad:Z,matricula:Texto,telefono:Texto -> constructor()
     */
    public Usuario(int id, String mail, String nombre, String apellidos, Boolean isAutobusero, int edad, String matricula, String telefono, String password) {
        this.id = id;
        this.mail = mail;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.isAutobusero = isAutobusero;
        this.edad = edad;
        this.matricula = matricula;
        this.telefono = telefono;
        this.password = password;
    }

    /**
     * Constructor para no autobusero, que no tiene como parámetro los atributos isAutobusero y de matricula
     *
     * @param id
     * @param mail
     * @param nombre
     * @param apellidos
     * @param edad
     * @param telefono
     * @param password
     *
     * id:Z,mail:Texto,nombre:Texto,apellidos:Texto,edad:Z,telefono:Texto -> constructor()
     */
    public Usuario(int id, String mail, String nombre, String apellidos, int edad, String telefono, String password) {
        this.id = id;
        this.mail = mail;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.telefono = telefono;
        this.password = password;
        this.isAutobusero = false;
        this.matricula = "Sin Matricula";


    }

    /**
     *
     * Getters y setters
     *
     */


    //id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //mail
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    //nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //apellidos
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    //isAutobusero
    public Boolean getAutobusero() {
        return isAutobusero;
    }

    public void setAutobusero(Boolean autobusero) {
        isAutobusero = autobusero;
    }

    //edad
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    //matricula
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    //telefono
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que transforma al usuario en un objeto json
     *
     * @return json - Objeto json con los datos del usuario
     * @throws JSONException
     *
     * toJson() -> jsonObject
     */

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("id_usuario", id);
        json.put("mail",mail);
        json.put("nombre", nombre);
        json.put("apellidos",apellidos);
        json.put("isAutobusero",isAutobusero);
        json.put("edad",edad);
        json.put("matricula",matricula);
        json.put("telefono",telefono);
        json.put("password",password);

        return  json;
    }
}
