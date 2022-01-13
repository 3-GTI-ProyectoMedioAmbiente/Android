package com.example.jcherram.appbeacon.controlador;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.ActivityHistorialMediciones;
import com.example.jcherram.appbeacon.ActivityHistorialMedicionesMensual;
import com.example.jcherram.appbeacon.ActivityHistorialMedicionesSemanal;
import com.example.jcherram.appbeacon.LoginActivity;
import com.example.jcherram.appbeacon.RegisterActivity;
import com.example.jcherram.appbeacon.Utilidades;
import com.example.jcherram.appbeacon.VincularDispositivoFragment;
import com.example.jcherram.appbeacon.fragment.IndiceCalidadAireFragment;
import com.example.jcherram.appbeacon.fragment.UserFragment;
import com.example.jcherram.appbeacon.modelo.Medicion;
import com.example.jcherram.appbeacon.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

// -----------------------------------------------------------------------------------
//LogicaFake.java
// @author: Juan Carlos Hernandez Ramirez y Sergi
// Fecha: 17/10/2021
//Fichero que contiene los metodos de la logica fake que se comunican con la logica de negocio
// -----------------------------------------------------------------------------------

public class LogicaFake {
    private final String direccionIpServidor = "http://192.168.58.219:5000/";
    public LogicaFake(){
    }

