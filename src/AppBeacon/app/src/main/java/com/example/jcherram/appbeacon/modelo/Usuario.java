package com.example.jcherram.appbeacon.modelo;

/**
 * Usuario.java
 * @author Sergi Sirvent Sempere
 * Fecha: 14/12/21
 * Clase que contiene el modelo del objeto usuario
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

    //constructor para un autobusero
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

    //constructor para no autobusero
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

    //getters y setters

    //id
    public int getId() {
        return id;
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
}
