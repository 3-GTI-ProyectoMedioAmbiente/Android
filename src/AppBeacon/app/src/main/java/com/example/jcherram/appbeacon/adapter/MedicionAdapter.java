package com.example.jcherram.appbeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jcherram.appbeacon.R;
import com.example.jcherram.appbeacon.Utilidades;
import com.example.jcherram.appbeacon.modelo.Medicion;

import java.util.ArrayList;
import java.util.List;

/**
 Clase NotificacionesAdapter para el recyclerView
 */

public class MedicionAdapter extends RecyclerView.Adapter<MedicionAdapter.ViewHolder> {



    private List<Medicion> medicionList;
    private Context context;

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    public MedicionAdapter(List<Medicion> medicionList, Context context) {
        this.medicionList = medicionList;
        this.context = context;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------



    public MedicionAdapter(ArrayList<Medicion> listaMedicion) {
        this.medicionList = listaMedicion;
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_medicion, parent, false);

        return new ViewHolder(view);
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtvalor.setText(medicionList.get(position).getValor());
        holder.txtfecha.setText(Utilidades.TimeToString(medicionList.get(position).getHora()));
        holder.txttipo.setText(medicionList.get(position).getTipoMedicion());
        holder.txtmedicion.setText(String.valueOf(medicionList.get(position).getMedicion()));
        if(medicionList.get(position).getMedicion()>40 && medicionList.get(position).getMedicion()<200){
            holder.txtvalor.setTextColor(context.getResources().getColor(R.color.naranja, null));
        }else if(medicionList.get(position).getMedicion()>200){
            holder.txtvalor.setTextColor(context.getResources().getColor(R.color.rojo, null));
        }
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
        return medicionList.size();
    }


    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo ViewHolder para el recyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txtvalor;
        private TextView txtmedicion;
        private TextView txtfecha;
        private TextView txttipo;

        public ViewHolder(@NonNull View itemView) {

            //Referenciamos los items
            super(itemView);
            txttipo = itemView.findViewById(R.id.txttipo);
            txtfecha = itemView.findViewById(R.id.textViewHoraUltima);
            txtvalor = itemView.findViewById(R.id.textViewValorUtima);
            txtmedicion = itemView.findViewById(R.id.textViewMedicionUltima);

        }
    }
}

