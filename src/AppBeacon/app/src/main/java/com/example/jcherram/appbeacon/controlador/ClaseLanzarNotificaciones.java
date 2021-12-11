package com.example.jcherram.appbeacon.controlador;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.jcherram.appbeacon.R;

/**
 * @Author: Alberto Valls Martinez
 * Fecha: 23/11/21
 * ClaseLanzarNotificaciones
 * Clase que manda las notificaciones al movil
 */
public class ClaseLanzarNotificaciones {
    private final Context context;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private PendingIntent pendingIntent;

    /**
     * Metodo que llama a la función crearNotificationChannel()
     * @param context le pasamos el contexto deseado
     */
    public ClaseLanzarNotificaciones(Context context){
        this.context = context;
        createNotificationChannel();
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para crear notificaciones
     * @param mensaje le pasamos el mensajes que visualizará la notificacion
     * @param titulo el titulo de la notificacion
     *
     * Texto, Texto->crearNotificacion()
     */
    public void crearNotificacion(String mensaje, String titulo){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        //builder.setContentTitle("Angle Corp");
        builder.setContentTitle(titulo);
        //builder.setContentText("Bateria baja del sensor");
        builder.setContentText(mensaje);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //builder.setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


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


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * La funcion createNotificationChannel() es una funcion la cual maneja la creacion de la notificacion.
     * createNotificationChannel()
     */
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //verifica la version del SDK
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    private Boolean isNotifiacionAlive(){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();
    }

}
