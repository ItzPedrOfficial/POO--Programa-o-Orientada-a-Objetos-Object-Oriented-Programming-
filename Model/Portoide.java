package Model;

public class Portoide extends Dispositivo{
    private int abertura;

    // --- Construtores ---

    public Portoide(){
        super();
        this.abertura = 0;
    }

    public Portoide(String id, String marca, String modelo, int consumo){
        super(id, marca, modelo, consumo);
        this.abertura = 0;
    }

    public Portoide(Portoide por){
        super(por);
        this.abertura = 0;
    }

    // --- Getters e Setters ---

    public int getAbertura() {
        return abertura;
    }

    public void setAbertura(int abertura) {
        this.abertura = abertura;
    }

    public void executarOperacao(String operacao, Object valor) {  
        switch (operacao) {
            case "setAbertura" -> setAbertura((int) valor);
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
        
        Portoide portoide = (Portoide) o;
        
        return super.equals(portoide) &&
               this.abertura == portoide.getAbertura();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Abertura: ").append(abertura);
        return sb.toString();
    }

    @Override
    public Portoide clone(){
        return new Portoide(this);
    }
}
