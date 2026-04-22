// ============================================================
// Ficheiro: Automacao.java
// Secção do enunciado: Secção 4 - As Automações
// Descrição: Representa uma automação associada a uma casa.
//            "Uma automação é assim a verificação de uma determinada
//            condição e a descrição da activação de dispositivos a
//            partir da verificação dessa condição." (Secção 4)
//            Requisito 8.3: "criar, pelo menos, duas automações e
//            vê-las a serem activadas"
// ============================================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Automacao implements Serializable {

    // ---- Atributos (Secção 4) ----
    private String id;
    private String nome;
    private String descricao;

    /**
     * Condição da automação em formato textual.
     * Ex: "pluviosidade > 5" ou "luminosidade < 30"
     * Secção 4: "caso detecte que está a chover [...] fechar as cortinas"
     */
    private String condicao;

    /**
     * Lista de acções a executar quando a condição é verdadeira.
     * Cada acção é um par (dispositivoId, operacao).
     * Secção 4: "a descrição da activação de dispositivos"
     */
    private List<AcaoDispositivo> acoes;

    private boolean activa;

    // ---- Construtores ----

    public Automacao() {
        this.id = "";
        this.nome = "";
        this.descricao = "";
        this.condicao = "";
        this.acoes = new ArrayList<>();
        this.activa = true;
    }

    public Automacao(String id, String nome, String descricao, String condicao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.condicao = condicao;
        this.acoes = new ArrayList<>();
        this.activa = true;
    }

    public Automacao(Automacao a) {
        this.id = a.id;
        this.nome = a.nome;
        this.descricao = a.descricao;
        this.condicao = a.condicao;
        this.acoes = new ArrayList<>(a.acoes);
        this.activa = a.activa;
    }

    // ---- Getters / Setters ----

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCondicao() { return condicao; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public List<AcaoDispositivo> getAcoes() { return new ArrayList<>(acoes); }

    public void adicionarAcao(AcaoDispositivo acao) {
        acoes.add(acao);
    }

    /**
     * Verifica se a condição está satisfeita e executa as acções.
     * Secção 4: verificação da condição e activação dos dispositivos.
     * A lógica real de avaliação da condição depende do contexto/sensores.
     *
     * @param valorSensor valor actual do sensor relevante
     * @return true se a automação foi disparada
     */
    public boolean avaliarEExecutar(double valorSensor) {
        // Implementação simplificada - a lógica real
        // dependerá do tipo de condição e dos sensores disponíveis
        return activa;
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Automacao)) return false;
        Automacao a = (Automacao) o;
        return Objects.equals(id, a.id);
    }

    @Override
    public String toString() {
        return "Automacao{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", condicao='" + condicao + '\'' +
                ", activa=" + activa +
                ", numAcoes=" + acoes.size() +
                '}';
    }

    public Automacao clone() {
        return new Automacao(this);
    }
}
