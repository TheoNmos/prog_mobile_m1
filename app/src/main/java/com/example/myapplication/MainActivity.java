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
import androidx.room.Room;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.ItemCardapioDao;
import com.example.myapplication.database.ItemCardapioEntity;
import com.example.myapplication.utils.ImageUtils;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    ArrayList<ItemCardapio> cardapio = new ArrayList<>();

    private AppDatabase db;
    private ItemCardapioDao dao;




    private List<ItemCardapio> parseJsonParaCardapio(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ItemCardapio>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    // implementando AsyncTask como inner class da activity.
    private class GetCardapioServices extends AsyncTask<Void, Void, String> {

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

                Log.i("GetCardapioService", "Requisição concluída: " + response);
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
                List<ItemCardapio> cardapioParsed = parseJsonParaCardapio(json);
                cardapio.addAll(cardapioParsed);

                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        // 🔸 Limpa o banco anterior
                        dao.clearAll();

                        List<ItemCardapioEntity> entities = new ArrayList<>();

                        for (ItemCardapio item : cardapioParsed) {
                            try {
                                // 🔸 Salva imagem local
                                String fileName = "img_" + item.getNome().hashCode() + ".jpg";
                                String imagePath = ImageUtils.downloadAndSave(
                                        getApplicationContext(),
                                        item.getImagemUrl(),
                                        fileName
                                );

                                // 🔸 Cria a entidade para o banco
                                ItemCardapioEntity entity = new ItemCardapioEntity(
                                        item.getNome(),
                                        item.getPreco(),
                                        item.getImagemUrl(),
                                        imagePath
                                );
                                entities.add(entity);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // 🔸 Salva todos no banco
                        dao.insertAll(entities);

                        runOnUiThread(() -> adapter.notifyDataSetChanged());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } else {
                // 🔸 Se falhar, carrega do cache local
                loadFromCache();
            }
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

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cardapio-db").build();
        dao = db.itemCardapioDao();


        new GetCardapioServices().execute();

        System.out.println("executou");
    }

    private void loadFromCache() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ItemCardapioEntity> items = dao.getAll();
            cardapio.clear();
            for (ItemCardapioEntity it : items) {
                ItemCardapio item = new ItemCardapio(
                        it.getNome(),
                        isOnline() ? it.getPreco() : "a consultar",
                        it.getImagemLocalPath()
                );
                cardapio.add(item);
            }

            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }


    private boolean isOnline() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("https://www.google.com").openConnection();
            con.setRequestMethod("HEAD");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            int responseCode = con.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (Exception e) {
            return false;
        }
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