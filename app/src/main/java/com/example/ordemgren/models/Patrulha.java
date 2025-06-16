package com.example.ordemgren.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma Patrulha.
 * Contém nome, pontuação da patrulha (atividades em grupo)
 * e lista de jovens com suas pontuações individuais.
 */
public class Patrulha {
    private String nome;
    private int pontosPatrulha;
    private List<Jovem> jovens;

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
        if (jovens.size() < 8) {
            jovens.add(jovem);
        }
    }

    public void adicionarPontosPatrulha(int pontos) {
        this.pontosPatrulha += pontos;
    }

    public int getPontosPatrulha() {
        return pontosPatrulha;
    }

    public int getPontuacaoTotal() {
        int totalJovens = 0;
        for (Jovem jovem : jovens) {
            totalJovens += jovem.getPontos();
        }
        return pontosPatrulha + totalJovens;
    }
}
