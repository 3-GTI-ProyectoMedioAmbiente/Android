package com.example.jcherram.appbeacon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.jcherram.appbeacon.controlador.LogicaFake;
import com.example.jcherram.appbeacon.modelo.Medicion;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author: Alberto Valls Martinez
 * Fecha: 23/11/21
 * ActivityHistorialMediciones
 * Activity que muestra el historial de mediciones del sensor
 */

//----------------------------------------------------------------
//----------------------------------------------------------------
public class ActivityHistorialMedicionesMensual extends AppCompatActivity {

    private List<Medicion> elements;
    private LogicaFake logicaFake;
    private Button button24h;
    private Button buttonSemanal;


    /**
     * Método onCreate
     * @param savedInstanceState guardamos la instancia de la página
     * Bundle->onCreate()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones_mensual);


        logicaFake = new LogicaFake();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int res = sharedPref.getInt(getString(R.string.usuarioActivoId), 1);
        logicaFake.obtenerTodasLasMedicionesMensuales(this,res,"mes");


        button24h = findViewById(R.id.buttonMensual);
        button24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity24h();
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


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------
    //Para abrir el historial semanal


    public void openActivitySemanal(){
        Intent intent= new Intent(this, ActivityHistorialMedicionesSemanal.class);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------
    //Para abrir el historial 24h

    public void openActivity24h(){
        Intent intent= new Intent(this, ActivityHistorialMediciones.class);
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

        GraphView graph = (GraphView) findViewById(R.id.graphMensual);


        LineGraphSeries<DataPoint> series3 =new LineGraphSeries<DataPoint>();

        int[] Data3={92,23,55,73,49,36,41,68,59,67,41,51,81};
        int[] Fecha3={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        for (int i=1;i<13;i++) {


            series3.appendData(new DataPoint(Fecha3[i],Data3[i]),false,94);

        }

        series3.setDrawDataPoints(true);
        series3.setDataPointsRadius(15);
        series3.setThickness(10);
        series3.setColor(Color.RED);
        series3.setTitle("SO");
        graph.addSeries(series3);

        LineGraphSeries<DataPoint> series2 =new LineGraphSeries<DataPoint>();

        int[] Data2={82,123,35,83,29,16,61,48,99,37,81,61,41};
        int[] Fecha2={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        for (int i=1;i<13;i++) {


            series2.appendData(new DataPoint(Fecha2[i],Data2[i]),false,94);

        }

        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(15);
        series2.setThickness(10);
        series2.setColor(Color.GREEN);
        series2.setTitle("NO2");
        graph.addSeries(series2);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        int[] Data={102,23,32,43,25,16,71,58,19,120,31,21,11};
        int[] Fecha={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        for (int i=1;i<13;i++) {


            series.appendData(new DataPoint(Fecha[i],Data[i]),false,94);

        }
        series.setTitle("CO2");
        series.setColor(Color.BLUE);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(10);
        graph.getGridLabelRenderer().setTextSize(35);
        graph.addSeries(series);
        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLUE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLUE);
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setBackgroundColor(Color.WHITE);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

    }






}