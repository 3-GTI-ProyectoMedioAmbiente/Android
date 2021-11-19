package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.jcherram.appbeacon.adapter.Medicion;
import com.example.jcherram.appbeacon.adapter.MedicionAdapter;

import java.util.ArrayList;

public class ActivityHistorialMediciones extends AppCompatActivity {



    
    ArrayList<Medicion> listaMedicion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones);

        init();

    }


    public void init(){


        listaMedicion.add(new Medicion("dasd","sdadsa", "adsasas", 23));
        listaMedicion.add(new Medicion("dasd","sdadsa", "adsasas", 23));
        listaMedicion.add(new Medicion("dasd","sdadsa", "adsasas", 23));
        listaMedicion.add(new Medicion("dasd","sdadsa", "adsasas", 23));

        MedicionAdapter medicionAdapter = new MedicionAdapter(listaMedicion, this);
        RecyclerView recyclerView =findViewById(R.id.recyclerMediciones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(medicionAdapter);

    }



}