package com.example.wesley.exerciciojsonsqlite.model;

import java.io.Serializable;

public class Voo implements Comparable<Voo>, Serializable{
    private String nome;
    private String origem;
    private String imagem;
    private double preco;
    private String destino;


    public static final String NAO_ENCONTRADO = "Voo não encontrado.";

    public Voo(String nome , String origem, String destino, String imagem, double preco) {
        this.nome = nome;
        this.origem = origem;
        this.imagem = imagem;
        this.preco = preco;
        this.destino = destino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public int compareTo(Voo voo) {
        if (voo.equals(this)){
            return 0;
        }
        return 1;
    }
}