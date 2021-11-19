package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.jcherram.appbeacon.adapter.Medicion;
import com.example.jcherram.appbeacon.adapter.MedicionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistorialMediciones extends AppCompatActivity {



    List<Medicion> elements;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones);

        init();



    }


    public void init(){

    elements=new ArrayList<>();
    elements.add(new Medicion("No perjudicial","13:43","CO2",34));
    elements.add(new Medicion("No perjudicial","13:43","CO2",34));
    elements.add(new Medicion("No perjudicial","13:43","CO2",34));

        MedicionAdapter medicionAdapter= new MedicionAdapter(elements, this);
        RecyclerView recyclerView=findViewById(R.id.recyclermedis);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(medicionAdapter);


    }



}