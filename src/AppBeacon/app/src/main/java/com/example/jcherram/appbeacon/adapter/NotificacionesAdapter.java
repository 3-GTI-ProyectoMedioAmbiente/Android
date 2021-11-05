package com.example.jcherram.appbeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jcherram.appbeacon.R;

import java.util.ArrayList;
import java.util.List;

/**
 Clase NotificacionesAdapter para el recyclerView
 */

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {



    List<Notificacion> notificacionesList;
    private Context context;

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo notificacionesAdapter
     * @param notificacionesList lista con nuestras notificaciones
     * @param context para acceder a la ruta especifica
     *
     * <Notifiacion>,context->NotificacionesAdapter()
     */
    public NotificacionesAdapter(List<Notificacion> notificacionesList, Context context) {
        this.notificacionesList = notificacionesList;
        this.context = context;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Constructor de NotificacionesAdapter
     * @param listaNotificacion array con las notificaciones
     *
     * <Notificacion>->NotificacionesAdapter()
     */
    public NotificacionesAdapter(ArrayList<Notificacion> listaNotificacion) {
        this.notificacionesList = listaNotificacion;
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo onCreateViewHolder para la vista
     * @param parent pasamos el padre de la view
     * @param viewType establecemos el tipo de vista
     * @return devolvemos la vista establecida
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notificaciones, parent, false);

        return new ViewHolder(view);
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo onBindViewHolder
     * @param holder contiene el diseño de un elemento individual de la lista
     * @param position contiene la posicion del elemento
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNoti.setText(notificacionesList.get(position).getDescripción());
        holder.txtFecha.setText(notificacionesList.get(position).getFecha());
        //Glide.with(context).load(notificacionesList.get(position).getFoto()).circleCrop().into(holder.imgIcon);
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo getItemCount para establecer el tamaño de la lista
     * @return devolvemos el tamaño de la lista de notificaciones
     *
     * getItemCount()->Z
     */
    @Override
    public int getItemCount() {
        return notificacionesList.size();
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo ViewHolder para el recyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imgIcon;
        private TextView txtNoti;
        private TextView txtFecha;

        public ViewHolder(@NonNull View itemView) {

            //Referenciamos los items
            super(itemView);
            imgIcon = itemView.findViewById(R.id.iconNoti);
            txtNoti = itemView.findViewById(R.id.txtNoti);
            txtFecha = itemView.findViewById(R.id.txtHora);

        }
    }
}
