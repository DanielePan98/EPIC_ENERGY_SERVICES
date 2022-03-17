package it.epicode.be.energy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.model.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
	
	boolean existsById(Long id);

}
