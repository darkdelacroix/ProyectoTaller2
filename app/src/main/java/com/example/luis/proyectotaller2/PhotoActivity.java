package com.example.luis.proyectotaller2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.luis.proyectotaller2.CameraActivity.MY_PERMISSIONS_REQUEST_CAMARA;

public class PhotoActivity extends AppCompatActivity {

    Button btFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        btFoto=findViewById(R.id.btFoto);
        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                    if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoActivity.this, Manifest.permission.CAMERA)) {
                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No se necesita dar una explicación al usuario, sólo pedimos el permiso.
                            ActivityCompat.requestPermissions(PhotoActivity.this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMARA );
                            // MY_PERMISSIONS_REQUEST_CAMARA es una constante definida en la app. El método callback obtiene el resultado de la petición.
                        }
                    }else{ //have permissions
                        abrirCamara ();
                    }
                }else{ // Pre-Marshmallow
                    abrirCamara ();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMARA : {
                // Si la petición es cancelada, el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // El permiso ha sido concedido.
                    abrirCamara ();
                } else {
                    // Permiso denegado, deshabilita la funcionalidad que depende de este permiso.
                }
                return;
            }
            // otros bloques de 'case' para controlar otros permisos de la aplicación
        }
    }

    public void abrirCamara (){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);
    }
}
