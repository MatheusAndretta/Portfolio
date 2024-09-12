package br.com.matheus.vendas.online.usecase;

import java.math.BigDecimal;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matheus.vendas.online.domain.Produto;
import br.com.matheus.vendas.online.domain.Venda;
import br.com.matheus.vendas.online.domain.Venda.Status;
import br.com.matheus.vendas.online.dto.VendaDTO;
import br.com.matheus.vendas.online.exception.EntityNotFoundException;
import br.com.matheus.vendas.online.repository.IVendaRepository;
import br.com.matheus.vendas.online.service.ClienteService;
import br.com.matheus.vendas.online.service.IProdutoService;
import jakarta.validation.Valid;

@Service
public class CadastroVenda {

  private IVendaRepository vendaRepository;

  private IProdutoService produtoService;

  private ClienteService clienteService;

  @Autowired
  public CadastroVenda(IVendaRepository vendaRepository, IProdutoService produtoService,
      ClienteService clienteService) {
    this.vendaRepository = vendaRepository;
    this.produtoService = produtoService;
    this.clienteService = clienteService;
  }

  public Venda cadastrar(@Valid VendaDTO vendaDTO) {
    Venda venda = convertToDomain(vendaDTO, Status.INICIADO);
    validarCliente(venda.getClienteId());
    venda.recalcularValorTotalVenda();
    return this.vendaRepository.insert(venda);

  }

  private void validarCliente(String clienteId) {
    Boolean isCadastrado = this.clienteService.isClienteCadastrado(clienteId);
    this.clienteService.isClienteCadastrado(clienteId);
    if (!isCadastrado) {
      new EntityNotFoundException(Venda.class, "clienteId", clienteId);
    }
  }

  public Venda adicionarProduto(String id, String codigoProduto, Integer quantidade) {
    Venda venda = buscarVenda(id);
    Produto produto = buscarProduto(codigoProduto);
    venda.validarStatus();
    venda.adicionarProdutos(produto, quantidade);
    return this.vendaRepository.save(venda);

  }

  private Produto buscarProduto(String codigoProduto) {
    Produto produto = produtoService.buscarProduto(codigoProduto);
    if (produto == null) {
      throw new EntityNotFoundException(Produto.class, "codigo", codigoProduto);
    }
    return produto;
  }

  private Venda buscarVenda(String id) {
    return vendaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Venda.class, "id", id));

  }

  private Venda convertToDomain(@Valid VendaDTO vendaDTO, Status status) {
    return Venda.builder()
        .clienteId(vendaDTO.getClienteId())
        .codigo(vendaDTO.getCodigo())
        .dataVenda(vendaDTO.getDataVenda())
        .status(status)
        .valorTotal(BigDecimal.ZERO)
        .produtos(new HashSet<>())
        .build();
  }

  public Venda finalizar(String id) {
    Venda venda = buscarVenda(id);
    venda.validarStatus();
    venda.setStatus(Status.CONCLUIDA);
    return this.vendaRepository.save(venda);
  }

  public Venda removerProduto(String id, String codigoProduto, Integer quantidade) {
    Venda venda = buscarVenda(id);
    Produto produto = buscarProduto(codigoProduto);
    venda.validarStatus();
    venda.removerProduto(produto, quantidade);
    return vendaRepository.save(venda);
}

public Venda atualizar(@Valid Venda venda) {
    return this.vendaRepository.save(venda);
}

public Venda cancelar(String id) {
  Venda venda = buscarVenda(id);
  venda.validarStatus();
  venda.setStatus(Status.CANCELADA);
  return this.vendaRepository.save(venda);
}

}
