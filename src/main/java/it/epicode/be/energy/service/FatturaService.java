package it.epicode.be.energy.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.Stato;
import it.epicode.be.energy.repository.ClienteRepository;
import it.epicode.be.energy.repository.FatturaRepository;
import it.epicode.be.energy.repository.StatoRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	StatoRepository statoRepository;

	public Page<Fattura> findByClienteId(Long id, Pageable pageable) {
		return fatturaRepository.findByClienteId(id, pageable);
	}

	public Page<Fattura> findByStato(String nome, Pageable pageable) {
		return fatturaRepository.findByStatoNomeContainingIgnoreCase(nome, pageable);
	}

	public Page<Fattura> findByData(Date data, Pageable pageable) {
		return fatturaRepository.findByData(data, pageable);
	}

	public Page<Fattura> findByAnno(Integer anno, Pageable pageable) {
		return fatturaRepository.findByAnno(anno, pageable);
	}

	public Page<Fattura> findByImportoBetween(BigDecimal valore1, BigDecimal valore2, Pageable pageable) {
		return fatturaRepository.findByImportoBetween(valore1, valore2, pageable);
	}

	public Optional<Fattura> findById(Long id) {
		return fatturaRepository.findById(id);
	}

	public Page<Fattura> findAll(Pageable pageable) {
		return fatturaRepository.findAll(pageable);
	}

	public Fattura update(Fattura fattura, Long id) {
		Optional<Fattura> fatturaResult = fatturaRepository.findById(id);
		Optional<Cliente> clienteResult = clienteRepository.findById(fattura.getCliente().getId());
		Optional<Stato> statoResult = statoRepository.findById(fattura.getStato().getId());
		Stato statoUpdate = null;
		Cliente clienteUpdate = null;
		if (fatturaResult.isPresent()) {
			if (statoResult.isPresent()) {
				statoUpdate = statoResult.get();
			}
			if (clienteResult.isPresent()) {
				clienteUpdate = clienteResult.get();
			}
			clienteUpdate = clienteResult.get();
			Fattura fatturaUpdate = fatturaResult.get();
			fatturaUpdate.setAnno(fattura.getAnno());
			fatturaUpdate.setCliente(clienteUpdate);
			fatturaUpdate.setData(fattura.getData());
			fatturaUpdate.setImporto(fattura.getImporto());
			fatturaUpdate.setNumero(fattura.getNumero());
			fatturaUpdate.setStato(statoUpdate);
			return fatturaRepository.save(fatturaUpdate);

		} else {
			throw new EnergyException("Fattura non aggiornata!");
		}
	}

	public Fattura save(Fattura fattura) {
		return fatturaRepository.save(fattura);
	}

	public void deleteById(Long id) {
		if (fatturaRepository.existsById(id) == true) {
			fatturaRepository.deleteById(id);
		} else {
			throw new EnergyException("Fattura non esistente!");
		}
	}

}
