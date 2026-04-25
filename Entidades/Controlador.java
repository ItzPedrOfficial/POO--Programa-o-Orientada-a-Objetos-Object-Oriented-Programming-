package Entidades;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import AI.Automacao;
import AI.Cenario;
import AI.ColunaSom;
import AI.Divisao;
import AI.Escalonamento;
import AI.Estado;
import AI.Lampada;
import AI.LampadaColorida;
import AI.Portoide;

public class Controlador {
    private Modelo modelo;

    public Controlador(Modelo modelo) {
        this.modelo = modelo;
    }

  // dispositivos em geral...
    public void ligarDispositivo(String id) {
    Dispositivo d = modelo.getDispositivo(id); //pedimos o id e se não existir temos exceção.
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    d.setEstado(Estado.LIGADO);
    }   

    public void desligarDispositivo(String id) { //igual ao acima mas este serve para desligar
    Dispositivo d = modelo.getDispositivo(id);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    d.setEstado(Estado.DESLIGADO);
    }



    //criar dispositivos...

    public void criarLampada(String id, String marca, String modelo, int consumo, int intensidade) {
    if (modelo.getDispositivo(id) != null) {
        throw new DispositivoJaExisteException("Já existe um dispositivo com o id " + id + ".");
    }
    if (intensidade < 0 || intensidade > 100) {
        throw new ValorInvalidoException("Intensidade deve estar entre 0 e 100.");
    }
    modelo.adicionarDispositivo(new Lampada(id, marca, modelo, consumo, intensidade));
    }




    public void criarLampadaColorida(String id, String marca, String modelo, int consumo, int intensidade, String cor) {
    if (modelo.getDispositivo(id) != null) {
        throw new DispositivoJaExisteException("Já existe um dispositivo com o id " + id + ".");
    }
    if (intensidade < 0 || intensidade > 100) {
        throw new ValorInvalidoException("Intensidade deve estar entre 0 e 100.");
    }
    modelo.adicionarDispositivo(new LampadaColorida(id, marca, modelo, consumo, intensidade, cor));
    }



    public void criarColunaSom(String id, String marca, String modelo, int consumo, int volume) {
    if (modelo.getDispositivo(id) != null) {
        throw new DispositivoJaExisteException("Já existe um dispositivo com o id " + id + ".");
    }
    if (volume < 0 || volume > 100) {
        throw new ValorInvalidoException("Volume deve estar entre 0 e 100.");
    }
    modelo.adicionarDispositivo(new ColunaSom(id, marca, modelo, consumo, volume));
    }


    public void criarPortoide(String id, String marca, String modelo, int consumo, int abertura) {
    if (modelo.getDispositivo(id) != null) {
        throw new DispositivoJaExisteException("Já existe um dispositivo com o id " + id + ".");
    }
    if (abertura < 0 || abertura > 100) {
        throw new ValorInvalidoException("Abertura deve estar entre 0 e 100.");
    }
    modelo.adicionarDispositivo(new Portoide(id, marca, modelo, consumo, abertura));
    }


