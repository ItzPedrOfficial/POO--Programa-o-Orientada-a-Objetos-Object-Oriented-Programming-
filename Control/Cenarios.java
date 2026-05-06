package Control;

import Model.*;
import java.util.Set;
import static Control.Exceptions.*;

public class Cenarios {

    private DomusControl modelo;
    private Casas casas;

    public Cenarios(DomusControl modelo, Casas casas) {
        this.modelo = modelo;
        this.casas = casas;
    }

    public void criar(String idUtilizador, String idCasa, String nomeCenario, Set<Acao> acoes) {
        if (!casas.isDono(idUtilizador, idCasa))
            throw new PermissaoNegadaException("Só o dono pode criar cenários.");
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        Casa casa = modelo.getCasa(idCasa);
        if (casa.getCenarios().containsKey(nomeCenario))
            throw new CenarioJaExisteException("Já existe um cenário com o nome " + nomeCenario + ".");
        modelo.addCenario(idCasa, new Cenario(nomeCenario, acoes));
    }

    public void ativar(String idCasa, String nomeCenario) {
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        Casa casa = modelo.getCasa(idCasa);
        if (!casa.getCenarios().containsKey(nomeCenario))
            throw new CenarioNaoEncontradoException("Não existe nenhum cenário com o nome " + nomeCenario + ".");
        modelo.executarCenario(idCasa, nomeCenario);
    }
}