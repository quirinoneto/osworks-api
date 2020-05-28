package com.algaworks.osworks.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService  cadastroClienteService;
	
	
	@GetMapping
    public List<Cliente> listar() {
		
		return clienteRepository.findAll();
		//return clienteRepository.findByNome("Maria");
		//return clienteRepository.findByNomeContaining("jo");		
	}
	
	@GetMapping("/{clienteID}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteID){
		Optional<Cliente> cliente = clienteRepository.findById(clienteID);
		
		
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroClienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteID}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable  Long clienteID,
			@RequestBody Cliente cliente) {
		
		if(!clienteRepository.existsById(clienteID)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteID);
		cliente = cadastroClienteService.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteID}")
	public ResponseEntity<Void> remover (@PathVariable Long clienteID) {
		
		if (!clienteRepository.existsById(clienteID)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroClienteService.excluir(clienteID);
		return ResponseEntity.noContent().build();
	}
	
	

//	@GetMapping("/clientes")
//	public List<Cliente> lista() {	
//		
//		Cliente cliente1 = new Cliente();
//		cliente1.setId(1L);
//		cliente1.setNome("Quirino Neto");
//		cliente1.setTelefone("83 996003679");
//		cliente1.setEmail("quirinoneto@gmail.com");
//				
//		Cliente cliente2 = new Cliente();
//		cliente2.setId(2L);
//		cliente2.setNome("Maria");
//		cliente2.setTelefone("83 977003679");
//		cliente2.setEmail("maria@gmail.com");
//		
//		return Arrays.asList(cliente1, cliente2);
//	}
	
}
