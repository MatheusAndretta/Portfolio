
package main.java.exceptions;

/**
 * Exceção lançada para erros relacionados a operações de tabela.
 * <p>
 * Esta classe estende {@link Exception} e é utilizada para indicar condições excepcionais que
 * ocorrem durante operações envolvendo tabelas, como manipulação de dados ou interações com
 * o banco de dados.
 * </p>
 */
public class TableException extends Exception {

	
	private static final long serialVersionUID = -7509649433607067138L;

	 /**
     * Constrói uma nova instância de {@code TableException} com uma mensagem de erro.
     * 
     * @param msg A mensagem de erro que descreve a condição excepcional.
     */
	public TableException(String msg) {
		super(msg);
    }

}
