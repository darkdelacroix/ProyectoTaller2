package com.example.luis.proyectotaller2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestCitasLista {
//    http://andresterol.int.elcampico.org:8080/taller/

    private static final String TAG = "PeticionTAG";
    public   static String WebRequest(String web) {
        String salida = "";
//        String webConstruida=web+"/citas/"+user;
        try {
            URL url = new URL(web);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setReadTimeout(15000);
            http.setConnectTimeout(15000);
            http.setDoInput(true);


            http.setRequestMethod("GET");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                salida=sb.toString();
                br.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: Httprequestcitas " + e.getMessage());
            e.printStackTrace();
        }

        return salida;
    }
}
