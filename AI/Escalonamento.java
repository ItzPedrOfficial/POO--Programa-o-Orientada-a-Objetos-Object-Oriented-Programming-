package AI;
// ============================================================
// Ficheiro: Escalonamento.java
// Secção do enunciado: Secção 5 - Os Escalonamentos
// Descrição: Representa uma regra temporal que actua sobre dispositivos.
//            "Podemos definir que uma determinada luz fica ligada entre
//            a hora HH:mm e a hora HH:mm, ou então que todas as manhãs,
//            pelas HH:mm, as cortinas são abertas." (Secção 5)
//            Requisito 8.3: "definir, pelo menos, quatro escalonamentos
//            e testá-los"
// ============================================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Escalonamento implements Serializable {

    /** Tipo de recorrência do escalonamento */
    public enum Recorrencia {
        UMA_VEZ,
        DIARIO,
        SEMANAL
    }

    // ---- Atributos (Secção 5) ----
    private String id;
    private String nome;

    /** Hora de início no formato HH:mm */
    private String horaInicio;

    /** Hora de fim no formato HH:mm (pode ser null para acções pontuais) */
    private String horaFim;

    private Recorrencia recorrencia;

    /**
     * Lista de acções a executar quando o escalonamento é activado.
     * Secção 5: "que se actuem sobre os dispositivos"
     */
    private List<AcaoDispositivo> acoes;

    private boolean activo;

    // ---- Construtores ----

    public Escalonamento() {
        this.id = "";
        this.nome = "";
        this.horaInicio = "00:00";
        this.horaFim = null;
        this.recorrencia = Recorrencia.DIARIO;
        this.acoes = new ArrayList<>();
        this.activo = true;
    }

    public Escalonamento(String id, String nome, String horaInicio,
                         String horaFim, Recorrencia recorrencia) {
        this.id = id;
        this.nome = nome;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.recorrencia = recorrencia;
        this.acoes = new ArrayList<>();
        this.activo = true;
    }

    public Escalonamento(Escalonamento e) {
        this.id = e.id;
        this.nome = e.nome;
        this.horaInicio = e.horaInicio;
        this.horaFim = e.horaFim;
        this.recorrencia = e.recorrencia;
        this.acoes = new ArrayList<>(e.acoes);
        this.activo = e.activo;
    }

    // ---- Getters / Setters ----

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFim() { return horaFim; }
    public Recorrencia getRecorrencia() { return recorrencia; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public List<AcaoDispositivo> getAcoes() { return new ArrayList<>(acoes); }

    public void adicionarAcao(AcaoDispositivo acao) {
        acoes.add(acao);
    }

    /**
     * Verifica se o escalonamento deve ser activado para a hora dada.
     * Secção 7: "é necessário que de alguma forma simulem a passagem do tempo"
     *
     * @param horaActual hora actual no formato HH:mm
     * @return true se o escalonamento deve ser activado
     */
    public boolean deveActivar(String horaActual) {
        return activo && horaActual.equals(horaInicio);
    }

    /**
     * Verifica se o escalonamento deve ser desactivado para a hora dada
     * (usado em escalonamentos com horaFim definida).
     */
    public boolean deveDesactivar(String horaActual) {
        return activo && horaFim != null && horaActual.equals(horaFim);
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Escalonamento)) return false;
        Escalonamento e = (Escalonamento) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public String toString() {
        return "Escalonamento{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFim='" + (horaFim != null ? horaFim : "N/A") + '\'' +
                ", recorrencia=" + recorrencia +
                ", activo=" + activo +
                '}';
    }

    public Escalonamento clone() {
        return new Escalonamento(this);
    }
}
