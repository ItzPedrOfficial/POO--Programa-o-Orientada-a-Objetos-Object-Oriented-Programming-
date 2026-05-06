package Model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Acao implements Serializable{
    private String operacao;
    private Object valor;

    // --- Construtores ---

    public Acao() {
        this.operacao = "";
        this.valor = 0;
    }
    
    public Acao(String operacao, Object valor) {
        this.operacao = operacao;
        this.valor = valor;
    }

    public Acao(Acao ad) {
        this.operacao = ad.getOperacao();
        this.valor = ad.getValor();
    }

    // --- Getters e Setters ---

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    // --- Overrides de Object ---

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Acao ad = (Acao) o;
        
        return this.operacao.equals(ad.getOperacao()) &&
               this.valor.equals(ad.getValor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operacao, valor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Operacao: ").append(operacao).append("\n");
        sb.append("Valor: ").append(valor);
        return sb.toString();
    }

    @Override
    public abstract Acao clone();
}
