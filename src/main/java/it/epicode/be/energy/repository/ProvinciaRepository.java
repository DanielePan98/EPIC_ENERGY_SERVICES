package it.epicode.be.energy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	Optional<Provincia> findByNomeIgnoreCase(String nome);

}
