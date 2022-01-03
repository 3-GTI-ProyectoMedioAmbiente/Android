package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.jcherram.appbeacon.modelo.Medicion;
import com.example.jcherram.appbeacon.adapter.MedicionAdapter;
import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.controlador.PeticionarioREST;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @Author: Alberto Valls Martinez
 * Fecha: 23/11/21
 * ActivityHistorialMediciones
 * Activity que muestra el historial de mediciones del sensor
 */

//----------------------------------------------------------------
//----------------------------------------------------------------
public class ActivityHistorialMediciones extends AppCompatActivity {

    private List<Medicion> elements;
    private LogicaFake logicaFake;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        int[] Data= {76,21,203,24, 42, 32,108,209,43,21,54,21,23,97,34};



        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i=0;i<14;i++) {
            series.appendData(new DataPoint(i,Data[i]),false,94);
        }




        series.setColor(Color.WHITE);
        series.setTitle("CO2");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(10);


        graph.addSeries(series);


        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        //graph.getGridLabelRenderer().setVerticalAxisTitle("µg/m3");
        //graph.getGridLabelRenderer().setHorizontalAxisTitle("Hora");



        logicaFake = new LogicaFake();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int res = sharedPref.getInt(getString(R.string.usuarioActivoId), 1);
        logicaFake.obtenerTodasLasMediciones(this,res,"mes");
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo que sirve para representar las mediciones recogidas en la bbdd en el recycler view
     * @param mediciones que son los datos recogidos por el sensor
     *
     * <Medicion>->loadMediciones()
     */

    public void loadMediciones(ArrayList<Medicion> mediciones){
        elements = mediciones;
        MedicionAdapter medicionAdapter= new MedicionAdapter(elements, this);
        //RecyclerView recyclerView=findViewById(R.id.recyclermedis);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(medicionAdapter);


    }



}