package com.example.jcherram.appbeacon.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.jcherram.appbeacon.ActivityInfoContaminante;
import com.example.jcherram.appbeacon.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapaFragment extends Fragment {

    private Dialog mydialog;
    private TextView txtclose;
    private WebView webView;

    public MapaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MedicionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Constructor de la vista del mapa
     * @param savedInstanceState instancia de medicionesFragment
     */

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
        View view = inflater.inflate(R.layout.fragment_mapa,
                container, false);




        mydialog = new Dialog(getContext());
        mydialog.setContentView(R.layout.popupgas);
        txtclose=(TextView) mydialog.findViewById(R.id.txtclose);

        webView=view.findViewById(R.id.webview);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.loadUrl("http://192.168.1.14/test.html");


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





        return view;
    }

    // -----------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------

    /**
     * Metodo para abrir el popup
     * @param v recibimos la vista deseada
     */

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