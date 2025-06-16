package com.example.ordemgren.models;

/**
 * Classe que representa um jovem dentro de uma patrulha.
 * Cada jovem possui um nome e uma pontuação individual.
 */
public class Jovem {
    private String nome;
    private int pontos;

    public Jovem(String nome) {
        this.nome = nome;
        this.pontos = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }
}
