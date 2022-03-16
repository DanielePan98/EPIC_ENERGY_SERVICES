package it.epicode.be.energy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.model.Comune;

public interface ComuneRepository extends JpaRepository<Comune, Long> {

	Page<Comune> findByNomeComuneContainingIgnoreCase(String nome, Pageable pageable);

}
