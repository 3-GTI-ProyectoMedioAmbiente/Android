package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @Author: Alberto Valls Martinez
 * Fecha: 23/11/21
 * SplashActivity
 * clase que inicializa una pantalla de inicio que dura unos segunos
 */

//----------------------------------------------------------------
//----------------------------------------------------------------

public class SplashActivity extends AppCompatActivity {

    /**
     *  Constructor de la Acticity de splash activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },2000);
    }
}