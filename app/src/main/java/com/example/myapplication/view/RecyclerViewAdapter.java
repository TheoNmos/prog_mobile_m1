package com.example.myapplication.view;

import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.HorarioElem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private ArrayList<HorarioElem> mDataSet;
    private OnDescricaoChangedListener onDescricaoChangedListener;

    // Interface de callback para notificar a MainActivity
    public interface OnDescricaoChangedListener {
        void onDescricaoChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView hora;
        private final EditText descricao;

        public ViewHolder(View v) {
            super(v);
            hora = v.findViewById(R.id.textHorario);
            descricao = v.findViewById(R.id.textDescription);

            v.setOnClickListener(view -> {
                Log.d(TAG, "Elemento " + getAdapterPosition() + " clicado.");
            });
        }

        public TextView getHorario() {
            return hora;
        }

        public EditText getDescricao(){return descricao;}


    }

    public RecyclerViewAdapter(ArrayList<HorarioElem> horarios, OnDescricaoChangedListener listener) {
        this.mDataSet = horarios;
        this.onDescricaoChangedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_product_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        HorarioElem h = mDataSet.get(position);
        viewHolder.getHorario().setText(h.getHora());
        viewHolder.getDescricao().setText(h.getDescricao());
        // Adiciona um TextWatcher para monitorar as mudanças no EditText
        viewHolder.getDescricao().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                h.setDescricao(s.toString());
                if (onDescricaoChangedListener != null) {
                    onDescricaoChangedListener.onDescricaoChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
