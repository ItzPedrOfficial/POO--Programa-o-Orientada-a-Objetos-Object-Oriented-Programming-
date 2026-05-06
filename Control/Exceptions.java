package Control;

public final class Exceptions {

    private Exceptions() {} // não instanciável

    // =========================================================================
    // Utilizadores
    // =========================================================================

    public static class UtilizadorJaExisteException extends RuntimeException {
        public UtilizadorJaExisteException(String msg) { super(msg); }
    }

    public static class UtilizadorNaoEncontradoException extends RuntimeException {
        public UtilizadorNaoEncontradoException(String msg) { super(msg); }
    }

    public static class PasswordIncorretaException extends RuntimeException {
        public PasswordIncorretaException(String msg) { super(msg); }
    }

    // =========================================================================
    // Casas
    // =========================================================================

    public static class CasaJaExisteException extends RuntimeException {
        public CasaJaExisteException(String msg) { super(msg); }
    }

    public static class CasaNaoEncontradaException extends RuntimeException {
        public CasaNaoEncontradaException(String msg) { super(msg); }
    }

    public static class PermissaoNegadaException extends RuntimeException {
        public PermissaoNegadaException(String msg) { super(msg); }
    }

    // =========================================================================
    // Divisões
    // =========================================================================

    public static class DivisaoJaExisteException extends RuntimeException {
        public DivisaoJaExisteException(String msg) { super(msg); }
    }

    public static class DivisaoNaoEncontradaException extends RuntimeException {
        public DivisaoNaoEncontradaException(String msg) { super(msg); }
    }

    // =========================================================================
    // Dispositivos
    // =========================================================================

    public static class DispositivoJaExisteException extends RuntimeException {
        public DispositivoJaExisteException(String msg) { super(msg); }
    }

    public static class DispositivoNaoEncontradoException extends RuntimeException {
        public DispositivoNaoEncontradoException(String msg) { super(msg); }
    }

    public static class DispositivoJaAssociadoException extends RuntimeException {
        public DispositivoJaAssociadoException(String msg) { super(msg); }
    }

    public static class TipoDispositivoInvalidoException extends RuntimeException {
        public TipoDispositivoInvalidoException(String msg) { super(msg); }
    }

    public static class ValorInvalidoException extends RuntimeException {
        public ValorInvalidoException(String msg) { super(msg); }
    }

    // =========================================================================
    // Cenários, Automações e Escalonamentos
    // =========================================================================

    public static class CenarioJaExisteException extends RuntimeException {
        public CenarioJaExisteException(String msg) { super(msg); }
    }

    public static class CenarioNaoEncontradoException extends RuntimeException {
        public CenarioNaoEncontradoException(String msg) { super(msg); }
    }

    public static class AutomacaoJaExisteException extends RuntimeException {
        public AutomacaoJaExisteException(String msg) { super(msg); }
    }

    public static class EscalonamentoJaExisteException extends RuntimeException {
        public EscalonamentoJaExisteException(String msg) { super(msg); }
    }

    // =========================================================================
    // Estatísticas
    // =========================================================================

    public static class SemCasasException extends RuntimeException {
        public SemCasasException(String msg) { super(msg); }
    }

    public static class SemDispositivosException extends RuntimeException {
        public SemDispositivosException(String msg) { super(msg); }
    }

    public static class SemDivisoesException extends RuntimeException {
        public SemDivisoesException(String msg) { super(msg); }
    }

    // =========================================================================
    // Persistência
    // =========================================================================

    public static class ErroAoGuardarException extends RuntimeException {
        public ErroAoGuardarException(String msg) { super(msg); }
    }

    public static class ErroAoCarregarException extends RuntimeException {
        public ErroAoCarregarException(String msg) { super(msg); }
    }
}