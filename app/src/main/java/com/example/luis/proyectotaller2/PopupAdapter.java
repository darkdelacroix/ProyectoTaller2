package com.example.luis.proyectotaller2;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import java.util.StringTokenizer;

class PopupAdapter implements InfoWindowAdapter {
    private View popup=null;
    private LayoutInflater inflater=null;
    private static final String TAG = "PopUp";
    private Button btnLlamar;

    PopupAdapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        String direccion,telefono;
        String snippet= marker.getSnippet();
        //esto es por si el snippet en algun marcador es null para que no falle
        if(snippet==null){
            direccion="no exite";
            telefono="no existe";
        }else {
//      String[] datos=snippet.split("\\:");//primero direccion y luego telefono de la informacion del snippet
            StringTokenizer tokens = new StringTokenizer(snippet, ":");
            direccion = tokens.nextToken();
            telefono = tokens.nextToken();
        }
        Log.e(TAG,"Dios"+snippet);
        if (popup == null) {
            popup=inflater.inflate(R.layout.popup, null);
        }

        TextView tv=(TextView)popup.findViewById(R.id.tvTitulo);

        tv.setText(marker.getTitle());
        tv=(TextView)popup.findViewById(R.id.tvDireccion);
        tv.setText(direccion);
        tv=(TextView)popup.findViewById(R.id.tvTelefono);
        tv.setText(telefono);
        btnLlamar=(Button)popup.findViewById(R.id.btnLlamar);

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(popup.getContext(), "Funcionara", Toast.LENGTH_SHORT).show();
            }
        });

        return(popup);
    }
}