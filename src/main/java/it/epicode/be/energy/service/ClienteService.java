package it.epicode.be.energy.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.model.Tipo;
import it.epicode.be.energy.repository.ClienteRepository;
import it.epicode.be.energy.repository.FatturaRepository;
import it.epicode.be.energy.repository.IndirizzoRepository;
import it.epicode.be.energy.repository.TipoRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	IndirizzoRepository indirizzoRepository;
	@Autowired
	FatturaRepository fatturaRepository;
	@Autowired
	TipoRepository tipoRepository;

// Tutti i metodi findAll riguardanti l'ordine

	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	public Page<Cliente> findAllByOrderBySedeLegaleComuneNomeProvincia(Pageable pageable) {
		return clienteRepository.findAllByOrderBySedeLegale_Comune_NomeProvincia(pageable);
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

	public Page<Cliente> findByFatturatoAnnuale(Long fatturato1, Long fatturato2, Pageable pageable) {
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
		Optional<Tipo> tipoResult = tipoRepository.findById(cliente.getTipo().getId());
		Optional<Indirizzo> indirizzoLegale = indirizzoRepository.findById(cliente.getSedeLegale().getId());
		Optional<Indirizzo> indirizzoOperativo = indirizzoRepository.findById(cliente.getSedeOperativa().getId());
		Optional<Fattura> fatturaResult = null;
		List<Fattura> fattureResult = null;
		List<Fattura> fattureUpdate = new ArrayList<>();
		Fattura fatturaUpdate = null;
		Tipo tipoUpdate = null;
		Indirizzo legale = null;
		Indirizzo operativo = null;
		if (clienteResult.isPresent()) {
			fattureResult = cliente.getFatture();
			if (fattureResult != null) {
				for (Fattura fattura : fattureResult) {
					fatturaResult = fatturaRepository.findById(fattura.getId());
					fatturaUpdate = fatturaResult.get();
					fattureUpdate.add(fatturaUpdate);
				}
			}
			if (tipoResult != null) {
				tipoUpdate = tipoResult.get();
			}

			if (indirizzoLegale != null) {
				legale = indirizzoLegale.get();
			}

			if (indirizzoOperativo != null) {
				operativo = indirizzoOperativo.get();
			}

			Cliente clienteUpdate = clienteResult.get();
			clienteUpdate.setCognomeContatto(cliente.getCognomeContatto());
			clienteUpdate.setDataInserimento(cliente.getDataInserimento());
			clienteUpdate.setDataUltimoContatto(cliente.getDataUltimoContatto());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setEmailContatto(cliente.getEmailContatto());
			clienteUpdate.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			clienteUpdate.setFatture(fattureUpdate);
			clienteUpdate.setNomeContatto(cliente.getNomeContatto());
			clienteUpdate.setPartitaIva(cliente.getPartitaIva());
			clienteUpdate.setPec(cliente.getPec());
			clienteUpdate.setRagioneSociale(cliente.getRagioneSociale());
			clienteUpdate.setSedeLegale(legale);
			clienteUpdate.setSedeOperativa(operativo);
			clienteUpdate.setTelefono(cliente.getTelefono());
			clienteUpdate.setTelefonoContatto(cliente.getTelefonoContatto());
			clienteUpdate.setTipo(tipoUpdate);
			return clienteRepository.save(clienteUpdate);
		} else {
			throw new EnergyException("Cliente non aggiornato!");
		}
	}

	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}
}
