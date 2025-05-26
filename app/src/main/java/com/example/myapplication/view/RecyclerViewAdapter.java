// src/main/java/com/example/myapplication/view/RecyclerViewAdapter.java
package com.example.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ItemCardapio;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ItemCardapio> mDataSet;

    public RecyclerViewAdapter(List<ItemCardapio> data) {
        this.mDataSet = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco;
        ImageView imagem;
        public ViewHolder(View v) {
            super(v);
            nome   = v.findViewById(R.id.nome);
            preco  = v.findViewById(R.id.preco);
            imagem = v.findViewById(R.id.imagem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup vg, int vt) {
        View v = LayoutInflater.from(vg.getContext())
                .inflate(R.layout.rv_product_item, vg, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int pos) {
        ItemCardapio item = mDataSet.get(pos);
        vh.nome.setText(item.getNome());
        vh.preco.setText(item.getPreco());

        String path = item.getImagePath();
        if (path != null) {
            Bitmap bmp = BitmapFactory.decodeFile(path);
            vh.imagem.setImageBitmap(bmp);
        } else {
            vh.imagem.setImageResource(R.drawable.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}