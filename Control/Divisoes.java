package Control;

import Model.*;
import static Control.Exceptions.*;

public class Divisoes {

    private DomusControl modelo;
    private Casas casas;

    public Divisoes(DomusControl modelo, Casas casas) {
        this.modelo = modelo;
        this.casas = casas;
    }

    public void adicionar(String idUtilizador, String idCasa, String nomeDivisao) {
        if (!casas.isDono(idUtilizador, idCasa))
            throw new PermissaoNegadaException("Só o dono pode adicionar divisões.");
        Casa casa = modelo.getCasa(idCasa);
        if (casa.getDivisoes().containsKey(nomeDivisao))
            throw new DivisaoJaExisteException("Já existe uma divisão com o nome " + nomeDivisao + ".");
        modelo.addDivisao(idCasa, new Divisao(nomeDivisao, new java.util.HashSet<>()));
    }
}