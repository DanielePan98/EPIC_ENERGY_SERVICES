package it.epicode.be.energy.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

// Tutti i metodi findAll riguardanti l'ordine

	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	public Page<Cliente> findAllByOrderBySedeLegaleComuneProvinciaNome(Pageable pageable) {
		return clienteRepository.findAllByOrderBySedeLegaleComuneProvinciaNome(pageable);
	}

	public Page<Cliente> findAllByOrderByFatturatoAnnuale(Pageable pageable) {
		return clienteRepository.findAllByOrderByFatturatoAnnuale(pageable);
	}

	public Page<Cliente> findAllByOrderByDataInserimento(Pageable pageable) {
		return clienteRepository.findAllByOrderByDataInserimento(pageable);
	}

	public Page<Cliente> findAllByOrderByDataUltimoContatto(Pageable pageable) {
		return clienteRepository.findAllByOrderByDataUltimoContatto(pageable);
	}

//Tutti i metodi findBy riguardanti il filtro

	public Page<Cliente> findByFatturatoAnnuale(Long fatturato1,Long fatturato2, Pageable pageable) {
		return clienteRepository.findByFatturatoAnnualeBetween(fatturato1, fatturato2, pageable);
	}

	public Page<Cliente> findByDataInserimento(LocalDate data, Pageable pageable) {
		return clienteRepository.findByDataInserimento(data, pageable);
	}

	public Page<Cliente> findByDataUltimoContatto(LocalDate data, Pageable pageable) {
		return clienteRepository.findByDataUltimoContatto(data, pageable);
	}

	public Page<Cliente> findByRagioneSociale(String nome, Pageable pageable) {
		return clienteRepository.findByRagioneSocialeContainingIgnoreCase(nome, pageable);
	}

//Altri metodi CRUD

	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente update(Cliente cliente, Long id) {
		Optional<Cliente> clienteResult = clienteRepository.findById(id);
		if (clienteResult.isPresent()) {
			Cliente clienteUpdate = clienteResult.get();
			clienteUpdate.setCognomeContatto(cliente.getCognomeContatto());
			clienteUpdate.setDataInserimento(cliente.getDataInserimento());
			clienteUpdate.setDataUltimoContatto(cliente.getDataUltimoContatto());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setEmailContatto(cliente.getEmailContatto());
			clienteUpdate.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			clienteUpdate.setFatture(cliente.getFatture());
			clienteUpdate.setNomeContatto(cliente.getNomeContatto());
			clienteUpdate.setPartitaIva(cliente.getPartitaIva());
			clienteUpdate.setPec(cliente.getPec());
			clienteUpdate.setRagioneSociale(cliente.getRagioneSociale());
			clienteUpdate.setSedeLegale(cliente.getSedeLegale());
			clienteUpdate.setSedeOperativa(cliente.getSedeOperativa());
			clienteUpdate.setTelefono(cliente.getTelefono());
			clienteUpdate.setTelefonoContatto(cliente.getTelefonoContatto());
			clienteUpdate.setTipo(cliente.getTipo());
			return clienteRepository.save(clienteUpdate);
		}
		else {
			throw new EnergyException("Cliente non aggiornato!");
		}
	}
	
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}
}
