
package main.java.exceptions;

/**
 * Exceção personalizada para erros relacionados a operações de Data Access Object (DAO).
 * <p>
 * Esta classe estende {@link Exception} e é usada para encapsular mensagens de erro e exceções
 * que ocorrem durante operações de acesso a dados.
 * </p>
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 7054379063290825137L;

	  /**
     * Constrói uma nova instância de {@code DAOException} com uma mensagem de erro e uma exceção
     * causa.
     * 
     * @param msg A mensagem de erro que descreve a causa da exceção.
     * @param ex A exceção original que é a causa da exceção atual.
     */

	public DAOException(String msg, Exception ex) {
		super(msg, ex);
    }
}