    //lampada...
    public void ajustarIntensidade(String id, int intensidade) { //ajustar intensidadade da luz
    Dispositivo d = modelo.getDispositivo(id);                  //verificamos se existe e se é lampada e se a intensidade está no intervalo válido.
    if (d == null) {                            
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    if (!(d instanceof Lampada)) {
        throw new TipoDispositivoInvalidoException("Dispositivo não é uma lâmpada.");
    }
    if (intensidade < 0 || intensidade > 100) {
        throw new ValorInvalidoException("Intensidade deve estar entre 0 e 100.");
    }
    ((Lampada) d).setIntensidade(intensidade);
    }



    //lampada colorida, subtipo de lampada...
    public void ajustarCor(String id, String cor) { //verificamos se a lampada e xiste e se é colorida e se sim definimos a cor
    Dispositivo d = modelo.getDispositivo(id);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    if (!(d instanceof LampadaColorida)) {
        throw new TipoDispositivoInvalidoException("Dispositivo não é uma lâmpada colorida.");
    }
    ((LampadaColorida) d).setCor(cor);
    }



    //portoide...

    public void abrirPortao(String id) { // Verifica se o dispositivo é portoide e abre o com abre() 
    Dispositivo d = modelo.getDispositivo(id);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    if (!(d instanceof Portoide)) {
        throw new TipoDispositivoInvalidoException("Dispositivo não é um portão.");
    }
    ((Portoide) d).abre();
    }


    public void fecharPortao(String id) { //igual ao abrir mas serve para fechar lol
    Dispositivo d = modelo.getDispositivo(id);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    if (!(d instanceof Portoide)) {
        throw new TipoDispositivoInvalidoException("Dispositivo não é um portão.");
    }
    ((Portoide) d).fecha();
    }


    public void ajustarAbertura(String id, int abertura) { //abrir o portão só até um dado ponto
    Dispositivo d = modelo.getDispositivo(id);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    if (!(d instanceof Portoide)) {
        throw new TipoDispositivoInvalidoException("Dispositivo não é um portão.");
    }
    if (abertura < 0 || abertura > 100) {
        throw new ValorInvalidoException("Abertura deve estar entre 0 e 100.");
    }
    ((Portoide) d).setAbertura(abertura);
    }


    //coluna de som...

    public void ajustarVolume(String id, int volume) { //verifica se o dispositivo existe e á coluna, e depois se o volume a por for entre 0 e 100, damos set.
    Dispositivo d = modelo.getDispositivo(id);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Dispositivo com id " + id + " não existe.");
    }
    if (!(d instanceof ColunaSom)) {
        throw new TipoDispositivoInvalidoException("Dispositivo não é uma coluna de som.");
    }
    if (volume < 0 || volume > 100) {
        throw new ValorInvalidoException("Volume deve estar entre 0 e 100.");
    }
    ((ColunaSom) d).setVolume(volume);
    }

    //utilizador...

    public void criarUtilizador(String nome, String email, String password) { //checkamos se existe algum user que ja tenha esse nome,senão, criamos um novo
    if (modelo.getUtilizador(email) != null) {
        throw new UtilizadorJaExisteException("Já existe um utilizador com o email " + email + ".");
    }
    modelo.adicionarUtilizador(new Utilizador(nome, email, password));
    }

    public Utilizador autenticar(String email, String password) { //dar log in
    Utilizador u = modelo.getUtilizador(email);
    if (u == null) {
        throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o email " + email + ".");
    }
    if (!u.getPassword().equals(password)) {
        throw new PasswordIncorretaException("Password incorreta.");
    }
    return u;
    }


    public void adicionarUtilizadorACasa(String emailDono, String emailUtilizador, String nomeCasa) { //verificamos se é o dono a adicionar, se existe o user, a casa e se ja la esta, senao, adicionamos.
    if (!isDono(emailDono, nomeCasa)) {
        throw new PermissaoNegadaException("Só o dono pode adicionar utilizadores à casa.");
    }
    Utilizador u = modelo.getUtilizador(emailUtilizador);
    if (u == null) {
        throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o email " + emailUtilizador + ".");
    }
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    if (casa.contemUtilizador(emailUtilizador)) {
        throw new UtilizadorJaExisteException("Utilizador já pertence a esta casa.");
    }
    casa.adicionarUtilizador(u);
    }
     

    public boolean isDono(String emailUtilizador, String nomeCasa) {
    Utilizador u = modelo.getUtilizador(emailUtilizador);
    if (u == null) {
        throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o email " + emailUtilizador + ".");
    }
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    return casa.getDono().equals(u);
    }



    //casa...
    public void criarCasa(String nome, String morada, String emailDono) { //verificamos se o utilizador (dono)existe, se ja existe uma casa com esse nome, e depois criar a casa e passar como argumento
    Utilizador dono = modelo.getUtilizador(emailDono);  
    if (dono == null) {
        throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o email " + emailDono + ".");
    }
    if (modelo.getCasa(nome) != null) {
        throw new CasaJaExisteException("Já existe uma casa com o nome " + nome + ".");
    }
    modelo.adicionarCasa(new Casa(nome, morada, dono));
    }


