package it.epicode.be.energy.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.model.Fattura;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {

	Page<Fattura> findByClienteRagioneSocialeContainingIgnoreCase(String nome, Pageable pageable);

	Page<Fattura> findByStatoNomeContainingIgnoreCase(String nome, Pageable pageable);

	Page<Fattura> findByData(Date data, Pageable pageable);

	Page<Fattura> findByAnno(Integer anno, Pageable pageable);

	Page<Fattura> findByImportoBetween(BigDecimal valore1, BigDecimal valore2, Pageable pageable);

	List<Fattura> findByIdIn(List<Long> id);

	List<Fattura> findByStatoId(Long id);

	boolean existsById(Long id);

}
