package com.example.myapplication.model;

public class HorarioElem {
    private String hora;
    private String descricao;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public HorarioElem(String hora, String descricao) {
        this.hora = hora;
        this.descricao = descricao;
    }
}
