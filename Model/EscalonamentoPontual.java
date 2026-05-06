package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class EscalonamentoPontual extends Escalonamento{
    private LocalDate data;

    public EscalonamentoPontual() {
        this.data = LocalDateTime.now().toLocalDate();
    }

    public EscalonamentoPontual(String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim, LocalDate data) {
        super(nome, acoes, inicio, fim);
        this.data = data;
    }

    public EscalonamentoPontual(EscalonamentoPontual esc) {
        super(esc);
        this.data = esc.getData();
    }

    // --- Getters e Setters ---

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    
    // --- Comportamentos ---
    
    public boolean deveExecutar(LocalDateTime agora) {
        return agora.toLocalTime().isAfter(this.getInicio()) && agora.toLocalTime().isBefore(this.getFim()) && agora.toLocalDate().equals(data);
    }

    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        EscalonamentoPontual escalonamento = (EscalonamentoPontual) o;
        
        return super.equals(escalonamento) &&
               this.data.equals(escalonamento.getData());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Data: ").append(data.toString());
        return sb.toString();
    }

    @Override
    public EscalonamentoPontual clone(){
        return new EscalonamentoPontual(this);
    }

}
