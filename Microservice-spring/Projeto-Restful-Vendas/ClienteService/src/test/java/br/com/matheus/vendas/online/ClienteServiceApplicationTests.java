package br.com.matheus.vendas.online;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.matheus.vendas.online.controllers.ClienteController;
import br.com.matheus.vendas.online.domain.Cliente;
import br.com.matheus.vendas.online.usecase.BuscarCliente;
import br.com.matheus.vendas.online.usecase.CadastroCliente;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SuppressWarnings("null")
class ClienteServiceApplicationTests {

	@MockBean
	private BuscarCliente buscarCliente;

	@MockBean
	private CadastroCliente cadastroCliente;

	@InjectMocks
	private ClienteController controller;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	private Cliente criarCliente() {
		return Cliente.builder()
				.id("A1")
				.nome("Teste nome")
				.cpf(123456L)
				.tel(654321L)
				.email("teste@gmail.com")
				.end("br")
				.numero(10)
				.cidade("SC")
				.estado("Sc")
				.build();
	}

	@Test
	public void testBuscarClientePorId() {
		String id = "A1";

		Cliente cliente = criarCliente();

		when(buscarCliente.buscarPorId(id)).thenReturn(cliente);

		ResponseEntity<Cliente> response = controller.buscarPorId(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cliente, response.getBody());
		verify(buscarCliente).buscarPorId(id);
	}

	@Test
	public void testBuscarClientePorCpf() {
		Long cpf = 123456789L;
		Cliente cliente = criarCliente();
		cliente.setCpf(cpf);

		when(buscarCliente.buscarPorCpf(cpf)).thenReturn(cliente);

		ResponseEntity<Cliente> response = controller.buscarPorCpf(cpf);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cliente, response.getBody());
		verify(buscarCliente).buscarPorCpf(cpf);

	}
	

	@Test
	public void testCadastrarCliente() {
		Cliente cliente = criarCliente();

		when(cadastroCliente.cadastrar(cliente)).thenReturn(cliente);

		ResponseEntity<Cliente> response = controller.cadastrar(cliente);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cliente, response.getBody());
		verify(cadastroCliente).cadastrar(cliente);
	}

	@Test
	public void testIsCadastrado(){
		String id = "A1";
		boolean isCadastrado = true;

		when(buscarCliente.isCadastrado(id)).thenReturn(isCadastrado);
		ResponseEntity<Boolean> response = controller.isCadastrado(id);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(isCadastrado, response.getBody());
		verify(buscarCliente).isCadastrado(id);
	}

	@Test
	public void testRemoverCliente() {
		String id = "A1";
		doNothing().when(cadastroCliente).remover(id);

		ResponseEntity<String> response = controller.remover(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Removido com sucesso", response.getBody());
		verify(cadastroCliente).remover(id);
	}

	@Test
	public void testAtualizarCliente() {
		Cliente cliente = criarCliente();

		cliente.setNome("Att");
		when(cadastroCliente.atualizar(cliente)).thenReturn(cliente);

		ResponseEntity<Cliente> response = controller.atualizar(cliente);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Att", response.getBody().getNome());
		verify(cadastroCliente).atualizar(cliente);

	}

	@Test
public void testBuscarTodosClientes() {
	List<Cliente> listaClientes = List.of(criarCliente(),criarCliente());
	Page<Cliente> pageClientes = new PageImpl<>(listaClientes);

	when(buscarCliente.buscar(any(Pageable.class))).thenReturn(pageClientes);

	ResponseEntity<Page<Cliente>> response = controller.buscar(Pageable.unpaged());
	assertEquals(HttpStatus.OK, response.getStatusCode());
	assertEquals(pageClientes, response.getBody());
}


}
