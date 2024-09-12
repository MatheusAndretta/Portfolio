package teste.java;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.math.RoundingMode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.dao.ClienteDAO;
import main.java.dao.IClienteDAO;
import main.java.dao.IProdutoDAO;
import main.java.dao.IVendaDAO;
import main.java.dao.ProdutoDAO;
import main.java.dao.VendaDAO;
import main.java.domain.Cliente;
import main.java.domain.Produto;
import main.java.domain.Venda;
import main.java.domain.Venda.Status;
import main.java.exceptions.DAOException;
import main.java.exceptions.MaisDeUmRegistroException;
import main.java.exceptions.TableException;
import main.java.exceptions.TipoChaveNaoEncontradaException;
import teste.java.dao.VendaExclusaoDAO;

public class VendaDAOTeste {

    private IVendaDAO vendaDAO;

    private IVendaDAO vendaExclusaoDAO;

    private IClienteDAO clienteDAO;

    private IProdutoDAO produtoDAO;

    private Random rd;

    private Cliente cliente;

    private Produto produto;

    public VendaDAOTeste() {
        vendaDAO = new VendaDAO();
        vendaExclusaoDAO = new VendaExclusaoDAO(null);
        clienteDAO = new ClienteDAO();
        produtoDAO = new ProdutoDAO();
        rd = new Random();
    }



    @Before
    public void init() throws TipoChaveNaoEncontradaException, DAOException{
        cliente = cadastrarCliente();
        produto = cadastrarProduto("A1", BigDecimal.TEN);
    }

