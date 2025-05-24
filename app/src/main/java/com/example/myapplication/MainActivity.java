package com.example.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.model.ItemCardapio;
import com.example.myapplication.view.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    ArrayList<ItemCardapio> cardapio = new ArrayList<>();



//    TextView teste = new TextView(l1.getContext());


    @Override
    public void onResume() {
        super.onResume();
        ItemCardapio teste = new ItemCardapio("agua","4.9","https://fortatacadista.vteximg.com.br/arquivos/ids/294006-800-800/7898165420039.jpg?v=637625571580730000");
        cardapio.add(teste);
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

        adapter = new RecyclerViewAdapter(cardapio);

        recyclerView.setAdapter(adapter);
    }

    private List<ItemCardapio> parseJsonParaCardapio(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ItemCardapio>>(){}.getType();
        return gson.fromJson(json, listType);
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