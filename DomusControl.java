// ============================================================
// Ficheiro: DomusControl.java
// Secção do enunciado: Secção 7 (Funcionamento), Secção 8.1 e 8.2
// Descrição: Classe computacional principal do sistema DomusControl.
//            Gere todas as entidades: utilizadores, casas, dispositivos,
//            automações, escalonamentos e cenários.
//            Secção 8.1: gestão base de entidades.
//            Secção 8.2: estatísticas (casa que mais consome, etc.).
//            Secção 10: salvaguarda do estado em ficheiro binário.
//            Nota: "A solução [...] deverá permitir que se possam
//            acrescentar novos tipos de dispositivos [...] sem obrigar
//            a alterar o código da classe computacional principal." (Secção 2)
// ============================================================

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DomusControl implements Serializable {

    // ---- Estado global do sistema ----

    /** Todos os utilizadores registados no sistema */
    private Map<String, Utilizador> utilizadores;

    /** Todas as casas do sistema, por ID */
    private Map<String, Casa> casas;

    /** Automações por casaId */
    private Map<String, List<Automacao>> automacoesPorCasa;

    /** Escalonamentos por casaId */
    private Map<String, List<Escalonamento>> escalonamentosPorCasa;

    /** Cenários por username de utilizador */
    private Map<String, List<Cenario>> cenariosPorUtilizador;

    /**
     * Simulação temporal interna.
     * Secção 7: "é necessário que de alguma forma simulem a passagem do tempo.
     * Uma solução simples é ter internamente variáveis que representem
     * uma data/hora"
     */
    private String dataHoraActual;  // formato "yyyy-MM-dd HH:mm"

    // ---- Construtor ----

    public DomusControl() {
        this.utilizadores = new HashMap<>();
        this.casas = new HashMap<>();
        this.automacoesPorCasa = new HashMap<>();
        this.escalonamentosPorCasa = new HashMap<>();
        this.cenariosPorUtilizador = new HashMap<>();
        this.dataHoraActual = "2026-01-01 08:00";
    }

    // ================================================================
    // SECÇÃO 8.1 - Requisitos base de gestão das entidades
    // ================================================================

    /** Regista um novo utilizador no sistema */
    public boolean adicionarUtilizador(Utilizador u) {
        if (utilizadores.containsKey(u.getUsername())) return false;
        utilizadores.put(u.getUsername(), u.clone());
        return true;
    }

    public Utilizador getUtilizador(String username) {
        Utilizador u = utilizadores.get(username);
        return u != null ? u.clone() : null;
    }

    /** Cria uma nova casa no sistema */
    public boolean adicionarCasa(Casa c) {
        if (casas.containsKey(c.getId())) return false;
        casas.put(c.getId(), c.clone());
        automacoesPorCasa.put(c.getId(), new ArrayList<>());
        escalonamentosPorCasa.put(c.getId(), new ArrayList<>());
        return true;
    }

    public Casa getCasa(String casaId) {
        Casa c = casas.get(casaId);
        return c != null ? c.clone() : null;
    }

    /**
     * Adiciona um dispositivo a uma divisão de uma casa.
     * Secção 7, ponto 2: "deverá ser possível adicionar um dispositivo
     * a uma divisão de uma casa"
     */
    public boolean adicionarDispositivoADivisao(String casaId, String divisaoId,
                                                 Dispositivo dispositivo) {
        Casa casa = casas.get(casaId);
        if (casa == null) return false;
        Divisao divisao = casa.getDivisao(divisaoId);
        if (divisao == null) return false;
        divisao.adicionarDispositivo(dispositivo);
        // actualizar a casa no mapa (defensive copy)
        casas.put(casaId, casa);
        return true;
    }

    // ================================================================
    // SECÇÃO 8.2 - Estatísticas
    // ================================================================

    /**
     * Retorna a casa que mais consome (dispositivos ligados).
     * Secção 8.2, ponto 1: "saber qual é a casa que mais consome"
     */
    public Casa casaQueMAisConsome() {
        return casas.values().stream()
                .max(Comparator.comparingInt(Casa::calcularConsumoTotal))
                .map(Casa::clone)
                .orElse(null);
    }

    /**
     * Retorna as três divisões (e respectiva casa) com mais dispositivos.
     * Secção 8.2, ponto 3: "quais as três divisões [...] que possuem mais
     * dispositivos"
     */
    public List<String> tresivisoesComMaisDispositivos() {
        List<String> resultado = new ArrayList<>();
        casas.values().forEach(casa ->
            casa.getDivisoes().forEach(div ->
                resultado.add(casa.getId() + " > " + div.getNome()
                        + " (" + div.numeroDispositivos() + " dispositivos)")
            )
        );
        resultado.sort((a, b) -> {
            int na = Integer.parseInt(a.replaceAll(".*\\((\\d+).*", "$1"));
            int nb = Integer.parseInt(b.replaceAll(".*\\((\\d+).*", "$1"));
            return nb - na;
        });
        return resultado.size() > 3 ? resultado.subList(0, 3) : resultado;
    }

    // ================================================================
    // SECÇÃO 4 - Automações
    // ================================================================

    public boolean adicionarAutomacao(String casaId, Automacao a) {
        List<Automacao> lista = automacoesPorCasa.get(casaId);
        if (lista == null) return false;
        lista.add(a.clone());
        return true;
    }

    public List<Automacao> getAutomacoes(String casaId) {
        List<Automacao> lista = automacoesPorCasa.get(casaId);
        if (lista == null) return new ArrayList<>();
        return lista.stream().map(Automacao::clone).collect(Collectors.toList());
    }

    // ================================================================
    // SECÇÃO 5 - Escalonamentos
    // ================================================================

    public boolean adicionarEscalonamento(String casaId, Escalonamento e) {
        List<Escalonamento> lista = escalonamentosPorCasa.get(casaId);
        if (lista == null) return false;
        lista.add(e.clone());
        return true;
    }

    public List<Escalonamento> getEscalonamentos(String casaId) {
        List<Escalonamento> lista = escalonamentosPorCasa.get(casaId);
        if (lista == null) return new ArrayList<>();
        return lista.stream().map(Escalonamento::clone).collect(Collectors.toList());
    }

    // ================================================================
    // SECÇÃO 6 - Cenários
    // ================================================================

    public void adicionarCenario(String username, Cenario c) {
        cenariosPorUtilizador
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add(c.clone());
    }

    public List<Cenario> getCenarios(String username) {
        List<Cenario> lista = cenariosPorUtilizador.get(username);
        if (lista == null) return new ArrayList<>();
        return lista.stream().map(Cenario::clone).collect(Collectors.toList());
    }

    // ================================================================
    // SECÇÃO 7 - Simulação do tempo
    // ================================================================

    public String getDataHoraActual() {
        return dataHoraActual;
    }

    /**
     * Avança o tempo interno da simulação em minutos.
     * Secção 7: "existam métodos que façam avançar o tempo"
     */
    public void avancarTempo(int minutos) {
        // Parsing simples de "yyyy-MM-dd HH:mm"
        String[] partes = dataHoraActual.split(" ");
        String[] hm = partes[1].split(":");
        int h = Integer.parseInt(hm[0]);
        int m = Integer.parseInt(hm[1]);
        m += minutos;
        h += m / 60;
        m = m % 60;
        h = h % 24;
        dataHoraActual = partes[0] + String.format(" %02d:%02d", h, m);
        verificarEscalonamentos();
    }

    /** Verifica e activa escalonamentos para a hora actual */
    private void verificarEscalonamentos() {
        String horaActual = dataHoraActual.split(" ")[1];
        escalonamentosPorCasa.forEach((casaId, lista) ->
            lista.stream()
                 .filter(e -> e.deveActivar(horaActual))
                 .forEach(e -> System.out.println("[Escalonamento activado] " + e.getNome()
                         + " na casa " + casaId))
        );
    }

    // ================================================================
    // SECÇÃO 10 - Salvaguarda do estado em ficheiro
    // ================================================================

    /**
     * Grava o estado actual do sistema em ficheiro binário.
     * Secção 10: "O programa deve permitir que em qualquer momento se
     * possa guardar em ficheiro a informação existente em memória"
     */
    public void gravarEstado(String caminhoFicheiro) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(caminhoFicheiro))) {
            oos.writeObject(this);
        }
    }

    /**
     * Carrega o estado do sistema a partir de ficheiro binário.
     * Secção 10: "A gravação deve ser feita de forma a permitir que
     * o estado que foi gravado seja recuperado novamente."
     */
    public static DomusControl carregarEstado(String caminhoFicheiro)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(caminhoFicheiro))) {
            return (DomusControl) ois.readObject();
        }
    }

    @Override
    public String toString() {
        return "DomusControl{" +
                "utilizadores=" + utilizadores.size() +
                ", casas=" + casas.size() +
                ", dataHora='" + dataHoraActual + '\'' +
                '}';
    }
}
