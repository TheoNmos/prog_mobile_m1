package com.example.myapplication.model;

public class Produto {
    private double preco;
    private String descricao;

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Produto(double preco, String descricao) {
        this.preco = preco;
        this.descricao = descricao;
    }
}