    //divisões da casa...
    public void adicionarDivisao(String emailUtilizador, String nomeCasa, String nomeDivisao) {  // verificamos se o user é dono, se a casa existe, se a divisao ja existe ou nao e adicionamos.
    if (!isDono(emailUtilizador, nomeCasa)) {
        throw new PermissaoNegadaException("Só o dono pode adicionar divisões.");
    }
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    if (casa.getDivisao(nomeDivisao) != null) {
        throw new DivisaoJaExisteException("Já existe uma divisão com o nome " + nomeDivisao + ".");
    }
    casa.adicionarDivisao(new Divisao(nomeDivisao));
    }



    //dispositivo-divisão

    public void associarDispositivoADivisao(String emailUtilizador, String idDispositivo, String nomeDivisao, String nomeCasa) { //verificamos e o utilizador é dono da casa, se a casa existe, se a divisão existe dentro dessa casa, se o dispositivo existe e se já não está associado a essa divisão
    if (!isDono(emailUtilizador, nomeCasa)) {
        throw new PermissaoNegadaException("Só o dono pode associar dispositivos.");
    }
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    Divisao divisao = casa.getDivisao(nomeDivisao);
    if (divisao == null) {
        throw new DivisaoNaoEncontradaException("Não existe nenhuma divisão com o nome " + nomeDivisao + ".");
    }
    Dispositivo d = modelo.getDispositivo(idDispositivo);
    if (d == null) {
        throw new DispositivoNaoEncontradoException("Não existe nenhum dispositivo com o id " + idDispositivo + ".");
    }
    if (divisao.contemDispositivo(idDispositivo)) {
        throw new DispositivoJaAssociadoException("Dispositivo já está associado a esta divisão.");
    }
    divisao.adicionarDispositivo(d);
    }


    // estatisticas...
    public Casa getCasaQueMaisConsome() { //percorre todas as casas com o far até encontrar a que consome mais
    List<Casa> casas = modelo.getCasas();
    if (casas.isEmpty()) {
        throw new SemCasasException("Não existe nenhuma casa registada.");
    }
    Casa maisConsome = casas.get(0);
    for (Casa c : casas) {
        if (c.getConsumoTotal() > maisConsome.getConsumoTotal()) {
            maisConsome = c;
        }
    }
    return maisConsome;
    }


    public List<Dispositivo> getTresDispositivosMaisUtilizadosporTempo(String nomeCasa) { // procura a casa noo modelo e os dispositivos, verifica todos os que existem, ordena os todos e devolve top 3 usando o Matmin
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    List<Dispositivo> dispositivos = casa.getDispositivos();
    if (dispositivos.isEmpty()) {
        throw new SemDispositivosException("Não existe nenhum dispositivo nesta casa.");
    }
    dispositivos.sort((d1, d2) -> d2.getTempoLigado() - d1.getTempoLigado());
    return dispositivos.subList(0, Math.min(3, dispositivos.size()));
    }


    public List<Dispositivo> getTresDispositivosMaisUtilizadosPorAtivacoes(String nomeCasa) {//igual ao anterior mas para ativações
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    List<Dispositivo> dispositivos = casa.getDispositivos();
    if (dispositivos.isEmpty()) {
        throw new SemDispositivosException("Não existe nenhum dispositivo nesta casa.");
    }
    dispositivos.sort((d1, d2) -> d2.getNumeroAtivacoes() - d1.getNumeroAtivacoes());
    return dispositivos.subList(0, Math.min(3, dispositivos.size()));
    }




    public List<Divisao> getTresDivisoesComMaisDispositivos() {  //ordena as divisoes por ordem de mais dispositivos e devolve o top 3
    List<Casa> casas = modelo.getCasas();
    if (casas.isEmpty()) {
        throw new SemCasasException("Não existe nenhuma casa registada.");
    }
    List<Divisao> divisoes = new ArrayList<>();
    for (Casa c : casas) {
        divisoes.addAll(c.getDivisoes());
    }
    if (divisoes.isEmpty()) {
        throw new SemDivisoesException("Não existe nenhuma divisão registada.");
    }
    divisoes.sort((d1, d2) -> d2.getNumeroDispositivos() - d1.getNumeroDispositivos());
    return divisoes.subList(0, Math.min(3, divisoes.size()));
    }


