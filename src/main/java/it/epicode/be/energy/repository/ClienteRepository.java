package it.epicode.be.energy.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Page<Cliente> findAllByOrderBySedeLegale_Comune_NomeProvincia(Pageable pageable);

	Page<Cliente> findAllByOrderByFatturatoAnnuale(Pageable pageable);

	Page<Cliente> findAllByOrderByDataInserimento(Pageable pageable);

	Page<Cliente> findAllByOrderByDataUltimoContatto(Pageable pageable);

	Page<Cliente> findByFatturatoAnnualeBetween(Long fatturato1, Long fatturato2, Pageable pageable);

	Page<Cliente> findByDataInserimento(LocalDate data, Pageable pageable);

	Page<Cliente> findByDataUltimoContatto(LocalDate data, Pageable pageable);

	Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String nome, Pageable pageable);// Containing viene tradotto in "… where x.firstname like ?1 (parameter bound wrapped in %)"

	Optional<Cliente> findBySedeLegaleId(Long id);

	Optional<Cliente> findBySedeOperativaId(Long id);
	
	boolean existsById(Long id);

}
