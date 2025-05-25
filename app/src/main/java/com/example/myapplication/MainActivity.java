package com.example.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.model.ItemCardapio;
import com.example.myapplication.view.RecyclerViewAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    ArrayList<ItemCardapio> cardapio = new ArrayList<>();

    private List<ItemCardapio> parseJsonParaCardapio(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ItemCardapio>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    // implementando AsyncTask como inner class da activity.
    private class GetCardapioServices extends AsyncTask<Void, Void, String> {
        // Ajuste aqui a URL do seu endpoint que retorna o JSON do cardápio
        private static final String RESOURCE_URL = "https://raw.githubusercontent.com/TheoNmos/prog_mobile_m1/refs/heads/refactor_to_m2/cardapioM2.json";

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
                Log.i("GetCardapioService", "requisição concluída: " + response);
                return response;
            } catch (Exception e) {
                Log.e("GetCardapioService", "Erro ao conectar em " + RESOURCE_URL, e);
                return null;
            } finally {
                if (con != null) con.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String json) {
            cardapio.clear();
            if (json != null) {
                // 1. Converte a String JSON em lista de ItemCardapio e adiciona a arraylist
                List<ItemCardapio> cardapio_parsed = parseJsonParaCardapio(json);
                System.out.println(cardapio_parsed.toString());
                cardapio.addAll(cardapio_parsed);


                // 2. Salva no banco local (Room) e dispara download das imagens...
//                Executors.newSingleThreadExecutor().execute(() -> {
//                    dao.clearAll();
//                    dao.insertAll(lista);
//                    for (ItemCardapio it : lista) {
//                        try {
//                            String fn = "img_" + it.getNome().hashCode() + ".jpg";
//                            String path = ImageUtils.downloadAndSave(
//                                    getApplicationContext(),
//                                    it.getImagemUrl(),
//                                    fn
//                            );
//                            it.setImagemLocalPath(path);
//                            dao.update(it);
//                        } catch (Exception ignored) { }
//                    }
//                    // 3. Atualiza a RecyclerView na thread principal
//                    runOnUiThread(() -> adapter.updateData(lista, isOnline()));
//                });
            } else {
                return;
                // fallback: carregar do SQLite
//                loadFromCache();
            }
            adapter.notifyDataSetChanged();
        }
    }


//    TextView teste = new TextView(l1.getContext());


    @Override
    public void onResume() {
        super.onResume();
        new GetCardapioServices().execute();

        System.out.println("executou");
        // codar logica geral
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // relacionando a view com a lista de intens, vulgo cardapio
        adapter = new RecyclerViewAdapter(cardapio);
        recyclerView.setAdapter(adapter);

        new GetCardapioServices().execute();

        System.out.println("executou");


    }


        // UTILIZANDO LINEAR LAYOUT
//        LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout);
//        int horario = new Date().getHours();
//        for(int i = 0; i< horario; i++){
//            LinearLayout llHorizontal = new LinearLayout(this);
//            llHorizontal.setOrientation(LinearLayout.HORIZONTAL);
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            TextView label = new TextView(this);
//            label.setText(String.valueOf(i));
//
//            EditText et = new EditText(this);
//            et.setHint("Informe sua atividade...");
//            et.setLayoutParams(layoutParams);
//
//            Button bt = new Button(this);
//            bt.setText("X");
//
//
//            llHorizontal.addView(label);
//            llHorizontal.addView(et);
//            llHorizontal.addView(bt);
//
//            l1.addView(llHorizontal);
//}





}