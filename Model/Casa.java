package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Casa implements Serializable{
    private String id;
    private String morada;
    private Map<String, Dispositivo> dispositivos;
    private Map<String, Divisao> divisoes;

    // --- Construtores ---

    public Casa() {
        this.id = "";
        this.morada = "";
        this.dispositivos = new HashMap<>();
        this.divisoes = new HashMap<>();
    }

    public Casa(String id, String morada, Map<String, Dispositivo> dispositivos, Map<String, Divisao> divisoes) {
        this.id = id;
        this.morada = morada;
        this.dispositivos = dispositivos.values().stream()
                                                 .map(Dispositivo::clone)
                                                 .collect(Collectors.toMap(d -> d.getId(), d -> d));
        this.divisoes = divisoes.values().stream()
                                             .map(Divisao::clone)
                                             .collect(Collectors.toMap(d -> d.getNome(), d -> d));
    }

    public Casa(Casa casa) {
        this.id = casa.getId();
        this.morada = casa.getMorada();
        this.dispositivos = casa.getDispositivos();
        this.divisoes = casa.getDivisoes();
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public Map<String, Dispositivo> getDispositivos() {
        return dispositivos.values().stream()
                                    .map(Dispositivo::clone)
                                    .collect(Collectors.toMap(d -> d.getId(), d -> d));
    }

    public void setDispositivos(Map<String, Dispositivo> dispositivos) {
        this.dispositivos = dispositivos.values().stream()
                                                 .map(Dispositivo::clone)
                                                 .collect(Collectors.toMap(d -> d.getId(), d -> d));
    }

    public Map<String, Divisao> getDivisoes() {
        return divisoes.values().stream()
                                .map(Divisao::clone)
                                .collect(Collectors.toMap(d -> d.getNome(), d -> d));
    }

    public void setDivisoes(Map<String, Divisao> divisoes) {
        this.divisoes = divisoes.values().stream()
                                         .map(Divisao::clone)
                                         .collect(Collectors.toMap(d -> d.getNome(), d -> d));
    }
    
    // --- Adds e Removes ---

    public void addDispositivo(String id, String marca, String modelo, int consumo){
        this.dispositivos.put(id, new Dispositivo(id, marca, modelo, consumo));
    }

    public void addDispositivo(Dispositivo dispositivo){
        this.dispositivos.put(dispositivo.getId(), dispositivo.clone());
    }

    public void removeDispositivo(String id){
        this.dispositivos.remove(id);
    }


    public void addDivisao(String nome, Set<String> dispositivos) {
        this.divisoes.put(nome, new Divisao(nome, dispositivos));
    }

    public void addDivisao(Divisao divisao) {
        this.divisoes.put(divisao.getNome(), divisao.clone());
    }

    public void removeDivisao(String nome) {
        this.divisoes.remove(nome);
    }

    // --- Overrides de Object ---
    
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Casa casa = (Casa) o;
        
        return this.id.equals(casa.getId()) &&
               this.morada.equals(casa.getMorada()) &&
               this.dispositivos.equals(casa.getDispositivos()) &&
               this.divisoes.equals(casa.getDivisoes());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("|");
        sb.append(morada).append("|");
        sb.append(dispositivos.toString()).append("|");
        sb.append(divisoes.toString());
        return sb.toString();
    }

    @Override
    public Casa clone(){
        return new Casa(this);
    }

}
