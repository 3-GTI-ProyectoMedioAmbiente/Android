package com.example.jcherram.appbeacon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeaconsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeaconsFragment extends Fragment {


    public BeaconsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeaconsFragment.
     */
    public static BeaconsFragment newInstance(String param1, String param2) {
        BeaconsFragment fragment = new BeaconsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_beacons2, container, false);
        Button buttonVerHistorial = v.findViewById(R.id.buttonVerHistorial);
        ImageView buttonConfigurarNodo = v.findViewById(R.id.imageViewConfigurarNodo);
        buttonConfigurarNodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarNodo();
            }
        });

        buttonVerHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verHistorial();
            }
        });
        return v;
    }
    private void configurarNodo(){
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    private void verHistorial(){
        Intent intent = new Intent(getContext(), ActivityHistorialMediciones.class);
        startActivity(intent);
    }

}