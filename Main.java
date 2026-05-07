import Control.Controlador;
import Model.DomusControl;
import View.TextUI;

public class Main {
    public static void main(String[] args) {
        DomusControl modelo = new DomusControl();
        Controlador controlador = new Controlador(modelo);
        TextUI ui = new TextUI(controlador);
        ui.run();
    }
}