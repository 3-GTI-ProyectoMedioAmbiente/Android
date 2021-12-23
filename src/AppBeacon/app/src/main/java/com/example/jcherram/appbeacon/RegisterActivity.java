package com.example.jcherram.appbeacon;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.modelo.Usuario;


// -----------------------------------------------------------------------------------
// @author: Alberto Valls Martinez
// Fecha: 17/10/2021
// SettingsActivity
// Clase para el registro del usuario
// -----------------------------------------------------------------------------------
public class RegisterActivity extends AppCompatActivity {

    Switch aSwitch;
    TextView tvMatricula;
    EditText etMatricula;

    Dialog mydialog;
    CheckBox cbDatos;
    TextView datosTv;
    String urlDatos;

    //edit texts
    EditText etNombre;
    EditText etApellidos;
    EditText etMail;
    DatePicker etFechaNacimiento;
    EditText etTelefono;

    EditText etPass;
    EditText etConfPass;

    //boton registro
    Button btnRegistro;

    //logica fake
    LogicaFake logicaFake;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        logicaFake = new LogicaFake();

        aSwitch=(Switch) findViewById(R.id.switch1);
        etMatricula=(EditText) findViewById(R.id.etMatriculaRegis);
        tvMatricula = (TextView) findViewById(R.id.tvMatricula);

        cbDatos = (CheckBox)findViewById(R.id.checkBoxDatos);

        datosTv = findViewById(R.id.tvCondicionesDatos);

        SpannableString mitextoU = new SpannableString(datosTv.getText().toString());
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        datosTv.setText(mitextoU);
        urlDatos = "https://www.boe.es/eli/es/lo/2018/12/05/3/con";

        etNombre = findViewById(R.id.etNombreRegis);
        etApellidos = findViewById(R.id.etApellidosRegis);
        etMail = findViewById(R.id.etMailRegis);
        etFechaNacimiento = findViewById(R.id.etEdadRegis);
        etTelefono = findViewById(R.id.etTelefonoRegis);
        etMatricula = findViewById(R.id.etMatriculaRegis);
        etPass = findViewById(R.id.etPassRegis);
        etConfPass = findViewById(R.id.etConfPassRegis);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        datosTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri =  Uri.parse(urlDatos);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

    }


    public void onclick(View view){
    if(view.getId()==R.id.switch1){
        if(aSwitch.isChecked()){

            tvMatricula.setVisibility(View.VISIBLE);
            etMatricula.setVisibility(View.VISIBLE);
        }else{
            tvMatricula.setVisibility(View.GONE);
            etMatricula.setVisibility(View.GONE);

        }
        }

    }
    public void registrarUsuario(){
        //miramos si hay algun campo vacio
        if (etNombre.getText().toString().equals("")||
            etApellidos.getText().toString().equals("")||
                etMail.getText().toString().equals("")||
                etTelefono.getText().toString().equals("")||
                etPass.getText().toString().equals("")||
                etConfPass.getText().toString().equals("")
        ){
            Toast.makeText(this, "Es obligatorio rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            //el switch esta activado pero maatricula vacio
            if (aSwitch.isChecked() && etMatricula.getText().toString().equals("")){
                Toast.makeText(this, "Es obligatorio rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }else{
                //caso correcto comprobamos si las pass con iguales
                if (etPass.getText().toString().equals(etConfPass.getText().toString())){
                    //las contraseñas coinciden
                if (cbDatos.isChecked()){
                    if(aSwitch.isChecked()){
                        //si se registra un autobusero
                        String fecha = etFechaNacimiento.getYear()+"-"+etFechaNacimiento.getMonth()+"-"+etFechaNacimiento.getDayOfMonth();





                        Usuario usuarioAutobusero = new Usuario(
                                etMail.getText().toString(),
                                etNombre.getText().toString(),
                                etApellidos.getText().toString(),
                                true,
                                fecha,
                                etMatricula.getText().toString(),
                                etTelefono.getText().toString(),
                                etPass.getText().toString()
                        );

                        //llamamos a la logica
                        logicaFake.crearUsuario(usuarioAutobusero,this);



                    }else{
                        //si se registra un NO autobusero

                        String fecha = etFechaNacimiento.getYear()+"-"+etFechaNacimiento.getMonth()+"-"+etFechaNacimiento.getDayOfMonth();



                        Usuario usuarioNoAutobusero = new Usuario(
                                etMail.getText().toString(),
                                etNombre.getText().toString(),
                                etApellidos.getText().toString(),
                                false,
                                fecha,
                                "Sin Matricula",
                                etTelefono.getText().toString(),
                                etPass.getText().toString()
                        );



                        //llamamos a la logica
                        logicaFake.crearUsuario(usuarioNoAutobusero,this);
                    }
                }else{
                    ///el check box no esta pulsado
                    Toast.makeText(this, "Debes aceptar las condiciones de tratamiento de datos", Toast.LENGTH_SHORT).show();
                }


                }else{
                    Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void redirigirLogin(){
        Toast.makeText(this, "El registro se ha completado exitosamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}