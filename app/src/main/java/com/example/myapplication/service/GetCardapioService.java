package com.example.myapplication.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetCardapioService extends AsyncTask<Void, Void, String> {
    private static final String TAG = "GetCardapioService";
    // Ajuste aqui a URL do seu endpoint que retorna o JSON do cardápio
    private static final String RESOURCE_URL = "http://192.168.0.14:3333/cardapio";

    @Override
    protected String doInBackground(Void... voids) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(RESOURCE_URL);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();
            InputStream is = (status >= 200 && status < 300)
                    ? con.getInputStream()
                    : con.getErrorStream();

            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String response = scanner.hasNext() ? scanner.next() : "";
            Log.i(TAG, "requisição concluída: " + response);
            return response;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao conectar em " + RESOURCE_URL, e);
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }
}