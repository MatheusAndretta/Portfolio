package teste.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Random;

import org.junit.After;
import org.junit.Test;

import main.java.dao.ClienteDAO;
import main.java.dao.IClienteDAO;
import main.java.domain.Cliente;
import main.java.exceptions.DAOException;
import main.java.exceptions.MaisDeUmRegistroException;
import main.java.exceptions.TableException;
import main.java.exceptions.TipoChaveNaoEncontradaException;

public class ClienteDAOTeste {

    private IClienteDAO clienteDAO;

    private Random rd;

    public ClienteDAOTeste() {
        clienteDAO = new ClienteDAO();
        rd = new Random();
    }

    @After
    public void end() throws DAOException {
        Collection<Cliente> lista = clienteDAO.buscarTodos();
        lista.forEach(cli -> {
            try {
                clienteDAO.excluir(cli);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        clienteDAO.cadastrar(cliente);

        Cliente clienteConsultadoDB = clienteDAO.consultar(cliente.getId());
        assertNotNull(clienteConsultadoDB);
    }

    @Test
    public void salvarCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        assertNotNull(retorno);

        Cliente clienteConsultadoDB = clienteDAO.consultar(cliente.getId());
        assertNotNull(clienteConsultadoDB);

        clienteDAO.excluir(cliente);

        Cliente clienteConsultadoDB2 = clienteDAO.consultar(retorno.getId());
        assertNull(clienteConsultadoDB2);
    }

    @Test
    public void excluirCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        assertNotNull(retorno);

        Cliente clienteConsultadoDB = clienteDAO.consultar(cliente.getId());
        assertNotNull(clienteConsultadoDB);

        clienteDAO.excluir(cliente);
        clienteConsultadoDB = clienteDAO.consultar(cliente.getId());
        assertNull(clienteConsultadoDB);
    }

    @Test
    public void alteraCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        assertNotNull(retorno);

        Cliente clienteConsultadoDB = clienteDAO.consultar(cliente.getId());
        assertNotNull(clienteConsultadoDB);

        clienteConsultadoDB.setNome("Matheus Andretta");
        clienteDAO.alterar(clienteConsultadoDB);

        Cliente clienteAlterado = clienteDAO.consultar(clienteConsultadoDB.getId());
        assertNotNull(clienteAlterado);
        assertEquals("Matheus Andretta", clienteConsultadoDB.getNome());

        clienteDAO.excluir(cliente);
        clienteConsultadoDB = clienteDAO.consultar(cliente.getId());
        assertNull(clienteConsultadoDB);
    }

    @Test
    public void buscarTodos() throws TipoChaveNaoEncontradaException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        assertNotNull(retorno);

        Cliente cliente1 = criarCliente();
        Cliente retorno1 = clienteDAO.cadastrar(cliente1);
        assertNotNull(retorno1);

        Collection<Cliente> lista = clienteDAO.buscarTodos();
        assertTrue(lista != null);
        assertTrue(lista.size() == 2);

        lista.forEach(cli -> {
            try {
                clienteDAO.excluir(cli);
            } catch (DAOException e) {
                
                e.printStackTrace();
            }
        });

        Collection<Cliente> list1 = clienteDAO.buscarTodos();
		assertTrue(list1 != null);
		assertTrue(list1.size() == 0);
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(rd.nextLong());
        cliente.setNome("Matheus");
        cliente.setCidade("Santa Catarina");
        cliente.setEnd("End");
        cliente.setEstado("SC");
        cliente.setNumero(10);
        cliente.setTelefone(11999999999L);
        return cliente;
    }

}
