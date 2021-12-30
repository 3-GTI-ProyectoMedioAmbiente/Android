package com.example.jcherram.appbeacon.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.LoginActivity;
import com.example.jcherram.appbeacon.R;
import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.modelo.Medicion;
import com.example.jcherram.appbeacon.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LogicaFake logicaFake;

    //info usuario
    TextView nombreUsuario;
    TextView nombreUsuarioInfo;
    TextView apellidosUsuarioInfo;
    TextView edadUsuarioInfo;
    TextView mailUsuarioInfo;
    TextView telefonoUsuarioInfo;
    TextView cerrarSesion;
    //Para el popup
    private Dialog mydialog;

    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextMail;
    private DatePicker dpEdadEditar;
    private EditText editTextTelefono;

    private EditText editTextAntiguaContra;
    private EditText editTextNuevaContra;
    private EditText editTextReNuevaContra;

    private TextView tvAntiConrta;
    private TextView tvNueConrta;
    private TextView tvReNueConrta;
    TextView txtclose;
    Switch sw1;
    Button btnGuardarCambios;

    double distanciaRecorrida;



    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Switch sw;
        logicaFake = new LogicaFake();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mydialog = new Dialog(getContext());
        mydialog.setContentView(R.layout.popeditarusuario);

        nombreUsuario = view.findViewById(R.id.tvNombreUsuario);
        nombreUsuarioInfo = view.findViewById(R.id.infoUsuarioNombre);
        apellidosUsuarioInfo = view.findViewById(R.id.infoUsuarioApellidos);
        mailUsuarioInfo = view.findViewById(R.id.infoUsuarioMail);
        edadUsuarioInfo = view.findViewById(R.id.infoUsuarioEdad);
        telefonoUsuarioInfo = view.findViewById(R.id.infoUsuarioTelefono);

        cargarUsuario();

        Button butt1=(Button) view.findViewById(R.id.btnEditarUsuario);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        cerrarSesion = view.findViewById(R.id.tvCerrarSesión);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cSesion();
            }
        });

        return view;

    }
    /**
     * Metodo para abrir el popup
     * @param v recibimos la vista deseada
     */
    /**
     * Metodo para abrir el popup
     * @param v recibimos la vista deseada
     */

    public void showPopup(View v){



        mydialog.setContentView(R.layout.popeditarusuario);
        txtclose=(TextView) mydialog.findViewById(R.id.txtCloseEditar);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        sw1 = (Switch) mydialog.findViewById(R.id.switch2);

        editTextNombre = (EditText) mydialog.findViewById(R.id.etNombreRegis);
        editTextApellidos = (EditText) mydialog.findViewById(R.id.etApellidosRegis);
        editTextMail = (EditText) mydialog.findViewById(R.id.etMailRegis);
        dpEdadEditar = (DatePicker) mydialog.findViewById(R.id.etEdadEditar);
        editTextTelefono = (EditText) mydialog.findViewById(R.id.etTelefonoRegis);

        editTextAntiguaContra = (EditText) mydialog.findViewById(R.id.etPassRegis);
        editTextNuevaContra = (EditText) mydialog.findViewById(R.id.editTextNuContraEditar);
        editTextReNuevaContra = (EditText) mydialog.findViewById(R.id.editTextReContraEditar);

        tvAntiConrta = (TextView) mydialog.findViewById(R.id.textViewAntiguaContra);
        tvNueConrta= (TextView) mydialog.findViewById(R.id.textViewNuevaContra);
        tvReNueConrta = (TextView) mydialog.findViewById(R.id.textViewReNuevaContra);
        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditTextContra(v,sw1);
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        //String res = sharedPref.getString("usuarioActivoNombre","noName");
        //Log.d("prefPopE",res);

        //rellenamos los datos del usuario activo
        editTextNombre.setText(sharedPref.getString("usuarioActivoNombre","null"));
        editTextApellidos.setText(sharedPref.getString("usuarioActivoApellidos","null"));
        editTextMail.setText(sharedPref.getString("usuarioActivoMail","null"));

        //para settear la fecha usuarioActivoFechaNacimiento
        String lista = sharedPref.getString("usuarioActivoFechaNacimiento","null");
        char a1 = lista.charAt(0);
        char a2 = lista.charAt(1);
        char a3 = lista.charAt(2);
        char a4 = lista.charAt(3);
        char m1 = lista.charAt(5);
        char m2 = lista.charAt(6);
        char d1 = lista.charAt(8);
        char d2 = lista.charAt(9);

        StringBuilder sba = new StringBuilder();
        sba.append(a1);
        sba.append(a2);
        sba.append(a3);
        sba.append(a4);



        StringBuilder sbm = new StringBuilder();
        sbm.append(m1);
        sbm.append(m2);

        StringBuilder sbd = new StringBuilder();
        sbd.append(d1);
        sbd.append(d2);

        dpEdadEditar.updateDate(Integer.valueOf(sba.toString())+0,Integer.valueOf(sbm.toString())-1,Integer.valueOf(sbd.toString())+0);




        //dpEdadEditar.updateDate();
        editTextTelefono.setText(sharedPref.getString("usuarioActivoTelefono","null"));

        //botonGuardar cambios
        btnGuardarCambios = (Button) mydialog.findViewById(R.id.btnRegistro);
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios(v);
            }
        });


        mydialog.show();

    }

    public void toggleEditTextContra(View view,Switch sw1){
        Log.d("holA","entro al metodo");
        if(view.getId()==R.id.switch2){
            Log.d("holA","hola");
            if(sw1.isChecked()){

                editTextAntiguaContra.setVisibility(View.VISIBLE);
                editTextNuevaContra.setVisibility(View.VISIBLE);
                editTextReNuevaContra.setVisibility(View.VISIBLE);

                tvAntiConrta.setVisibility(View.VISIBLE);
                tvNueConrta.setVisibility(View.VISIBLE);
                tvReNueConrta.setVisibility(View.VISIBLE);
            }else{
                editTextAntiguaContra.setVisibility(View.GONE);
                editTextNuevaContra.setVisibility(View.GONE);
                editTextReNuevaContra.setVisibility(View.GONE);

                tvAntiConrta.setVisibility(View.GONE);
                tvNueConrta.setVisibility(View.GONE);
                tvReNueConrta.setVisibility(View.GONE);

            }
        }

    }
    public void guardarCambios(View v){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        Log.d("testEdit","Entro a guardar cambios");

        if (sw1.isChecked()){
                //cambiar contraseña esta activado
            if(editTextNombre.getText().toString().equals("") ||
                    editTextApellidos.getText().toString().equals("")||
                    editTextMail.getText().toString().equals("")||
                    editTextTelefono.getText().toString().equals("")||
                    editTextAntiguaContra.getText().toString().equals("")||
                    editTextNuevaContra.getText().toString().equals("")||
                    editTextReNuevaContra.getText().toString().equals("")
            ){
                Toast.makeText(this.getActivity().getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }else{
                //no hay nada vacio comprobamos si la contraseña antigua es diferente a la nueva
                if (editTextAntiguaContra.getText().toString().equals(editTextNuevaContra.getText().toString())){
                    Toast.makeText(this.getActivity().getApplicationContext(), "Tu nueva contraseña debe ser diferente a la antigua", Toast.LENGTH_SHORT).show();

                }else{
                    //comprueba qeu las nuevas contraseñas son iguales
                    if (!editTextNuevaContra.getText().toString().equals(editTextReNuevaContra.getText().toString())){
                        Toast.makeText(this.getActivity().getApplicationContext(), "Las nuevas contraseñas deben coincidir", Toast.LENGTH_SHORT).show();

                    }else{
                        //comprueba que la contraseña antigua es igual a la qeu esta escrita
                        if (!editTextAntiguaContra.getText().toString().equals(sharedPref.getString("usuarioActivoPassword","noPass"))){
                            Toast.makeText(this.getActivity().getApplicationContext(), "La contraseña antigua es incorrecta", Toast.LENGTH_SHORT).show();

                        }else{
                            //cambiamos el formato de la fecha si es necesario
                            int mes = dpEdadEditar.getMonth()+1;
                            String mesStr = String.valueOf(mes);
                            if (mes<10){
                                mesStr = 0 + String.valueOf(mes);
                            }

                            int dia = dpEdadEditar.getDayOfMonth();
                            String diaStr = String.valueOf(dia);
                            if (dia<10){
                                diaStr = 0 + String.valueOf(dia);
                            }

                            String fecha = dpEdadEditar.getYear() + "-"+ mesStr +"-"+diaStr;
                            //caso correcto
                            Usuario usuario = new Usuario(
                                    sharedPref.getInt("usuarioActivoId",-1),
                                    editTextMail.getText().toString(),
                                    editTextNombre.getText().toString(),
                                    editTextApellidos.getText().toString(),
                                    fecha,
                                    editTextTelefono.getText().toString(),
                                    editTextNuevaContra.getText().toString()
                            );

                            usuario.setId_sensor(sharedPref.getInt(getString(R.string.usuarioActivoIdSensor),-1));
                            logicaFake.editarUsuario(usuario,this);





                        }
                    }
                }

            }
        }else{
            //cambiar contraseña no esta activado
            if(editTextNombre.getText().toString().equals("") ||
                    editTextApellidos.getText().toString().equals("")||
                    editTextMail.getText().toString().equals("")||
                    //dpEdadEditar.getText().toString().equals("")||
                    editTextTelefono.getText().toString().equals("")
            ){
                Toast.makeText(this.getActivity().getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }else {
                //cambiamos el formato de la fecha si es necesario
                int mes = dpEdadEditar.getMonth()+1;
                String mesStr = String.valueOf(mes);
                if (mes<10){
                    mesStr = 0 + String.valueOf(mes);
                }

                int dia = dpEdadEditar.getDayOfMonth();
                String diaStr = String.valueOf(dia);
                if (dia<10){
                    diaStr = 0 + String.valueOf(dia);
                }

                String fecha = dpEdadEditar.getYear() + "-"+ mesStr +"-"+diaStr;
                //caso correcto
                Usuario usuario = new Usuario(
                        sharedPref.getInt("usuarioActivoId",-1),
                        editTextMail.getText().toString(),
                        editTextNombre.getText().toString(),
                        editTextApellidos.getText().toString(),
                        fecha,
                        editTextTelefono.getText().toString(),
                        sharedPref.getString("usuarioActivoPassword","noPass")
                );
                usuario.setId_sensor(sharedPref.getInt(getString(R.string.usuarioActivoIdSensor),-1));
                logicaFake.editarUsuario(usuario,this);


            }

        }

    }
    public void settearUsuarioActivo(){
        //adjudicamos los cambios a las shared preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        //nombre
        editor.putString("usuarioActivoNombre",editTextNombre.getText().toString());
        editor.apply();
        //apellidos
        editor.putString("usuarioActivoApellidos",editTextApellidos.getText().toString());
        editor.apply();
        //mail
        editor.putString("usuarioActivoMail",editTextMail.getText().toString());
        editor.apply();
        //fechaNacimiento

            //cambiamos el formato de la fecha si es necesario
            int mes = dpEdadEditar.getMonth()+1;
            String mesStr = String.valueOf(mes);
            if (mes<10){
                mesStr = 0 + String.valueOf(mes);
            }

            int dia = dpEdadEditar.getDayOfMonth();
            String diaStr = String.valueOf(dia);
            if (dia<10){
                diaStr = 0 + String.valueOf(dia);
            }
        String fecha = dpEdadEditar.getYear() + "-"+ mesStr +"-"+diaStr;
        editor.putString("usuarioActivoFechaNacimiento",fecha);
        //telefono
        editor.putString("usuarioActivoTelefono",editTextTelefono.getText().toString());
        editor.apply();
        //contraseña
        editor.putString("usuarioActivoPassword",editTextNuevaContra.getText().toString());
        editor.apply();

        Toast.makeText(this.getActivity().getApplicationContext(), "Los cambios se han guardado correctamente", Toast.LENGTH_SHORT).show();
        mydialog.dismiss();
        cargarUsuario();

    }
    public void cargarUsuario(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        String res = "Bienvenido, "+ sharedPref.getString("usuarioActivoNombre","noName")+".";
        nombreUsuario.setText(res);
        nombreUsuarioInfo.setText(sharedPref.getString("usuarioActivoNombre","noName"));
        apellidosUsuarioInfo.setText(sharedPref.getString("usuarioActivoApellidos","noName"));
        mailUsuarioInfo.setText(sharedPref.getString("usuarioActivoMail","noName"));
        edadUsuarioInfo.setText(sharedPref.getString("usuarioActivoFechaNacimiento","noEdad"));
        telefonoUsuarioInfo.setText(sharedPref.getString("usuarioActivoTelefono","noName"));
        Log.d("testFecha",sharedPref.getString("usuarioActivoFechaNacimiento","noEdad"));
    }
    public void cSesion(){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        logicaFake.obtenerMedicionesConPeriodoPorUsuario("dia",sharedPref.getInt("usuarioActivoId",-1),this);

    }

    /**
     *
     * Metodo para calcular la distancia que ha recorrido un usuario en 24 h
     *
     * @param listaMediciones - Lista de mediciones que hay que calcular su distancia
     */

    public void prepararInfoPrivada(ArrayList<Medicion> listaMediciones) throws JSONException {
        //Log.d("testCalcularDistancia", listaMediciones.get(0).toString());
        distanciaRecorrida =0;
        if (listaMediciones.isEmpty()){
            distanciaRecorrida = 0;
        }else{
            //la lista no esta vacia
            //Log.d("testCalcularDistancia", listaMediciones.get(0).ge.toString());

            for(int i = 0;i<=listaMediciones.size()-1;i++){
                if (i+1 >= listaMediciones.size()){

                }else{
                    double R = 6371; //radio de la tierra en km
                    double lat1Rad = Math.toRadians(listaMediciones.get(i).getLocalizacion_lat());
                    double lat2Rad = Math.toRadians(listaMediciones.get(i+1).getLocalizacion_lat());
                    double increLat = Math.toRadians(listaMediciones.get(i+1).getLocalizacion_lat() - listaMediciones.get(i).getLocalizacion_lat());
                    double increLong = Math.toRadians(listaMediciones.get(i+1).getLocalizacion_lon() - listaMediciones.get(i).getLocalizacion_lon());

                    double a = Math.sin(increLat/2) * Math.sin(increLat/2) +
                            Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                                    Math.sin(increLong/2) * Math.sin(increLong/2);

                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    distanciaRecorrida = distanciaRecorrida + (c*R);


                }


            }

        }


        //Minutos Activo
        //Restamos las horas para obtener el tiempo que ha estado el usuario activo
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        long horaDeInicio = sharedPref.getLong("horaAlEntrar",-1);
        long horaActual = System.currentTimeMillis();
        long diferenciaHoras = horaActual - horaDeInicio;

        double minutos = diferenciaHoras * 0.0000166667;
        BigDecimal bigDecimal = new BigDecimal(minutos).setScale(2, RoundingMode.HALF_UP);
        double minutos_red = bigDecimal.doubleValue();
        //Log.d("testHora",minutos + "");

        JSONObject jsonInfo = new JSONObject();
        jsonInfo.put("id_usuario",sharedPref.getInt("usuarioActivoId",-1));
        jsonInfo.put("id_sensor",sharedPref.getInt("usuarioActivoIdSensor",-1));
        jsonInfo.put("telefono",sharedPref.getString("usuarioActivoTelefono","noTelefono"));
        jsonInfo.put("distancia_recorrida",distanciaRecorrida);
        jsonInfo.put("nombre",sharedPref.getString("usuarioActivoNombre","noName"));
        jsonInfo.put("minutos_activo",minutos_red);

        logicaFake.publicarInfoPrivada(jsonInfo);

        Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}