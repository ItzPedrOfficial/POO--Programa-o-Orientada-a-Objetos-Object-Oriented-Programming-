// ============================================================
// Ficheiro: Utilizador.java
// Secção do enunciado: Secção 3 - Os Utilizadores
// Descrição: Representa um utilizador do sistema DomusControl.
//            "Cada utilizador tem acesso às instalações da sua(s) casa(s).
//            Existem para uma mesma casa dois tipos de utilizadores:
//            os que apenas utilizam a aplicação para interagir com os
//            dispositivos [...] e os que administram a instalação da casa."
//            (Secção 3)
// ============================================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizador implements Serializable {

    // ---- Atributos (Secção 3) ----
    private String username;
    private String nome;
    private String email;
    private String password;

    /**
     * Lista de IDs das casas a que o utilizador tem acesso.
     * Secção 3: "Um utilizador do DomusControl terá acesso a todas as
     * casas de que for proprietário ou usufrutuário."
     */
    private List<String> casasIds;

    // ---- Construtores ----

    public Utilizador() {
        this.username = "";
        this.nome = "";
        this.email = "";
        this.password = "";
        this.casasIds = new ArrayList<>();
    }

    public Utilizador(String username, String nome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.casasIds = new ArrayList<>();
    }

    public Utilizador(Utilizador u) {
        this.username = u.username;
        this.nome = u.nome;
        this.email = u.email;
        this.password = u.password;
        this.casasIds = new ArrayList<>(u.casasIds);
    }

    // ---- Getters / Setters ----

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getCasasIds() { return new ArrayList<>(casasIds); }

    // ---- Comportamento ----

    /** Associa uma casa ao utilizador (como proprietário ou usufrutuário) */
    public void adicionarCasa(String casaId) {
        if (!casasIds.contains(casaId)) {
            casasIds.add(casaId);
        }
    }

    public void removerCasa(String casaId) {
        casasIds.remove(casaId);
    }

    public boolean temAcessoA(String casaId) {
        return casasIds.contains(casaId);
    }

    // ---- Métodos de Object ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizador)) return false;
        Utilizador u = (Utilizador) o;
        return Objects.equals(username, u.username);
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", casas=" + casasIds +
                '}';
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }
}
