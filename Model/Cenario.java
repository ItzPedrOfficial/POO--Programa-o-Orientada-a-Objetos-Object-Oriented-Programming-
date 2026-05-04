package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Cenario implements Serializable{
    private String nome;
    private Set<Acao> acoes;

    // --- Construtores ---

    public Cenario() {
        this.nome = "";
        this.acoes = new HashSet<>();
    }

    public Cenario(String nome, Set<Acao> acoes) {
        this.nome = nome;
        this.acoes = new HashSet<>(acoes);
    }

    public Cenario(Cenario cen) {
        this.nome = cen.getNome();
        this.acoes = cen.getAcoes();
    }

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        
        Cenario cenario = (Cenario) o;
        
        return this.nome.equals(cenario.getNome()) &&
               this.acoes.equals(cenario.getAcoes());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Acoes: ").append(acoes.toString());
        return sb.toString();
    }

    @Override
    public Cenario clone(){
        return new Cenario(this);
    }
}
