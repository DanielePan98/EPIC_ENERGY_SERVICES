package it.epicode.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.repository.ComuneRepository;
import it.epicode.be.energy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzoRepository;
	@Autowired
	ComuneRepository comuneRepository;

	public Optional<Indirizzo> findById(Long id) {
		return indirizzoRepository.findById(id);
	}

	public Page<Indirizzo> findAll(Pageable pageable) {
		return indirizzoRepository.findAll(pageable);
	}

	public Indirizzo save(Indirizzo indirizzo) {
		return indirizzoRepository.save(indirizzo);
	}

	public Indirizzo update(Indirizzo indirizzo, Long id) {
		Optional<Indirizzo> indirizzoResult = indirizzoRepository.findById(id);
		Optional<Comune> comuneResult = comuneRepository.findById(indirizzo.getComune().getId());
		if (indirizzoResult.isPresent()&comuneResult.isPresent()) {
			Indirizzo indirizzoUpdate = indirizzoResult.get();
			Comune comuneUpdate=comuneResult.get();
			indirizzoUpdate.setCap(indirizzo.getCap());
			indirizzoUpdate.setCivico(indirizzo.getCivico());
			indirizzoUpdate.setVia(indirizzo.getVia());
			indirizzoUpdate.setLocalita(indirizzo.getLocalita());
			indirizzoUpdate.setComune(comuneUpdate);
			indirizzoUpdate.setClientiSedeLegale(indirizzo.getClientiSedeLegale());
			indirizzoUpdate.setClientiSedeOperativa(indirizzo.getClientiSedeOperativa());
			return indirizzoRepository.save(indirizzoUpdate);

		} else {
			throw new EnergyException("Indirizzo non aggiornato!");
		}
	}

	public void delete(Long id) {
		indirizzoRepository.deleteById(id);
	}

}
