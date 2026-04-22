package AI;
// ============================================================
// Ficheiro: AcaoDispositivo.java
// Secção do enunciado: Secções 4, 5 e 6 (Automações, Escalonamentos e Cenários)
// Descrição: Representa uma acção a executar sobre um dispositivo,
//            usada em automações, escalonamentos e cenários.
//            Ex: ligar dispositivo X, definir volume de Y para 50.
// ============================================================

import java.io.Serializable;

public class AcaoDispositivo implements Serializable {

    // ---- Tipos de acção possíveis ----
    public enum TipoAcao {
        LIGAR,
        DESLIGAR,
        TOGGLE,
        SET_INTENSIDADE,
        SET_VOLUME,
        SET_ABERTURA,
        SET_COR,
        ABRIR,
        FECHAR
    }

    // ---- Atributos ----
    private String dispositivoId;
    private TipoAcao tipoAcao;
    private String valor;  // valor opcional (ex: "75" para intensidade)

    // ---- Construtores ----

    public AcaoDispositivo(String dispositivoId, TipoAcao tipoAcao) {
        this.dispositivoId = dispositivoId;
        this.tipoAcao = tipoAcao;
        this.valor = null;
    }

    public AcaoDispositivo(String dispositivoId, TipoAcao tipoAcao, String valor) {
        this.dispositivoId = dispositivoId;
        this.tipoAcao = tipoAcao;
        this.valor = valor;
    }

    // ---- Getters ----

    public String getDispositivoId() { return dispositivoId; }
    public TipoAcao getTipoAcao() { return tipoAcao; }
    public String getValor() { return valor; }

    @Override
    public String toString() {
        return "AcaoDispositivo{dispositivo='" + dispositivoId +
                "', acao=" + tipoAcao +
                (valor != null ? ", valor=" + valor : "") + '}';
    }
}
