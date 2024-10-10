package br.com.matheus.vendas.online.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matheus.vendas.online.domain.Cliente;
import br.com.matheus.vendas.online.usecase.BuscarCliente;
import br.com.matheus.vendas.online.usecase.CadastroCliente;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    private BuscarCliente buscarCliente;

    private CadastroCliente cadastroCliente;

    @Autowired
    public ClienteController(BuscarCliente buscarCliente,CadastroCliente cadastroCliente) {
        this.buscarCliente = buscarCliente;
        this.cadastroCliente = cadastroCliente;
    }

    @GetMapping
    @Operation(summary = "Buscar Todos Clientes")
    public ResponseEntity<Page<Cliente>> buscar(Pageable pageable){
        return ResponseEntity.ok(buscarCliente.buscar(pageable));
    }
    
    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar um cliente pelo id")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable(value = "id",required = true)String id){
        return ResponseEntity.ok(buscarCliente.buscarPorId(id));
    }

    @GetMapping(value = "isCadastrado/{id}")
    @Operation(summary = "Validar se o cliente estar cadastrado. Passando ID")
    public ResponseEntity<Boolean> isCadastrado(@PathVariable(value = "id",required = true) String id){
        return ResponseEntity.ok(buscarCliente.isCadastrado(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar Cliente")
    public ResponseEntity<Cliente> cadastrar(@RequestBody @Valid Cliente cliente){
        return ResponseEntity.ok(cadastroCliente.cadastrar(cliente));

    }
    @GetMapping(value = "/cpf/{cpf}")
    @Operation(summary = "Buscar um Cliente pelo cpf")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable(value = "cpf",required = true)Long cpf){
        return ResponseEntity.ok(buscarCliente.buscarPorCpf(cpf));
    }

    @PutMapping
	@Operation(summary = "Atualiza um cliente")
	public ResponseEntity<Cliente> atualizar(@RequestBody @Valid Cliente cliente) {
		return ResponseEntity.ok(cadastroCliente.atualizar(cliente));
	}

    public ResponseEntity<String> remover(@PathVariable(value = "id")String id){
        cadastroCliente.remover(id);
        return ResponseEntity.ok("Removido com sucesso");
    }


}
