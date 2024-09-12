package br.com.matheus.vendas.online.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matheus.vendas.online.domain.Produto;
import br.com.matheus.vendas.online.domain.Produto.Status;
import br.com.matheus.vendas.online.exception.EntityNotFoundException;
import br.com.matheus.vendas.online.repository.IProdutoRepository;
import jakarta.validation.Valid;

@Service
public class CadastroProduto {

    private IProdutoRepository produtoRepository;

    
    @Autowired
    public CadastroProduto(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }



    public Produto cadastrar(@Valid Produto produto) {
        produto.setStatus(Status.ATIVO);
        return this.produtoRepository.insert(produto);
    }



    public Produto atualizar(@Valid Produto produto) {
      return this.produtoRepository.save(produto);
    }



    public void remover(String id) {
        Produto produto = produtoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Produto.class,"id",id));
        produto.setStatus(Status.INATIVO);
        this.produtoRepository.save(produto);
       
    }

}