    /**
     * Realizar peticione de insercion de datos mediante peticion REST
     * @param mediciones lista de mediciones a insetar
     *
     * <Medicion>->guardarMediciones()
     */
    public void guardarMediciones(ArrayList<Medicion> mediciones, int id_sensor){
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
     * @param beaconsFragment le pasamos el fragment de beacons
     *
     * Texto, BeaconsFragment->obtenerMedicionesUltimas24h()
     */

    public void obtenerMedicionesUltimas24h(String tipo, int idUsuario, IndiceCalidadAireFragment beaconsFragment){

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET",  direccionIpServidor+"obtenerMedicionesConPeriodoPorUsuario?periodo="+tipo+"&idUsuario="+idUsuario,"",
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
    public void obtenerTodasLasMediciones(ActivityHistorialMediciones activity,  int idUsuario, String tipo){

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET",  direccionIpServidor+"obtenerMedicionesConPeriodoPorUsuario?periodo="+tipo+"&idUsuario="+idUsuario,"",
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
                arrayListMediciones.add(new Medicion(id,medicion,fecha,time, localizacion_lat,localizacion_lon, 3,1));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayListMediciones;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * loginUsuario
     * Metodo que recibe un mail y un usuario y hace una peticion en a base de datos
     * y si el usuario esta registrado se guarda el usuario
     *
     *
     * @param mail - Correo introducido por el usuario
     * @param pass - Contraseña introducida por el usuario
     */
    public void loginUsuario(String mail, String pass , LoginActivity activity){
        //hacemos peticion rest
        //?mail=sergisise@gmail.com&pass=hola
        Log.d("test","entroLogicaFake");
        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET", direccionIpServidor + "loginUsuario?mail="+mail+"&pass="+pass, "",
                new PeticionarioREST.RespuestaREST() {

                    @Override
                    public void callback(int codigo, String cuerpo) {
                        //aqui lo que me devuelva la base de datos
                        if (codigo == 200){
                            if (cuerpo.equals("-1")){
                                Toast.makeText(activity.getApplicationContext(), "Usuario no registrado o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                //Log.d("res","entro al if de menos 1");
                            }else{
                                //aqui proceso los datos de la bd
                                Log.d("cuerpoPeticion",cuerpo);
                                try {
                                    Log.d("cuerpoPeticion","Entro al try");
                                    Usuario user = crearUsuarioDeJsonString(cuerpo);
                                    activity.settearUsuarioActivo(user);
                                } catch (JSONException e) {
                                    Log.d("ErrorJSON","Algo va mal en el json de login");
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            Log.d("ResultadoBDLogin","Algo ha ido mal, res != 200");
                        }
                    }

                });

    }

    /**
     * Metodo que trara los datos json antes de crear un usuario
     * @param jsonUsuario string que contiene el json del usuario
     * @return Usuario creado a partir del JSON
     * @throws JSONException por si falla algo en el formato del JSON
     */
    private Usuario crearUsuarioDeJsonString(String jsonUsuario) throws JSONException {
        JSONObject json = new JSONObject(jsonUsuario);
        int id_usuario = json.getInt("id");
        String nombre = json.getString("nombre");
        String apellidos = json.getString("apellidos");
        String mail = json.getString("mail");
        String matricula = json.getString("matricula");
        String password = json.getString("password");
        String telefono = json.getString("telefono");

        int isAutobusero = json.getInt("isAutobusero");
        boolean autobuseroBool = false;
        if (isAutobusero == 1){
            autobuseroBool = true;
        }
        String fechaNacimiento = json.getString("fechaNacimiento");

        int id_sensor=-1;
        if(!json.isNull("id_sensor")){
            id_sensor = json.getInt("id_sensor");
        }
        return new Usuario(id_usuario,mail, nombre, apellidos, autobuseroBool, fechaNacimiento,matricula,telefono,password,id_sensor);
    }

    /**
     * Metodo de la logica fake que se encarga de comunicarse con crearusuario de la logica verdadera
     *
     * @param usuario - Objeto usuario que contiene la informacion para crear el usuario
     * @param activity - Parámetro referente a la actividad actual
     *
     * Usuario usuario,Context context,LoginActivity activity -> crearUsuario()
     */
    public void crearUsuario(Usuario usuario, RegisterActivity activity){

        JSONObject jsonUsuario = new JSONObject();
        try {


            jsonUsuario.put("nombre",usuario.getNombre());
            jsonUsuario.put("apellidos",usuario.getApellidos());
            jsonUsuario.put("mail",usuario.getMail());
            jsonUsuario.put("fechaNacimiento",usuario.getfechaNacimiento());
            jsonUsuario.put("telefono",usuario.getTelefono());
            jsonUsuario.put("password",usuario.getPassword());
            jsonUsuario.put("isAutobusero",usuario.getAutobusero().toString());
            jsonUsuario.put("matricula",usuario.getMatricula());

        } catch (JSONException e) {
            Log.d("json","Algo va mal en la creacion de json de editar usuario");
            e.printStackTrace();
        }
        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("POST", direccionIpServidor + "crearUsuario", jsonUsuario.toString(), new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                if (codigo == 200){
                    if (cuerpo.equals("-1")){
                        Log.d("crearUsuario","El correo de usuario ya ha sido creado");
                        Toast.makeText(activity.getApplicationContext(), "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show();
                    }else{
                        //caso correcto proceso la info de la bd
                        activity.redirigirLogin();
                    }
                }else{

                    Log.d("crearUsuario","Algo ha ido mal en la conexion con crear usuario");
                }
            }
        });
    }

    /**
     * Método de la lógica fake que se comunica con el método de editar usuario de la lógica
     * de negocio, encargada de editar un usuario de la base de datos
     *
     * @param usuario - usuario que se quiere editar
     * @param fragment -Fragment desde donde se lanza el usuario
     */
    public void editarUsuario(Usuario usuario,UserFragment fragment){
        JSONObject jsonUsuario = new JSONObject();
        try {

            jsonUsuario.put("id_usuario",usuario.getId());
            jsonUsuario.put("nombre",usuario.getNombre());
            jsonUsuario.put("apellidos",usuario.getApellidos());
            jsonUsuario.put("mail",usuario.getMail());
            jsonUsuario.put("fechaNacimiento",usuario.getfechaNacimiento());
            jsonUsuario.put("telefono",usuario.getTelefono());
            jsonUsuario.put("password",usuario.getPassword());
            if (usuario.getId_sensor() == -1){
                jsonUsuario.put("id_sensor","null");
            }else{
                jsonUsuario.put("id_sensor",usuario.getId_sensor());
            }


        } catch (JSONException e) {
            Log.d("json","Algo va mal en la creacion de json de editar usuario");
            e.printStackTrace();
        }

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("PUT", direccionIpServidor + "editarUsuario", jsonUsuario.toString(), new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //procesar la respuesta del put
                if (fragment!=null){
                    fragment.settearUsuarioActivo();
                }
            }
        });

    }


    /**
     * Metodo que recibe una mac y devuelvo la id del sensor
     * @param mac -mac del sensor del que se desea sacar la mac
     */
    public void obtenerIdSensorMedianteMac(String qr,String mac, VincularDispositivoFragment activity){
        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET", direccionIpServidor + "obtenerIdSensorMedianteMac?mac="+mac, "",
                new PeticionarioREST.RespuestaREST() {

                    @Override
                    public void callback(int codigo, String cuerpo) {
                        //aqui lo que me devuelva la base de datos
                        if (codigo == 200){
                            if (cuerpo.equals("-1")){
                                Toast.makeText(activity.getContext(), "Dirección mac incorrecta", Toast.LENGTH_SHORT).show();
                            }else{
                                activity.vincularNodo(Integer.parseInt(cuerpo),qr);
                            }
                        }else{
                            Log.d("ResultadoBDLogin","Algo ha ido mal, res != 200");
                        }
                    }

                });

    }

    /**
     * Meotodo que se encarga de publicar la info en la base de datos privada
     * id-usuario
     * id_sensor
     * telefono
     * distancia_recorrida
     * nombre
     * horas_activo
     * @param jsonInfo - Json que contiene la info necesaria del usuario
     *
     *
     */
    public void publicarInfoPrivada(JSONObject jsonInfo){
       PeticionarioREST peticionarioREST = new PeticionarioREST();
       peticionarioREST.hacerPeticionREST("POST", direccionIpServidor + "publicarInfoPrivada", jsonInfo.toString(),
               new PeticionarioREST.RespuestaREST() {
                   @Override
                   public void callback(int codigo, String cuerpo) {
                       if (Integer.valueOf(cuerpo) == 1){
                           Log.d("controlPublicarInfo","Todo ha ido bien");
                       }else{
                           Log.d("controlPublicarInfo","Algo ha ido mal");
                       }
                   }
               });
    }

    /**
     * Metodo al que se le indica un periodo y un usuario y te devuelve las mediciones correspondientes
     * @param id_usuario {Z} - Id del usuario que queremos acceder
     * @param periodo {Texto} - Periodo de tiempo que queremos filtrar las mediciones
     */

    public void obtenerMedicionesConPeriodoPorUsuario(String periodo, int id_usuario , UserFragment userFragment){
        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET", direccionIpServidor + "obtenerMedicionesConPeriodoPorUsuario?periodo=" + periodo + "&idUsuario=" + id_usuario, "",
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) throws JSONException {

                        if (!cuerpo.isEmpty()){
                            //si todo ha ido bien
                            userFragment.prepararInfoPrivada(parsearJsonToArrayMediciones(cuerpo));
                        }
                    }
                });
    }


    /**
     * Metodo para recoger todas las mediciones de la bbdd mediante peticion REST
     * @param activity le pasamos la activity que recibirá las mediciones
     */
    public void obtenerTodasLasMedicionesSemanales(ActivityHistorialMedicionesSemanal activity,  int idUsuario, String tipo){

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET",  direccionIpServidor+"obtenerMedicionesConPeriodoPorUsuario?periodo="+tipo+"&idUsuario="+idUsuario,"",
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        activity.loadMediciones(parsearJsonToArrayMediciones(cuerpo));

                    }
                }
        );
    }

    public void obtenerTodasLasMedicionesMensuales(ActivityHistorialMedicionesMensual activity,  int idUsuario, String tipo){

        PeticionarioREST peticionarioREST = new PeticionarioREST();
        peticionarioREST.hacerPeticionREST("GET",  direccionIpServidor+"obtenerMedicionesConPeriodoPorUsuario?periodo="+tipo+"&idUsuario="+idUsuario,"",
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        activity.loadMediciones(parsearJsonToArrayMediciones(cuerpo));

                    }
                }
        );
    }

}



