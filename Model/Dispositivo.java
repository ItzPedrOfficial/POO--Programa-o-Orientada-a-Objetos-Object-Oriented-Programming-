package Model;

import java.io.Serializable;

public class Dispositivo implements Serializable, Operavel {
    private String id;
    private String marca;
    private String modelo;
    private int consumo;
    private boolean ligado;

    // --- Construtores ---
    
    /**
     * Construtor vazio
     */
    public Dispositivo(){
        this.id = "";
        this.marca = "";
        this.modelo = "";
        this.consumo = 0;
        this.ligado = false;
    }

    /**
     * Construtor parametrizado
     * 
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     */
    public Dispositivo(String id, String marca, String modelo, int consumo){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo = consumo;
        this.ligado = false;
    }

    /**
     * Construtor de cópia
     * 
     * @param dis
     */
    public Dispositivo(Dispositivo dis){
        this.id = dis.getId();
        this.marca = dis.getMarca();
        this.modelo = dis.getModelo();
        this.consumo = dis.getConsumo();
        this.ligado = false;
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public boolean isLigado() {
        return ligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    // --- Comportamentos ---

    /**
     * Liga o dispositivo se estiver desligado e desliga se estiver ligado
     */
    public void toggle(){
        this.ligado = !this.ligado;
    }

    /**
     * Liga o dispositivo
     */
    public void ligar(){
        this.ligado = true;
    }

    /**
     * Desliga o dispositivo
     */
    public void desligar(){
        this.ligado = false;
    }

    public void executarOperacao(String operacao, int valor) {
        switch (operacao) {
            case "ligar" -> ligar();
            case "desligar" -> desligar();
            case "toggle" -> toggle();
        }
    }

    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Dispositivo dispositivo = (Dispositivo) o;
        
        return this.id.equals(dispositivo.getId()) &&
        this.marca.equals(dispositivo.getMarca()) &&
        this.modelo.equals(dispositivo.getModelo()) &&
        this.consumo == dispositivo.getConsumo() &&
        this.ligado == dispositivo.isLigado();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(id).append(" | ");
        sb.append("Marca: ").append(marca).append(" | ");
        sb.append("Modelo: ").append(modelo).append(" | ");
        sb.append("Consumo: ").append(consumo).append(" | ");
        sb.append("Ligado: ").append(ligado);
        return sb.toString();
    }

    @Override
    public Dispositivo clone(){
        return new Dispositivo(this);
    }
}
