package main.java.dao;

import main.java.dao.generic.IGenericDAO;
import main.java.domain.Produto;

/**
 * Interface para operações de acesso a dados (DAO) específicas para a entidade {@link Produto}.
 * <p>
 * Esta interface estende {@link IGenericDAO} com a entidade {@link Produto} e o tipo de identificador {@link Long}.
 * Ela define métodos de acesso a dados específicos para a entidade {@code Produto}, herdando métodos genéricos
 * para operações de CRUD (Create, Read, Update, Delete) e outras operações comuns.
 * </p>
 * 
 * @see Produto
 * @see IGenericDAO
 */
public interface IProdutoDAO extends IGenericDAO<Produto,Long> {

}
