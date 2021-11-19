package com.example.jcherram.appbeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jcherram.appbeacon.R;

import java.util.ArrayList;
import java.util.List;

/**
 Clase NotificacionesAdapter para el recyclerView
 */

public class MedicionAdapter extends RecyclerView.Adapter<MedicionAdapter.ViewHolder> {



    List<Medicion> medicionList;
    private Context context;

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     *
     * <Notifiacion>,context->NotificacionesAdapter()
     */
    public MedicionAdapter(List<Medicion> medicionList, Context context) {
        this.medicionList = medicionList;
        this.context = context;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------


    /**

     */
    public MedicionAdapter(ArrayList<Medicion> listaMedicion) {
        this.medicionList = listaMedicion;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_medicion, parent, false);

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
        holder.txtValor.setText(medicionList.get(position).getValor());
        holder.txtFecha.setText(medicionList.get(position).getFecha());
        holder.txtTipo.setText(medicionList.get(position).getTipo());
        holder.txtmedicion.setText(medicionList.get(position).getMedicion());


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



        private TextView txtValor;
        private TextView txtFecha;
        private TextView txtmedicion;
        private TextView txtTipo;

        public ViewHolder(@NonNull View itemView) {

            //Referenciamos los items
            super(itemView);

            txtmedicion =itemView.findViewById(R.id.txtmedicion);
            txtFecha = itemView.findViewById(R.id.txthora);
            txtValor= itemView.findViewById(R.id.txtvalor);
            txtTipo=itemView.findViewById(R.id.txttipo);

        }
    }
}
