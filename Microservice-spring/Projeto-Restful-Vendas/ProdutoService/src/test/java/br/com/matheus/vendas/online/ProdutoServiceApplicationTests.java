package br.com.matheus.vendas.online;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.matheus.vendas.online.controllers.ProdutoController;
import br.com.matheus.vendas.online.domain.Produto;
import br.com.matheus.vendas.online.repository.IProdutoRepository;
import br.com.matheus.vendas.online.usecase.BuscaProduto;
import br.com.matheus.vendas.online.usecase.CadastroProduto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SuppressWarnings("null")
class ProdutoServiceApplicationTests {

	@InjectMocks
	private ProdutoController controller;

	@MockBean
	private BuscaProduto buscaProduto;

	@MockBean
	private CadastroProduto cadastroProduto;

	@MockBean
	private IProdutoRepository produtoRepository;


	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	public void cadastrarProdutoTeste() {
		Produto produto = Produto.builder()
				.id("A1")
				.codigo("PDT")
				.nome("Produto Teste")
				.descricao("Produto dsc")
				.valor(BigDecimal.valueOf(50.00))
				.status(Produto.Status.ATIVO)
				.build();

		when(cadastroProduto.cadastrar(produto)).thenReturn(produto);

		ResponseEntity<Produto> response = controller.cadastrar(produto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(produto, response.getBody());
		verify(cadastroProduto).cadastrar(produto);
	}

	@Test
	public void atualizarProdutoTeste() {
		Produto produto = Produto.builder()
				.id("A1")
				.codigo("PDT")
				.nome("Produto Teste")
				.descricao("Produto dsc")
				.valor(BigDecimal.valueOf(50.00))
				.status(Produto.Status.ATIVO)
				.build();
		when(cadastroProduto.cadastrar(produto)).thenReturn(produto);

		produto.setValor(BigDecimal.valueOf(100.00));
		produto.setNome("Novo Nome");

		when(cadastroProduto.atualizar(produto)).thenReturn(produto);

		ResponseEntity<Produto> response = controller.atualizar(produto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(produto, response.getBody());
		assertEquals(BigDecimal.valueOf(100.00), response.getBody().getValor());
		assertEquals("Novo Nome", response.getBody().getNome());
		verify(cadastroProduto).atualizar(produto);

	}

	@Test
	public void removerProdutoTeste() {
		Produto produto = Produto.builder()
				.id("A1")
				.codigo("PDT")
				.nome("Produto Teste")
				.descricao("Produto dsc")
				.valor(BigDecimal.valueOf(50.00))
				.status(Produto.Status.ATIVO)
				.build();
		doNothing().when(cadastroProduto).remover("A1");

		ResponseEntity<String> response = controller.remover("A1");

		verify(cadastroProduto, times(1)).remover("A1");
		assertEquals("Removido com sucesso", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	    @Test
    void testBuscar() throws Exception {
        
         Pageable pageable = PageRequest.of(0, 10); 
        Produto produto = Produto.builder()
                .id("A1")
                .codigo("PDT")
                .nome("Produto Teste")
                .descricao("Produto dsc")
                .valor(BigDecimal.valueOf(50.00))
                .status(Produto.Status.ATIVO)
                .build();

        Page<Produto> page = new PageImpl<>(Collections.singletonList(produto));
        
        when(buscaProduto.buscar(pageable)).thenReturn(page);

        
        ResponseEntity<Page<Produto>> response = controller.buscar(pageable);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("A1", response.getBody().getContent().get(0).getId());
        assertEquals("PDT", response.getBody().getContent().get(0).getCodigo());
    }

    @Test
    void testBuscarPorStatus() throws Exception {
        
		Pageable pageable = PageRequest.of(0, 10);
        Produto produto = Produto.builder()
                .id("A2")
                .codigo("PDT2")
                .nome("Produto Teste 2")
                .descricao("Produto dsc 2")
                .valor(BigDecimal.valueOf(75.00))
                .status(Produto.Status.ATIVO)
                .build();

        Page<Produto> page = new PageImpl<>(Collections.singletonList(produto));
        
        when(buscaProduto.buscar(pageable, Produto.Status.ATIVO)).thenReturn(page);

        
        ResponseEntity<Page<Produto>> response = controller.buscarPorStatus(pageable, Produto.Status.ATIVO);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("A2", response.getBody().getContent().get(0).getId());
    }

    @Test
    void testBuscarPorCodigo() throws Exception {
        
        String codigo = "PDT123";
        Produto produto = Produto.builder()
                .id("A3")
                .codigo(codigo)
                .nome("Produto Código")
                .descricao("Descrição do produto código")
                .valor(BigDecimal.valueOf(100.00))
                .status(Produto.Status.ATIVO)
                .build();
        
        when(buscaProduto.buscarPorCodigo(codigo)).thenReturn(produto);

        
        ResponseEntity<Produto> response = controller.buscarPorCodigo(codigo);

        
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("A3", response.getBody().getId());
        assertEquals(codigo, response.getBody().getCodigo());}

}
