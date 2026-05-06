package Model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class EscalonamentoSemanal extends Escalonamento{
    private DayOfWeek diaDaSemana;

    public EscalonamentoSemanal() {
        this.diaDaSemana = LocalDateTime.now().getDayOfWeek();
    }

    public EscalonamentoSemanal(String nome, Set<Acao> acoes, LocalTime inicio, LocalTime fim, DayOfWeek diaDaSemana) {
        super(nome, acoes, inicio, fim);
        this.diaDaSemana = diaDaSemana;
    }

    public EscalonamentoSemanal(EscalonamentoSemanal esc) {
        super(esc);
        this.diaDaSemana = esc.getDiaDaSemana();
    }

    // --- Getters e Setters ---

    public DayOfWeek getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(DayOfWeek diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }
    
    // --- Comportamentos ---
    
    public boolean deveExecutar(LocalDateTime agora) {
        return agora.toLocalTime().isAfter(this.getInicio()) && agora.toLocalTime().isBefore(this.getFim()) && agora.getDayOfWeek().equals(diaDaSemana);
    }

    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        EscalonamentoSemanal escalonamento = (EscalonamentoSemanal) o;
        
        return super.equals(escalonamento) &&
               this.diaDaSemana.equals(escalonamento.getDiaDaSemana());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Dia da Semana: ").append(diaDaSemana.toString());
        return sb.toString();
    }

    @Override
    public EscalonamentoSemanal clone(){
        return new EscalonamentoSemanal(this);
    }


}
