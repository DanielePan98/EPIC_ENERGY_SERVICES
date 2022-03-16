package it.epicode.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Stato;
import it.epicode.be.energy.repository.StatoRepository;

@Service
public class StatoService {

	@Autowired
	StatoRepository statoRepository;

	public Optional<Stato> findById(Long id) {
		return statoRepository.findById(id);
	}

	public Page<Stato> findAll(Pageable pageable) {
		return statoRepository.findAll(pageable);
	}

	public Stato update(Stato stato, Long id) {
		Optional<Stato> statoResult = statoRepository.findById(id);
		if (statoResult.isPresent()) {
			Stato statoUpdate = statoResult.get();
			statoUpdate.setNome(stato.getNome());
			statoUpdate.setFatture(stato.getFatture());
			return statoRepository.save(statoUpdate);

		} else {
			throw new EnergyException("Stato fattura non aggiornato!");
		}

	}

	public Stato save(Stato stato) {
		return statoRepository.save(stato);
	}

	public void delete(Long id) {
		statoRepository.deleteById(id);
	}

}
