package View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import Model.Exceptions.*;
import Control.Controlador;
import Model.*;

public class TextUI {

    private Controlador controlador;
    private Utilizador utilizadorAtual;
    private Scanner sc;

    public TextUI(Controlador controlador) {
        this.controlador = controlador;
        this.sc = new Scanner(System.in);
    }

    // -------------------------------------------------------------------------
    // Entrada
    // -------------------------------------------------------------------------

    public void run() {
        NewMenu menu = new NewMenu(new String[] { "Login", "Novo Utilizador" });
        menu.setHandler(1, () -> login());
        menu.setHandler(2, () -> novoUtilizador());
        menu.run();
    }

    private void login() {
        System.out.print("Id: ");
        String id = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            utilizadorAtual = controlador.autenticar(id, password);
            menuAdministrador();
        } catch (UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (PasswordIncorretaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void novoUtilizador() {
        System.out.print("Id do utilizador: ");
        String id = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            controlador.criarUtilizador(id, password);
            System.out.println("Utilizador criado com sucesso!");
        } catch (UtilizadorJaExisteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Menu Administrador
    // -------------------------------------------------------------------------

    private void menuAdministrador() {
        NewMenu menu = new NewMenu(new String[] {
                "Criar Casa",
                "Gerir Casa",
                "Aceder como utilizador"
        });
        menu.setPreCondition(2, () -> !utilizadorAtual.getCasasAdmin().isEmpty());
        menu.setPreCondition(3, () -> !utilizadorAtual.getCasasAcessiveis().isEmpty());
        menu.setHandler(1, () -> criarCasa());
        menu.setHandler(2, () -> escolherCasaAdmin());
        menu.setHandler(3, () -> menuUtilizador());
        menu.run();
    }

    private void criarCasa() {
        System.out.print("Id da casa: ");
        String idCasa = sc.nextLine();
        System.out.print("Morada: ");
        String morada = sc.nextLine();

        try {
            controlador.criarCasa(utilizadorAtual.getId(), idCasa, morada);
            utilizadorAtual = controlador.autenticar(
                    utilizadorAtual.getId(), utilizadorAtual.getPassword());
            System.out.println("Casa criada com sucesso!");
        } catch (CasaJaExisteException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (UtilizadorNaoEncontradoException | PasswordIncorretaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void escolherCasaAdmin() {
        Set<String> casas = utilizadorAtual.getCasasAdmin();
        if (casas.isEmpty()) {
            System.out.println("Não tens casas para gerir.");
            return;
        }

        List<String> lista = new ArrayList<>(casas);
        NewMenu menu = new NewMenu(lista.toArray(new String[0]));
        for (int i = 0; i < lista.size(); i++) {
            String nomeCasa = lista.get(i);
            menu.setHandler(i + 1, () -> menuCasaAdmin(nomeCasa));
        }
        menu.run();
    }

    private void menuCasaAdmin(String idCasa) {
        NewMenu menu = new NewMenu(new String[] {
                "Adicionar Divisão",
                "Criar Dispositivo",
                "Associar Dispositivo a Divisão",
                "Listar Dispositivos",
                "Adicionar Utilizador à Casa",
                "Gerir Dispositivos",
                "Criar Cenário",
                "Ativar Cenário",
                "Avançar Tempo",
                "Estatísticas"
        });
        menu.setHandler(1, () -> adicionarDivisao(idCasa));
        menu.setHandler(2, () -> menuCriarDispositivo(idCasa));
        menu.setHandler(3, () -> associarDispositivoADivisao(idCasa));
        menu.setHandler(4, () -> listarDispositivos(idCasa));
        menu.setHandler(5, () -> adicionarUtilizadorACasa(idCasa));
        menu.setHandler(6, () -> menuGerirDispositivos(idCasa));
        menu.setHandler(7, () -> criarCenario(idCasa));
        menu.setHandler(8, () -> ativarCenario(idCasa));
        menu.setHandler(9, () -> avancarTempo());
        menu.setHandler(10, () -> menuEstatisticas(idCasa));
        menu.run();
    }

    // -------------------------------------------------------------------------
    // Divisões
    // -------------------------------------------------------------------------

    private void adicionarDivisao(String idCasa) {
        System.out.print("Nome da divisão: ");
        String nomeDiv = sc.nextLine();
        try {
            controlador.adicionarDivisao(utilizadorAtual.getId(), idCasa, nomeDiv);
            System.out.println("Divisão adicionada com sucesso!");
        }  catch (PermissaoNegadaException | CasaNaoEncontradaException | UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Criar dispositivo
    // -------------------------------------------------------------------------

    private void menuCriarDispositivo(String idCasa) {
        NewMenu menu = new NewMenu(new String[] {
                "Lâmpada",
                "Lâmpada Colorida",
                "Coluna de Som",
                "Portoide",
                "Detetor"
        });
        menu.setHandler(1, () -> criarDispositivo(idCasa, "Lampada"));
        menu.setHandler(2, () -> criarDispositivo(idCasa, "LampadaColorida"));
        menu.setHandler(3, () -> criarDispositivo(idCasa, "ColunaDeSom"));
        menu.setHandler(4, () -> criarDispositivo(idCasa, "Portoide"));
        menu.setHandler(5, () -> criarDispositivo(idCasa, "Detetor"));
        menu.run();
    }

    private void criarDispositivo(String idCasa, String tipo) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Consumo (Wh): ");
        int consumo = lerInt();

        try {
            switch (tipo) {
                case "Lampada" -> controlador.criarLampada(
                        utilizadorAtual.getId(), idCasa, id, marca, modelo, consumo);
                case "LampadaColorida" -> controlador.criarLampadaColorida(
                        utilizadorAtual.getId(), idCasa, id, marca, modelo, consumo);
                case "ColunaDeSom" -> controlador.criarColunaDeSom(
                        utilizadorAtual.getId(), idCasa, id, marca, modelo, consumo);
                case "Portoide" -> controlador.criarPortoide(
                        utilizadorAtual.getId(), idCasa, id, marca, modelo, consumo);
                case "Detetor" -> controlador.criarDetetor(
                        utilizadorAtual.getId(), idCasa, id, marca, modelo, consumo);
            }
            System.out.println("Dispositivo criado com sucesso!");
        } catch (PermissaoNegadaException | CasaNaoEncontradaException |
                 UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void associarDispositivoADivisao(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String idDisp = sc.nextLine();
        System.out.print("Nome da divisão: ");
        String nomeDiv = sc.nextLine();

        try {
            controlador.associarDispositivoADivisao(
                    utilizadorAtual.getId(), idCasa, idDisp, nomeDiv);
            System.out.println("Dispositivo associado!");
        } catch (DivisaoNaoEncontradaException | PermissaoNegadaException |
                 CasaNaoEncontradaException | UtilizadorNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarDispositivos(String idCasa) {
        try {
            Casa casa = controlador.getCasa(utilizadorAtual.getId(), idCasa);
            Map<String, Dispositivo> dispositivos = casa.getDispositivos();
            if (dispositivos.isEmpty()) {
                System.out.println("Esta casa não tem dispositivos.");
                return;
            }
            for (Dispositivo d : dispositivos.values()) {
                System.out.println("---");
                System.out.println(d);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void adicionarUtilizadorACasa(String idCasa) {
        System.out.print("Id do utilizador a adicionar: ");
        String idUtil = sc.nextLine();

        try {
            controlador.adicionarUtilizadorACasa(utilizadorAtual.getId(), idUtil, idCasa);
            System.out.println("Utilizador adicionado à casa!");
        } catch (UtilizadorNaoEncontradoException | PermissaoNegadaException |
                 CasaNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Menu Utilizador
    // -------------------------------------------------------------------------

    private void menuUtilizador() {
        Set<String> casas = utilizadorAtual.getCasasAcessiveis();
        if (casas.isEmpty()) {
            System.out.println("Não tens casas acessíveis.");
            return;
        }

        List<String> lista = new ArrayList<>(casas);
        NewMenu menu = new NewMenu(lista.toArray(new String[0]));
        for (int i = 0; i < lista.size(); i++) {
            String idCasa = lista.get(i);
            menu.setHandler(i + 1, () -> menuCasaUtilizador(idCasa));
        }
        menu.run();
    }

    private void menuCasaUtilizador(String idCasa) {
        NewMenu menu = new NewMenu(new String[] {
                "Gerir Dispositivos",
                "Ativar Cenário",
                "Avançar Tempo",
                "Listar Dispositivos"
        });
        menu.setHandler(1, () -> menuGerirDispositivos(idCasa));
        menu.setHandler(2, () -> ativarCenario(idCasa));
        menu.setHandler(3, () -> avancarTempo());
        menu.setHandler(4, () -> listarDispositivos(idCasa));
        menu.run();
    }

    // -------------------------------------------------------------------------
    // Operar dispositivos
    // -------------------------------------------------------------------------

    private void menuGerirDispositivos(String idCasa) {
        NewMenu menu = new NewMenu(new String[] {
                "Ligar dispositivo",
                "Desligar dispositivo",
                "Ajustar luminosidade (Lâmpada)",
                "Ajustar cor (Lâmpada Colorida)",
                "Ajustar volume (Coluna)",
                "Ajustar abertura (Portoide)",
                "Abrir portão",
                "Fechar portão"
        });
        menu.setHandler(1, () -> ligarDispositivo(idCasa));
        menu.setHandler(2, () -> desligarDispositivo(idCasa));
        menu.setHandler(3, () -> ajustarLuminosidade(idCasa));
        menu.setHandler(4, () -> ajustarCor(idCasa));
        menu.setHandler(5, () -> ajustarVolume(idCasa));
        menu.setHandler(6, () -> ajustarAbertura(idCasa));
        menu.setHandler(7, () -> abrirPortao(idCasa));
        menu.setHandler(8, () -> fecharPortao(idCasa));
        menu.run();
    }

    private void ligarDispositivo(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        try {
            controlador.ligarDispositivo(utilizadorAtual.getId(), idCasa, id);
            System.out.println("Dispositivo ligado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void desligarDispositivo(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        try {
            controlador.desligarDispositivo(utilizadorAtual.getId(), idCasa, id);
            System.out.println("Dispositivo desligado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void ajustarLuminosidade(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        System.out.print("Luminosidade (0-100): ");
        int v = lerInt();
        try {
            controlador.ajustarLuminosidade(utilizadorAtual.getId(), idCasa, id, v);
            System.out.println("Luminosidade ajustada.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void ajustarCor(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        System.out.print("Cor: ");
        String cor = sc.nextLine();
        try {
            controlador.ajustarCor(utilizadorAtual.getId(), idCasa, id, cor);
            System.out.println("Cor ajustada.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void ajustarVolume(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        System.out.print("Volume (0-100): ");
        int v = lerInt();
        try {
            controlador.ajustarVolume(utilizadorAtual.getId(), idCasa, id, v);
            System.out.println("Volume ajustado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void ajustarAbertura(String idCasa) {
        System.out.print("Id do dispositivo: ");
        String id = sc.nextLine();
        System.out.print("Abertura (0-100): ");
        int v = lerInt();
        try {
            controlador.ajustarAbertura(utilizadorAtual.getId(), idCasa, id, v);
            System.out.println("Abertura ajustada.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void abrirPortao(String idCasa) {
        System.out.print("Id do portão: ");
        String id = sc.nextLine();
        try {
            controlador.abrirPortao(utilizadorAtual.getId(), idCasa, id);
            System.out.println("Portão aberto.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void fecharPortao(String idCasa) {
        System.out.print("Id do portão: ");
        String id = sc.nextLine();
        try {
            controlador.fecharPortao(utilizadorAtual.getId(), idCasa, id);
            System.out.println("Portão fechado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Cenários
    // -------------------------------------------------------------------------

    private void criarCenario(String idCasa) {
        System.out.print("Nome do cenário: ");
        String nome = sc.nextLine();
        try {
            controlador.criarCenario(utilizadorAtual.getId(), idCasa, nome, new HashSet<>());
            System.out.println("Cenário criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void ativarCenario(String idCasa) {
        System.out.print("Nome do cenário: ");
        String nome = sc.nextLine();
        try {
            controlador.ativarCenario(utilizadorAtual.getId(), idCasa, nome);
            System.out.println("Cenário ativado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Tempo e Estatísticas
    // -------------------------------------------------------------------------

    private void avancarTempo() {
        System.out.print("Avançar quantos minutos: ");
        int min = lerInt();
        controlador.avancarTempo(min);
        System.out.println("Tempo atual: " + controlador.getTempoAtual());
    }

    private void menuEstatisticas(String idCasa) {
        NewMenu menu = new NewMenu(new String[] {
                "Casa que mais consome",
                "Top 3 dispositivos por tempo",
                "Top 3 dispositivos por ativações",
                "Top 3 divisões com mais dispositivos"
        });
        menu.setHandler(1, () -> {
            try {
                System.out.println(controlador.getCasaQueMaisConsome());
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        });
        menu.setHandler(2, () -> {
            try {
                controlador.getTresDispositivosMaisUtilizadosPorTempo(utilizadorAtual.getId(), idCasa)
                        .forEach(d -> System.out.println("---\n" + d));
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        });
        menu.setHandler(3, () -> {
            try {
                controlador.getTresDispositivosMaisUtilizadosPorAtivacoes(utilizadorAtual.getId(), idCasa)
                        .forEach(d -> System.out.println("---\n" + d));
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        });
        menu.setHandler(4, () -> {
            try {
                controlador.getTresDivisoesComMaisDispositivos(utilizadorAtual.getId(), idCasa)
                        .forEach(d -> System.out.println("---\n" + d));
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        });
        menu.run();
    }

    // -------------------------------------------------------------------------
    // Auxiliares
    // -------------------------------------------------------------------------

    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}