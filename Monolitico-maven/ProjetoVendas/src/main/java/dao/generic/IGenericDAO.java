package main.java.dao.generic;

import java.io.Serializable;

import java.util.Collection;

import main.java.dao.Persistente;
import main.java.exceptions.DAOException;
import main.java.exceptions.MaisDeUmRegistroException;
import main.java.exceptions.TableException;
import main.java.exceptions.TipoChaveNaoEncontradaException;

public interface IGenericDAO<T extends Persistente, E extends Serializable> {

    public Collection<T> buscarTodos()throws DAOException;

    public void excluir(T entity) throws DAOException; 

    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public T consultar(E id)throws MaisDeUmRegistroException, TableException, DAOException;

    public T alterar(T entity)throws TipoChaveNaoEncontradaException, DAOException;

}
