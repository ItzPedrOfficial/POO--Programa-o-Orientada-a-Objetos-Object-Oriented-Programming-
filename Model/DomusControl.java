package Model;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
 
public class DomusControl implements Serializable {
    private Map<String, Casa> casas;
    private Map<String, Utilizador> utilizadores;
    private LocalDateTime tempoAtual;
 
    // --- Construtores ---
 
    public DomusControl() {
        this.casas = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.tempoAtual = LocalDateTime.now();
    }
 
    public DomusControl(Map<String, Casa> casas, Map<String, Utilizador> utilizadores) {
        this.casas = casas.values().stream()
                                   .map(Casa::clone)
                                   .collect(Collectors.toMap(c -> c.getId(), c -> c));
        this.utilizadores = utilizadores.values().stream()
                                                 .map(Utilizador::clone)
                                                 .collect(Collectors.toMap(u -> u.getId(), u -> u));
        this.tempoAtual = LocalDateTime.now();
    }
 
    public DomusControl(DomusControl dc) {
        this.casas = dc.getCasas();
        this.utilizadores = dc.getUtilizadores();
        this.tempoAtual = dc.getTempoAtual();
    }
 
    // --- Getters e Setters ---
 
    public Map<String, Casa> getCasas() {
        return casas.values().stream()
                             .map(Casa::clone)
                             .collect(Collectors.toMap(c -> c.getId(), c -> c));
    }
 
    public void setCasas(Map<String, Casa> casas) {
        this.casas = casas.values().stream()
                                   .map(Casa::clone)
                                   .collect(Collectors.toMap(c -> c.getId(), c -> c));
    }
 
