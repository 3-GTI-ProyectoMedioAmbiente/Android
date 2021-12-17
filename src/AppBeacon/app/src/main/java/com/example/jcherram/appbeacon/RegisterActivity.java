package com.example.jcherram.appbeacon;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
    EditText editText;
    Dialog mydialog;

    //edit texts
    EditText etNombre;
    EditText etApellidos;
    EditText etMail;
    EditText etEdad;
    EditText etTelefono;
    EditText etMatricula;
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
        logicaFake = new LogicaFake("http://192.168.1.35:5000/");

        aSwitch=(Switch) findViewById(R.id.switch1);
        editText=(EditText) findViewById(R.id.etMatriculaRegis);

        etNombre = findViewById(R.id.etNombreRegis);
        etApellidos = findViewById(R.id.etApellidosRegis);
        etMail = findViewById(R.id.etMailRegis);
        etEdad = findViewById(R.id.etEdadRegis);
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

    }


    public void onclick(View view){
    if(view.getId()==R.id.switch1){
        if(aSwitch.isChecked()){

            editText.setVisibility(View.VISIBLE);
        }else{
            editText.setVisibility(View.INVISIBLE);

        }
        }

    }
    public void registrarUsuario(){
        //miramos si hay algun campo vacio
        if (etNombre.getText().toString().equals("")||
            etApellidos.getText().toString().equals("")||
                etMail.getText().toString().equals("")||
                etEdad.getText().toString().equals("")||
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
                //caso correcto comprobamos si las matriculas con iguales
                if (etPass.getText().toString().equals(etConfPass.getText().toString())){
                    //las contraseñas coinciden

                    if(aSwitch.isChecked()){
                        //si se registra un autobusero
                        Usuario usuarioAutobusero = new Usuario(
                            etMail.getText().toString(),
                            etNombre.getText().toString(),
                                etApellidos.getText().toString(),
                                true,
                                Integer.parseInt(etEdad.getText().toString()),
                                etMatricula.getText().toString(),
                                etTelefono.getText().toString(),
                                etPass.getText().toString()
                        );

                        //llamamos a la logica
                        logicaFake.crearUsuario(usuarioAutobusero,this);

                    }else{
                        //si se registra un NO autobusero

                        Usuario usuarioNoAutobusero = new Usuario(
                                etMail.getText().toString(),
                                etNombre.getText().toString(),
                                etApellidos.getText().toString(),
                                false,
                                Integer.parseInt(etEdad.getText().toString()),
                                "Sin Matricula",
                                etTelefono.getText().toString(),
                                etPass.getText().toString()
                        );

                        //llamamos a la logica
                        logicaFake.crearUsuario(usuarioNoAutobusero,this);
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