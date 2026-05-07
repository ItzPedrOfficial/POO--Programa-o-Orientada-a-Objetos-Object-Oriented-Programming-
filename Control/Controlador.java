package Control;

import Model.*;
import Model.Exceptions.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Set;

public class Controlador {

    private DomusControl modelo;
    

    // -------------------------------------------------------------------------
    // Construtor
    // -------------------------------------------------------------------------

    public Controlador(DomusControl modelo) {
        this.modelo = modelo;
    }

    // -------------------------------------------------------------------------
    // Tempo
    // -------------------------------------------------------------------------

    public void avancarTempo(int minutos) {
        modelo.avancarTempo(minutos);
        modelo.atualizarEscalonamentos();
        modelo.atualizarAutomacoes();
    }

    public java.time.LocalDateTime getTempoAtual() {
        return modelo.getTempoAtual();
    }

    // -------------------------------------------------------------------------
    // Utilizadores
    // -------------------------------------------------------------------------

    public void criarUtilizador(String id, String password)
            throws UtilizadorJaExisteException {
        modelo.addUtilizador(new Utilizador(id, password, new java.util.HashSet<>(), new java.util.HashSet<>()));
    }

    public Utilizador autenticar(String id, String password)
            throws UtilizadorNaoEncontradoException, PasswordIncorretaException {
        if (!modelo.autenticar(id, password))
            throw new PasswordIncorretaException("Password incorreta.");
        return modelo.getUtilizador(id);
    }

    // -------------------------------------------------------------------------
    // Casas
    // -------------------------------------------------------------------------

    public void criarCasa(String idUtilizador, String idCasa, String morada)
        throws UtilizadorNaoEncontradoException, CasaJaExisteException {
    Casa casa = new Casa();
    casa.setId(idCasa);
    casa.setMorada(morada);
    modelo.addCasa(idUtilizador, casa);
    }
    public void removerCasa(String idUtilizador, String idCasa)
            throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException, PermissaoNegadaException {
        modelo.removeCasa(idUtilizador, idCasa);
    }

