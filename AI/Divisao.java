package AI;
// ============================================================
// Ficheiro: Divisao.java
// Secção do enunciado: Secção 1 e 7 - Introdução / Funcionamento
// Descrição: Representa uma divisão de uma casa (ex: sala jantar,
//            cozinha, quarto, garagem). "Os utilizadores dividem a
//            casa em divisões [...] e associam a essas divisões os
//            seus dispositivos conectados." (Secção 1)
//            Requisito 8.1: "criar utilizadores, casas, dispositivos
//            e associá-los às divisões das casas."
// ============================================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Divisao implements Serializable {

    // ---- Atributos ----
    private String id;
    private String nome;  // ex: "Sala Estar", "Cozinha", "Garagem"

    /**
     * Dispositivos associados a esta divisão.
     * Secção 7, ponto 2: "deverá ser possível adicionar um dispositivo
     * a uma divisão de uma casa"
     */
    private List<Dispositivo> dispositivos;

    // ---- Construtores ----

    public Divisao() {
        this.id = "";
        this.nome = "";
        this.dispositivos = new ArrayList<>();
    }

    public Divisao(String id, String nome) {
        this.id = id;
        this.nome = nome;
        this.dispositivos = new ArrayList<>();
    }

    public Divisao(Divisao d) {
        this.id = d.id;
        this.nome = d.nome;
        this.dispositivos = new ArrayList<>();
        for (Dispositivo disp : d.dispositivos) {
            this.dispositivos.add(disp.clone());
        }
    }

    // ---- Getters / Setters ----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Dispositivo> getDispositivos() {
        List<Dispositivo> copia = new ArrayList<>();
        for (Dispositivo d : dispositivos) copia.add(d.clone());
        return copia;
    }

    // ---- Comportamento ----

    public void adicionarDispositivo(Dispositivo d) {
        dispositivos.add(d.clone());
    }

    public boolean removerDispositivo(String dispositivoId) {
        return dispositivos.removeIf(d -> d.getId().equals(dispositivoId));
    }

    public Dispositivo getDispositivo(String dispositivoId) {
        return dispositivos.stream()
                .filter(d -> d.getId().equals(dispositivoId))
                .findFirst()
                .map(Dispositivo::clone)
                .orElse(null);
    }

    public int numeroDispositivos() {
        return dispositivos.size();
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Divisao)) return false;
        Divisao d = (Divisao) o;
        return Objects.equals(id, d.id) && Objects.equals(nome, d.nome);
    }

    @Override
    public String toString() {
        return "Divisao{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", numDispositivos=" + dispositivos.size() +
                '}';
    }

    public Divisao clone() {
        return new Divisao(this);
    }
}
