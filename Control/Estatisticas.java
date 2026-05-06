package Control;

import Model.*;
import java.util.ArrayList;
import java.util.List;
import static Control.Exceptions.*;

public class Estatisticas {

    private DomusControl modelo;

    public Estatisticas(DomusControl modelo) {
        this.modelo = modelo;
    }

    public Casa casaQueMaisConsome() {
        Casa c = modelo.casaQueMaisConsome();
        if (c == null)
            throw new SemCasasException("Não existe nenhuma casa registada.");
        return c;
    }

    public List<Dispositivo> tresDispositivosMaisUtilizadosPorTempo(String idCasa) {
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        List<Dispositivo> dispositivos = new ArrayList<>(modelo.getCasa(idCasa).getDispositivos().values());
        if (dispositivos.isEmpty())
            throw new SemDispositivosException("Não existe nenhum dispositivo nesta casa.");
        dispositivos.sort((d1, d2) -> Long.compare(d2.getTempoLigado(), d1.getTempoLigado()));
        return dispositivos.subList(0, Math.min(3, dispositivos.size()));
    }

    public List<Dispositivo> tresDispositivosMaisUtilizadosPorAtivacoes(String idCasa) {
        if (!modelo.casaExiste(idCasa))
            throw new CasaNaoEncontradaException("Não existe nenhuma casa com o id " + idCasa + ".");
        List<Dispositivo> dispositivos = new ArrayList<>(modelo.getCasa(idCasa).getDispositivos().values());
        if (dispositivos.isEmpty())
            throw new SemDispositivosException("Não existe nenhum dispositivo nesta casa.");
        dispositivos.sort((d1, d2) -> d2.getAtivacoes() - d1.getAtivacoes());
        return dispositivos.subList(0, Math.min(3, dispositivos.size()));
    }

    public List<Divisao> tresDivisoesComMaisDispositivos() {
        List<Casa> todasCasas = new ArrayList<>(modelo.getCasas().values());
        if (todasCasas.isEmpty())
            throw new SemCasasException("Não existe nenhuma casa registada.");
        List<Divisao> divisoes = new ArrayList<>();
        for (Casa c : todasCasas)
            divisoes.addAll(c.getDivisoes().values());
        if (divisoes.isEmpty())
            throw new SemDivisoesException("Não existe nenhuma divisão registada.");
        divisoes.sort((d1, d2) -> d2.getDispositivos().size() - d1.getDispositivos().size());
        return divisoes.subList(0, Math.min(3, divisoes.size()));
    }
}