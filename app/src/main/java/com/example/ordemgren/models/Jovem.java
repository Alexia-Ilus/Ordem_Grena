package com.example.ordemgren.models;
public class Jovem {
    private String nome;
    private int pontos;
    private boolean monitoria;

    public Jovem(String nome) {
        this.nome = nome;
        this.pontos = 0;
        this.monitoria = false;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void adicionarPontos(int valor) {
        pontos += valor;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void setMonitor(boolean monitor) {
        this.monitoria = monitor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getMonitoria() {
        return monitoria;
    }
}
