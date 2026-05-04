package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Divisao implements Serializable{
    private String nome;
    private Set<String> dispositivos;

    public Divisao(){
        this.nome = "";
        this.dispositivos = new HashSet<>();
    }

    public Divisao(String nome, Set<String> dispositivos){
        this.nome = nome;
        this.dispositivos = new HashSet<>(dispositivos);
    }

    public Divisao(Divisao div){
        this.nome = div.getNome();
        this.dispositivos = div.getDispositivos();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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
        
        Divisao divisao = (Divisao) o;
        
        return this.nome.equals(divisao.getNome()) &&
               this.dispositivos.equals(divisao.getDispositivos());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append("\n");
        sb.append(dispositivos.toString());
        return sb.toString();
    }

    @Override
    public Divisao clone(){
        return new Divisao(this);
    }

}
