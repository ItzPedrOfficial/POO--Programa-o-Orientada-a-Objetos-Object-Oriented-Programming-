package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import Model.Exceptions.*;

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

    // --- Auxiliares de permissão ---

    private void requerUtilizador(String idUtilizador) throws UtilizadorNaoEncontradoException {
        if (!utilizadores.containsKey(idUtilizador))
            throw new UtilizadorNaoEncontradoException("O utilizador " + idUtilizador + " não existe.");
    }

    private void requerCasa(String idCasa) throws CasaNaoEncontradaException {
        if (!casas.containsKey(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
    }

    private void requerAcesso(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerUtilizador(idUtilizador);
        requerCasa(idCasa);
        if (!utilizadores.get(idUtilizador).getCasasAcessiveis().contains(idCasa))
            throw new PermissaoNegadaException("O utilizador " + idUtilizador + " não tem acesso à casa " + idCasa + ".");
    }

    private void requerAdmin(String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerUtilizador(idUtilizador);
        requerCasa(idCasa);
        if (!utilizadores.get(idUtilizador).getCasasAdmin().contains(idCasa))
            throw new PermissaoNegadaException("O utilizador " + idUtilizador + " não é administrador da casa " + idCasa + ".");
    }

    // --- Gestao de Utilizadores ---

    public boolean utilizadorExiste(String id) {
        return this.utilizadores.containsKey(id);
    }

    public boolean autenticar(String id, String password) throws UtilizadorNaoEncontradoException {
        requerUtilizador(id);
        return utilizadores.get(id).getPassword().equals(password);
    }

    public void addUtilizador(Utilizador u) throws UtilizadorJaExisteException {
        if (utilizadores.containsKey(u.getId()))
            throw new UtilizadorJaExisteException("O utilizador " + u.getId() + " já existe.");
        this.utilizadores.put(u.getId(), u.clone());
    }

    public void removeUtilizador(String id) throws UtilizadorNaoEncontradoException {
        requerUtilizador(id);
        this.utilizadores.remove(id);
    }

    public Utilizador getUtilizador(String id) throws UtilizadorNaoEncontradoException {
        requerUtilizador(id);
        return this.utilizadores.get(id).clone();
    }

    // --- Gestao de Casas ---

    public boolean casaExiste(String id) {
        return this.casas.containsKey(id);
    }

    public void addCasa(String idUtilizador, Casa casa) throws UtilizadorNaoEncontradoException, CasaJaExisteException {
        requerUtilizador(idUtilizador);
        if (casas.containsKey(casa.getId()))
            throw new CasaJaExisteException("Já existe uma casa com o id " + casa.getId() + ".");
        this.casas.put(casa.getId(), casa.clone());
        this.utilizadores.get(idUtilizador).addCasasAdmin(casa.getId());
    }

    public void removeCasa(String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.remove(idCasa);
    }

    public Casa getCasa(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return this.casas.get(idCasa).clone();
    }

    public int quantosDispositivos(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).quantosDispositivos();
    }

    public int quantasDivisoes(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).quantasDivisoes();
    }

    public int quantosCenarios(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).quantosCenarios();
    }

    public int quantasAutomacoes(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).quantasAutomacoes();
    }

    public int quantosEscalonamentos(String idUtilizador, String idCasa) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).quantosEscalonamentos();
    }

    // --- Gestao de Permissões ---

    public boolean isAdmin(String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerUtilizador(idUtilizador);
        requerCasa(idCasa);
        return this.utilizadores.get(idUtilizador).getCasasAdmin().contains(idCasa);
    }

    public boolean temAcesso(String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerUtilizador(idUtilizador);
        requerCasa(idCasa);
        return this.utilizadores.get(idUtilizador).getCasasAcessiveis().contains(idCasa);
    }

    public void addCasaAcessivelAUtilizador(String idAdmin, String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idAdmin, idCasa);
        requerUtilizador(idUtilizador);
        this.utilizadores.get(idUtilizador).addCasasAcessiveis(idCasa);
    }

    public void removeCasaAcessivelAUtilizador(String idAdmin, String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idAdmin, idCasa);
        requerUtilizador(idUtilizador);
        this.utilizadores.get(idUtilizador).removeCasasAcessiveis(idCasa);
    }

    public void addCasaAdminAUtilizador(String idAdmin, String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idAdmin, idCasa);
        requerUtilizador(idUtilizador);
        this.utilizadores.get(idUtilizador).addCasasAdmin(idCasa);
    }

    public void removeCasaAdminAUtilizador(String idAdmin, String idUtilizador, String idCasa) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idAdmin, idCasa);
        requerUtilizador(idUtilizador);
        this.utilizadores.get(idUtilizador).removeCasasAdmin(idCasa);
    }

    // --- Gestao de Dispositivos ---

    public void addDispositivo(String idUtilizador, String idCasa, Dispositivo dispositivo) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addDispositivo(dispositivo);
    }

    public void addDispositivo(String idUtilizador, String idCasa, String id, String marca, String modelo, int consumo) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addDispositivo(id, marca, modelo, consumo);
    }

    public void removeDispositivo(String idUtilizador, String idCasa, String idDispositivo) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).removeDispositivo(idDispositivo);
    }

    public Dispositivo getDispositivo(String idUtilizador, String idCasa, String idDispositivo) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        return this.casas.get(idCasa).getDispositivo(idDispositivo);
    }

    public void toggle(String idUtilizador, String idCasa, String idDispositivo) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).toggle(tempoAtual, idDispositivo);
    }

    public void toggle(String idUtilizador, String idCasa, Set<String> idDispositivos) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).toggle(tempoAtual, idDispositivos);
    }

    public void ligar(String idUtilizador, String idCasa, String idDispositivo) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).ligar(tempoAtual, idDispositivo);
    }

    public void ligar(String idUtilizador, String idCasa, Set<String> idDispositivos) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).ligar(tempoAtual, idDispositivos);
    }

    public void desligar(String idUtilizador, String idCasa, String idDispositivo) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).desligar(tempoAtual, idDispositivo);
    }

    public void desligar(String idUtilizador, String idCasa, Set<String> idDispositivos) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).desligar(tempoAtual, idDispositivos);
    }

    public void executarOperacao(String idUtilizador, String idCasa, String idDispositivo, String operacao, Object valor) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).executarOperacao(tempoAtual, idDispositivo, operacao, valor);
    }

    public void executarOperacao(String idUtilizador, String idCasa, Set<String> idDispositivos, String operacao, Object valor) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).executarOperacao(tempoAtual, idDispositivos, operacao, valor);
    }

    // --- Gestao de Divisoes ---

    public void addDivisao(String idUtilizador, String idCasa, Divisao divisao) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addDivisao(divisao);
    }

    public void addDivisao(String idUtilizador, String idCasa, String nome, Set<String> dispositivos) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addDivisao(nome, dispositivos);
    }

    public void removeDivisao(String idUtilizador, String idCasa, String nomeDivisao) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).removeDivisao(nomeDivisao);
    }

    public Divisao getDivisao(String idUtilizador, String idCasa, String nomeDivisao) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        Divisao d = this.casas.get(idCasa).getDivisao(nomeDivisao);
        if (d == null)
            throw new DivisaoNaoEncontradaException("Não existe nenhuma divisão com o nome " + nomeDivisao + ".");
        return d;
    }

    // --- Gestao de Cenarios ---

    public void addCenario(String idUtilizador, String idCasa, Cenario cenario) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addCenario(cenario);
    }

    public void addCenario(String idUtilizador, String idCasa, String nome, Set<Acao> acoes) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addCenario(nome, acoes);
    }

    public void removeCenario(String idUtilizador, String idCasa, String nomeCenario) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).removeCenario(nomeCenario);
    }

    public Cenario getCenario(String idUtilizador, String idCasa, String nomeCenario) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException, CenarioNaoEncontradoException {
        requerAcesso(idUtilizador, idCasa);
        Cenario c = this.casas.get(idCasa).getCenario(nomeCenario);
        if (c == null)
            throw new CenarioNaoEncontradoException("Não existe nenhum cenário com o nome " + nomeCenario + ".");
        return c;
    }

    public void executarCenario(String idUtilizador, String idCasa, String nomeCenario) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        this.casas.get(idCasa).executarCenario(tempoAtual, nomeCenario);
    }

    // --- Gestao de Automacoes ---

    public void addAutomacao(String idUtilizador, String idCasa, Automacao automacao) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addAutomacao(automacao);
    }

    public void removeAutomacao(String idUtilizador, String idCasa, String nomeAutomacao) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).removeAutomacao(nomeAutomacao);
    }

    public Automacao getAutomacao(String idUtilizador, String idCasa, String nomeAutomacao) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException, AutomacaoNaoEncontradaException {
        requerAcesso(idUtilizador, idCasa);
        Automacao a = this.casas.get(idCasa).getAutomacao(nomeAutomacao);
        if (a == null)
            throw new AutomacaoNaoEncontradaException("Não existe nenhuma automação com o nome " + nomeAutomacao + ".");
        return a;
    }

    public void atualizarAutomacoes() {
        this.casas.values().forEach(c -> c.atualizarAutomacoes(this.tempoAtual));
    }

    // --- Gestao de Escalonamentos ---

    public void addEscalonamento(String idUtilizador, String idCasa, Escalonamento escalonamento) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).addEscalonamento(escalonamento);
    }

    public void removeEscalonamento(String idUtilizador, String idCasa, String nomeEscalonamento) throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        requerAdmin(idUtilizador, idCasa);
        this.casas.get(idCasa).removeEscalonamento(nomeEscalonamento);
    }

    public Escalonamento getEscalonamento(String idUtilizador, String idCasa, String nomeEscalonamento) throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException, EscalonamentoNaoEncontradoException {
        requerAcesso(idUtilizador, idCasa);
        Escalonamento e = this.casas.get(idCasa).getEscalonamento(nomeEscalonamento);
        if (e == null)
            throw new EscalonamentoNaoEncontradoException("Não existe nenhum escalonamento com o nome " + nomeEscalonamento + ".");
        return e;
    }

    public void atualizarEscalonamentos() {
        this.casas.values().forEach(c -> c.atualizarEscalonamentos(this.tempoAtual));
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

    public List<Dispositivo> getTresDispositivosMaisUtilizadosPorTempo(String idUtilizador, String idCasa)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        int max = quantosDispositivos(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).getDispositivos().values().stream()
                                                              .sorted((d1, d2) -> Long.compare(d2.getTempoLigado(), d1.getTempoLigado()))
                                                              .collect(Collectors.toList())
                                                              .subList(0, Math.min(3, max));
    }

    public List<Dispositivo> getTresDispositivosMaisUtilizadosPorAtivacoes(String idUtilizador, String idCasa)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        int max = quantosDispositivos(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).getDispositivos().values().stream()
                                                              .sorted((d1, d2) -> d2.getAtivacoes() - d1.getAtivacoes())
                                                              .collect(Collectors.toList())
                                                              .subList(0, Math.min(3, max));
    }

    public List<Divisao> getTresDivisoesComMaisDispositivos(String idUtilizador, String idCasa) 
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        int max = quantasDivisoes(idUtilizador, idCasa);
        return getCasa(idUtilizador, idCasa).getDivisoes().values().stream()
                                                              .sorted((d1, d2) -> d2.getDispositivos().size() - d1.getDispositivos().size())
                                                              .collect(Collectors.toList())
                                                              .subList(0, Math.min(3, max));
    }

    // --- Guardar e Carregar estado ---

    public void guardar(String ficheiro) throws IOException {
        FileOutputStream fos = new FileOutputStream(ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public static DomusControl carregar(String ficheiro) throws IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream(ficheiro);
        ObjectInputStream oos = new ObjectInputStream(fos);
        DomusControl dc = (DomusControl) oos.readObject();
        oos.close();
        return dc;
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