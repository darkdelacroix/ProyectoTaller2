package com.example.luis.proyectotaller2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.design.widget.TextInputLayout;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    public SharedPreferences sp;
    private EditText  etPass;
    private Button btLogin;
    private TextInputLayout tilUsuario;

    // UI references.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        etPass = findViewById(R.id.etPass);
        tilUsuario = findViewById(R.id.tilUsuario);
        btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(accionLogin);


    }

    public boolean validar(String usuario){
        boolean salida=false;
        Pattern patron= Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$");
        if(!patron.matcher(usuario).matches()){
            tilUsuario.setError("Dirección email no valida");
        }else{
            tilUsuario.setError(null);
            salida=true;
        }


        return salida;
    }

    OnClickListener accionLogin = new OnClickListener() {
        @Override
        public void onClick(View v) {


            String usuario = tilUsuario.getEditText().getText().toString();
            String password = etPass.getText().toString();
            boolean exitoValidar=validar(usuario);
            if(exitoValidar) {
                String web = "http://www.bernadiego.es/pruebaLogin";
//            AsyncTask<String, Boolean, Boolean> task = new UserLoginTask(LoginActivity.this).execute(web, usuario, password);
                new UserLoginTask().execute(web, usuario, password);
            }else{
                Toast.makeText(LoginActivity.this, "Mensaje:  Inténtelo de nuevo", Toast.LENGTH_SHORT).show();


            }

        }
    };

    public class UserLoginTask extends AsyncTask<String, Boolean, Boolean> {

        private String msg;
        private boolean exito;
//        private Activity activity;
//
//        public UserLoginTask(Activity activity) {
//            this.activity = activity;
//        }

        @Override
        protected Boolean doInBackground(String... strings) {
            // TODO: attempt authentication against a network service.
            String res = PeticionLoginHttp.WebRequest(strings[0], strings[1], strings[2]);
            String resultado = "";
            try {
                // Simulate network access.
                Thread.sleep(1000);
                JSONObject json = new JSONObject(res);
                msg = json.getString("msg");
                exito = json.getBoolean("succes");

            } catch (InterruptedException e) {
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            publishProgress(exito);


            // TODO: register the new account here.
            if (exito) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            if (values[0]) {
                try {
                    sp = getSharedPreferences("Taller", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putBoolean("login", true);
                    ed.commit();
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(LoginActivity.this, "Mensaje:  " + msg, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(LoginActivity.this, "Mensaje:  " + msg, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(LoginActivity.this, "Mensaje:  Inténtelo de nuevo", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {

        }
    }
}

