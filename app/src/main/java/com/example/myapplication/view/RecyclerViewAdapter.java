package com.example.myapplication.view;

import android.net.Uri;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ItemCardapio;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private ArrayList<ItemCardapio> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nome;
        private final TextView preco;
        private final ImageView imagem;

        public ViewHolder(View v) {
            super(v);
            nome = v.findViewById(R.id.nome);
            preco = v.findViewById(R.id.preco);
            imagem = v.findViewById(R.id.imagem);

            v.setOnClickListener(view -> {
                Log.d(TAG, "Elemento " + getAdapterPosition() + " clicado.");
            });
        }

        public TextView getNome() {
            return nome;
        }

        public TextView getPreco(){
            return preco;
        }

        public ImageView getImagem(){
            return imagem;
        }


    }

    public RecyclerViewAdapter(ArrayList<ItemCardapio> cardapio) {
        this.mDataSet = cardapio;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_product_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ItemCardapio i = mDataSet.get(position);
        viewHolder.getNome().setText(i.getNome());
        viewHolder.getPreco().setText(i.getPreco());
        viewHolder.getImagem().setImageURI(Uri.parse(i.getImagemUrl()));
        // Adiciona um TextWatcher para monitorar as mudanças no EditText
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
