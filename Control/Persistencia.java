package Control;

import Model.DomusControl;
import java.io.IOException;
import static Control.Exceptions.*;

public class Persistencia {

    private DomusControl modelo;

    public Persistencia(DomusControl modelo) {
        this.modelo = modelo;
    }

    public void guardar(String caminhoFicheiro) {
        try {
            modelo.guardar(caminhoFicheiro);
        } catch (IOException e) {
            throw new ErroAoGuardarException("Erro ao guardar o estado: " + e.getMessage());
        }
    }

    public DomusControl carregar(String caminhoFicheiro) {
        try {
            return DomusControl.carregar(caminhoFicheiro);
        } catch (IOException | ClassNotFoundException e) {
            throw new ErroAoCarregarException("Erro ao carregar o estado: " + e.getMessage());
        }
    }
}