    @Test
    public void pesquisarVenda() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        Venda venda = criarVenda("A1");
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);

        Venda vendaDB = vendaDAO.consultar(venda.getId());
        assertNotNull(vendaDB);
        assertEquals(venda.getCodigo(), vendaDB.getCodigo());

    }

    @Test
    public void salvarVenda() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        Venda venda = criarVenda("A2");
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);

        assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
        assertTrue(venda.getStatus().equals(Status.INICIADA));

        Venda vendaDB = vendaDAO.consultar(venda.getId());
        assertTrue(vendaDB.getId() != null);
        assertEquals(venda.getCodigo(), vendaDB.getCodigo());

    }

    @Test
    public void cancelarVenda() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        String codigoVenda = "A3";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(venda);
        assertNotNull(retorno);

        assertEquals(codigoVenda, venda.getCodigo());

        retorno.setStatus(Status.CANCELADA);
        vendaDAO.cancelarVenda(venda);

        Venda vendaDB = vendaDAO.consultar(venda.getId());
        assertEquals(codigoVenda, vendaDB.getCodigo());
        assertEquals(Status.CANCELADA, vendaDB.getStatus());
    }

    @Test
    public void adicionarMaisProdutosDoMesmo() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        String codigoVenda = "A4";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(venda);
        assertNotNull(retorno);

        assertEquals(codigoVenda, venda.getCodigo());

        Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
        vendaDB.adicionarProduto(produto, 1);

        assertTrue(vendaDB.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = criandoBigDecimal(30, 2, RoundingMode.HALF_DOWN);
        assertTrue(vendaDB.getValorTotal().equals(valorTotal));
        assertTrue(vendaDB.getStatus().equals(Status.INICIADA));
    }

    @Test
    public void adicionarMaisProdutosDiferentes() throws TipoChaveNaoEncontradaException, DAOException{
        String codigoVenda = "A5";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(venda);
        assertNotNull(retorno);
        assertEquals(codigoVenda, venda.getCodigo());

        Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
        vendaDB.adicionarProduto(prod, 1);

        assertTrue(vendaDB.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = criandoBigDecimal(70, 2, RoundingMode.HALF_DOWN);
        assertTrue(vendaDB.getValorTotal().equals(valorTotal));
        assertTrue(vendaDB.getStatus().equals(Status.INICIADA));

    }

    @SuppressWarnings("unlikely-arg-type")
    @Test(expected = DAOException.class)
    public void vendaComMesmoCodigo() throws TipoChaveNaoEncontradaException, DAOException{
        Venda venda = criarVenda("A6");
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);


        Venda venda1 = criarVenda("A6");
        Venda retorno1 = vendaDAO.cadastrar(venda1);
        assertNotNull(retorno1);
        assertTrue(venda.getCodigo().equals(Status.INICIADA));
    }

    @Test
    public void vendaRemoverProduto() throws TipoChaveNaoEncontradaException, DAOException{
        String codigoVenda = "A7";
        Venda venda = criarVenda(codigoVenda);
        Venda retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
        vendaDB.adicionarProduto(prod, 1);
        assertTrue(vendaDB.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = criandoBigDecimal(70, 2, RoundingMode.HALF_DOWN);
        assertTrue(vendaDB.getValorTotal().equals(valorTotal));

        vendaDB.removerProduto(prod, 1);
        assertTrue(vendaDB.getQuantidadeTotalProdutos() == 2);
        valorTotal = criandoBigDecimal(20, 2, RoundingMode.HALF_DOWN);
        assertTrue(vendaDB.getValorTotal().equals(valorTotal));
        assertTrue(vendaDB.getStatus().equals(Status.INICIADA));
    }

    @Test
	public void removerApenasUmProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
		String codigoVenda = "A8";
		Venda venda = criarVenda(codigoVenda);
		Venda retorno = vendaDAO.cadastrar(venda);
		assertNotNull(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCodigo());
		
		Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
		vendaDB.adicionarProduto(prod, 1);
		assertTrue(vendaDB.getQuantidadeTotalProdutos() == 3);
		BigDecimal valorTotal = criandoBigDecimal(70, 2, RoundingMode.HALF_DOWN);
		assertTrue(vendaDB.getValorTotal().equals(valorTotal));
		
		
		vendaDB.removerProduto(prod, 1);
		assertTrue(vendaDB.getQuantidadeTotalProdutos() == 2);
		valorTotal = criandoBigDecimal(20, 2, RoundingMode.HALF_DOWN);
		assertTrue(vendaDB.getValorTotal().equals(valorTotal));
		assertTrue(vendaDB.getStatus().equals(Status.INICIADA));
	} 
    
    @Test
	public void removerTodosProdutos() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
		String codigoVenda = "A9";
		Venda venda = criarVenda(codigoVenda);
		Venda retorno = vendaDAO.cadastrar(venda);
		assertNotNull(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCodigo());
		
		Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
		vendaDB.adicionarProduto(prod, 1);
		assertTrue(vendaDB.getQuantidadeTotalProdutos() == 3);
		 BigDecimal valorTotal = criandoBigDecimal(70, 2, RoundingMode.HALF_DOWN);
		 assertTrue(vendaDB.getValorTotal().equals(valorTotal));
		
		
		vendaDB.removerTodosProdutos();
		assertTrue(vendaDB.getQuantidadeTotalProdutos() == 0);
		assertTrue(vendaDB.getValorTotal().equals(BigDecimal.valueOf(0)));
		assertTrue(vendaDB.getStatus().equals(Status.INICIADA));
	} 

    @Test
	public void finalizarVenda() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
		String codigoVenda = "A10";
		Venda venda = criarVenda(codigoVenda);
		Venda retorno = vendaDAO.cadastrar(venda);
		assertNotNull(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		venda.setStatus(Status.CONCLUIDA);
		vendaDAO.finalizarVenda(venda);
		
		Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
		assertEquals(venda.getCodigo(), vendaDB.getCodigo());
		assertEquals(Status.CONCLUIDA, vendaDB.getStatus());
	}

    @Test(expected = UnsupportedOperationException.class)
	public void tentarAdicionarProdutosVendaFinalizada() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
		String codigoVenda = "A11";
		Venda venda = criarVenda(codigoVenda);
		Venda retorno = vendaDAO.cadastrar(venda);
		assertNotNull(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		venda.setStatus(Status.CONCLUIDA);
		vendaDAO.finalizarVenda(venda);
		
		Venda vendaDB = vendaDAO.consultarComCollection(venda.getId());
		assertEquals(venda.getCodigo(), vendaDB.getCodigo());
		assertEquals(Status.CONCLUIDA, vendaDB.getStatus());
		
		vendaDB.adicionarProduto(this.produto, 1);
		
	}

    @After
    public void end() throws DAOException{
        excluirVendas();
        excluirProdutos();
        clienteDAO.excluir(cliente);
    }

    public static BigDecimal criandoBigDecimal(double value, int scale, RoundingMode roundingMode) {
        return BigDecimal.valueOf(value).setScale(scale, roundingMode);
    }

    private void excluirVendas() throws DAOException {
		Collection<Venda> list = this.vendaExclusaoDAO.buscarTodos();
		list.forEach(prod -> {
			try {
				this.vendaExclusaoDAO.excluir(prod);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}

    private void excluirProdutos() throws DAOException{
        Collection<Produto> list = this.produtoDAO.buscarTodos();
		list.forEach(prod -> {
			try {
				this.produtoDAO.excluir(prod);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
    }

    private Venda criarVenda(String codigo){
        Venda venda = new Venda();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(this.cliente);
        venda.setStatus(Status.INICIADA);
        venda.adicionarProduto(this.produto, 2);
        return venda;
    }

    private Produto cadastrarProduto(String codigo, BigDecimal valor) throws TipoChaveNaoEncontradaException, DAOException {
        Produto produto = new Produto();
		produto.setCodigo(codigo);
		produto.setDescricao("Produto 1");
		produto.setNome("Produto 1");
		produto.setValor(valor);
		produtoDAO.cadastrar(produto);
		return produto;
    }

    private Cliente cadastrarCliente() throws TipoChaveNaoEncontradaException, DAOException {
        Cliente cliente = new Cliente();
		cliente.setCpf(rd.nextLong());
		cliente.setNome("Matheus");
		cliente.setCidade("Santa Catarina");
		cliente.setEnd("End");
		cliente.setEstado("SC");
		cliente.setNumero(10);
		cliente.setTelefone(1199999999L);
		clienteDAO.cadastrar(cliente);
		return cliente;
    }

    
}
