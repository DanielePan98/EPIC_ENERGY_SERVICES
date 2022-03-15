package it.epicode.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.repository.ProvinciaRepository;

@Service
public class ProvinciaService {
	
	@Autowired
	ProvinciaRepository provinciaRepository;
	
	public Optional<Provincia> findByNome(String nome) {
		return provinciaRepository.findByNomeIgnoreCase(nome);
	}
	
	public Optional<Provincia> findById(Long id) {
		return provinciaRepository.findById(id);
	}
	
	public Page<Provincia> findAll(Pageable pageable) {
		return provinciaRepository.findAll(pageable);
	}

}
