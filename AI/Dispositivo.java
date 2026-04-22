package AI;
// ============================================================
// Ficheiro: Dispositivo.java
// Secção do enunciado: Secção 2 - Os Dispositivos
// Descrição: Classe base (abstrata) que representa qualquer dispositivo
//            conectado no sistema DomusControl. Todos os dispositivos
//            partilham: identificador, marca, modelo, consumo por hora
//            e estado. Conforme o diagrama UML fornecido.
// ============================================================

import java.io.Serializable;
import java.util.Objects;

public abstract class Dispositivo implements Serializable, Cloneable {

    // ---- Atributos (Secção 2) ----
    private String id;
    private String marca;
    private String modelo;
    private int consumo;       // consumo por hora em Wh
    private Estado estado;

    // ---- Construtores ----

    /** Construtor vazio */
    public Dispositivo() {
        this.id = "";
        this.marca = "";
        this.modelo = "";
        this.consumo = 0;
        this.estado = Estado.DESLIGADO;
    }

    /** Construtor parametrizado */
    public Dispositivo(String id, String marca, String modelo, int consumo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo = consumo;
        this.estado = Estado.DESLIGADO;
    }

    /** Construtor de cópia */
    public Dispositivo(Dispositivo disp) {
        this.id = disp.id;
        this.marca = disp.marca;
        this.modelo = disp.modelo;
        this.consumo = disp.consumo;
        this.estado = disp.estado;
    }

    // ---- Getters ----

    public String getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    /** getConsumo devolve String conforme diagrama UML */
    public String getConsumo() {
        return String.valueOf(consumo);
    }

    public Estado getEstado() {
        return estado;
    }

    // ---- Setters ----

    public int setId(String id) {
        this.id = id;
        return 0;
    }

    public int setMarca(String marca) {
        this.marca = marca;
        return 0;
    }

    public int setModelo(String modelo) {
        this.modelo = modelo;
        return 0;
    }

    /** setConsumo recebe String conforme diagrama UML */
    public int setConsumo(String consumo) {
        try {
            this.consumo = Integer.parseInt(consumo);
        } catch (NumberFormatException e) {
            return -1;
        }
        return 0;
    }

    public int setEstado(Estado estado) {
        this.estado = estado;
        return 0;
    }

    // ---- Comportamento (Secção 2) ----

    /**
     * Alterna o estado entre LIGADO e DESLIGADO.
     * Secção 7, ponto 3: "o utilizador deverá poder operar individualmente um dispositivo"
     */
    public void toggle() {
        if (this.estado == Estado.LIGADO) {
            this.estado = Estado.DESLIGADO;
        } else {
            this.estado = Estado.LIGADO;
        }
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dispositivo)) return false;
        Dispositivo d = (Dispositivo) o;
        return consumo == d.consumo
                && Objects.equals(id, d.id)
                && Objects.equals(marca, d.marca)
                && Objects.equals(modelo, d.modelo)
                && estado == d.estado;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id='" + id + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", consumo=" + consumo + "Wh" +
                ", estado=" + estado +
                '}';
    }

    /** clone abstrato - cada subclasse devolve o seu próprio tipo */
    @Override
    public abstract Dispositivo clone();
}
