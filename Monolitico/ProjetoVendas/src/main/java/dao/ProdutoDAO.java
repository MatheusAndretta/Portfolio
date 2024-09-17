package main.java.dao;

import main.java.dao.generic.GenericDAO;
import main.java.domain.Produto;
/**
 * Implementação da interface {@link IProdutoDAO} para operações de acesso a dados específicas para a entidade {@link Produto}.
 * <p>
 * Esta classe estende {@link GenericDAO} com a entidade {@code Produto} e o tipo de identificador {@code Long}.
 * Fornece uma implementação básica para operações de acesso a dados para a entidade {@code Produto}.
 * </p>
 */
public class ProdutoDAO  extends GenericDAO<Produto,Long> implements IProdutoDAO{

     /**
     * Construtor padrão que inicializa a classe {@code Produto} no {@link GenericDAO}.
     * <p>
     * Este construtor chama o construtor da classe pai {@link GenericDAO} com a classe {@code Produto},
     * configurando a entidade que esta DAO irá gerenciar.
     * </p>
     */

    public ProdutoDAO() {
        super(Produto.class);
        
    }

}
