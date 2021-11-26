package com.example.jcherram.appbeacon;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jcherram.appbeacon.adapter.Notificacion;
import com.example.jcherram.appbeacon.adapter.NotificacionesAdapter;
import com.example.jcherram.appbeacon.controlador.ClaseLanzarNotificaciones;

import java.util.ArrayList;

public class NotificacionesFragment extends Fragment  {


    private Button btNotificacion;
    private RecyclerView recyclerNotificaciones;
    private ArrayList<Notificacion> listaNotificacion;
    private ClaseLanzarNotificaciones notificaciones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaciones,
                container, false);

        notificaciones =  new ClaseLanzarNotificaciones(getActivity());
        btNotificacion = view.findViewById(R.id.btNotificacion);
        btNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //notificaciones.crearNotificacion("Notificacion prueba!");
            }
        });

        listaNotificacion=new ArrayList<>();

        recyclerNotificaciones= view.findViewById(R.id.recyclerNotis);

        recyclerNotificaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();



        NotificacionesAdapter adapter = new NotificacionesAdapter(listaNotificacion);
        recyclerNotificaciones.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        recyclerNotificaciones.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerNotificaciones.setLayoutManager(manager);

        // Inflate the layout for this fragment
        return view;
    }

    public NotificacionesFragment() {
        // Required empty public constructor
    }






    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * La funcion llenarLista() es el recicler view.
     * llenarLista()
     */
    private void llenarLista(){

        listaNotificacion.add(new Notificacion("Bateria baja", "Hace 2 horas",R.drawable.battery));

        listaNotificacion.add(new Notificacion("Nivel de CO2 alto!", "Hace 6 horas",R.drawable.alert));
        listaNotificacion.add(new Notificacion("Alerta de prueba", "Hace 9 horas",R.drawable.info));
    }

}