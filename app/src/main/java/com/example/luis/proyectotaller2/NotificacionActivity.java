package com.example.luis.proyectotaller2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificacionActivity extends AppCompatActivity {
//    public static final int NOTIFICACION_ID=1;
//      NotificationCompat.Builder notificacion;
//private static final String TAGLOG = "Error en Firebase";
//    private DatabaseReference mDatabase;
//    private ValueEventListener eventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);


//        mDatabase = FirebaseDatabase.getInstance().getReference();

//        dbAplicacion =
//                FirebaseDatabase.getInstance().getReference()
//                        .child("ejemplo1").child("mensaje")
//        ;
//
//        eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
//            }
//        };
//        dbAplicacion.addValueEventListener(eventListener);


//
//        final NotificationManager mNotific= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        CharSequence name="Luis";
//        String desc="Mi notificación";
//        int imp=NotificationManager.IMPORTANCE_HIGH;
//        final String ChannelID="MiCanal";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            NotificationChannel mChannel = new NotificationChannel(ChannelID, name, imp);
//            mChannel.setDescription(desc);
//            mChannel.setLightColor(Color.CYAN);
//            mChannel.canShowBadge();
//            mChannel.setShowBadge(true);
//            mNotific.createNotificationChannel(mChannel);
//        }
//
//        final int ncode=101;
//        notificacion= new NotificationCompat.Builder(NotificacionActivity.this,ChannelID)
//                .setSmallIcon(android.R.drawable.ic_dialog_map)
//                .setLargeIcon(((BitmapDrawable)getResources()
//                        .getDrawable(android.R.drawable.ic_dialog_map)).getBitmap())
//                .setContentTitle("Notificación de mapas")
//                .setContentText("Pasa a la pantalla que contiene la latitud y la longitud")
//                .setContentInfo("1")
//                .setTicker("Nuevo marcador en el mapa");
//
//
//        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICACION_ID,notificacion.build());





    }
}
