package com.example.myapplication.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Produto;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private ArrayList<Produto> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView preco;
        private final TextView nome;

        public ViewHolder(View v) {
            super(v);
            nome = v.findViewById(R.id.textNome);
            preco = v.findViewById(R.id.textPreco);

            v.setOnClickListener(view -> {
                Log.d(TAG, "Elemento " + getAdapterPosition() + " clicado.");
            });
        }

        public TextView getPreco() {
            return preco;
        }

        public TextView getNome() {
            return nome;
        }
    }

    public RecyclerViewAdapter(ArrayList<Produto> produtos) {
        this.mDataSet = produtos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_product_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Produto p = mDataSet.get(position);
        viewHolder.getNome().setText(p.getDescricao());
        viewHolder.getPreco().setText(String.valueOf(p.getPreco()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
