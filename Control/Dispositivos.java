package Control;

import Model.*;
import static Control.Exceptions.*;

public class Dispositivos {

    private DomusControl modelo;
    private Casas casas;

    public Dispositivos(DomusControl modelo, Casas casas) {
        this.modelo = modelo;
        this.casas = casas;
    }

    // --- Criação ---

    public void criarLampada(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo) {
        verificarCriacao(idUtilizador, idCasa, id);
        modelo.addDispositivo(idCasa, new Lampada(id, marca, nomeModelo, consumo));
    }

    public void criarLampadaColorida(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo) {
        verificarCriacao(idUtilizador, idCasa, id);
        modelo.addDispositivo(idCasa, new LampadaColorida(id, marca, nomeModelo, consumo));
    }

    public void criarColunaDeSom(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo) {
        verificarCriacao(idUtilizador, idCasa, id);
        modelo.addDispositivo(idCasa, new ColunaDeSom(id, marca, nomeModelo, consumo));
    }

    public void criarPortoide(String idUtilizador, String idCasa, String id, String marca, String nomeModelo, int consumo) {
        verificarCriacao(idUtilizador, idCasa, id);
        modelo.addDispositivo(idCasa, new Portoide(id, marca, nomeModelo, consumo));
    }

    private void verificarCriacao(String idUtilizador, String idCasa, String id) {
        if (!casas.isDono(idUtilizador, idCasa))
            throw new PermissaoNegadaException("Só o dono pode adicionar dispositivos.");
        if (modelo.getCasa(idCasa).getDispositivos().containsKey(id))
            throw new DispositivoJaExisteException("Já existe um dispositivo com o id " + id + ".");
    }

    // --- Associar a divisão ---

    public void associarADivisao(String idUtilizador, String idCasa, String idDispositivo, String nomeDivisao) {
        if (!casas.isDono(idUtilizador, idCasa))
            throw new PermissaoNegadaException("Só o dono pode associar dispositivos.");
        Casa casa = modelo.getCasa(idCasa);
        if (!casa.getDispositivos().containsKey(idDispositivo))
            throw new DispositivoNaoEncontradoException("Não existe nenhum dispositivo com o id " + idDispositivo + ".");
        Divisao divisao = modelo.getDivisao(idCasa, nomeDivisao);
        if (divisao == null)
            throw new DivisaoNaoEncontradaException("Não existe nenhuma divisão com o nome " + nomeDivisao + ".");
        if (divisao.getDispositivos().contains(idDispositivo))
            throw new DispositivoJaAssociadoException("Dispositivo já está associado a esta divisão.");
        divisao.addDispositivo(idDispositivo);
        modelo.addDivisao(idCasa, divisao);
    }

    // --- Operar ---

    public void ligar(String idCasa, String idDispositivo) {
        verificarExistencia(idCasa, idDispositivo);
        modelo.ligar(idCasa, idDispositivo);
    }

    public void desligar(String idCasa, String idDispositivo) {
        verificarExistencia(idCasa, idDispositivo);
        modelo.desligar(idCasa, idDispositivo);
    }

    public void ajustarLuminosidade(String idCasa, String idDispositivo, int luminosidade) {
        if (luminosidade < 0 || luminosidade > 100)
            throw new ValorInvalidoException("Luminosidade deve estar entre 0 e 100.");
        Dispositivo d = obterDispositivo(idCasa, idDispositivo);
        if (!(d instanceof Lampada))
            throw new TipoDispositivoInvalidoException("Dispositivo não é uma lâmpada.");
        modelo.executarOperacao(idCasa, idDispositivo, "setLuminosidade", luminosidade);
    }

    public void ajustarCor(String idCasa, String idDispositivo, String cor) {
        Dispositivo d = obterDispositivo(idCasa, idDispositivo);
        if (!(d instanceof LampadaColorida))
            throw new TipoDispositivoInvalidoException("Dispositivo não é uma lâmpada colorida.");
        modelo.executarOperacao(idCasa, idDispositivo, "setCor", cor);
    }

    public void ajustarVolume(String idCasa, String idDispositivo, int volume) {
        if (volume < 0 || volume > 100)
            throw new ValorInvalidoException("Volume deve estar entre 0 e 100.");
        Dispositivo d = obterDispositivo(idCasa, idDispositivo);
        if (!(d instanceof ColunaDeSom))
            throw new TipoDispositivoInvalidoException("Dispositivo não é uma coluna de som.");
        modelo.executarOperacao(idCasa, idDispositivo, "setVolume", volume);
    }

    public void ajustarAbertura(String idCasa, String idDispositivo, int abertura) {
        if (abertura < 0 || abertura > 100)
            throw new ValorInvalidoException("Abertura deve estar entre 0 e 100.");
        Dispositivo d = obterDispositivo(idCasa, idDispositivo);
        if (!(d instanceof Portoide))
            throw new TipoDispositivoInvalidoException("Dispositivo não é um portão.");
        modelo.executarOperacao(idCasa, idDispositivo, "setAbertura", abertura);
    }

    public void abrirPortao(String idCasa, String idDispositivo) {
        ajustarAbertura(idCasa, idDispositivo, 100);
    }

    public void fecharPortao(String idCasa, String idDispositivo) {
        ajustarAbertura(idCasa, idDispositivo, 0);
    }

    // --- Auxiliares ---

    private void verificarExistencia(String idCasa, String idDispositivo) {
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        if (!modelo.getCasa(idCasa).getDispositivos().containsKey(idDispositivo))
            throw new DispositivoNaoEncontradoException("Não existe nenhum dispositivo com o id " + idDispositivo + ".");
    }

    private Dispositivo obterDispositivo(String idCasa, String idDispositivo) {
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        Dispositivo d = modelo.getDispositivo(idCasa, idDispositivo);
        if (d == null)
            throw new DispositivoNaoEncontradoException("Não existe nenhum dispositivo com o id " + idDispositivo + ".");
        return d;
    }
}