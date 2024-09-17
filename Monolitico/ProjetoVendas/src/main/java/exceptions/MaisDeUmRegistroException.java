
package main.java.exceptions;
/**
 * Exceção lançada quando múltiplos registros são encontrados quando apenas um era esperado.
 * <p>
 * Esta classe estende {@link Exception} e é usada para indicar situações em que uma operação
 * de acesso a dados retornou mais de um registro, quando apenas um registro era esperado.
 * </p>
 */

public class MaisDeUmRegistroException extends Exception {

	
	private static final long serialVersionUID = -7509649433607067138L;

	  /**
     * Constrói uma nova instância de {@code MaisDeUmRegistroException} com uma mensagem de erro.
     * 
     * @param msg A mensagem de erro que descreve o motivo da exceção.
     */

	public MaisDeUmRegistroException(String msg) {
		super(msg);
    }

}
