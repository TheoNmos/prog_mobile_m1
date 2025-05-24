package com.example.myapplication.model;

public class ItemCardapio {

    private String nome;
    private String preco;
    private String imagemUrl;

    public ItemCardapio(String nome, String preco, String imagemUrl) {
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
