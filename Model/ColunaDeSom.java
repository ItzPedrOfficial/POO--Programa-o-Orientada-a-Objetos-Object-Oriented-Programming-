package Model;

import java.time.LocalDateTime;

public class ColunaDeSom extends Dispositivo{
    private int volume;

    // --- Construtores ---

    public ColunaDeSom(){
        super();
        this.volume = 75;
    }

    public ColunaDeSom(String id, String marca, String modelo, int consumo){
        super(id, marca, modelo, consumo);
        this.volume = 75;
    }

    public ColunaDeSom(ColunaDeSom col){
        super(col);
        this.volume = 75;
    }

    // --- Getters e Setters ---

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void executarOperacao(LocalDateTime agora, String operacao, Object valor) {  
        switch (operacao) {
            case "setVolume" -> setVolume((int) valor);
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
        
        ColunaDeSom coluna = (ColunaDeSom) o;
        
        return super.equals(coluna) &&
               this.volume == coluna.getVolume();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Volume: ").append(volume);
        return sb.toString();
    }

    @Override
    public ColunaDeSom clone(){
        return new ColunaDeSom(this);
    }
}
