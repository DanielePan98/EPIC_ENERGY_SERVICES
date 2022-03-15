package it.epicode.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.repository.ComuneRepository;

@Service
public class ComuneService {
	
	@Autowired
	ComuneRepository comuneRepository;
	
	public Optional<Comune> findById(Long id) {
		return comuneRepository.findById(id);
	}
	
	public Page<Comune> findByNomeComune(String nome,Pageable pageable) {
		return comuneRepository.findByNomeComuneContainingIgnoreCase(nome,pageable);
	}
	
	public Page<Comune> findAll(Pageable pageable) {
		return comuneRepository.findAll(pageable);
	}

}
