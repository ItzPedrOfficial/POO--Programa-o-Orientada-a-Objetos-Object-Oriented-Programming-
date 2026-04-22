package AI;
// ============================================================
// Ficheiro: Administrador.java
// Secção do enunciado: Secção 3 - Os Utilizadores
// Descrição: Utilizador com permissões de administrador numa casa.
//            "os que administram a instalação da casa. Estes últimos
//            são responsáveis por criar a casa, definir as divisões
//            e associar os dispositivos às mesmas." (Secção 3)
//            Nota: "um utilizador pode ser administrador numa casa
//            e apenas utilizador numa outra."
// ============================================================

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Utilizador {

    /**
     * Lista de IDs das casas onde este utilizador é administrador.
     * Secção 3: pode ser admin numa casa e só user noutra.
     */
    private List<String> casasAdminIds;

    // ---- Construtores ----

    public Administrador() {
        super();
        this.casasAdminIds = new ArrayList<>();
    }

    public Administrador(String username, String nome, String email, String password) {
        super(username, nome, email, password);
        this.casasAdminIds = new ArrayList<>();
    }

    public Administrador(Administrador a) {
        super(a);
        this.casasAdminIds = new ArrayList<>(a.casasAdminIds);
    }

    // ---- Comportamento específico de admin ----

    public void adicionarCasaAdmin(String casaId) {
        if (!casasAdminIds.contains(casaId)) {
            casasAdminIds.add(casaId);
            adicionarCasa(casaId); // também tem acesso normal
        }
    }

    public boolean isAdminDe(String casaId) {
        return casasAdminIds.contains(casaId);
    }

    public List<String> getCasasAdminIds() {
        return new ArrayList<>(casasAdminIds);
    }

    // ---- Métodos de Object ----

    @Override
    public String toString() {
        return "Administrador{" + super.toString() +
                ", casasAdmin=" + casasAdminIds +
                '}';
    }

    @Override
    public Administrador clone() {
        return new Administrador(this);
    }
}
