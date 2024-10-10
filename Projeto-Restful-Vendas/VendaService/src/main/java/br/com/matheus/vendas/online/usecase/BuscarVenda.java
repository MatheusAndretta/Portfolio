package br.com.matheus.vendas.online.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.matheus.vendas.online.domain.Venda;
import br.com.matheus.vendas.online.exception.EntityNotFoundException;
import br.com.matheus.vendas.online.repository.IVendaRepository;

@Service
public class BuscarVenda {

    private IVendaRepository vendaRepository;

    @Autowired
    public BuscarVenda(IVendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Page<Venda> buscar(Pageable pageable) {
        return vendaRepository.findAll(pageable);
    }

    public Venda buscarPorCodigo(String codigo) {
		return vendaRepository.findByCodigo(codigo)
				.orElseThrow(() -> new EntityNotFoundException(Venda.class, "codigo", codigo));
	}

}
