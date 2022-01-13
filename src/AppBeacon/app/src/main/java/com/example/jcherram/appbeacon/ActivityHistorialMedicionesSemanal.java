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
public class ActivityHistorialMedicionesSemanal extends AppCompatActivity {

    private List<Medicion> elements;
    private LogicaFake logicaFake;
    private Button buttonMensual;
    private Button buttonSemanal;
    private Medicion ultimaMedicion;


    /**
     * Método onCreate
     * @param savedInstanceState guardamos la instancia de la página
     * Bundle->onCreate()
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mediciones_semanal);


        logicaFake = new LogicaFake();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int res = sharedPref.getInt(getString(R.string.usuarioActivoId), 1);
        logicaFake.obtenerTodasLasMedicionesSemanales(this,res,"mes");

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
                openActivity24h();
            }
        });
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------
    //Para abrir el historial mensual

    public void openActivityMensual(){
        Intent intent= new Intent(this, ActivityHistorialMedicionesMensual.class);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------
    //Para abrir el historial 24h

    public void openActivity24h(){
        Intent intent= new Intent(this, ActivityHistorialMediciones.class);
        startActivity(intent);
    }




    /**
     * Metodo para calcular la media de las mediciones y mostrarlas en el grafico
     * @param list lista de mediciones para calcular la media
     * <Medicion>->calcularMedia()
     */

    public void calcularMedia(ArrayList<Medicion> list) {
        if (!list.isEmpty()) {
            float res = 0;
            for (int i = 0; i < list.size(); i++) {

                res += list.get(i).getMedicion();
            }
            res = res / list.size();


        }


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

        GraphView graph = (GraphView) findViewById(R.id.graphSemanal);

        Medicion medicion;



        int[] y3 ={62,86,55,43,79,83,67,45};
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
        for (int i=0;i<7;i++) {
            medicion = elements.get(i);

            Date x3 =medicion.getFecha();


            series3.appendData(new DataPoint(x3,y3[i]),false,24);

        }

        series3.setDrawDataPoints(true);
        series3.setDataPointsRadius(15);
        series3.setThickness(10);
        series3.setColor(Color.RED);
        series3.setTitle("SO");
        graph.addSeries(series3);


        int[] y2 ={22,26,75,83,59,23,107,65};
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
        for (int i=0;i<7;i++) {
            medicion = elements.get(i);

            Date x2 =medicion.getFecha();


            series2.appendData(new DataPoint(x2,y2[i]),false,24);

        }

        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(15);
        series2.setThickness(10);
        series2.setColor(Color.GREEN);
        series2.setTitle("NO2");
        graph.addSeries(series2);


        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM");
        for (int i=0;i<7;i++) {
            medicion = elements.get(i);

            Date x =medicion.getFecha();
            float y = medicion.getMedicion();

            series.appendData(new DataPoint(x,y),false,24);

        }


        series.setTitle("CO2");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(10);
        graph.getGridLabelRenderer().setTextSize(35);

        graph.addSeries(series);
        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLUE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLUE);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setBackgroundColor(Color.WHITE);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

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