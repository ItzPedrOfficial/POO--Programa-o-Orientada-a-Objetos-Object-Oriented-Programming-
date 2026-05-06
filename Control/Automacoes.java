package Control;

import Model.*;
import java.util.Set;
import static Control.Exceptions.*;

public class Automacoes {

    private DomusControl modelo;
    private Casas casas;

    public Automacoes(DomusControl modelo, Casas casas) {
        this.modelo = modelo;
        this.casas = casas;
    }

    public void criar(String idUtilizador, String idCasa, String nomeAutomacao, Condicao condicao, Set<Acao> acoes) {
        if (!casas.isDono(idUtilizador, idCasa))
            throw new PermissaoNegadaException("Só o dono pode criar automações.");
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        if (modelo.getAutomacao(idCasa, nomeAutomacao) != null)
            throw new AutomacaoJaExisteException("Já existe uma automação com o nome " + nomeAutomacao + ".");
        modelo.addAutomacao(idCasa, new Automacao(nomeAutomacao, condicao, acoes));
    }

    void verificar() {
        modelo.verificarEExecutarAutomacoes();
    }
}