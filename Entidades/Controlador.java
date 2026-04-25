package Entidades;

import java.util.ArrayList;
import java.util.List;

import AI.ColunaSom;
import AI.Divisao;
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

    public void adicionarDivisao(String nomeCasa, String nomeDivisao) { //Procurmaos a casa em modelo e chekamos se ela existe bem como a divisão. Caso não exista a divisão criamo la
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

    public void associarDispositivoADivisao(String idDispositivo, String nomeDivisao, String nomeCasa) {
    Casa casa = modelo.getCasa(nomeCasa);  //verificamos se ja ta associado, se a casa e divisao existem e se ja existe este dispositivo e associamos
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


    // estatissticas
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


}