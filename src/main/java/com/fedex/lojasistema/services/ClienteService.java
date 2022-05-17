package com.fedex.lojasistema.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fedex.lojasistema.domain.Cliente;
import com.fedex.lojasistema.dto.ClienteDTO;
import com.fedex.lojasistema.dto.ClienteNewDTO;
import com.fedex.lojasistema.enums.TipoCliente;
import com.fedex.lojasistema.repositories.ClienteRepository;
import com.fedex.lojasistema.services.exceptions.DataIntegrityException;
import com.fedex.lojasistema.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possuí pedidos");
		}
	}
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente findByEmail(String email) {
		Cliente obj = clienteRepository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado!, Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null,
				objDto.getNome(),
				objDto.getEmail(),
				objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), objDto.getSenha());
		
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
