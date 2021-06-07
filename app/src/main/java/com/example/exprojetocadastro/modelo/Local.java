package com.example.exprojetocadastro.modelo;

import java.io.Serializable;

public class Local implements Serializable {

    private int id;
    private String nome_local;
    private String bairro;
    private String cidade;
    private String capacidade;

    //public Local(int id, String nome_local) {
     //   this.id = id;
     //   this.nome_local = nome_local;
    //}

    public Local(int id, String nome_local, String bairro, String cidade, String capacidade) {
        this.id = id;
        this.nome_local = nome_local;
        this.bairro = bairro;
        this.cidade = cidade;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getNome_local() {
        return nome_local;
    }

    public void setNome_local(String nome_local) {
        this.nome_local = nome_local;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return this.nome_local;
    }
}
