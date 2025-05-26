package com.example.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "cardapio")
public class ItemCardapioEntity {

    @PrimaryKey
    @NonNull
    private String nome;

    private String preco;
    private String imagemUrl;
    private String imagemLocalPath;

    public ItemCardapioEntity(@NonNull String nome, String preco, String imagemUrl, String imagemLocalPath) {
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.imagemLocalPath = imagemLocalPath;
    }

    // Getters e Setters

    @NonNull
    public String getNome() { return nome; }

    public void setNome(@NonNull String nome) { this.nome = nome; }

    public String getPreco() { return preco; }

    public void setPreco(String preco) { this.preco = preco; }

    public String getImagemUrl() { return imagemUrl; }

    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getImagemLocalPath() { return imagemLocalPath; }

    public void setImagemLocalPath(String imagemLocalPath) { this.imagemLocalPath = imagemLocalPath; }
}
