package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Utilizador implements Serializable {
    private String id;
    private String password;
    private Set<String> casasAcessiveis;
    private Set<String> casasAdmin;

    // --- Construtores ---
    
    /**
     * Construtor vazio
     */
    public Utilizador(){
        this.id = "";
        this.password = "";
        this.casasAcessiveis = new HashSet<>();
        this.casasAdmin = new HashSet<>();
    }

    /**
     * Construtor parametrizado
     * 
     * @param id
     * @param password
     * @param casasAcessiveis
     * @param casasAdmin
     */
    public Utilizador(String id, String password, Set<String> casasAcessiveis, Set<String> casasAdmin){
        this.id = id;
        this.password = password;
        this.casasAcessiveis = new HashSet<>(casasAcessiveis);
        this.casasAdmin = new HashSet<>(casasAdmin);
    }

    /**
     * Construtor de cópia
     * 
     * @param u utilizador a copiar
     */
    public Utilizador(Utilizador u){
        this.id = u.getId();
        this.password = u.getPassword();
        this.casasAcessiveis = u.getCasasAcessiveis();
        this.casasAdmin = u.getCasasAdmin();
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void addCasasAcessiveis(String casa) {
        this.casasAcessiveis.add(casa);
    }
    
    public void removeCasasAcessiveis(String casa) {
        if (this.casasAdmin.contains(casa)) this.casasAdmin.remove(casa);
        this.casasAcessiveis.remove(casa);
    }

    public Set<String> getCasasAcessiveis() {
        return new HashSet<>(this.casasAcessiveis);
    }

    public void setCasasAcessiveis(Set<String> casasAcessiveis) {
        this.casasAcessiveis = new HashSet<>(casasAcessiveis);
    }

    public void addCasasAdmin(String casa) {
        if (!this.casasAcessiveis.contains(casa)) this.casasAcessiveis.add(casa);
        this.casasAdmin.add(casa);
    }
    
    public void removeCasasAdmin(String casa) {
        this.casasAdmin.remove(casa);
    }

    public Set<String> getCasasAdmin() {
        return new HashSet<>(this.casasAdmin);
    }

    public void setCasasAdmin(Set<String> casasAdmin) {
        this.casasAdmin = new HashSet<>(casasAdmin);
    }

    // --- Overrides de Object ---
    
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Utilizador utilizador = (Utilizador) o;
        
        return this.id.equals(utilizador.getId()) &&
               this.password.equals(utilizador.getPassword()) &&
               this.casasAcessiveis.equals(utilizador.getCasasAcessiveis()) &&
               this.casasAdmin.equals(utilizador.getCasasAdmin());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("\n");
        sb.append(password).append("\n");
        sb.append(casasAcessiveis.toString()).append("\n");
        sb.append(casasAdmin.toString());
        return sb.toString();
    }

    @Override
    public Utilizador clone(){
        return new Utilizador(this);
    }
}
