package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;


// -----------------------------------------------------------------------------------
// @author: Alberto Valls Martinez
// Fecha: 17/10/2021
// SettingsActivity
// Clase para el registro del usuario
// -----------------------------------------------------------------------------------
public class RegisterActivity extends AppCompatActivity {

    Switch aSwitch;
    EditText editText;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        aSwitch=(Switch) findViewById(R.id.switch1);
        editText=(EditText) findViewById(R.id.matriculaText);
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
}