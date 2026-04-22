package AI;
// ============================================================
// Ficheiro: LampadaColorida.java
// Secção do enunciado: Secção 2 - Os Dispositivos
// Descrição: Representa uma lâmpada com cor configurável.
//            "algumas lâmpadas [possibilitam] a cor da luz
//            (numa escala que vai tipicamente dos 2700K até 4000K)"
//            (Secção 2). Herda de Lampada conforme diagrama UML.
// ============================================================

public class LampadaColorida extends Lampada {

    // ---- Atributos específicos (Secção 2) ----
    private String cor;   // ex: "2700K", "4000K"

    // ---- Construtores ----

    public LampadaColorida() {
        super();
        this.cor = "2700K";
    }

    public LampadaColorida(String id, String marca, String modelo,
                           int consumo, int intensidade, String cor) {
        super(id, marca, modelo, consumo, intensidade);
        this.cor = cor;
    }

    public LampadaColorida(LampadaColorida lc) {
        super(lc);
        this.cor = lc.cor;
    }

    // ---- Getters / Setters ----

    public String getCor() {
        return cor;
    }

    /** setIntensidade(cor) conforme assinatura no diagrama UML */
    public int setIntensidade(String cor) {
        this.cor = cor;
        return 0;
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        LampadaColorida lc = (LampadaColorida) o;
        return cor.equals(lc.cor);
    }

    @Override
    public String toString() {
        return "LampadaColorida{" + super.toString() +
                ", cor='" + cor + '\'' +
                '}';
    }

    @Override
    public LampadaColorida clone() {
        return new LampadaColorida(this);
    }
}
