package Model;

import java.time.LocalDateTime;

public class Lampada extends Dispositivo {
    private int luminosidade;

    // --- Construtores ---

    public Lampada(){
        super();
        this.luminosidade = 100;
    }

    public Lampada(String id, String marca, String modelo, int consumo){
        super(id, marca, modelo, consumo);
        this.luminosidade = 100;
    }

    public Lampada(Lampada lamp){
        super(lamp);
        this.luminosidade = 100;
    }

    // --- Getters e Setters ---

    public int getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(int luminosidade) {
        this.luminosidade = luminosidade;
    }

    public void executarOperacao(LocalDateTime agora, String operacao, Object valor) {  
        switch (operacao) {
            case "setLuminosidade" -> setLuminosidade((int) valor);
            default -> super.executarOperacao(agora, operacao, valor);
        }
    }

    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Lampada lampada = (Lampada) o;
        
        return super.equals(lampada) &&
               this.luminosidade == lampada.getLuminosidade();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Luminosidade: ").append(luminosidade);
        return sb.toString();
    }

    @Override
    public Lampada clone(){
        return new Lampada(this);
    }
}
