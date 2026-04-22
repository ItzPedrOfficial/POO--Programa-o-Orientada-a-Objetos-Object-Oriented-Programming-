package AI;
// ============================================================
// Ficheiro: Lampada.java
// Secção do enunciado: Secção 2 - Os Dispositivos
// Descrição: Representa uma lâmpada conectada. Além dos atributos
//            base de Dispositivo, possui intensidade de luminosidade.
//            "as lâmpadas possibilitam que se defina a intensidade
//            da luminosidade" (Secção 2).
// ============================================================

public class Lampada extends Dispositivo {

    // ---- Atributos específicos (Secção 2) ----
    private int intensidade;  // 0 a 100 (%)

    // ---- Construtores ----

    public Lampada() {
        super();
        this.intensidade = 0;
    }

    public Lampada(String id, String marca, String modelo, int consumo, int intensidade) {
        super(id, marca, modelo, consumo);
        this.intensidade = intensidade;
    }

    public Lampada(Lampada l) {
        super(l);
        this.intensidade = l.intensidade;
    }

    // ---- Getters / Setters ----

    public int getIntensidade() {
        return intensidade;
    }

    public int setIntensidade(int intensidade) {
        this.intensidade = intensidade;
        return 0;
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Lampada l = (Lampada) o;
        return intensidade == l.intensidade;
    }

    @Override
    public String toString() {
        return "Lampada{" + super.toString() +
                ", intensidade=" + intensidade + "%" +
                '}';
    }

    @Override
    public Lampada clone() {
        return new Lampada(this);
    }
}
