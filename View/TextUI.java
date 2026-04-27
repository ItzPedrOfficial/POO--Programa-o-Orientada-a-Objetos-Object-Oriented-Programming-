package View;

import java.util.Scanner;
import Control.Controlador;
import Model.Casa;
import Model.Dispositivo;
import Model.Utilizador;
import View.NewMenu; 

public class TextUI {
    private Controlador model;
    private Utilizador utilizadorAtual;
    //scanner 
    private Scanner sc;

    /**
     * Construtor que cria os menus e o model
     */

    public TextUI(Controlador controlador) {
        this.model = controlador;
        this.sc = new Scanner(System.in);
    } 


    public void run(){
        NewMenu menu = new NewMenu(new String[] {"Login","Novo Utilizador"});
        menu.setHandler(1, () -> Login());
        menu.setHandler(2, () -> NovoUtilizador());

        menu.run();

        

    }

    private void Login(){
        System.out.println("Id: ");
        String id = sc.nextLine();
        System.out.println("PassWord: ");
        String password = sc.nextLine();

        try {
            this.model.autenticar(id, password);
            utilizadorAtual = this.model.autenticar(id, password);
            if(!utilizadorAtual.getCasasAdmin().isEmpty()){
                menu.menuAdministrador();
            }else{
                menu.menuUtilizador();
            }
        } catch (UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: Utilizador Nao Encontrado!");
        } catch (PasswordIncorretaException e){
            System.out.println("Erro: Password Incorreta!");
        }
    }
    // preciso que implementem as mensagem exception 

    private void NovoUtilizador(){
        System.out.println("Insira o Id do Utilizador: ");
        String id = sc.nextLine();
        System.out.println("Insira a password do Utilizador: ");
        String password = sc.nextLine();
        
        try {
            this.model.criarUtilizador(id, password);
        } catch (UtilizadorJaExisteException e) {
            System.out.println("Erro: Utilizador Já Existe!");
        }
    }

    private void menuAdministrador(){
        NewMenu menuAd = new NewMenu(new String[] {"Criar Casa","Gerir Casa","Apagar Casa"});
        menuAd.setPreCondition(2,  () -> !utilizadorAtual.getCasasAdmin().isEmpty());
        menuAd.setPreCondition(3,  () -> !utilizadorAtual.getCasasAdmin().isEmpty());
        menuAd.setHandler(1, () -> criaCasaAdmin());
        menuAd.setHandler(2, () -> modificaCasaAdmin());
        menuAd.setHandler(3, () -> apagaCasasAdmin());

        menuAd.run();
    }

    private void criaCasaAdmin(){

    }

    private void modificaCasaAdmin(){

    }

    private void apagaCasasAdmin(){

    }

    private void menuUtilizador(){

    }



}
