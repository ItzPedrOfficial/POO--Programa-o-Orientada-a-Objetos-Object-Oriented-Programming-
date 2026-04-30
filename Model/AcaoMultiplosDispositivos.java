package Model;

import java.util.HashSet;
import java.util.Set;

public class AcaoMultiplosDispositivos extends Acao{
    private Set<String> dispositivos;

    // --- Construtores ---

    public AcaoMultiplosDispositivos() {
        super();
        this.dispositivos = new HashSet<>(dispositivos);
    }
    
    public AcaoMultiplosDispositivos(Set<String> dispositivos, String operacao, int valor) {
        super(operacao, valor);
        this.dispositivos = dispositivos;
    }

    public AcaoMultiplosDispositivos(AcaoMultiplosDispositivos ad) {
        super(ad);
        this.dispositivos = ad.getDispositivos();
    }
    
    // --- Getters e Setters ---
    
    public void addDispositivo(String dispositivo) {
        this.dispositivos.add(dispositivo);
    }

    public void removeDispositivo(String dispositivo) {
        this.dispositivos.remove(dispositivo);
    }

    public Set<String> getDispositivos() {
        return new HashSet<>(this.dispositivos);
    }

    public void setDispositivos(Set<String> dispositivos) {
        this.dispositivos = new HashSet<>(dispositivos);
    }
    
    // --- Overrides de Object ---
    
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        AcaoMultiplosDispositivos ad = (AcaoMultiplosDispositivos) o;
        
        return super.equals(ad) &&
               this.dispositivos.equals(ad.getDispositivos());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Ids dos Dispositivos: ").append(dispositivos.toString());
        return sb.toString();
    }

    @Override
    public AcaoMultiplosDispositivos clone(){
        return new AcaoMultiplosDispositivos(this);
    }

}
