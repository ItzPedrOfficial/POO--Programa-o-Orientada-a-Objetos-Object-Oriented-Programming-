package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Automacao implements Serializable {
    private String nome;
    private String detetor;
    private Set<Acao> acoes;

    // --- Construtores ---

    public Automacao() {
        this.nome = "";
        this.detetor = "";
        this.acoes = new HashSet<>();
    }

    public Automacao(String nome, String detetor, Set<Acao> acoes) {
        this.nome = nome;
        this.detetor = detetor;
        this.acoes = new HashSet<>(acoes);
    }

    public Automacao(Automacao a) {
        this.nome = a.getNome();
        this.detetor = a.getDetetor();
        this.acoes = a.getAcoes();
    }

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetetor() {
        return detetor;
    }

    public void setDetetor(String detetor) {
        this.detetor = detetor;
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
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Automacao automacao = (Automacao) o;
        
        return this.nome.equals(automacao.getNome()) &&
               this.detetor.equals(automacao.getDetetor()) &&
               this.acoes.equals(automacao.getAcoes());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Dispositivo detetor: ").append(detetor).append("\n");
        sb.append("Acoes: ").append(acoes.toString());
        return sb.toString();
    }

    @Override
    public Automacao clone(){
        return new Automacao(this);
    }
}