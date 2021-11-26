package com.example.jcherram.appbeacon;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.jcherram.appbeacon.adapter.Notificacion;
import com.example.jcherram.appbeacon.modelo.ActivityInfoContaminante;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicionesFragment extends Fragment {

    Dialog mydialog;
    TextView txtclose;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MedicionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicionesFragment newInstance(String param1, String param2) {
        MedicionesFragment fragment = new MedicionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String[] items={"CO2", "NO2", "CST"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adpterItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mediciones,
                container, false);

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView2);

        adpterItems = new ArrayAdapter<String>(getContext(), R.layout.list_gas, items);

        autoCompleteTextView.setAdapter(adpterItems);
        mydialog = new Dialog(getContext());
        mydialog.setContentView(R.layout.popupgas);
        txtclose=(TextView) mydialog.findViewById(R.id.txtclose);

        ImageButton btninfo =  view.findViewById(R.id.btninfo);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        ImageButton btninfocontaminantes =  view.findViewById(R.id.btninfocontaminantes);
        btninfocontaminantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityInfoContaminante.class);
                getActivity().startActivity(intent);
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "Gas: "+item,Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }

    public void showPopup(View v){
        TextView txtclose;

        mydialog.setContentView(R.layout.popupgas);
        txtclose=(TextView) mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        mydialog.show();

    }





}