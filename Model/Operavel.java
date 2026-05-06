package Model;

import java.time.LocalDateTime;

public interface Operavel {
    void executarOperacao(LocalDateTime agora, String operacao, Object valor);
    
}
