package Control;

import Model.*;
import static Control.Exceptions.*;

public class Utilizadores {

    private DomusControl modelo;

    public Utilizadores(DomusControl modelo) {
        this.modelo = modelo;
    }

    public void criar(String id, String password) {
        if (modelo.utilizadorExiste(id))
            throw new UtilizadorJaExisteException("Já existe um utilizador com o id " + id + ".");
        modelo.addUtilizador(new Utilizador(id, password, new java.util.HashSet<>(), new java.util.HashSet<>()));
    }

    public Utilizador autenticar(String id, String password) {
        if (!modelo.utilizadorExiste(id))
            throw new UtilizadorNaoEncontradoException("Não existe nenhum utilizador com o id " + id + ".");
        if (!modelo.autenticar(id, password))
            throw new PasswordIncorretaException("Password incorreta.");
        return modelo.getUtilizador(id);
    }
}