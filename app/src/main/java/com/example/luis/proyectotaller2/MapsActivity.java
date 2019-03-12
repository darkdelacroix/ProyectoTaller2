package com.example.luis.proyectotaller2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {//GoogleMap.OnInfoWindowClickListener
    private static final String TAG = "Map";
    private static final String LOGTAG ="MAPA" ;
    private GoogleMap mMap;
    ArrayList<Taller> datos = new ArrayList<Taller>();
    private GoogleApiClient apiClient;
    private static final int PETICION_PERMISO_LOCALIZACION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng alicante = new LatLng(38.3451700,  -0.4814900);
//        mMap.addMarker(new MarkerOptions().position(alicante).title("Marker in Alicante"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(alicante));

        new MapaTalleresTask().execute("http://andresterol.int.elcampico.org:8080/taller/talleres");

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Toast.makeText(this, "Error grave al conectar con Google Play Services", Toast.LENGTH_SHORT).show();

    }

    public void funcionPrueba(ArrayList<Taller> list) {
      LatLng latLng;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.car);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 84, 84, false);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        for(int i=0;i<list.size();i++) {

          latLng = new LatLng(list.get(i).getLatitud(), list.get(i).getLongitud());
          Point coord = mMap.getProjection().toScreenLocation(latLng);
//          Toast.makeText(this, "Lat: " + latLng.latitude + " | Long: " + latLng.longitude + "\nX:" + coord.x + "\tY:" + coord.y, Toast.LENGTH_SHORT).show();
          mMap.addMarker(new MarkerOptions()
                  .position(latLng)
                  .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                  .snippet(list.get(i).getDireccion()+":"+list.get(i).getTelefono())
//                  .snippet("Telefono: "+list.get(i).getTelefono())
                  .title(list.get(i).getNombre()+""));
      }
        mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
//        mMap.setOnInfoWindowClickListener(this);
      }



//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
//    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {
            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            updateUI(lastLocation);
        }
    }

    private void updateUI(Location loc) {
        LatLng latLng;
        latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

        if (loc != null) {
//            tvLongitud.setText("Longitud: " + loc.getLongitude());
//            tvLatitud.setText("Latitud: " + loc.getLatitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .snippet("este eres tu : cago end ios")
                    .title("soy yo"));
            mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));

        } else {
//            tvLongitud.setText("Longitud: (deconocida)");
//            tvLatitud.setText("Latitud: (deconocida)");
//            mMap.addMarker(new MarkerOptions()
//                    .position(latLng)
//                    .snippet("este eres tu")
//                    .title("soy yo"));
//            mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public class MapaTalleresTask extends AsyncTask<String, Void, ArrayList<Taller>>{
        private Taller taller;
        private String nombre,direccion,foto;
        private double latitud,longitud;
        private String telefono;
        @Override
        protected ArrayList<Taller> doInBackground(String... strings) {
            // TODO: attempt authentication against a network service.

            try {

                String res = HttpRequestTalleres.WebRequest(strings[0]);
                JSONArray respJSON = new JSONArray(res);
                for (int i = 0; i < respJSON.length(); i++) {

                    JSONObject json = respJSON.getJSONObject(i);
                    nombre = json.getString("nombre");
                    direccion = json.getString("direccion");
                    telefono =json.getString("telefono");
                    foto = json.getString("foto");

                    latitud =json.getDouble("latitud");
                    longitud =json.getDouble("longitud");
                    taller=new Taller(nombre,direccion,foto,telefono,latitud,longitud);
                    datos.add(taller);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return datos;
        }


        @Override
        protected void onPostExecute(ArrayList<Taller> tallers) {
            super.onPostExecute(tallers);
            funcionPrueba(tallers);


        }

        @Override
        protected void onCancelled() {

        }
    }




    }
