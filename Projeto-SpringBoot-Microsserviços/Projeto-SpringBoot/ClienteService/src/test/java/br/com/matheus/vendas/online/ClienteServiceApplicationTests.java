package br.com.matheus.vendas.online;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import br.com.matheus.vendas.online.domain.Cliente;
import br.com.matheus.vendas.online.reources.ClienteResource;
import br.com.matheus.vendas.online.usecase.BuscarCliente;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClienteServiceApplicationTests {

	@MockBean
	private BuscarCliente buscarCliente;

	@InjectMocks
	private ClienteResource clienteResource;

	@BeforeEach
    public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void buscarPorId(){
		Cliente cliente = Cliente.builder().id("1").nome("Teste 1").build();

		when(buscarCliente.buscarPorId("1")).thenReturn(cliente);

		ResponseEntity<Cliente> result = clienteResource.buscarPorId("1");

		Cliente clienteResult = result.getBody();
		assertThat(clienteResult).isEqualTo(cliente);
        assertThat(clienteResult.getId()).isEqualTo(cliente.getId());
        assertThat(clienteResult.getNome()).isEqualTo(cliente.getNome());
	
	}

}
