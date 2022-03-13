package it.epicode.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirzzoRepository;

	public Optional<Indirizzo> findById(Long id) {
		return indirzzoRepository.findById(id);
	}

	public Page<Indirizzo> findAll(Pageable pageable) {
		return indirzzoRepository.findAll(pageable);
	}

	public Indirizzo save(Indirizzo indirizzo) {
		return indirzzoRepository.save(indirizzo);
	}

	public Indirizzo update(Indirizzo indirizzo, Long id) {
		Optional<Indirizzo> studenteResult = indirzzoRepository.findById(id);
		if (studenteResult.isPresent()) {
			Indirizzo indirizzoUpdate = studenteResult.get();
			indirizzoUpdate.setCap(indirizzo.getCap());
			indirizzoUpdate.setCivico(indirizzo.getCivico());
			indirizzoUpdate.setVia(indirizzo.getVia());
			indirizzoUpdate.setLocalita(indirizzo.getLocalita());
			indirizzoUpdate.setComune(indirizzo.getComune());
			indirizzoUpdate.setClientiSedeLegale(indirizzo.getClientiSedeLegale());
			indirizzoUpdate.setClientiSedeOperativa(indirizzo.getClientiSedeOperativa());
			return indirzzoRepository.save(indirizzoUpdate);

		} else {
			throw new EnergyException("Indirizzo non aggiornato!");
		}
	}

	public void delete(Long id) {
		indirzzoRepository.deleteById(id);
	}

}
