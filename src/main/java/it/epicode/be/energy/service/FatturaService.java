package it.epicode.be.energy.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepository;

	public Page<Fattura> findByClienteId(Long id, Pageable pageable) {
		return fatturaRepository.findByClienteId(id, pageable);
	}

	public Page<Fattura> findByStatoId(Long id, Pageable pageable) {
		return fatturaRepository.findByStatoId(id, pageable);
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
		if (fatturaResult.isPresent()) {
			Fattura fatturaUpdate = fatturaResult.get();
			fatturaUpdate.setAnno(fattura.getAnno());
			fatturaUpdate.setCliente(fattura.getCliente());
			fatturaUpdate.setData(fattura.getData());
			fatturaUpdate.setImporto(fattura.getImporto());
			fatturaUpdate.setNumero(fattura.getNumero());
			fatturaUpdate.setStato(fattura.getStato());
			return fatturaRepository.save(fatturaUpdate);

		} else {
			throw new EnergyException("Fattura non aggiornata!");
		}
	}

	public Fattura save(Fattura fattura) {
		return fatturaRepository.save(fattura);
	}

	public void delete(Long id) {
		fatturaRepository.deleteById(id);
	}

}