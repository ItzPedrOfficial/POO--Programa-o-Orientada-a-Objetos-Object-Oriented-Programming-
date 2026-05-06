package Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class EscalonamentoDiário extends Escalonamento{

    // --- Construtores ---

    public EscalonamentoDiário() {
        super();
    }

    public EscalonamentoDiário(String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim) {
        super(nome, acoes, inicio, fim);
    }
    
    public EscalonamentoDiário(EscalonamentoDiário esc) {
        super(esc);
        
    }
    
    // --- Comportamentos ---
    
    public boolean deveExecutar(LocalDateTime agora) {
        return agora.toLocalTime().isAfter(this.getInicio()) && agora.toLocalTime().isBefore(this.getFim());
    }

    // --- Overrides de Object ---
    
    @Override
    public EscalonamentoDiário clone(){
        return new EscalonamentoDiário(this);
    }
}
