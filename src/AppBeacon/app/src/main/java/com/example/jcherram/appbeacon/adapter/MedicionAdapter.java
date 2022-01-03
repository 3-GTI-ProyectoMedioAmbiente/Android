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
 * @Author: Alberto Valls Martinez
 * Fecha: 23/11/21
 * MedicionAdapter
 * Adaptador para el recycler view de mediciones
 */

// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------

public class MedicionAdapter extends RecyclerView.Adapter<MedicionAdapter.ViewHolder> {



    public static List<Medicion> medicionList;
    private Context context;

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Constructor de medicionAdapter
     *
     * @param medicionList le pasamos la lista de mediciones
     * @param context le pasamos el contexto deseado
     */
    public MedicionAdapter(List<Medicion> medicionList, Context context) {

        this.context = context;
        ArrayList<Medicion> arraListaInvertida = new ArrayList<>();
        for (int i=medicionList.size()-1;i>=0;i--){
            arraListaInvertida.add(medicionList.get(i));
        }
        this.medicionList = arraListaInvertida;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     *
     * @param listaMedicion
     */
    public MedicionAdapter(ArrayList<Medicion> listaMedicion) {
        ArrayList<Medicion> arraListaInvertida = new ArrayList<>();
        for (int i=listaMedicion.size()-1;i>=0;i--){
            arraListaInvertida.add(listaMedicion.get(i));
        }

        this.medicionList = listaMedicion;
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**
     * Metodo para adjuntar la vista de las tarjetas de medicion
     *
     * @param parent introducimos el padre que necesitamos
     * @param viewType para el tipo de vista
     * @return devolvemos la vista con las mediciones
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_medicion, parent, false);

        return new ViewHolder(view);
    }
    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Matodo para relacionar cada texto con su valor
     *
     * @param holder para la vista
     * @param position para coger la posicion del valor requerido
     *
     * ViewHolder, Z-> onBindViewHolder()
     */
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

