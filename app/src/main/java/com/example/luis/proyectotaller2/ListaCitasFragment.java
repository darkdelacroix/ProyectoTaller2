package com.example.luis.proyectotaller2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ListaCitasFragment extends Fragment {
    public SharedPreferences sp;
    public RecyclerView recyclerView;
    ArrayList<Citas> datos = new ArrayList<Citas>();
    public static final String TAG = "PeticionTAG";

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {

        if(container!=null){
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_lista_citas, container, false);
        //Recogemos el recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.rvListaCitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        new CitasListasTask().execute("http://andresterol.int.elcampico.org:8080/taller/citas/andres@admin.com");

        return view;
    }



    public class CitasListasTask extends AsyncTask<String, Void, ArrayList<Citas>> {
        CitasAdaptador adaptador;
        public SharedPreferences sp;
        FragmentManager manager;
        private String msg, fecha, hora, matricula, nombre, idFoto;
        private int km, contador = 0;
        private boolean exito = true;
        Citas cita;

        @Override
        protected ArrayList<Citas> doInBackground(String... strings) {
            // TODO: attempt authentication against a network service.
            try {
                String res = HttpRequestCitasLista.WebRequest(strings[0]);
                JSONArray respJSON = new JSONArray(res);
                for (int i = 0; i < respJSON.length(); i++) {

                    JSONObject json = respJSON.getJSONObject(i);
                    fecha = json.getString("fecha");
                    hora = json.getString("hora");
                  //  km = json.getInt("km");//da error
                    matricula = json.getString("id_vehiculo");
                    nombre = json.getString("id_taller");
                    idFoto = json.getString("foto");
                    cita = new Citas(nombre, matricula, fecha, hora, idFoto, 80);
                    datos.add(cita);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return datos;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Citas> list) {
            super.onPostExecute(list);
//Añadimos el adaptador al recyclerview con los datos de la peticion post
            CitasAdaptador adaptador = new CitasAdaptador(list);
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hay que rellenear esta parte para ponerle un evento cuando se clickee en el item de la lista
                        int posicion=(int)recyclerView.getChildAdapterPosition(v);

                   Citas cita=new Citas(
                           datos.get(posicion).getNombreTaller(),
                           datos.get(posicion).getMatriculaVehiculo(),
                           datos.get(posicion).getFecha(),
                           datos.get(posicion).getHora(),
                           datos.get(posicion).getId(),
                           4
                   );
//                   manager.beginTransaction().replace(R.id.fragment,new CitaDetalleFragment(cita));
                   // getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new CitaDetalleFragment()).commit();
                }
            });
            recyclerView.setAdapter(adaptador);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        protected void onCancelled() {

        }
    }


}
