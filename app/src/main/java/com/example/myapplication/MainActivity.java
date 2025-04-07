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

import com.example.myapplication.model.HorarioElem;
import com.example.myapplication.view.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    ArrayList<HorarioElem> listaHorarios = new ArrayList<>();



//    TextView teste = new TextView(l1.getContext());


    @Override
    public void onResume() {
        super.onResume();
        Date dataAtual = new Date();
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
        int horaAtual = Integer.parseInt(sdfHora.format(dataAtual));
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        checkHorarios(horaAtual, listaHorarios);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }



    public void checkHorarios(int horaAtual, ArrayList<HorarioElem> listaHorarios){
        int qtdItens = horaAtual-8; // qtdItens é meio que a quantidade que deveria ter de elementos na tela

        if (horaAtual < 9){
            // se abrir o app antes de começar o dia de trabalho
            if (listaHorarios.size() > 0){
                listaHorarios.clear();
            }
            return;
        }

        if (listaHorarios.size() == 9){
            // nesse caso todos os horarios já foram inseridos na lista
            return;
        }

        // para tratar troca de dias
        if(listaHorarios.size() > qtdItens){
            listaHorarios.clear();
        }

        // verifica se os horarios adicionados ja sao os esperados
        if (listaHorarios.size() < qtdItens){
            for (int i = listaHorarios.size(); i < qtdItens; i++){
                // esse for seria pra adionar todos os horários que faltaram desde a ultima vez que o user entrou
                int horario = 8+i;
                if (horario>17){
                    break;
                }
                if (horario == 12){
                    continue;
                }


                boolean horarioExiste = false;
                for(int j = 0; j< listaHorarios.size()-1;j++){
                    if (listaHorarios.get(j).getHora().equals(horario + ":00h")){
                        horarioExiste = true;
                    }
                }
                if (!horarioExiste){
                    listaHorarios.add(new HorarioElem(horario + ":00h", ""));
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // minha sugestão pra implementar o contador:
        // cada vez que o user clicar/selecionar algum dos intens, disparar um for verificando se o texto de cada item é igual a "Fala o que você fez"
        // ele ja ta printando no terminal quando algum elemento é clicado, mas quando seleciona a caixa de texto não printa
        // ai nao sei como vai fazer pra acessar o texto de cada item, mas ctz que da.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView counterTextView = findViewById(R.id.counter);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Date dataAtual = new Date();
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
        int horaAtual = Integer.parseInt(sdfHora.format(dataAtual));

        checkHorarios(horaAtual,listaHorarios);
        System.out.println("-----------------------------");


        adapter = new RecyclerViewAdapter(listaHorarios, new RecyclerViewAdapter.OnDescricaoChangedListener() {
            @Override
            public void onDescricaoChanged() {
                int filledCount = 0;
                for (HorarioElem elem : listaHorarios) {
                    if (elem.getDescricao() != null && !elem.getDescricao().trim().isEmpty()) {
                        filledCount++;
                    }
                }
                counterTextView.setText(String.valueOf(filledCount));
            }
        });
        // o adapter foi criado juntamente com a função que trata mudanças nos elementos editText da lista
        // foi a maneira que achamos de conectar os itens da recyclerView com a MainActivity
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
//}




}
}