// src/main/java/com/example/myapplication/model/ItemCardapio.java
package com.example.myapplication.model;

public class ItemCardapio {
    private String nome;
    private String preco;
    private String imagemUrl;
    private String imagePath;  // CAMPO PARA O CAMINHO LOCAL

    public ItemCardapio(String nome, String preco, String imagemUrl) {
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }

    public String getNome()       { return nome; }
    public String getPreco()      { return preco; }
    public String getImagemUrl()  { return imagemUrl; }

    public String getImagePath()  { return imagePath; }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}