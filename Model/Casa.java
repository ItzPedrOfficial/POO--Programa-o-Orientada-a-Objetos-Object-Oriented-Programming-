package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private Map<String, Automacao> automacoes;
    private Map<String, Escalonamento> escalonamentos;

    // --- Construtores ---

    public Casa() {
        this.id = "";
        this.morada = "";
        this.dispositivos = new HashMap<>();
        this.divisoes = new HashMap<>();
        this.cenarios = new HashMap<>();
        this.automacoes = new HashMap<>();
        this.escalonamentos = new HashMap<>();
    }

    public Casa(String id, String morada, Map<String, Dispositivo> dispositivos, Map<String, Divisao> divisoes, Map<String, Cenario> cenarios, Map<String, Automacao> automacoes, Map<String, Escalonamento> escalonamentos) {
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
        this.automacoes = automacoes.values().stream()
                                             .map(Automacao::clone)
                                             .collect(Collectors.toMap(c -> c.getNome(), c -> c));
        this.escalonamentos = escalonamentos.values().stream()
                                                    .map(Escalonamento::clone)
                                                    .collect(Collectors.toMap(e -> e.getNome(), e -> e));
    }

    public Casa(Casa casa) {
        this.id = casa.getId();
        this.morada = casa.getMorada();
        this.dispositivos = casa.getDispositivos();
        this.divisoes = casa.getDivisoes();
        this.cenarios = casa.getCenarios();
        this.automacoes = casa.getAutomacoes();
        this.escalonamentos = casa.getEscalonamentos();
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

    public Map<String, Automacao> getAutomacoes() {
        return automacoes.values().stream()
                                  .map(Automacao::clone)
                                  .collect(Collectors.toMap(a -> a.getNome(), a -> a));
    }
    
    public void setAutomacoes(Map<String, Automacao> automacoes) {
        this.automacoes = automacoes.values().stream()
                                             .map(Automacao::clone)
                                             .collect(Collectors.toMap(c -> c.getNome(), c -> c));
    }
    
    public Map<String, Escalonamento> getEscalonamentos() {
        return escalonamentos.values().stream()
                                    .map(Escalonamento::clone)
                                    .collect(Collectors.toMap(e -> e.getNome(), e -> e));
    }

    public void setEscalonamentos(Map<String, Escalonamento> escalonamentos) {
        this.escalonamentos = escalonamentos.values().stream()
                                                    .map(Escalonamento::clone)
                                                    .collect(Collectors.toMap(e -> e.getNome(), e -> e));
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


    public void addAutomacao(String nome, Set<Acao> acoes, String detetor) {
        this.automacoes.put(nome, new Automacao(nome, detetor, acoes));
    }

    public void addAutomacao(Automacao automacao) {
        this.automacoes.put(automacao.getNome(), automacao.clone());
    }

    public void removeAutomacao(String nome) {
        this.automacoes.remove(nome);
    }

    public void addEscalonamento(Escalonamento escalonamento) {
        this.escalonamentos.put(escalonamento.getNome(), escalonamento.clone());
    }

    public void removeEscalonamento(String nome) {
        this.escalonamentos.remove(nome);
    }

    // --- Comportamentos ---

    public void toggle(LocalDateTime agora, String dispositivo) {
        this.dispositivos.get(dispositivo).toggle(agora);
    }

    public void toggle(LocalDateTime agora, Set<String> dispositivos) {
        dispositivos.forEach(disp ->  this.dispositivos.get(disp).toggle(agora));
    }
    
    public void ligar(LocalDateTime agora, String dispositivo) {
        this.dispositivos.get(dispositivo).ligar(agora);
    }

    public void ligar(LocalDateTime agora, Set<String> dispositivos) {
        dispositivos.forEach(disp ->  this.dispositivos.get(disp).ligar(agora));
    }

    public void desligar(LocalDateTime agora, String dispositivo) {
        this.dispositivos.get(dispositivo).desligar(agora);
    }

    public void desligar(LocalDateTime agora, Set<String> dispositivos) {
        dispositivos.forEach(disp ->  this.dispositivos.get(disp).desligar(agora));
    }

    public void executarOperacao(LocalDateTime agora, String dispositivo, String operacao, Object valor) {
        this.dispositivos.get(dispositivo).executarOperacao(agora, operacao, valor);
    }

    public void executarOperacao(LocalDateTime agora, Set<String> dispositivos, String operacao, Object valor) {
        dispositivos.forEach(disp -> this.dispositivos.get(disp).executarOperacao(agora, operacao, valor));
    }

    public void executarOperacao(LocalDateTime agora, AcaoDispositivo acao) {
        this.dispositivos.get(acao.getIdDispositivo()).executarOperacao(agora, acao.getOperacao(), acao.getValor());
    }
    
    public void executarOperacao(LocalDateTime agora, AcaoMultiplosDispositivos acao) {
        acao.getDispositivos().forEach(disp -> this.dispositivos.get(disp).executarOperacao(agora, acao.getOperacao(), acao.getValor()));
    }

    public void executarCenario(LocalDateTime agora, String cenario) {
        this.cenarios.get(cenario).getAcoes().forEach(a -> {
                            if (a instanceof AcaoDispositivo ad)
                                this.dispositivos.get(ad.getIdDispositivo()).executarOperacao(agora, ad.getOperacao(), ad.getValor());
                            else if (a instanceof AcaoMultiplosDispositivos am)
                                am.getDispositivos().forEach(disp -> this.dispositivos.get(disp).executarOperacao(agora, am.getOperacao(), am.getValor()));
                        });
    }

    public void atualizarAutomacoes(LocalDateTime agora) {
        this.automacoes.values().stream()
                                .forEach(aut -> {
                                    Detetor det = (Detetor) this.dispositivos.get(aut.getDetetor());
                                    if (det.isADetetar()) {
                                        aut.getAcoes().forEach(a -> {
                                                        if (a instanceof AcaoDispositivo ad)
                                                            this.dispositivos.get(ad.getIdDispositivo()).executarOperacao(agora, ad.getOperacao(), ad.getValor());
                                                        else if (a instanceof AcaoMultiplosDispositivos am)
                                                            am.getDispositivos().forEach(disp -> this.dispositivos.get(disp).executarOperacao(agora, am.getOperacao(), am.getValor()));
                                                        });
                                    }});
    }

    public void atualizarEscalonamentos(LocalDateTime agora) {
        this.escalonamentos.values().stream()
                                    .filter(e -> e.deveExecutar(agora))
                                    .forEach(e -> e.getAcoes().forEach(a -> {
                                        if (a instanceof AcaoDispositivo ad)
                                            this.dispositivos.get(ad.getIdDispositivo()).executarOperacao(agora, ad.getOperacao(), ad.getValor());
                                        else if (a instanceof AcaoMultiplosDispositivos am)
                                            am.getDispositivos().forEach(disp -> this.dispositivos.get(disp).executarOperacao(agora, am.getOperacao(), am.getValor()));
                                    }));
    }

    
    public int quantosDispositivos() {
        return this.dispositivos.size();
    }

    public int quantasDivisoes() {
        return this.divisoes.size();
    }

    public int quantosCenarios() {
        return this.cenarios.size();
    }

    public int quantasAutomacoes() {
        return this.automacoes.size();
    }

    public int quantosEscalonamentos() {
        return this.escalonamentos.size();
    }

    public Dispositivo getDispositivo(String dispositivo) {
        Dispositivo d = this.dispositivos.get(dispositivo);
        return d == null ? null : d.clone();
    }

    public Divisao getDivisao(String divisao) {
        Divisao d = this.divisoes.get(divisao);
        return d == null ? null : d.clone();
    }

    public Cenario getCenario(String cenario) {
        Cenario c = this.cenarios.get(cenario);
        return c == null ? null : c.clone();
    }

    public int getConsumoTotal() {
        return this.dispositivos.values().stream()
                .filter(Dispositivo::isLigado)
                .mapToInt(Dispositivo::getConsumo)
                .sum();
    }

    public Automacao getAutomacao(String nome) {
        Automacao a = this.automacoes.get(nome);
        return a == null ? null : a.clone();
    }

    public Escalonamento getEscalonamento(String nome) {
        Escalonamento e = this.escalonamentos.get(nome);
        return e == null ? null : e.clone();
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
