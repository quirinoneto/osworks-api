package com.algaworks.osworks.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		
		
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já exite um cliente cadastrado com este e-mail");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir (Long clienteID) {
		clienteRepository.deleteById(clienteID);	}
	
}
