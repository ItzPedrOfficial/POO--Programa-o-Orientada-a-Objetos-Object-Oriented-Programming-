package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Automacao implements Serializable {
    private String nome;
    private Condicao condicao;
    private Set<Acao> acoes;

    // --- Construtores ---

    public Automacao() {
        this.nome = "";
        this.condicao = null;
        this.acoes = new HashSet<>();
    }

    public Automacao(String nome, Condicao condicao, Set<Acao> acoes) {
        this.nome = nome;
        this.condicao = condicao;
        this.acoes = new HashSet<>(acoes);
    }

    public Automacao(Automacao a) {
        this.nome = a.getNome();
        this.condicao = a.getCondicao();
        this.acoes = a.getAcoes();
    }

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Condicao getCondicao() {
        return condicao;
    }

    public void setCondicao(Condicao condicao) {
        this.condicao = condicao;
    }

    public Set<Acao> getAcoes() {
        return this.acoes.stream()
                         .map(Acao::clone)
                         .collect(Collectors.toSet());
    }

    public void setAcoes(Set<Acao> acoes) {
        this.acoes = acoes.stream()
                          .map(Acao::clone)
                          .collect(Collectors.toSet());
    }

    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Automacao a = (Automacao) o;
        return this.nome.equals(a.getNome()) &&
               this.acoes.equals(a.getAcoes());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Acoes: ").append(acoes.toString());
        return sb.toString();
    }

    @Override
    public Automacao clone() {
        return new Automacao(this);
    }
}