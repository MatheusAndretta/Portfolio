
package main.java.exceptions;

/**
 * Exceção lançada quando um tipo de elemento não é reconhecido.
 * <p>
 * Esta classe estende {@link Exception} e é utilizada para indicar que um tipo
 * de elemento
 * esperado não é conhecido ou não pode ser identificado. Pode ser útil em
 * contextos onde
 * diferentes tipos de elementos são processados e um tipo desconhecido é
 * encontrado.
 * </p>
 */
public class TipoElementoNaoConhecidoException extends Exception {

    private static final long serialVersionUID = -2268140970978666251L;

    /**
     * Constrói uma nova instância de {@code TipoElementoNaoConhecidoException} com
     * uma mensagem de erro.
     * 
     * @param msg A mensagem de erro que descreve a condição excepcional.
     */
    public TipoElementoNaoConhecidoException(String msg) {
        this(msg, null);
    }

    /**
     * Constrói uma nova instância de {@code TipoElementoNaoConhecidoException} com
     * uma mensagem de erro
     * e uma causa subjacente.
     * 
     * @param msg A mensagem de erro que descreve a condição excepcional.
     * @param e   A causa subjacente da exceção, ou {@code null} se não houver uma
     *            causa.
     */
    public TipoElementoNaoConhecidoException(String msg, Throwable e) {
        super(msg, e);
    }

}