    public Casa getCasa(String idUtilizador, String idCasa)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        return modelo.getCasa(idUtilizador, idCasa);
    }

    public boolean isDono(String idUtilizador, String idCasa)
            throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        return modelo.isAdmin(idUtilizador, idCasa);
    }

    public boolean temAcesso(String idUtilizador, String idCasa)
            throws UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        return modelo.temAcesso(idUtilizador, idCasa);
    }

    public void adicionarUtilizadorACasa(String idDono, String idUtilizador, String idCasa)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        modelo.addCasaAcessivelAUtilizador(idDono, idUtilizador, idCasa);
    }

    // -------------------------------------------------------------------------
    // Divisões
    // -------------------------------------------------------------------------

    public void adicionarDivisao(String idUtilizador, String idCasa, String nomeDivisao)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addDivisao(idUtilizador, idCasa, new Divisao(nomeDivisao, new java.util.HashSet<>()));
    }

    public void removerDivisao(String idUtilizador, String idCasa, String nomeDivisao)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.removeDivisao(idUtilizador, idCasa, nomeDivisao);
    }

    public Divisao getDivisao(String idUtilizador, String idCasa, String nomeDivisao)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException,
                   CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        return modelo.getDivisao(idUtilizador, idCasa, nomeDivisao);
    }

    // -------------------------------------------------------------------------
    // Dispositivos — criação
    // -------------------------------------------------------------------------

    public void criarLampada(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addDispositivo(idUtilizador, idCasa, new Lampada(id, marca, nomeModelo, consumo));
    }

    public void criarLampadaColorida(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addDispositivo(idUtilizador, idCasa, new LampadaColorida(id, marca, nomeModelo, consumo));
    }

    public void criarColunaDeSom(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addDispositivo(idUtilizador, idCasa, new ColunaDeSom(id, marca, nomeModelo, consumo));
    }

    public void criarPortoide(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addDispositivo(idUtilizador, idCasa, new Portoide(id, marca, nomeModelo, consumo));
    }

    public void criarDetetor(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addDispositivo(idUtilizador, idCasa, new Detetor(id, marca, nomeModelo, consumo));
    }

    // -------------------------------------------------------------------------
    // Dispositivos — associar a divisão
    // -------------------------------------------------------------------------

    public void associarDispositivoADivisao(String idUtilizador, String idCasa, String idDispositivo, String nomeDivisao)
            throws PermissaoNegadaException, CasaNaoEncontradaException,
                   UtilizadorNaoEncontradoException, DivisaoNaoEncontradaException {
        Divisao divisao = modelo.getDivisao(idUtilizador, idCasa, nomeDivisao);
        divisao.addDispositivo(idDispositivo);
        modelo.addDivisao(idUtilizador, idCasa, divisao);
    }

    // -------------------------------------------------------------------------
    // Dispositivos — operar
    // -------------------------------------------------------------------------

    public void ligarDispositivo(String idUtilizador, String idCasa, String idDispositivo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.ligar(idUtilizador, idCasa, idDispositivo);
    }

    public void desligarDispositivo(String idUtilizador, String idCasa, String idDispositivo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.desligar(idUtilizador, idCasa, idDispositivo);
    }

    public void ajustarLuminosidade(String idUtilizador, String idCasa, String idDispositivo, int luminosidade)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.executarOperacao(idUtilizador, idCasa, idDispositivo, "setLuminosidade", luminosidade);
    }

    public void ajustarCor(String idUtilizador, String idCasa, String idDispositivo, String cor)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.executarOperacao(idUtilizador, idCasa, idDispositivo, "setCor", cor);
    }

    public void ajustarVolume(String idUtilizador, String idCasa, String idDispositivo, int volume)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.executarOperacao(idUtilizador, idCasa, idDispositivo, "setVolume", volume);
    }

    public void ajustarAbertura(String idUtilizador, String idCasa, String idDispositivo, int abertura)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.executarOperacao(idUtilizador, idCasa, idDispositivo, "setAbertura", abertura);
    }

    public void abrirPortao(String idUtilizador, String idCasa, String idDispositivo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        ajustarAbertura(idUtilizador, idCasa, idDispositivo, 100);
    }

    public void fecharPortao(String idUtilizador, String idCasa, String idDispositivo)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        ajustarAbertura(idUtilizador, idCasa, idDispositivo, 0);
    }

    public void ajustarDetetor(String idUtilizador, String idCasa, String idDispositivo, boolean aDetetar)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.executarOperacao(idUtilizador, idCasa, idDispositivo, "setADetetar", aDetetar);
    }

    public Dispositivo getDispositivo(String idUtilizador, String idCasa, String idDispositivo)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        return modelo.getDispositivo(idUtilizador, idCasa, idDispositivo);
    }

    // -------------------------------------------------------------------------
    // Cenários
    // -------------------------------------------------------------------------

    public void criarCenario(String idUtilizador, String idCasa, String nomeCenario, Set<Acao> acoes)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addCenario(idUtilizador, idCasa, new Cenario(nomeCenario, acoes));
    }

    public void ativarCenario(String idUtilizador, String idCasa, String nomeCenario)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.executarCenario(idUtilizador, idCasa, nomeCenario);
    }

    public Cenario getCenario(String idUtilizador, String idCasa, String nomeCenario)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException,
                   CasaNaoEncontradaException, CenarioNaoEncontradoException {
        return modelo.getCenario(idUtilizador, idCasa, nomeCenario);
    }

    // -------------------------------------------------------------------------
    // Automações
    // -------------------------------------------------------------------------

    public void criarAutomacao(String idUtilizador, String idCasa, String nomeAutomacao, String idDetetor, Set<Acao> acoes)
        throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
    modelo.addAutomacao(idUtilizador, idCasa, new Automacao(nomeAutomacao, idDetetor, acoes));
}
    public Automacao getAutomacao(String idUtilizador, String idCasa, String nomeAutomacao)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException,
                   CasaNaoEncontradaException, AutomacaoNaoEncontradaException {
        return modelo.getAutomacao(idUtilizador, idCasa, nomeAutomacao);
    }

    // -------------------------------------------------------------------------
    // Escalonamentos
    // -------------------------------------------------------------------------

    public void criarEscalonamentoDiario(String idUtilizador, String idCasa, String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addEscalonamento(idUtilizador, idCasa, new EscalonamentoDiário(nome, acoes, inicio, fim));
    }

    public void criarEscalonamentoPontual(String idUtilizador, String idCasa, String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim, LocalDate data)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addEscalonamento(idUtilizador, idCasa, new EscalonamentoPontual(nome, acoes, inicio, fim, data));
    }

    public void criarEscalonamentoSemanal(String idUtilizador, String idCasa, String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim, DayOfWeek diaDaSemana)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        modelo.addEscalonamento(idUtilizador, idCasa, new EscalonamentoSemanal(nome, acoes, inicio, fim, diaDaSemana));
    }

    public Escalonamento getEscalonamento(String idUtilizador, String idCasa, String nomeEscalonamento)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException,
                   CasaNaoEncontradaException, EscalonamentoNaoEncontradoException {
        return modelo.getEscalonamento(idUtilizador, idCasa, nomeEscalonamento);
    }
    // -------------------------------------------------------------------------
    // Estatísticas
    // -------------------------------------------------------------------------

    public Casa getCasaQueMaisConsome() {
        return modelo.casaQueMaisConsome();
    }

    public List<Dispositivo> getTresDispositivosMaisUtilizadosPorTempo(String idUtilizador, String idCasa)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        return modelo.getTresDispositivosMaisUtilizadosPorTempo(idUtilizador, idCasa);
    }

    public List<Dispositivo> getTresDispositivosMaisUtilizadosPorAtivacoes(String idUtilizador, String idCasa)
            throws PermissaoNegadaException, CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        return modelo.getTresDispositivosMaisUtilizadosPorAtivacoes(idUtilizador, idCasa);
    }

    public List<Divisao> getTresDivisoesComMaisDispositivos(String idUtilizador, String idCasa)
            throws PermissaoNegadaException, UtilizadorNaoEncontradoException, CasaNaoEncontradaException {
        return modelo.getTresDivisoesComMaisDispositivos(idUtilizador, idCasa);
    }
    // -------------------------------------------------------------------------
    // Persistência
    // -------------------------------------------------------------------------

    public void guardarEstado(String caminhoFicheiro) throws IOException {
        modelo.guardar(caminhoFicheiro);
    }

    public void carregarEstado(String caminhoFicheiro) throws IOException, ClassNotFoundException {
        this.modelo = DomusControl.carregar(caminhoFicheiro);
    }
}