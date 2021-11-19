package com.example.jcherram.appbeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jcherram.appbeacon.R;

import java.util.List;

public class MedicionAdapter extends RecyclerView.Adapter<MedicionAdapter.ViewHolder> {

    private List<Medicion> mData;
    private LayoutInflater mInflater;
    private Context context;


    public MedicionAdapter(List<Medicion> itemList, Context context)
    {

        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
    }


    @Override

    public int getItemCount(){return mData.size();}

    @Override
    public MedicionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view=mInflater.inflate(R.layout.card_medicion, null);
        return new MedicionAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MedicionAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Medicion>items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtvalor;
        TextView txtmedicion;
        TextView txtfecha;
        TextView txttipo;

        ViewHolder(View itemView){
            super(itemView);
            txtvalor.findViewById(R.id.txtvalor);
            txtmedicion.findViewById(R.id.txtmedicion);
            txtfecha.findViewById(R.id.txthora);
            txttipo.findViewById(R.id.txttipo);
        }


        void bindData(final Medicion item){

            txttipo.setText(item.getTipo());
            txtfecha.setText(item.getFecha());
            txtmedicion.setText(item.getMedida());
            txtvalor.setText(item.getValor());
        }


    }

}
