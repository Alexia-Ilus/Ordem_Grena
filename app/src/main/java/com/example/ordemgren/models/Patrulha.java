package com.example.ordemgren.models;

import java.util.ArrayList;
import java.util.List;

public class Patrulha {
    private String nome;
    private List<Jovem> jovens;
    private int pontosPatrulha;

    public Patrulha(String nome) {
        this.nome = nome;
        this.jovens = new ArrayList<>();
        this.pontosPatrulha = 0;
    }

    public String getNome() {
        return nome;
    }

    public List<Jovem> getJovens() {
        return jovens;
    }

    public void adicionarJovem(Jovem jovem) {
        jovens.add(jovem);
    }

    public void adicionarPontosPatrulha(int pontos) {
        pontosPatrulha += pontos;
    }

    public void setPontosPatrulha(int pontos) {
        this.pontosPatrulha = pontos;
    }

    public int getPontosPatrulha() {
        return pontosPatrulha;
    }

}
