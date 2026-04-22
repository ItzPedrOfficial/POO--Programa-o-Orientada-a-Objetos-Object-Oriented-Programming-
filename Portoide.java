// ============================================================
// Ficheiro: Portoide.java
// Secção do enunciado: Secção 2 - Os Dispositivos
// Descrição: Representa um portão de garagem (ou cortina) conectado.
//            "os portões de garagem permitem que se abra ou feche
//            totalmente o dispositivo ou que se permita apenas algum
//            grau de abertura" (Secção 2).
//            Herda directamente de Dispositivo conforme diagrama UML.
// ============================================================

public class Portoide extends Dispositivo {

    // ---- Atributos específicos (Secção 2) ----
    private int abertura;  // 0 (fechado) a 100 (totalmente aberto)

    // ---- Construtores ----

    public Portoide() {
        super();
        this.abertura = 0;
    }

    public Portoide(String id, String marca, String modelo, int consumo, int abertura) {
        super(id, marca, modelo, consumo);
        this.abertura = abertura;
    }

    public Portoide(Portoide p) {
        super(p);
        this.abertura = p.abertura;
    }

    // ---- Getters / Setters ----

    public int getAbertura() {
        return abertura;
    }

    public int setAbertura(int abertura) {
        this.abertura = abertura;
        return 0;
    }

    // ---- Comportamento específico (Secção 2) ----

    /** Abre totalmente o portão/cortina */
    public void abre() {
        this.abertura = 100;
        this.setEstado(Estado.LIGADO);
    }

    /** Fecha totalmente o portão/cortina */
    public void fecha() {
        this.abertura = 0;
        this.setEstado(Estado.DESLIGADO);
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Portoide p = (Portoide) o;
        return abertura == p.abertura;
    }

    @Override
    public String toString() {
        return "Portoide{" + super.toString() +
                ", abertura=" + abertura + "%" +
                '}';
    }

    @Override
    public Portoide clone() {
        return new Portoide(this);
    }
}
