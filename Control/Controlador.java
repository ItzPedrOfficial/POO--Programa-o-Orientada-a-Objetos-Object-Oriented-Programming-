package Control;

import Model.DomusControl;


public class Controlador {

    private DomusControl modelo;

    public final Utilizadores utilizadores;
    public final Casas casas;
    public final Divisoes divisoes;
    public final Dispositivos dispositivos;
    public final Cenarios cenarios;
    public final Automacoes automacoes;
    public final Escalonamentos escalonamentos;
    public final Estatisticas estatisticas;
    public final Persistencia persistencia;
    

    public Controlador(DomusControl modelo) {
        this.modelo = modelo;
        this.utilizadores = new Utilizadores(modelo);
        this.casas = new Casas(modelo);
        this.divisoes = new Divisoes(modelo, this.casas);
        this.dispositivos = new Dispositivos(modelo, this.casas);
        this.cenarios = new Cenarios(modelo, this.casas);
        this.automacoes = new Automacoes(modelo, this.casas);
        this.escalonamentos = new Escalonamentos(modelo, this.casas);
        this.estatisticas = new Estatisticas(modelo);
        this.persistencia = new Persistencia(modelo);
    }

    public Model.Casa estatisticasGetCasa(String idCasa) {
    return modelo.getCasa(idCasa);
    }
    // --- Tempo ---

    public void avancarTempo(int minutos) {
        modelo.avancarTempo(minutos);
        escalonamentos.verificar();
        automacoes.verificar();
    }

    public java.time.LocalDateTime getTempoAtual() {
        return modelo.getTempoAtual();
    }
}