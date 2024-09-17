package main.java.dao;

import main.java.dao.generic.GenericDAO;
import main.java.domain.Cliente;

/**
 * Implementação da interface {@link IClienteDAO} para operações de acesso a
 * dados específicas para a entidade {@link Cliente}.
 * <p>
 * Esta classe estende {@link GenericDAO} com a entidade {@code Cliente} e o
 * tipo de identificador {@code Long}.
 * Fornece uma implementação básica para operações de acesso a dados para a
 * entidade {@code Cliente}.
 * </p>
 */
public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {
    /**
     * Construtor padrão que inicializa a classe {@code Cliente} no
     * {@link GenericDAO}.
     * <p>
     * Este construtor chama o construtor da classe pai {@link GenericDAO} com a
     * classe {@code Cliente},
     * configurando a entidade que esta DAO irá gerenciar.
     * </p>
     */
    public ClienteDAO() {
        super(Cliente.class);
    }

}
