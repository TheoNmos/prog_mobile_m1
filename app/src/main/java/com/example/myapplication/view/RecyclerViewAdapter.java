package com.example.myapplication.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private ArrayList<String> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView horario;
        private final EditText descricao;

        public ViewHolder(View v) {
            super(v);
            horario = v.findViewById(R.id.textHorario);
            descricao = v.findViewById(R.id.textDescription);

            descricao.setOnFocusChangeListener((view, b) -> {
                        if(descricao.getText().toString() != ""){
                            EditText counter = v.findViewById(R.id.counter);



                        }
                        Log.d(TAG,descricao.getText().toString());
                    }
            );
            v.setOnClickListener(view -> {
                Log.d(TAG, "Elemento " + getAdapterPosition() + " clicado.");
            });
        }

        public TextView getHorario() {
            return horario;
        }

        public EditText getDescricao(){return descricao;}

    }

    public RecyclerViewAdapter(ArrayList<String> horarios) {
        this.mDataSet = horarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_product_item, viewGroup, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String h = mDataSet.get(position);
        viewHolder.getHorario().setText(h);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
