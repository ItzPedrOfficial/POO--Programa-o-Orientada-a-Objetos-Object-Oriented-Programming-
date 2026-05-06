package Control;

import Model.*;
import static Control.Exceptions.*;

public class Casas {

    private DomusControl modelo;

    public Casas(DomusControl modelo) {
        this.modelo = modelo;
    }

    public void criar(String idUtilizador, String idCasa, String morada) {
        if (!modelo.utilizadorExiste(idUtilizador))
            throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o id " + idUtilizador + ".");
        if (modelo.casaExiste(idCasa))
            throw new CasaJaExisteException("Já existe uma casa com o id " + idCasa + ".");
        modelo.addCasa(new Casa(idCasa, morada,
                new java.util.HashMap<>(),
                new java.util.HashMap<>(),
                new java.util.HashMap<>()));
        modelo.addCasaAdminAUtilizador(idUtilizador, idCasa);
    }

    public boolean isDono(String idUtilizador, String idCasa) {
        if (!modelo.utilizadorExiste(idUtilizador))
            throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o id " + idUtilizador + ".");
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        return modelo.isAdmin(idUtilizador, idCasa);
    }

    public boolean temAcesso(String idUtilizador, String idCasa) {
        if (!modelo.utilizadorExiste(idUtilizador))
            throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o id " + idUtilizador + ".");
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        return modelo.temAcesso(idUtilizador, idCasa);
    }

    public void adicionarUtilizador(String idDono, String idUtilizador, String idCasa) {
        if (!isDono(idDono, idCasa))
            throw new PermissaoNegadaException("Só o dono pode adicionar utilizadores à casa.");
        if (!modelo.utilizadorExiste(idUtilizador))
            throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o id " + idUtilizador + ".");
        if (modelo.temAcesso(idUtilizador, idCasa))
            throw new UtilizadorJaExisteException("Utilizador já tem acesso a esta casa.");
        modelo.addCasaAcessivelAUtilizador(idUtilizador, idCasa);
    }
}