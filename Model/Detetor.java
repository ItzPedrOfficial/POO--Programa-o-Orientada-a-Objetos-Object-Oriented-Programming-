package Model;

import java.time.LocalDateTime;

public class Detetor extends Dispositivo {
    private boolean aDetetar;

    // --- Construtores ---

    public Detetor(){
        super();
        this.aDetetar = false;
    }

    public Detetor(String id, String marca, String modelo, int consumo){
        super(id, marca, modelo, consumo);
        this.aDetetar = false;
    }

    public Detetor(Detetor col){
        super(col);
        this.aDetetar = false;
    }

    // --- Getters e Setters ---

    public boolean isADetetar() {
        return aDetetar;
    }

    public void setADetetar(boolean aDetetar) {
        this.aDetetar = aDetetar;
    }

    public void executarOperacao(LocalDateTime agora, String operacao, Object valor) {  
        switch (operacao) {
            case "setADetetar" -> setADetetar((boolean) valor);
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
        
        Detetor detetor = (Detetor) o;
        
        return super.equals(detetor) &&
               this.aDetetar == detetor.isADetetar();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("A Detetar: ").append(aDetetar);
        return sb.toString();
    }

    @Override
    public Detetor clone(){
        return new Detetor(this);
    }
}
