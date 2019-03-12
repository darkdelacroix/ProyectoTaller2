package com.example.luis.proyectotaller2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public SharedPreferences sp;
    private static final String TAGLOG ="Error firebase" ;
    DatabaseReference dbCielo;
    private ValueEventListener eventListener;
    public static final int NOTIFICACION_ID=1;
    private  NotificationCompat.Builder notificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("Taller", Context.MODE_PRIVATE);
        ListaCitasFragment citasFragment=new ListaCitasFragment();
        //comprobamos si esta logeado y si no lo pasamos al login
//        Boolean login=sp.getBoolean("login",false);
//        if (login){
//        }else{
//            Intent i=new Intent(this,LoginActivity.class);
//            startActivity(i);
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Aqui ponemos las notificaciones con el databasefirebase



        dbCielo =
                FirebaseDatabase.getInstance().getReference()
                        .child("prueba")
                        .child("nombre");

        final NotificationManager mNotific= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name="Luis";
        String desc="Mi notificación";
        int imp=NotificationManager.IMPORTANCE_HIGH;
        final String ChannelID="MiCanal";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(ChannelID, name, imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            mNotific.createNotificationChannel(mChannel);
        }

        final int ncode=101;
        notificacion= new NotificationCompat.Builder(MainActivity.this,ChannelID)
                .setSmallIcon(android.R.drawable.ic_dialog_map)
                .setLargeIcon(((BitmapDrawable)getResources()
                        .getDrawable(android.R.drawable.ic_dialog_map)).getBitmap())
                .setContentTitle("Ha habido un cambio en la base de datos")
                .setContentText("Ok")
                .setContentInfo("1")
                .setTicker("Cambio");


        dbCielo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICACION_ID,notificacion.build());
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_siniestros) {

            Intent i=new Intent(MainActivity.this,PhotoActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_gallery) {

            Intent i=new Intent(MainActivity.this,NotificacionActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new CitaDetalleFragment()).commit();

        } else if (id == R.id.nav_map) {

            Intent i=new Intent(MainActivity.this,MapsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            sp=getSharedPreferences("Taller", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean("login", false);
            ed.commit();
            Intent i=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
