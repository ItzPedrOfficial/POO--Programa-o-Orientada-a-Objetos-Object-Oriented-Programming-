// ============================================================
// Ficheiro: Casa.java
// Secção do enunciado: Secção 1 (Introdução), Secção 7 e Secção 8.1
// Descrição: Representa uma casa no sistema DomusControl.
//            "Os utilizadores dividem a casa em divisões [...] e
//            associam a essas divisões os seus dispositivos." (Secção 1)
//            Requisito 8.2: "saber qual é a casa que mais consome"
// ============================================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Casa implements Serializable {

    // ---- Atributos ----
    private String id;
    private String morada;
    private String proprietarioUsername; // dono/administrador principal

    /**
     * Divisões da casa.
     * Secção 8.1: "criar utilizadores, casas, dispositivos e associá-los
     * às divisões das casas"
     */
    private List<Divisao> divisoes;

    /**
     * Usernames dos utilizadores com acesso (usufrutuários).
     * Secção 3: "Um utilizador do DomusControl terá acesso a todas as
     * casas de que for proprietário ou usufrutuário."
     */
    private List<String> utilizadoresIds;

    // ---- Construtores ----

    public Casa() {
        this.id = "";
        this.morada = "";
        this.proprietarioUsername = "";
        this.divisoes = new ArrayList<>();
        this.utilizadoresIds = new ArrayList<>();
    }

    public Casa(String id, String morada, String proprietarioUsername) {
        this.id = id;
        this.morada = morada;
        this.proprietarioUsername = proprietarioUsername;
        this.divisoes = new ArrayList<>();
        this.utilizadoresIds = new ArrayList<>();
    }

    public Casa(Casa c) {
        this.id = c.id;
        this.morada = c.morada;
        this.proprietarioUsername = c.proprietarioUsername;
        this.divisoes = new ArrayList<>();
        for (Divisao d : c.divisoes) this.divisoes.add(d.clone());
        this.utilizadoresIds = new ArrayList<>(c.utilizadoresIds);
    }

    // ---- Getters / Setters ----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }

    public String getProprietarioUsername() { return proprietarioUsername; }

    public List<Divisao> getDivisoes() {
        List<Divisao> copia = new ArrayList<>();
        for (Divisao d : divisoes) copia.add(d.clone());
        return copia;
    }

    // ---- Gestão de Divisões ----

    public void adicionarDivisao(Divisao d) {
        divisoes.add(d.clone());
    }

    public Divisao getDivisao(String divisaoId) {
        return divisoes.stream()
                .filter(d -> d.getId().equals(divisaoId))
                .findFirst()
                .map(Divisao::clone)
                .orElse(null);
    }

    // ---- Gestão de Utilizadores ----

    public void adicionarUtilizador(String username) {
        if (!utilizadoresIds.contains(username)) {
            utilizadoresIds.add(username);
        }
    }

    // ---- Estatísticas (Secção 8.2) ----

    /**
     * Calcula o consumo total da casa (soma de todos os dispositivos ligados).
     * Secção 8.2: "saber qual é a casa que mais consome"
     */
    public int calcularConsumoTotal() {
        int total = 0;
        for (Divisao d : divisoes) {
            for (Dispositivo disp : d.getDispositivos()) {
                if (disp.getEstado() == Estado.LIGADO) {
                    total += Integer.parseInt(disp.getConsumo());
                }
            }
        }
        return total;
    }

    /**
     * Retorna a divisão com mais dispositivos.
     * Secção 8.2: "quais as três divisões [...] que possuem mais dispositivos"
     */
    public List<Divisao> divisoesOrdenadasPorDispositivos() {
        List<Divisao> sorted = new ArrayList<>(divisoes);
        sorted.sort((a, b) -> b.numeroDispositivos() - a.numeroDispositivos());
        return sorted;
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Casa)) return false;
        Casa c = (Casa) o;
        return Objects.equals(id, c.id);
    }

    @Override
    public String toString() {
        return "Casa{" +
                "id='" + id + '\'' +
                ", morada='" + morada + '\'' +
                ", proprietario='" + proprietarioUsername + '\'' +
                ", numDivisoes=" + divisoes.size() +
                '}';
    }

    public Casa clone() {
        return new Casa(this);
    }
}
