package Model;

public class AcaoDispositivo extends Acao{
    private String idDispositivo;

    // --- Construtores ---

    public AcaoDispositivo() {
        super();
        this.idDispositivo = "";
    }
    
    public AcaoDispositivo(String idDispositivo, String operacao, int valor) {
        super(operacao, valor);
        this.idDispositivo = idDispositivo;
    }

    public AcaoDispositivo(AcaoDispositivo ad) {
        super(ad);
        this.idDispositivo = ad.getIdDispositivo();
    }

    // --- Getters e Setters ---

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }
    
    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        AcaoDispositivo ad = (AcaoDispositivo) o;
        
        return super.equals(ad) &&
               this.idDispositivo.equals(ad.getIdDispositivo());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Id do Dispositivo: ").append(idDispositivo);
        return sb.toString();
    }

    @Override
    public AcaoDispositivo clone(){
        return new AcaoDispositivo(this);
    }
}
