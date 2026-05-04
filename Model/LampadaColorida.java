package Model;

public class LampadaColorida extends Lampada{
    private String cor;

    // --- Construtores ---

    public LampadaColorida(){
        super();
        this.cor = "Branco";
    }

    public LampadaColorida(String id, String marca, String modelo, int consumo){
        super(id, marca, modelo, consumo);
        this.cor = "Branco";
    }

    public LampadaColorida(LampadaColorida lamp){
        super(lamp);
        this.cor = "Branco";
    }

    // --- Getters e Setters ---

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void executarOperacao(String operacao, Object valor) {  
        switch (operacao) {
            case "setCor" -> setCor((String) valor);
            default -> super.executarOperacao(operacao, valor);
        }
    }

    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        LampadaColorida lampada = (LampadaColorida) o;
        
        return super.equals(lampada) &&
               this.cor.equals(lampada.getCor());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Cor: ").append(cor);
        return sb.toString();
    }

    @Override
    public LampadaColorida clone(){
        return new LampadaColorida(this);
    }
}
