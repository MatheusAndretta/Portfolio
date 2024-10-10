package main.java.dao;

import main.java.dao.generic.IGenericDAO;
import main.java.domain.Cliente;
/**
 * Interface para operações de acesso a dados (DAO) específicas para a entidade {@link Cliente}.
 * <p>
 * Esta interface estende {@link IGenericDAO} com a entidade {@link Cliente} e o tipo de identificador {@link Long}.
 * Ela define métodos de acesso a dados específicos para a entidade {@code Produto}, herdando métodos genéricos
 * para operações de CRUD (Create, Read, Update, Delete) e outras operações comuns.
 * </p>
 * 
 * @see Cliente
 * @see IGenericDAO
 */
public interface IClienteDAO extends IGenericDAO<Cliente,Long>{

    

}