    public Map<String, Utilizador> getUtilizadores() {
        return utilizadores.values().stream()
                                    .map(Utilizador::clone)
                                    .collect(Collectors.toMap(u -> u.getId(), u -> u));
    }
 
    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores.values().stream()
                                                 .map(Utilizador::clone)
                                                 .collect(Collectors.toMap(u -> u.getId(), u -> u));
    }
 
    public LocalDateTime getTempoAtual() {
        return tempoAtual;
    }
 
    // --- Tempo ---
 
    public void avancarTempo(long minutos) {
        this.tempoAtual = this.tempoAtual.plusMinutes(minutos);
    }
 
    public void atualizaTempoReal() {
        this.tempoAtual = LocalDateTime.now();
    }
 
    // --- Gestao de Utilizadores ---
 
    public void addUtilizador(Utilizador u) {
        this.utilizadores.put(u.getId(), u.clone());
    }
 
    public void removeUtilizador(String id) {
        this.utilizadores.remove(id);
    }
 
    public Utilizador getUtilizador(String id) {
        return this.utilizadores.get(id).clone();
    }
 
    public boolean utilizadorExiste(String id) {
        return this.utilizadores.containsKey(id);
    }
 
    public boolean autenticar(String id, String password) {
        Utilizador u = this.utilizadores.get(id);
        return u != null && u.getPassword().equals(password);
    }
 
    public void addCasaAcessivelAUtilizador(String idUtilizador, String idCasa) {
        this.utilizadores.get(idUtilizador).addCasasAcessiveis(idCasa);
    }
 
    public void removeCasaAcessivelAUtilizador(String idUtilizador, String idCasa) {
        this.utilizadores.get(idUtilizador).removeCasasAcessiveis(idCasa);
    }
 
    public void addCasaAdminAUtilizador(String idUtilizador, String idCasa) {
        this.utilizadores.get(idUtilizador).addCasasAdmin(idCasa);
    }
 
    public void removeCasaAdminAUtilizador(String idUtilizador, String idCasa) {
        this.utilizadores.get(idUtilizador).removeCasasAdmin(idCasa);
    }
 
    public boolean isAdmin(String idUtilizador, String idCasa) {
        return this.utilizadores.get(idUtilizador).getCasasAdmin().contains(idCasa);
    }
 
    public boolean temAcesso(String idUtilizador, String idCasa) {
        return this.utilizadores.get(idUtilizador).getCasasAcessiveis().contains(idCasa);
    }
 
    // --- Gestao de Casas ---
 
    public void addCasa(Casa casa) {
        this.casas.put(casa.getId(), casa.clone());
    }
 
    public void removeCasa(String id) {
        this.casas.remove(id);
    }
 
    public Casa getCasa(String id) {
        return this.casas.get(id).clone();
    }
 
    public boolean casaExiste(String id) {
        return this.casas.containsKey(id);
    }
 
    // --- Gestao de Dispositivos ---
 
    public void addDispositivo(String idCasa, Dispositivo dispositivo) {
        this.casas.get(idCasa).addDispositivo(dispositivo);
    }
 
    public void addDispositivo(String idCasa, String id, String marca, String modelo, int consumo) {
        this.casas.get(idCasa).addDispositivo(id, marca, modelo, consumo);
    }
 
    public void removeDispositivo(String idCasa, String idDispositivo) {
        this.casas.get(idCasa).removeDispositivo(idDispositivo);
    }
 
    public Dispositivo getDispositivo(String idCasa, String idDispositivo) {
        return this.casas.get(idCasa).getDispositivo(idDispositivo);
    }
 
    public void toggle(String idCasa, String idDispositivo) {
        this.casas.get(idCasa).toggle(tempoAtual, idDispositivo);
    }
 
    public void toggle(String idCasa, Set<String> idDispositivos) {
        this.casas.get(idCasa).toggle(tempoAtual, idDispositivos);
    }
 
    public void ligar(String idCasa, String idDispositivo) {
        this.casas.get(idCasa).ligar(tempoAtual, idDispositivo);
    }
 
    public void ligar(String idCasa, Set<String> idDispositivos) {
        this.casas.get(idCasa).ligar(tempoAtual, idDispositivos);
    }
 
    public void desligar(String idCasa, String idDispositivo) {
        this.casas.get(idCasa).desligar(tempoAtual, idDispositivo);
    }
 
    public void desligar(String idCasa, Set<String> idDispositivos) {
        this.casas.get(idCasa).desligar(tempoAtual, idDispositivos);
    }
 
    public void executarOperacao(String idCasa, String idDispositivo, String operacao, Object valor) {
        this.casas.get(idCasa).executarOperacao(tempoAtual, idDispositivo, operacao, valor);
    }
 
    public void executarOperacao(String idCasa, Set<String> idDispositivos, String operacao, Object valor) {
        this.casas.get(idCasa).executarOperacao(tempoAtual, idDispositivos, operacao, valor);
    }
 
    // --- Gestao de Divisoes ---
 
    public void addDivisao(String idCasa, Divisao divisao) {
        this.casas.get(idCasa).addDivisao(divisao);
    }
 
    public void addDivisao(String idCasa, String nome, Set<String> dispositivos) {
        this.casas.get(idCasa).addDivisao(nome, dispositivos);
    }
 
    public void removeDivisao(String idCasa, String nomeDivisao) {
        this.casas.get(idCasa).removeDivisao(nomeDivisao);
    }
 
    public Divisao getDivisao(String idCasa, String nomeDivisao) {
        return this.casas.get(idCasa).getDivisao(nomeDivisao);
    }
 
    // --- Gestao de Cenarios ---
 
    public void addCenario(String idCasa, Cenario cenario) {
        this.casas.get(idCasa).addCenario(cenario);
    }
 
    public void addCenario(String idCasa, String nome, Set<Acao> acoes) {
        this.casas.get(idCasa).addCenario(nome, acoes);
    }
 
    public void removeCenario(String idCasa, String nomeCenario) {
        this.casas.get(idCasa).removeCenario(nomeCenario);
    }
 
    public Cenario getCenario(String idCasa, String nomeCenario) {
        return this.casas.get(idCasa).getCenario(nomeCenario);
    }
 
    public void executarCenario(String idCasa, String nomeCenario) {
        this.casas.get(idCasa).executarCenario(tempoAtual, nomeCenario);
    }
 
    // --- Estatisticas ---
 
    public Casa casaQueMaisConsome() {
        return this.casas.values().stream()
                         .max((a, b) -> {
                             int consumoA = a.getDispositivos().values().stream()
                                             .mapToInt(Dispositivo::getConsumo).sum();
                             int consumoB = b.getDispositivos().values().stream()
                                             .mapToInt(Dispositivo::getConsumo).sum();
                             return Integer.compare(consumoA, consumoB);
                         })
                         .map(Casa::clone)
                         .orElse(null);
    }
 
    // --- Guardar e Carregar estado ---
 
    public void guardar(String ficheiro) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            out.writeObject(this);
        }
    }
 
    public static DomusControl carregar(String ficheiro) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (DomusControl) in.readObject();
        }
    }
 
    // --- Overrides de Object ---
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        DomusControl dc = (DomusControl) o;
        return this.casas.equals(dc.getCasas()) &&
               this.utilizadores.equals(dc.getUtilizadores());
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tempo Atual: ").append(tempoAtual).append("\n");
        sb.append("Casas: ").append(casas.toString()).append("\n");
        sb.append("Utilizadores: ").append(utilizadores.toString());
        return sb.toString();
    }
 
    @Override
    public DomusControl clone() {
        return new DomusControl(this);
    }
}