  //automações e escalonamento...
    public void criarAutomacao(String nomeCasa, String nomeAutomacao, Condicao condicao, List<Acao> acoes) {//verifica se a casa existe e se já há um automação com o mesmo nome. Cria-se e adiciona-se à casa.
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    if (casa.getAutomacao(nomeAutomacao) != null) {
        throw new AutomacaoJaExisteException("Já existe uma automação com o nome " + nomeAutomacao + ".");
    }
    casa.adicionarAutomacao(new Automacao(nomeAutomacao, condicao, acoes));
    }


    private void verificarAutomacoes() { //percorre todas as casas e verifica as suas automações. Ativa se necessario. Esta funcão é chamada constantemente pela passar tempo.
    List<Casa> casas = modelo.getCasas();
    for (Casa casa : casas) {
        for (Automacao automacao : casa.getAutomacoes()) {
            if (automacao.getCondicao().verificar()) {
                for (Acao acao : automacao.getAcoes()) {
                    acao.executar();
                }
            }
        }
    }
    }

    public void criarEscalonamento(String nomeCasa, String nomeEscalonamento, LocalTime hora, List<Acao> acoes) {
    Casa casa = modelo.getCasa(nomeCasa);
    if (casa == null) {
        throw new CasaNaoEncontradaException("Não existe nenhuma casa com o nome " + nomeCasa + ".");
    }
    if (casa.getEscalonamento(nomeEscalonamento) != null) {
        throw new EscalonamentoJaExisteException("Já existe um escalonamento com o nome " + nomeEscalonamento + ".");
    }
    casa.adicionarEscalonamento(new Escalonamento(nomeEscalonamento, hora, acoes));
    }


    private void verificarEscalonamentos() {
    List<Casa> casas = modelo.getCasas();
    for (Casa casa : casas) {
        for (Escalonamento e : casa.getEscalonamentos()) {
            if (e.getHora().equals(tempoAtual.toLocalTime())) {
                for (Acao acao : e.getAcoes()) {
                    acao.executar();
                }
            }
        }
    }
    }

 //imp: O escalonamento ativa uma ação com base numa hora (tipo 18h ligfa as luzes, por exemplo), enquanto que a automação é com base numa condição.

    public void avancarTempo(int minutos) {
    this.tempoAtual = this.tempoAtual.plusMinutes(minutos);
    verificarEscalonamentos(); 
    verificarAutomacoes();     
    }

    //cenarios...

    //os cenarios tem de ser disparados manualmente pelo utilizador.

    public void criarCenario(String emailUtilizador, String nomeCenario, List<Acao> acoes) {
    Utilizador u = modelo.getUtilizador(emailUtilizador);
    if (u == null) {
        throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o email " + emailUtilizador + ".");
    }
    if (u.getCenario(nomeCenario) != null) {
        throw new CenarioJaExisteException("Já existe um cenário com o nome " + nomeCenario + ".");
    }
    u.adicionarCenario(new Cenario(nomeCenario, acoes));
    }
 

    public void ativarCenario(String emailUtilizador, String nomeCenario) {
    Utilizador u = modelo.getUtilizador(emailUtilizador);
    if (u == null) {
        throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o email " + emailUtilizador + ".");
    }
    Cenario cenario = u.getCenario(nomeCenario);
    if (cenario == null) {
        throw new CenarioNaoEncontradoException("Não existe nenhum cenário com o nome " + nomeCenario + ".");
    }
    for (Acao acao : cenario.getAcoes()) {
        acao.executar();
    }
    }

    //persistencia...



    public void guardarEstado(String caminhoFicheiro) { //pede ao modelo para guardar tudo em binario
    try {
        modelo.guardar(caminhoFicheiro);
    } catch (IOException e) {
        throw new ErroAoGuardarException("Erro ao guardar o estado: " + e.getMessage());
    }
    }

    public void carregarEstado(String caminhoFicheiro) { //pede  ao modelo para converter binario num estado e voltarmos a esse estado.
    try {
        modelo.carregar(caminhoFicheiro);
    } catch (IOException e) {
        throw new ErroAoCarregarException("Erro ao carregar o estado: " + e.getMessage());
    }
    }
}