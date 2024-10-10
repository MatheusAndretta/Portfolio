package main.java.exceptions;

/**
 * Exceção lançada quando um tipo de chave não é encontrado.
 * <p>
 * Esta classe estende {@link Exception} e é utilizada para indicar que um tipo de chave
 * esperado não foi encontrado. Pode ser usado em cenários onde uma operação requer uma chave
 * específica que não está presente ou não pôde ser localizada.
 * </p>
 */

public class TipoChaveNaoEncontradaException extends Exception {

    
	private static final long serialVersionUID = -1389494676398525746L;


    /**
     * Constrói uma nova instância de {@code TipoChaveNaoEncontradaException} com uma mensagem de erro.
     * 
     * @param msg A mensagem de erro que descreve a condição excepcional.
     */
	public TipoChaveNaoEncontradaException(String msg) {
        this(msg, null);
    }

    /**
     * Constrói uma nova instância de {@code TipoChaveNaoEncontradaException} com uma mensagem de erro
     * e uma causa subjacente.
     * 
     * @param msg A mensagem de erro que descreve a condição excepcional.
     * @param e A causa subjacente da exceção, ou {@code null} se não houver uma causa.
     */

    public TipoChaveNaoEncontradaException(String msg, Throwable e) {
        super(msg, e);
    }
}
