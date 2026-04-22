// ============================================================
// Ficheiro: Cenario.java
// Secção do enunciado: Secção 6 - Os Cenários
// Descrição: Representa um cenário que executa acções em vários
//            dispositivos simultaneamente.
//            "A identificação de cenários possibilita a execução de
//            instruções numa série de dispositivos." (Secção 6)
//            Exemplos do enunciado:
//              - "Fora de Casa": desliga luzes, fecha cortinas, desliga colunas
//              - "Ver Cinema": reduz luzes da sala, aumenta volume
//            Requisito 8.3: "criar quatro cenários (sair de casa, jantar com
//            amigos, deitar e acordar) e testá-los"
// ============================================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cenario implements Serializable {

    // ---- Atributos (Secção 6) ----
    private String id;
    private String nome;           // ex: "Fora de Casa", "Ver Cinema"
    private String descricao;
    private String criadoPorUsername;

    /**
     * Lista de acções do cenário.
     * Secção 6: "execução de instruções numa série de dispositivos"
     */
    private List<AcaoDispositivo> acoes;

    // ---- Cenários predefinidos (Secção 8.3) ----
    public static final String SAIR_DE_CASA    = "Sair de Casa";
    public static final String JANTAR_AMIGOS   = "Jantar com Amigos";
    public static final String DEITAR          = "Deitar";
    public static final String ACORDAR         = "Acordar";

    // ---- Construtores ----

    public Cenario() {
        this.id = "";
        this.nome = "";
        this.descricao = "";
        this.criadoPorUsername = "";
        this.acoes = new ArrayList<>();
    }

    public Cenario(String id, String nome, String descricao, String criadoPorUsername) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.criadoPorUsername = criadoPorUsername;
        this.acoes = new ArrayList<>();
    }

    public Cenario(Cenario c) {
        this.id = c.id;
        this.nome = c.nome;
        this.descricao = c.descricao;
        this.criadoPorUsername = c.criadoPorUsername;
        this.acoes = new ArrayList<>(c.acoes);
    }

    // ---- Getters ----

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCriadoPorUsername() { return criadoPorUsername; }
    public List<AcaoDispositivo> getAcoes() { return new ArrayList<>(acoes); }

    // ---- Comportamento ----

    public void adicionarAcao(AcaoDispositivo acao) {
        acoes.add(acao);
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cenario)) return false;
        Cenario c = (Cenario) o;
        return Objects.equals(id, c.id);
    }

    @Override
    public String toString() {
        return "Cenario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", criadoPor='" + criadoPorUsername + '\'' +
                ", numAcoes=" + acoes.size() +
                '}';
    }

    public Cenario clone() {
        return new Cenario(this);
    }
}
