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
        Date ultimoDia;




        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM");
        for (int i=0;i<6;i++) {
            medicion = elements.get(i);

            Date x =medicion.getFecha();
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

        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        //graph.getViewport().setMinX();
    }





}