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
public class ActivityHistorialMedicionesMensual extends AppCompatActivity {

    private List<Medicion> elements;
    private LogicaFake logicaFake;
    private Button button24h;
    private Button buttonSemanal;



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


    public void openActivitySemanal(){
        Intent intent= new Intent(this, ActivityHistorialMedicionesSemanal.class);
        startActivity(intent);
    }

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

        Medicion medicion;




        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM");
        for (int i=0;i<29;i++) {
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

        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
    }







}