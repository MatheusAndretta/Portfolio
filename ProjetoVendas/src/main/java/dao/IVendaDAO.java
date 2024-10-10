package main.java.dao;

import main.java.dao.generic.IGenericDAO;
import main.java.domain.Venda;

/**
 * Interface para operações de acesso a dados (DAO) específicas para a entidade
 * {@link Venda}.
 * <p>
 * Esta interface estende {@link IGenericDAO} com a entidade {@code Venda} e o
 * tipo de identificador {@code Long}.
 * Adiciona métodos específicos para gerenciar o status das vendas, como
 * finalizar e cancelar.
 * </p>
 * 
 * @see Venda
 * @see IGenericDAO
 */
public interface IVendaDAO extends IGenericDAO<Venda, Long> {
	/**
	 * Finaliza a venda, atualizando o status da venda para finalizada.
	 * <p>
	 * Este método deve ser implementado para garantir que a venda seja marcada como
	 * finalizada.
	 * </p>
	 * 
	 * @param venda A venda a ser finalizada.
	 */
	public void finalizarVenda(Venda venda);

	/**
	 * Cancela a venda, atualizando o status da venda para cancelada.
	 * <p>
	 * Este método deve ser implementado para garantir que a venda seja marcada como
	 * cancelada.
	 * </p>
	 * 
	 * @param venda A venda a ser cancelada.
	 */
	public void cancelarVenda(Venda venda);

	/**
	 * Usando este método para evitar a exception
	 * org.hibernate.LazyInitializationException
	 * Ele busca todos os dados de objetos que tenham colletion pois a mesma por
	 * default é lazy
	 * Mas você pode configurar a propriedade da collection como fetch =
	 * FetchType.EAGER na anotação @OneToMany e usar o consultar genérico normal
	 * 
	 * 
	 * 
	 * @see Venda produtos
	 * 
	 * @param id
	 * @return
	 */

	public Venda consultarComCollection(Long id);
}
