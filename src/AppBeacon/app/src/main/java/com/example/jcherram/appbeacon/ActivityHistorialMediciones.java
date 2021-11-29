package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.jcherram.appbeacon.modelo.Medicion;
import com.example.jcherram.appbeacon.adapter.MedicionAdapter;
import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.controlador.PeticionarioREST;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistorialMediciones extends AppCompatActivity {

    private List<Medicion> elements;
    private LogicaFake logicaFake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String ipServidor = sharedPreferences.getString(getString(R.string.preferenceIpServidor), "noIp");
        logicaFake = new LogicaFake(ipServidor);
        logicaFake.getTodasLasMediciones(this);



    }


    public void loadMediciones(ArrayList<Medicion> mediciones){
        elements = mediciones;
        MedicionAdapter medicionAdapter= new MedicionAdapter(elements, this);
        RecyclerView recyclerView=findViewById(R.id.recyclermedis);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(medicionAdapter);


    }



}