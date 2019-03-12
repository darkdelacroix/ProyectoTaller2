package com.example.luis.proyectotaller2;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeticionLoginHttp {
    public static final String TAG = "PeticionTAG";


    public static String WebRequest(String web,String user,String pass) {
        String salida = "";
        try {
            URL url = new URL(web);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setReadTimeout(15000);
            http.setConnectTimeout(15000);
//            http.setDoInput(true);//get
            http.setDoOutput(true);// post o put
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

//            http.setRequestProperty("X-CSRF-TOKEN", "9GrZM0aoeRuqGHuAlUxli7D7qB2hqfAjkkDmhSqI");

//            http.setDoOutput(true);// post o put
            Uri.Builder params = new Uri.Builder()
//                    .appendQueryParameter("_token", "9GrZM0aoeRuqGHuAlUxli7D7qB2hqfAjkkDmhSqI")
                    .appendQueryParameter("user", user)
                    .appendQueryParameter("pass", pass);
//
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(http.getOutputStream(), "UTF-8"));
            bw.write(params.build().getEncodedQuery());
            bw.flush();
            bw.close();
            http.connect();
            Log.e(TAG, "Error: " + http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                salida = sb.toString();
                br.close();
            }
            Log.e(TAG, "Error: " + salida);

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }


        return salida;
    }
}
