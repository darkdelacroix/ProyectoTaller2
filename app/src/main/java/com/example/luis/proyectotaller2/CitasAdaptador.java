package com.example.luis.proyectotaller2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CitasAdaptador extends RecyclerView.Adapter<CitasAdaptador.CitasViewHolder> implements View.OnClickListener {
    private  ArrayList<Citas> datos;
    private View.OnClickListener listener;
    public CitasAdaptador(ArrayList<Citas> datos){
        this.datos=datos;
    }

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.citas_layout,viewGroup,false);
        itemView.setOnClickListener(this);
        CitasViewHolder cvh=new CitasViewHolder(itemView);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder citasViewHolder, int i) {
        citasViewHolder.bindCitas(datos.get(i));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
//


    @Override
    public void onClick(View v) {
        if(listener!=null)
            listener.onClick(v);





    }

    static class CitasViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreTaller,tvMatricula,tvFecha,tvHora;
        private ImageView ivImagen;


        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreTaller=itemView.findViewById(R.id.tvNombreTaller);
            tvMatricula=itemView.findViewById(R.id.tvMatricula);
            tvHora=itemView.findViewById(R.id.tvHora);
            tvFecha=itemView.findViewById(R.id.tvFecha);
            ivImagen=itemView.findViewById(R.id.ivImagen);
        }
        public void bindCitas(Citas citas){
            tvNombreTaller.setText(citas.getNombreTaller());
            tvMatricula.setText(citas.getMatriculaVehiculo());
            tvHora.setText(citas.getHora());
            tvFecha.setText(citas.getFecha());

//            ivImagen.setImageDrawable(ivImagen.getResources().getDrawable(R.drawable.ic_menu_camera));
//            GlideApp.with(this)
//                    .load("http://via.placeholder.com/300.png")
//                    .into(ivImagen);

            String uri="http://andresterol.int.elcampico.org:8080/taller/"+citas.getId();

            Glide.with(itemView.getContext())
                    .load(uri)
                    .into(ivImagen);
        }


    }

    public void setDatos(ArrayList<Citas> datos) {
        this.datos = datos;
    }
}
