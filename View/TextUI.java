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
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("PassWord: ");
        String password = sc.nextLine();

        try {
            this.model.autenticar(email, password);
            utilizadorAtual = this.model.autenticar(email, password);
        } catch (UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: Utilizador Nao Encontrado!");
        } catch (PasswordIncorretaException e){
            System.out.println("Erro: Password Incorreta!");
        }
    }
    // preciso que implementem as mensagem exception 

    private void NovoUtilizador(){
        System.out.println("Insira o nome do Utilizador: ");
        String nome = sc.nextLine();
        System.out.println("Insira o email do Utilizador: ");
        String email = sc.nextLine();
        System.out.println("Insira a password do Utilizador: ");
        String password = sc.nextLine();
        
        try {
            this.model.criarUtilizador(nome, email, password);
        } catch (UtilizadorJaExisteException e) {
            System.out.println("Erro: Utilizador Já Existe!");
        }
    }



}
