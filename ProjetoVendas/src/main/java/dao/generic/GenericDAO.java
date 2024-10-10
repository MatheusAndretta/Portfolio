package main.java.dao.generic;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import main.java.dao.Persistente;
import main.java.exceptions.DAOException;
import main.java.exceptions.TipoChaveNaoEncontradaException;

/**
 * Implementação genérica para operações de acesso a dados (DAO) usando JPA.
 * <p>
 * Esta classe fornece operações básicas de CRUD para entidades que implementam
 * a interface {@link Persistente}.
 * É uma classe genérica que pode ser usada para qualquer entidade que estenda
 * {@link Persistente}.
 * </p>
 *
 * @param <T> O tipo da entidade que estende {@link Persistente}.
 * @param <E> O tipo do identificador da entidade, que deve ser
 *            {@link Serializable}.
 * 
 * @see Persistente
 * @see IGenericDAO
 */

public class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {

    protected EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    private Class<T> persistenteClass;

    /**
     * Construtor que inicializa o {@link GenericDAO} com a classe da entidade a ser
     * gerenciada.
     * 
     * @param persistenteClass A classe da entidade que esta DAO irá gerenciar.
     */

    public GenericDAO(Class<T> persistenteClass) {
        this.persistenteClass = persistenteClass;
    }

    /**
     * Busca todas as entidades do tipo {@code T}.
     * <p>
     * Este método abre uma conexão com o banco de dados, executa uma consulta para
     * obter todas as entidades,
     * e fecha a conexão após a operação.
     * </p>
     * 
     * @return Uma coleção de todas as entidades do tipo {@code T}.
     */

    @Override
    public Collection<T> buscarTodos() {
        openConnection();
        List<T> lista = entityManager.createQuery(getSelectSql(), this.persistenteClass).getResultList();
        closeConnection();
        return lista;

    }

    /**
     * Exclui uma entidade do banco de dados.
     * <p>
     * Este método abre uma conexão com o banco de dados, mescla a entidade
     * fornecida, remove-a,
     * e fecha a conexão após a operação.
     * </p>
     * 
     * @param entity A entidade a ser excluída.
     * @throws DAOException Se ocorrer um erro durante a operação de exclusão.
     */
    @Override
    public void excluir(T entity) throws DAOException {
        openConnection();
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        closeConnection();
    }

    /**
     * Atualiza uma entidade no banco de dados.
     * <p>
     * Este método abre uma conexão com o banco de dados, mescla a entidade
     * fornecida,
     * comete a transação e fecha a conexão após a operação.
     * </p>
     * 
     * @param entity A entidade a ser atualizada.
     * @return A entidade atualizada.
     */

    @Override
    public T alterar(T entity) {
        openConnection();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    /**
     * Cadastra uma nova entidade no banco de dados.
     * <p>
     * Este método abre uma conexão com o banco de dados, persiste a entidade
     * fornecida,
     * comete a transação e fecha a conexão após a operação.
     * </p>
     * 
     * @param entity A entidade a ser cadastrada.
     * @return A entidade cadastrada com o identificador atribuído.
     * @throws TipoChaveNaoEncontradaException Se ocorrer um erro relacionado ao
     *                                         tipo de chave.
     * @throws DAOException                    Se ocorrer um erro ao salvar a
     *                                         entidade.
     */
    @Override
    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        openConnection();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;

    }

    /**
     * Consulta uma entidade pelo seu identificador.
     * <p>
     * Este método abre uma conexão com o banco de dados, encontra a entidade pelo
     * identificador fornecido,
     * comete a transação e fecha a conexão após a operação.
     * </p>
     * 
     * @param valor O identificador da entidade a ser consultada.
     * @return A entidade consultada ou {@code null} se não for encontrada.
     */
    @Override
    public T consultar(E valor) {
        openConnection();
        T entity = entityManager.find(persistenteClass, valor);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    /**
     * Gera a consulta SQL para selecionar todas as entidades do tipo {@code T}.
     * 
     * @return A consulta SQL como uma {@link String}.
     */
    private String getSelectSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT obj FROM ");
        sb.append(this.persistenteClass.getSimpleName());
        sb.append(" obj");
        return sb.toString();
    }

    /**
     * Abre uma conexão com o banco de dados e inicia uma transação.
     * <p>
     * Este método cria uma instância de {@link EntityManagerFactory} e
     * {@link EntityManager},
     * e inicia uma transação.
     * </p>
     */
    protected void openConnection() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPA");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    /**
     * Fecha a conexão com o banco de dados e o {@link EntityManagerFactory}.
     * <p>
     * Este método encerra a transação e fecha o {@link EntityManager} e o
     * {@link EntityManagerFactory}.
     * </p>
     */
    protected void closeConnection() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
