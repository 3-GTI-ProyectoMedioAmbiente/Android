package com.example.jcherram.appbeacon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.modelo.Usuario;

public class LoginActivity extends AppCompatActivity
{

    private Button button;
    private Button buttonIniciarSesion;
    private EditText editTextCorreoLogin;
    private EditText editTextPasswordLogin;
    LogicaFake logicaFake;
    LoginActivity activity;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Context context = getApplicationContext();
        logicaFake = new LogicaFake();


        //botones
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        //inputs
        editTextCorreoLogin = findViewById(R.id.editTextCorreo);
        editTextPasswordLogin = findViewById(R.id.editTextPassword);
        //on click iniciar sesion
        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","entro al onclick");
                Log.d("inputsLogin",editTextCorreoLogin.getText().toString());
                Log.d("inputsLogin",editTextPasswordLogin.getText().toString());
                if (editTextCorreoLogin.getText().toString().equals("") || editTextPasswordLogin.getText().toString().equals("")){
                    Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    iniciarSesion();
                }

            }
        });

        button = (Button) findViewById(R.id.btnregister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRegister();
            }
        });


    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para abrir la pagina de registrto
     */

    public void openRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para iniciarSesion
     */
    public void iniciarSesion() {
        logicaFake.loginUsuario(editTextCorreoLogin.getText().toString(),editTextPasswordLogin.getText().toString(),this);
    }

    /**
     * Metodo para guardar el usuario activo en shared references
     * @param {Usuario} - Usuario procedente de la base de datos
     * Usuario -> settearUsuarioActivo()
     */
    public void settearUsuarioActivo(Usuario usuario){
        Log.d("test","entro a settear");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //id
        editor.putInt(getString(R.string.usuarioActivoId), usuario.getId());
        editor.apply();
        //nombre
        editor.putString(getString(R.string.usuarioActivoNombre), usuario.getNombre());
        editor.apply();
        //apellidos
        editor.putString(getString(R.string.usuarioActivoApellidos), usuario.getApellidos());
        editor.apply();
        //mail
        editor.putString(getString(R.string.usuarioActivoMail), usuario.getMail());
        editor.apply();
        //edad
        editor.putInt(getString(R.string.usuarioActivoEdad), usuario.getEdad());
        editor.apply();
        //isAutobusero
        editor.putBoolean(getString(R.string.usuarioActivoIsAutobusero), usuario.getAutobusero());
        editor.apply();
        //matricula
        editor.putString(getString(R.string.usuarioActivoMatricula), usuario.getMatricula());
        editor.apply();
        //telefono
        editor.putString(getString(R.string.usuarioActivoTelefono), usuario.getTelefono());
        editor.apply();
        //password
        editor.putString(getString(R.string.usuarioActivoPassword), usuario.getPassword());
        editor.apply();

        //id_sensor
        editor.putString(getString(R.string.usuarioActivoIdSensor), usuario.getPassword());
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }




}
