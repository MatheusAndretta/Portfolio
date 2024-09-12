package teste.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import java.util.Collection;


import org.junit.After;
import org.junit.Test;

import main.java.dao.IProdutoDAO;
import main.java.dao.ProdutoDAO;
import main.java.domain.Produto;
import main.java.exceptions.DAOException;
import main.java.exceptions.MaisDeUmRegistroException;
import main.java.exceptions.TableException;
import main.java.exceptions.TipoChaveNaoEncontradaException;

public class ProdutoDAOTeste {

    private IProdutoDAO produtoDAO;

    

    public ProdutoDAOTeste() {
        produtoDAO = new ProdutoDAO();
    }

    @Test
    public void pesquisarProduto() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        Produto produto = criarProduto("A1");
        assertNotNull(produto);

        Produto produtoDB = produtoDAO.consultar(produto.getId());
        assertNotNull(produtoDB);
    }

    @Test
    public void salvarProduto() throws TipoChaveNaoEncontradaException, DAOException{
        Produto produto = criarProduto("A2");
        assertNotNull(produto);
    }

    @Test
    public void excluirProdutor() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException{
        Produto produto = criarProduto("A3");
        assertNotNull(produto);

        produtoDAO.excluir(produto);

        Produto produtoDB = produtoDAO.consultar(produto.getId());
        assertNull(produtoDB);
    }

    @Test
    public void alteraProduto() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        Produto produto = criarProduto("A4");
        assertNotNull(produto);

        produto.setNome("Produto 2");
        produtoDAO.alterar(produto);

        Produto produtoDB = produtoDAO.consultar(produto.getId());
        assertNotNull(produtoDB);
        assertEquals("Produto 2", produtoDB.getNome());

    }

    @Test
    public void buscarTodosProdutos() throws DAOException, TipoChaveNaoEncontradaException{
        criarProduto("A5");
        criarProduto("A6");
        criarProduto("A7");

        Collection<Produto> lista = produtoDAO.buscarTodos();
        assertTrue(lista !=null);
        assertTrue(lista.size() == 3);

        for (Produto produto : lista) {
            produtoDAO.excluir(produto);
        }

        lista = produtoDAO.buscarTodos();
        assertTrue(lista !=null);
        assertTrue(lista.size() == 0);
    }

    	@After
	public void end() throws DAOException {
		Collection<Produto> list = produtoDAO.buscarTodos();
		list.forEach(cli -> {
			try {
				produtoDAO.excluir(cli);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}


    private Produto criarProduto(String codigo) throws TipoChaveNaoEncontradaException, DAOException{
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setNome("Produto 1");
        produto.setDescricao("Produto 1");
        produto.setValor(BigDecimal.TEN);
        produtoDAO.cadastrar(produto);
        return produto;
        
    }
}
