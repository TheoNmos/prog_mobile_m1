// src/main/java/com/example/myapplication/MainActivity.java
package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.CardapioDBHelper;
import com.example.myapplication.model.ItemCardapio;
import com.example.myapplication.view.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private CardapioDBHelper dbHelper;
    private List<ItemCardapio> listaCache;
    private boolean isOfflineMode = false; // Flag to indicate offline mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new CardapioDBHelper(this);
        listaCache = dbHelper.getAllItems(); // Load initial data from DB

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass initial offline status (assuming online until proven otherwise)
        adapter = new RecyclerViewAdapter(listaCache, isOfflineMode);
        recyclerView.setAdapter(adapter);

        // Inicia o fluxo de download + DB
        new GetCardapioTask().execute();
    }

    private class GetCardapioTask extends AsyncTask<Void,Void,List<ItemCardapio>> {
        private static final String JSON_URL =
                "https://raw.githubusercontent.com/TheoNmos/prog_mobile_m1/refactor_to_m2/cardapioM2.json";

        @Override
        protected List<ItemCardapio> doInBackground(Void... voids) {
            try {
                // 1) Baixa o JSON
                HttpURLConnection conJson = (HttpURLConnection)
                        new URL(JSON_URL).openConnection();
                conJson.setRequestMethod("GET");
                conJson.setConnectTimeout(5000); // Add connection timeout
                conJson.setReadTimeout(5000);    // Add read timeout

                int responseCode = conJson.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    // If not successful, return null to indicate failure
                    return null;
                }

                InputStream isJson = conJson.getInputStream();
                String json = new Scanner(isJson).useDelimiter("\\A")
                        .next();
                conJson.disconnect();

                // 2) Desserializa
                Type t = new TypeToken<List<ItemCardapio>>(){}.getType();
                List<ItemCardapio> lista = new Gson().fromJson(json, t);

                // 3) Para cada item, baixa a imagem e salva em arquivo
                for (ItemCardapio it : lista) {
                    URL imgUrl = new URL(it.getImagemUrl());
                    HttpURLConnection conImg = (HttpURLConnection)
                            imgUrl.openConnection();
                    conImg.setRequestMethod("GET");
                    conImg.setConnectTimeout(5000); // Add connection timeout
                    conImg.setReadTimeout(5000);    // Add read timeout

                    int imgResponseCode = conImg.getResponseCode();
                    if (imgResponseCode != HttpURLConnection.HTTP_OK) {
                        // If image download fails, set path to null so placeholder is used
                        it.setImagePath(null);
                        conImg.disconnect();
                        continue; // Skip to next item
                    }

                    InputStream isImg = conImg.getInputStream();

                    // grava em storage interno
                    String filename = "img_" + it.getNome().hashCode() + ".jpg";
                    FileOutputStream fos =
                            openFileOutput(filename, MODE_PRIVATE);
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = isImg.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    isImg.close();
                    conImg.disconnect();

                    // guarda o path absoluto no objeto
                    String path = getFileStreamPath(filename).getAbsolutePath();
                    it.setImagePath(path);
                }

                return lista;
            } catch (Exception e) {
                e.printStackTrace();
                // Return null on any exception during network operation
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ItemCardapio> lista) {
            if (lista != null) {
                // 4) Persiste no SQLite
                dbHelper.clearAll();
                for (ItemCardapio it : lista) {
                    dbHelper.insertItem(it);
                }
                // 5) Recarrega do DB para a listaCache e notifica adapter
                listaCache.clear();
                listaCache.addAll(dbHelper.getAllItems());
                isOfflineMode = false; // Successfully fetched, so not in offline mode
            } else {
                // If list is null, it means network operation failed.
                // Keep showing existing cache and set offline mode.
                isOfflineMode = true;
            }
            adapter.setOfflineMode(isOfflineMode); // Update adapter with offline status
            adapter.notifyDataSetChanged();
        }
    }
}