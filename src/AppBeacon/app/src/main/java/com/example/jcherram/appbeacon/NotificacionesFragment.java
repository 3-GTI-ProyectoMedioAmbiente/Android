package com.example.jcherram.appbeacon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jcherram.appbeacon.adapter.Notificacion;
import com.example.jcherram.appbeacon.adapter.NotificacionesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionesFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button btNotificacion;
    private PendingIntent pendingIntent;

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    RecyclerView recyclerNotificaciones;
    ArrayList<Notificacion> listaNotificacion;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaciones,
                container, false);


        btNotificacion = view.findViewById(R.id.btNotificacion);
        btNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotificationChannel();
                //setPendingIntent();
                createNotification();
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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  NotificacionesFragment newInstance(String param1, String param2) {
        NotificacionesFragment fragment = new NotificacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * La funcion createNotificationChannel() es una funcion la cual maneja la creacion de la notificacion.
     * createNotificationChannel()
     */
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //verifica la version del SDK
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * La funcion setPendingIntent() es una funcion la cual utilizarems en un futuro, redirigir al usuario a un fragment al pulsar la notificacion.
     * setPendingIntent()
     */
    /*private void setPendingIntent(){
        Intent intent = new Intent(getContext(), NotificacionesFragment.class );
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        //stackBuilder.addParentStack(NotificacionesFragment.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
        //Log.d("pene","pending");
    }*/


    /**
     * La funcion createNotification() crea la notificacion.
     * createNotification()
     */
    
    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle("Angle Corp:");
        builder.setContentText("Bateria baja del sensor");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //builder.setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
        //Log.d("pene","createNotification");
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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