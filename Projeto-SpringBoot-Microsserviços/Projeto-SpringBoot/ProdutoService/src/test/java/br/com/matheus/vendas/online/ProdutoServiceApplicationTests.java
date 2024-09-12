package br.com.matheus.vendas.online;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import br.com.matheus.vendas.online.controller.ProdutoResource;
import br.com.matheus.vendas.online.domain.Produto;
import br.com.matheus.vendas.online.domain.Produto.Status;
import br.com.matheus.vendas.online.usecase.BuscaProduto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProdutoServiceApplicationTests {

	@MockBean
	private BuscaProduto buscaProduto;

	@InjectMocks
	private ProdutoResource produtoResource;

	@BeforeEach
    public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void buscarPorId(){
		Produto produto = Produto.builder()
		.nome("Teste 2")
		.descricao("Teste 3")
		.status(Status.ATIVO)
		.codigo("A1")
		.valor(BigDecimal.valueOf(123.88D))
		.build();

		when(buscaProduto.buscarPorCodigo("A1")).thenReturn(produto);

		ResponseEntity<Produto> result = produtoResource.buscarPorCodigo("A1");

		Produto produtoResult = result.getBody();
		assertThat(produtoResult).isEqualTo(produto);
		assertThat(produtoResult.getCodigo()).isEqualTo(produto.getCodigo());
		assertThat(produtoResult.getNome()).isEqualTo(produto.getNome());

	}
}
