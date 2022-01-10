package com.example.jcherram.appbeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jcherram.appbeacon.modelo.Medicion;
import com.example.jcherram.appbeacon.adapter.MedicionAdapter;
import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.controlador.PeticionarioREST;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private Button buttonMensual;
    private Button buttonSemanal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones);


        logicaFake = new LogicaFake();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int res = sharedPref.getInt(getString(R.string.usuarioActivoId), 1);
        logicaFake.obtenerTodasLasMediciones(this,res,"mes");

        buttonMensual = findViewById(R.id.buttonMensual);
        buttonMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMensual();
            }
        });


        buttonSemanal = findViewById(R.id.buttonSemanal);
        buttonSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySemanal();
            }
        });
    }

    public void openActivityMensual(){
        Intent intent= new Intent(this, ActivityHistorialMedicionesMensual.class);
        startActivity(intent);
    }

    public void openActivitySemanal(){
        Intent intent= new Intent(this, ActivityHistorialMedicionesSemanal.class);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo que sirve para representar las mediciones recogidas en la bbdd
     * @param mediciones que son los datos recogidos por el sensor
     *
     * <Medicion>->loadMediciones()
     */

    public void loadMediciones(ArrayList<Medicion> mediciones){
        elements = mediciones;

        GraphView graph = (GraphView) findViewById(R.id.graph);

        Medicion medicion;




        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        for (int i=elements.size()-14;i<elements.size();i++) {
            medicion = elements.get(i);
            Time x =medicion.getHora();
            float y = medicion.getMedicion();
            series.appendData(new DataPoint(x,y),false,24);

        }



        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(10);
        graph.getGridLabelRenderer().setTextSize(35);

        graph.addSeries(series);
        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        //graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override

            public String formatLabel(double value, boolean isValueX){

                if(isValueX){
                    return simpleDateFormat.format(new Date((long)value));
                }else {

                    return super.formatLabel(value, isValueX);
                }
            }


        });

        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
    }







}