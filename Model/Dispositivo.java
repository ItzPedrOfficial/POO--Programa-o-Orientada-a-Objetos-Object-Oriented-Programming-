package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Dispositivo implements Serializable, Operavel {
    private String id;
    private String marca;
    private String modelo;
    private int consumo;
    private boolean ligado;
    private int ativacoes;
    private long tempoLigado;
    private LocalDateTime ultimaVezLigado;

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
        this.ativacoes = 0;
        this.tempoLigado = 0;
        this.ultimaVezLigado = null;
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
        this.ativacoes = 0;
        this.tempoLigado = 0;
        this.ultimaVezLigado = null;
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
        this.ativacoes = dis.getAtivacoes();
        this.tempoLigado = dis.getTempoLigado();
        this.ultimaVezLigado = dis.getUltimaVezLigado();
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

    public int getAtivacoes() {
        return ativacoes;
    }

    public void setAtivacoes(int ativacoes) {
        this.ativacoes = ativacoes;
    }

    public long getTempoLigado() {
        return tempoLigado;
    }

    public void setTempoLigado(long tempoLigado) {
        this.tempoLigado = tempoLigado;
    }

    public LocalDateTime getUltimaVezLigado() {
        return ultimaVezLigado;
    }

    public void setUltimaVezLigado(LocalDateTime ultimaVezLigado) {
        this.ultimaVezLigado = ultimaVezLigado;
    }

    // --- Comportamentos ---

    /**
     * Liga o dispositivo se estiver desligado e desliga se estiver ligado
     */
    public void toggle(LocalDateTime agora){
        if (this.ligado && this.ultimaVezLigado != null) {
            this.tempoLigado += ChronoUnit.MINUTES.between(ultimaVezLigado, agora);
        } else {
            ativacoes++;
            ultimaVezLigado = agora;
        }
        this.ligado = !this.ligado;
    }

    /**
     * Liga o dispositivo
     */
    public void ligar(LocalDateTime agora){
        if (!ligado) {
            ativacoes++;
            ultimaVezLigado = agora;
        }
        this.ligado = true;
    }

    /**
     * Desliga o dispositivo
     */
    public void desligar(LocalDateTime agora){
        if (this.ligado && this.ultimaVezLigado != null) {
            this.tempoLigado += ChronoUnit.MINUTES.between(ultimaVezLigado, agora);
        }
        this.ligado = false;
    }

    public void executarOperacao(LocalDateTime agora, String operacao, Object valor) {
        switch (operacao) {
            case "ligar" -> ligar(agora);
            case "desligar" -> desligar(agora);
            case "toggle" -> toggle(agora);
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
        sb.append("Id: ").append(id).append("\n");
        sb.append("Marca: ").append(marca).append("\n");
        sb.append("Modelo: ").append(modelo).append("\n");
        sb.append("Consumo: ").append(consumo).append("\n");
        sb.append("Ligado: ").append(ligado);
        return sb.toString();
    }

    @Override
    public Dispositivo clone(){
        return new Dispositivo(this);
    }
}
