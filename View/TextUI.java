package View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
            utilizadorAtual = this.model.autenticar(id, password);
            if(!utilizadorAtual.getCasasAdmin().isEmpty()){
                menuAdministrador();
            }else{
                menuUtilizador();
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

    //Menu Administrador 

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
        System.out.println("Morada da casa: ");
        String morada = sc.nextLine();
        System.out.println("Nome da casa: ");
        String nomeCasa = sc.nextLine();

    try {
        // utilizadorAtual é o dono
        this.model.criarCasa(nomeCasa,morada,utilizadorAtual.getId());
        System.out.println("Casa criada com sucesso!");
        } catch (UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: Utilizador inválido");
        } catch (CasaJaExisteException e) {
            System.out.println("Erro: Já existe uma casa com essa morada!");
        }
    }

    private void modificaCasaAdmin(){
        Set<String> casas = utilizadorAtual.getCasasAdmin();

        if (casas.isEmpty()) {
            System.out.println("Não tens casas para gerir.");
            return;
        }

        List<String> listaCasas = new ArrayList<>(casas);

        NewMenu menu = new NewMenu(listaCasas.toArray(new String[0]));

        for (int i = 0; i < listaCasas.size(); i++) {
            String nomeCasa = listaCasas.get(i);
            menu.setHandler(i + 1, () -> menuCasaAdmin(nomeCasa));
        }

        menu.run();
    }

    private void menuCasaAdmin(String nomeCasa){
        NewMenu menu = new NewMenu(new String[] {
            "Adicionar Divisão",
            "Associar Dispositivo",
            "Listar Dispositivos"
        });

        menu.setHandler(1, () -> adicionarDivisao(nomeCasa));
        menu.setHandler(2, () -> associarDispositivo(nomeCasa));
        menu.setHandler(3, () -> listarDispositivos(nomeCasa));

        menu.run();
    }

    private void adicionarDivisao(String moradaCasa){
        System.out.println("Nome da divisão:");
        String nomeDiv = sc.nextLine();

        try {
            model.adicionarDivisao(utilizadorAtual.getId(), moradaCasa, nomeDiv);
            System.out.println("Divisão adicionada com sucesso!");
        } catch (DivisaoJaExisteException e) {
            System.out.println("Erro: Divisão já existe!");
        }
    }

    private void associarDispositivo(String moradaCasa){

    }

    private void listarDispositivos(String moradaCasa){

    }

    private void apagaCasasAdmin(){
        Set<String> casas = utilizadorAtual.getCasasAdmin();

        if (casas.isEmpty()) {
            System.out.println("Não tens casas para apagar!.");
            return;
        }

        List<String> listaCasas = new ArrayList<>(casas);

        NewMenu menu = new NewMenu(listaCasas.toArray(new String[0]));

        for (int i = 0; i < listaCasas.size(); i++) {
            String nomeCasa = listaCasas.get(i);
            menu.setHandler(i + 1, () -> apagarCasa(nomeCasa));
        }

        menu.run();
    }

    private void apagarCasa(String nomeCasa){
        //preciso do apagarCasa do controlador 
    }


    // Menu Utilizador
    private void menuUtilizador(){
        Set<String> casas = utilizadorAtual.getCasasAcessiveis();

        if (casas.isEmpty()) {
            System.out.println("Não tens casas para gerir.");
            return;
        }

        List<String> listaCasas = new ArrayList<>(casas);

        NewMenu menu = new NewMenu(listaCasas.toArray(new String[0]));

        for (int i = 0; i < listaCasas.size(); i++) {
            String nomeCasa = listaCasas.get(i);
            menu.setHandler(i + 1, () -> menuCasaUtilizador(nomeCasa));
        }

        menu.run();
    }

    private void menuCasaUtilizador(String nomeCasa){
        NewMenu menu = new NewMenu(new String[] {
            "Gerir Dispositivos",
            "Ativar Cenário",
            "Avançar Tempo"
        });

        menu.setHandler(1, () -> nemuGerirDispositivos(nomeCasa));
        menu.setHandler(2, () -> ativarCenario(nomeCasa));
        menu.setHandler(3, () -> tempoInput(nomeCasa));

        menu.run();

    }
    
    private void ativarCenario(String nomeCasa){}

    private void tempoInput(String nomeCasa){}

    private void nemuGerirDispositivos(String nomeCasa){
        NewMenu menu = new NewMenu(new String[] {
            "Ver os dispositivos da casa",
            "Ligar dispositivo",
            "Desligar dispositivo"
        });

        menu.setHandler(1, () -> verDispositivosCasa(nomeCasa));
        menu.setHandler(2, () -> ligarDispositivo(nomeCasa));
        menu.setHandler(3, () -> desligarDispositivo(nomeCasa));

        menu.run();
    }

    private void verDispositivosCasa(String nomeCasa){
        Map<String, Dispositivo> dispositivos = model.getDispositivosDaCasa(nomeCasa);

        for (Dispositivo d : dispositivos.values()) {
        System.out.println(d);
        }
    }

    private void ligarDispositivo(String nomeCasa){}

    private void desligarDispositivo(String nomeCasa){}


}
