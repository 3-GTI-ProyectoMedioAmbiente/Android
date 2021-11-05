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

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {



    List<Notificacion> notificacionesList;
    private Context context;

    public NotificacionesAdapter(List<Notificacion> notificacionesList, Context context) {
        this.notificacionesList = notificacionesList;
        this.context = context;
    }

    public NotificacionesAdapter(ArrayList<Notificacion> listaNotificacion) {
        this.notificacionesList = listaNotificacion;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notificaciones, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNoti.setText(notificacionesList.get(position).getDescripción());
        holder.txtFecha.setText(notificacionesList.get(position).getFecha());
        //Glide.with(context).load(notificacionesList.get(position).getFoto()).circleCrop().into(holder.imgIcon);
    }

    @Override
    public int getItemCount() {
        return notificacionesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imgIcon;
        private TextView txtNoti;
        private TextView txtFecha;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imgIcon = itemView.findViewById(R.id.iconNoti);
            txtNoti = itemView.findViewById(R.id.txtNoti);
            txtFecha = itemView.findViewById(R.id.txtHora);

        }
    }
}
