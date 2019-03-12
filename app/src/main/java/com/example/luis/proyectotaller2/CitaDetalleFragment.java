package com.example.luis.proyectotaller2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CitaDetalleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private TextView tvNombreTaller,tvMatricula,tvFecha,tvHora;
    private ImageView ivImagen;
    private Citas cita;


    public CitaDetalleFragment(){

    }

//    public CitaDetalleFragment(Citas cita) {
//         Required empty public constructor
//        this.cita=cita;
//
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cita_detalle, container, false);
        tvNombreTaller=view.findViewById(R.id.tvNombreTaller);
        tvMatricula=view.findViewById(R.id.tvMatricula);
        tvFecha=view.findViewById(R.id.tvFecha);
        tvHora=view.findViewById(R.id.tvHora);
        ivImagen=view.findViewById(R.id.ivImagen);

        if(container!=null){
            container.removeAllViews();
        }

        tvNombreTaller.setText(cita.getNombreTaller());
        return view;
    }




}
