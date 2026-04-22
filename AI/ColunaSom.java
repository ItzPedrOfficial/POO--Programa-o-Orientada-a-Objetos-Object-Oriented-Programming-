package AI;
// ============================================================
// Ficheiro: ColunaSom.java
// Secção do enunciado: Secção 2 - Os Dispositivos
// Descrição: Representa uma coluna de som conectada. Possui controlo
//            de volume. "As colunas de som, além de outras características
//            permitem regular a intensidade do volume" (Secção 2).
//            Herda directamente de Dispositivo conforme diagrama UML.
// ============================================================

public class ColunaSom extends Dispositivo {

    // ---- Atributos específicos (Secção 2) ----
    private int volume;  // 0 a 100

    // ---- Construtores ----

    public ColunaSom() {
        super();
        this.volume = 0;
    }

    public ColunaSom(String id, String marca, String modelo, int consumo, int volume) {
        super(id, marca, modelo, consumo);
        this.volume = volume;
    }

    public ColunaSom(ColunaSom cs) {
        super(cs);
        this.volume = cs.volume;
    }

    // ---- Getters / Setters ----

    public int getVolume() {
        return volume;
    }

    public int setVolume(int volume) {
        this.volume = volume;
        return 0;
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        ColunaSom cs = (ColunaSom) o;
        return volume == cs.volume;
    }

    @Override
    public String toString() {
        return "ColunaSom{" + super.toString() +
                ", volume=" + volume +
                '}';
    }

    @Override
    public ColunaSom clone() {
        return new ColunaSom(this);
    }
}
