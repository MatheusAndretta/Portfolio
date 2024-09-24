package br.com.matheus.vendas.online;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.matheus.vendas.online.controllers.VendaController;
import br.com.matheus.vendas.online.domain.Produto;
import br.com.matheus.vendas.online.domain.ProdutoQuantidade;
import br.com.matheus.vendas.online.domain.Venda;
import br.com.matheus.vendas.online.dto.VendaDTO;
import br.com.matheus.vendas.online.service.IProdutoService;
import br.com.matheus.vendas.online.usecase.BuscarVenda;
import br.com.matheus.vendas.online.usecase.CadastroVenda;

public class VendasResourcesTest {

    @InjectMocks
    private VendaController vendasResources;

    @Mock
    private BuscarVenda buscarVenda;

    @Mock
    private CadastroVenda cadastroVenda;

    @Mock
    private IProdutoService produtoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

@SuppressWarnings("unchecked")
@Test
    public void testeBuscar() {

        Pageable pageable = mock(Pageable.class);
        Page<Venda> vendasPage = mock(Page.class);
        when(buscarVenda.buscar(pageable)).thenReturn(vendasPage);

        ResponseEntity<Page<Venda>> response = vendasResources.buscar(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendasPage, response.getBody());
        verify(buscarVenda).buscar(pageable);
    }

    @Test
    public void testeCadastrar() {

        VendaDTO vendaDTO = new VendaDTO("codigo1", "clienteId1", Instant.now());
        Venda venda = Venda.builder()
                .id("1")
                .codigo("codigo1")
                .clienteId("clienteId1")
                .dataVenda(Instant.now())
                .valorTotal(BigDecimal.valueOf(100.00))
                .status(Venda.Status.INICIADO)
                .produtos(new HashSet<>())
                .build();

        when(cadastroVenda.cadastrar(vendaDTO)).thenReturn(venda);

        ResponseEntity<Venda> response = vendasResources.cadastrar(vendaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());
        assertEquals(venda.getValorTotal(), BigDecimal.valueOf(100.00));
        verify(cadastroVenda).cadastrar(vendaDTO);
    }

    @Test
    public void testeAtualizar() {

        Venda venda = Venda.builder()
                .id("1")
                .codigo("codigo1")
                .clienteId("clienteId1")
                .dataVenda(Instant.now())
                .valorTotal(BigDecimal.valueOf(100.00))
                .status(Venda.Status.INICIADO)
                .produtos(new HashSet<>())
                .build();

        venda.setValorTotal(BigDecimal.valueOf(1000.00));
        when(cadastroVenda.atualizar(venda)).thenReturn(venda);

        ResponseEntity<Venda> responseAtt = vendasResources.atualizar(venda);

        assertEquals(HttpStatus.OK, responseAtt.getStatusCode());
        assertEquals(venda, responseAtt.getBody());
        assertEquals(venda.getValorTotal(), BigDecimal.valueOf(1000.00));
        verify(cadastroVenda).atualizar(venda);

    }

    @Test
    public void testeAdicionarProduto() {
        String id = "1";
        String codigoProduto = "Produto1";
        Integer quantidade = 2;

        Produto produto = Produto.builder()
                .codigo(codigoProduto)
                .nome("Produto Teste")
                .descricao("Descrição do Produto Teste")
                .valor(BigDecimal.valueOf(50.00))
                .build();

        ProdutoQuantidade quantidadeP = ProdutoQuantidade.builder()
                .produto(produto)
                .quantidade(quantidade)
                .build();

        Set<ProdutoQuantidade> produtos = new HashSet<>();
        produtos.add(quantidadeP);

        Venda venda = Venda.builder()
                .id(id)
                .codigo("codigo1")
                .clienteId("clienteId1")
                .dataVenda(Instant.now())
                .valorTotal(produto.getValor().multiply(BigDecimal.valueOf(quantidade)))
                .status(Venda.Status.INICIADO)
                .produtos(produtos)
                .build();

        when(cadastroVenda.adicionarProduto(eq(id), eq(codigoProduto), eq(quantidade))).thenReturn(venda);
        when(produtoService.buscarProduto(codigoProduto)).thenReturn(produto);

        ResponseEntity<Venda> response = vendasResources.adicionarProduto(id, codigoProduto, quantidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());
        assertEquals(venda.getValorTotal(), produto.getValor().multiply(BigDecimal.valueOf(2)));
        verify(cadastroVenda).adicionarProduto(id, codigoProduto, quantidade);
    }

    @SuppressWarnings("null")
@Test
    public void testeRemoverProduto() {

        String id = "1";
        String codigoProduto = "produto1";
        Integer quantidade = 1;

        Produto produto = Produto.builder()
                .codigo(codigoProduto)
                .nome("Produto Teste")
                .descricao("Descrição do Produto Teste")
                .valor(BigDecimal.valueOf(50.00))
                .build();

        ProdutoQuantidade quantidadeP = ProdutoQuantidade.builder()
                .produto(produto)
                .quantidade(quantidade)
                .build();

        Set<ProdutoQuantidade> produtos = new HashSet<>();
        produtos.add(quantidadeP);

        Venda venda = Venda.builder()
                .id(id)
                .codigo("codigo1")
                .clienteId("clienteId1")
                .dataVenda(Instant.now())
                .valorTotal(produto.getValor().multiply(BigDecimal.valueOf(quantidade)))
                .status(Venda.Status.INICIADO)
                .produtos(produtos)
                .build();

        assertEquals(BigDecimal.valueOf(50.00), venda.getValorTotal());
        assertEquals(1, venda.getProdutos().size());
        assertEquals(produto, venda.getProdutos().iterator().next().getProduto());

        BigDecimal novoValorTotal = BigDecimal.ZERO;

        when(cadastroVenda.removerProduto(eq(id), eq(codigoProduto), eq(quantidade))).thenReturn(
                Venda.builder()
                        .id(id)
                        .codigo("codigo1")
                        .clienteId("clienteId1")
                        .dataVenda(Instant.now())
                        .valorTotal(novoValorTotal)
                        .status(Venda.Status.INICIADO)
                        .produtos(new HashSet<>())
                        .build());

        ResponseEntity<Venda> response = vendasResources.removerProduto(id, codigoProduto, quantidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(novoValorTotal, response.getBody().getValorTotal());
        assertTrue(response.getBody().getProdutos().isEmpty());
        verify(cadastroVenda).removerProduto(id, codigoProduto, quantidade);
    }

    @Test
    public void testeFinalizar() {
        String id = "1";
        Venda venda = Venda.builder()
                .id(id)
                .codigo("codigo")
                .dataVenda(Instant.now())
                .valorTotal(BigDecimal.valueOf(100.00))
                .status(Venda.Status.INICIADO)
                .produtos(new HashSet<>())
                .build();

        when(cadastroVenda.finalizar(eq(id))).thenReturn(venda);

        ResponseEntity<Venda> response = vendasResources.finalizar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());
        verify(cadastroVenda).finalizar(id);
    }

    @Test
    public void testeCancelar() {

        String id = "1";
        Venda venda = Venda.builder()
                .id(id)
                .codigo("codigo1")
                .clienteId("clienteId1")
                .dataVenda(Instant.now())
                .valorTotal(BigDecimal.valueOf(100.00))
                .status(Venda.Status.INICIADO)
                .produtos(new HashSet<>())
                .build();

        when(cadastroVenda.cancelar(eq(id))).thenReturn(venda);

        ResponseEntity<Venda> response = vendasResources.cancelar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());
        verify(cadastroVenda).cancelar(id);
    }

}