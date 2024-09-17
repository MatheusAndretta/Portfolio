package main.java.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import main.java.dao.generic.GenericDAO;
import main.java.domain.Cliente;
import main.java.domain.Produto;
import main.java.domain.Venda;
import main.java.exceptions.DAOException;
import main.java.exceptions.TipoChaveNaoEncontradaException;

/**
 * Implementação da interface {@link IVendaDAO} para operações de acesso a dados específicas para a entidade {@link Venda}.
 * <p>
 * Esta classe estende {@link GenericDAO} com a entidade {@code Venda} e o tipo de identificador {@code Long}.
 * Fornece implementações específicas para operações de CRUD e outras operações relacionadas a {@code Venda}.
 * </p>
 */
public class VendaDAO extends GenericDAO<Venda,Long> implements IVendaDAO{

    public VendaDAO(){
        super(Venda.class);
    }

    @Override
    public void finalizarVenda(Venda venda) {
        super.alterar(venda);
    }

    @Override
    public void cancelarVenda(Venda venda) {
        super.alterar(venda);
        }

    @Override
    public void excluir(Venda entity) throws DAOException {
       
        throw new UnsupportedOperationException("OPERAÇÂO NÂO PERMITIDA");
    }

     /**
     * Metodo específico para cadastra uma nova venda no banco de dados.
     * <p>
     * Este método salva a venda, incluindo a associação com produtos e o cliente, e gerencia a transação.
     * </p>
     * 
     * @param entity A venda a ser cadastrada.
     * @return A venda cadastrada com o identificador atribuído.
     * @throws TipoChaveNaoEncontradaException Se ocorrer um erro relacionado ao tipo de chave.
     * @throws DAOException Se ocorrer um erro ao salvar a venda.
     */

    @Override
    public Venda cadastrar(Venda entity) throws TipoChaveNaoEncontradaException, DAOException {
        try {
            openConnection();
            entity.getProdutos().forEach(prod ->{
                Produto produto = entityManager.merge(prod.getProduto());
                prod.setProduto(produto);
            } );
            Cliente cliente = entityManager.merge(entity.getCliente());
            entity.setCliente(cliente);
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            closeConnection();
            return entity;
        } catch (Exception e) {
            throw new DAOException("Erro SALVANDO VENDA", null);
        }
    }

    /**
     * Consulta uma venda com suas associações (cliente e produtos) carregadas.
     * 
     * @param id O identificador da venda a ser consultada.
     * @return A venda com as associações carregadas.
     */

    @Override
    public Venda consultarComCollection(Long id) {
        openConnection();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Venda> query = builder.createQuery(Venda.class);
        Root<Venda> root = query.from(Venda.class);
        root.fetch("cliente");
        root.fetch("produtos");
        query.select(root).where(builder.equal(root.get("id"), id));
        TypedQuery<Venda> tpQuery = entityManager.createQuery(query);
        Venda venda = tpQuery.getSingleResult();
        closeConnection();
        return venda;
    }
}
