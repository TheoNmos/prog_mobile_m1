package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.model.Produto;
import com.example.myapplication.view.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;


//    TextView teste = new TextView(l1.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Criando lista de produtos
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto(5, "agua"));
        listaProdutos.add(new Produto(20, "pao"));
        listaProdutos.add(new Produto(7, "refri"));
        listaProdutos.add(new Produto(5, "agua"));
        listaProdutos.add(new Produto(20, "pao"));
        listaProdutos.add(new Produto(7, "refri"));
        listaProdutos.add(new Produto(5, "agua"));
        listaProdutos.add(new Produto(20, "pao"));
        listaProdutos.add(new Produto(7, "refri"));

        adapter = new RecyclerViewAdapter(listaProdutos);
        recyclerView.setAdapter(adapter);

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
//        }




    }
}