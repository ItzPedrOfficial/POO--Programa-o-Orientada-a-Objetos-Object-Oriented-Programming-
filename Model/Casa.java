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
    private Map<String, Cenario> cenarios;

    // --- Construtores ---

    public Casa() {
        this.id = "";
        this.morada = "";
        this.dispositivos = new HashMap<>();
        this.divisoes = new HashMap<>();
        this.cenarios = new HashMap<>();
    }

    public Casa(String id, String morada, Map<String, Dispositivo> dispositivos, Map<String, Divisao> divisoes, Map<String, Cenario> cenarios) {
        this.id = id;
        this.morada = morada;
        this.dispositivos = dispositivos.values().stream()
                                                 .map(Dispositivo::clone)
                                                 .collect(Collectors.toMap(d -> d.getId(), d -> d));
        this.divisoes = divisoes.values().stream()
                                         .map(Divisao::clone)
                                         .collect(Collectors.toMap(d -> d.getNome(), d -> d));
        this.cenarios = cenarios.values().stream()
                                         .map(Cenario::clone)
                                         .collect(Collectors.toMap(c -> c.getNome(), c -> c));
    }

    public Casa(Casa casa) {
        this.id = casa.getId();
        this.morada = casa.getMorada();
        this.dispositivos = casa.getDispositivos();
        this.divisoes = casa.getDivisoes();
        this.cenarios = casa.getCenarios();
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

    public Map<String, Cenario> getCenarios() {
        return cenarios.values().stream()
                                .map(Cenario::clone)
                                .collect(Collectors.toMap(c -> c.getNome(), c -> c));
    }

    public void setCenarios(Map<String, Cenario> cenarios) {
        this.cenarios = cenarios.values().stream()
                                         .map(Cenario::clone)
                                         .collect(Collectors.toMap(c -> c.getNome(), c -> c));
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

    public void addCenario(String nome, Set<Acao> acoes) {
        this.cenarios.put(nome, new Cenario(nome, acoes));
    }

    public void addCenario(Cenario cenario) {
        this.cenarios.put(cenario.getNome(), cenario.clone());
    }

    public void removeCenario(String nome) {
        this.cenarios.remove(nome);
    }

    // --- Comportamentos ---

    public void toggle(String dispositivo) {
        this.dispositivos.get(dispositivo).toggle();
    }

    public void toggle(Set<String> dispositivos) {
        dispositivos.forEach(disp ->  this.dispositivos.get(disp).toggle());
    }
    
    public void ligar(String dispositivo) {
        this.dispositivos.get(dispositivo).ligar();
    }

    public void ligar(Set<String> dispositivos) {
        dispositivos.forEach(disp ->  this.dispositivos.get(disp).ligar());
    }

    public void desligar(String dispositivo) {
        this.dispositivos.get(dispositivo).desligar();
    }

    public void desligar(Set<String> dispositivos) {
        dispositivos.forEach(disp ->  this.dispositivos.get(disp).desligar());
    }

    public void executarOperacao(String dispositivo, String operacao, Object valor) {
        this.dispositivos.get(dispositivo).executarOperacao(operacao, valor);
    }

    public void executarOperacao(Set<String> dispositivos, String operacao, Object valor) {
        dispositivos.forEach(disp -> this.dispositivos.get(disp).executarOperacao(operacao, valor));
    }

    public void executarOperacao(AcaoDispositivo acao) {
        this.dispositivos.get(acao.getIdDispositivo()).executarOperacao(acao.getOperacao(), acao.getValor());
    }
    
    public void executarOperacao(AcaoMultiplosDispositivos acao) {
        acao.getDispositivos().forEach(disp -> this.dispositivos.get(disp).executarOperacao(acao.getOperacao(), acao.getValor()));
    }

    public void executarCenario(String cenario) {
        cenarios.get(cenario).getAcoes().forEach(a -> {
                            if (a instanceof AcaoDispositivo ad)
                                this.dispositivos.get(ad.getIdDispositivo()).executarOperacao(ad.getOperacao(), ad.getValor());
                            else if (a instanceof AcaoMultiplosDispositivos am)
                                am.getDispositivos().forEach(disp -> this.dispositivos.get(disp).executarOperacao(am.getOperacao(), am.getValor()));
                        });
    }
    
    public int quantosDispositivos() {
        return (int) this.dispositivos.values().stream()
                                      .count();
    }

    public int quantasDivisoes() {
        return (int) this.divisoes.values().stream()
                                  .count();
    }

    public int quantosCenarios() {
        return (int) this.cenarios.values().stream()
                                  .count();
    }

    public Dispositivo getDispositivo(String dispositivo) {
        return this.dispositivos.get(dispositivo).clone();
    }

    public Divisao getDivisao(String divisao) {
        return this.divisoes.get(divisao).clone();
    }

    public Cenario getCenario(String cenario) {
        return this.cenarios.get(cenario).clone();
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
               this.divisoes.equals(casa.getDivisoes()) &&
               this.cenarios.equals(casa.getCenarios());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(id).append("\n");
        sb.append("Morada: ").append(morada).append("\n");
        sb.append("Dispositivos: ").append(dispositivos.toString()).append("\n");
        sb.append("Divisoes: ").append(divisoes.toString()).append("\n");
        sb.append("Cenarios: ").append(cenarios.toString());
        return sb.toString();
    }

    @Override
    public Casa clone(){
        return new Casa(this);
    }

}
