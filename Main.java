import Control.Controlador;
import View.TextUI;
import Model.Casa;
import Model.Dispositivo;
import Model.Utilizador;

public class Main {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo);
        TextUI ui = new TextUI(controlador);
        ui.run();
    }
}
