package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Escalonamento implements Serializable{
    private String nome;
    private Set<Acao> acoes;
    private LocalTime inicio;
    private LocalTime fim;

    // --- Construtores ---

    public Escalonamento() {
        this.nome = "";
        this.acoes = new HashSet<>();
        this.inicio = LocalTime.now();
        this.fim = LocalTime.now();
    }

    public Escalonamento(String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim) {
        this.nome = nome;
        this.acoes = new HashSet<>(acoes);
        this.inicio = inicio;
        this.fim = fim;
    }

    public Escalonamento(Escalonamento esc) {
        this.nome = esc.getNome();
        this.acoes = esc.getAcoes();
        this.inicio = esc.getInicio();
        this.fim = esc.getFim();
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

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }
    
    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }

    // --- Comportamentos ---
    
    public abstract boolean deveExecutar(LocalDateTime agora);

    // --- Overrides de Object ---
    
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Escalonamento escalonamento = (Escalonamento) o;
        
        return this.nome.equals(escalonamento.getNome()) &&
               this.acoes.equals(escalonamento.getAcoes()) &&
               this.inicio.equals(escalonamento.getInicio()) &&
               this.fim.equals(escalonamento.getFim());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Acoes: ").append(acoes.toString()).append("\n");
        sb.append("Inicio: ").append(inicio.toString()).append("\n");
        sb.append("Fim: ").append(fim.toString());
        return sb.toString();
    }

    @Override
    public abstract Escalonamento clone();
}
