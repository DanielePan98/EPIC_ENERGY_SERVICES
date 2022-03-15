package it.epicode.be.energy.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Page<Cliente> findAllByOrderBySedeLegaleComuneProvinciaNome(Pageable pageable);
	Page<Cliente> findAllByOrderByFatturatoAnnuale(Pageable pageable);
	Page<Cliente> findAllByOrderByDataInserimento(Pageable pageable);
	Page<Cliente> findAllByOrderByDataUltimoContatto(Pageable pageable);
	
	Page<Cliente> findByFatturatoAnnualeBetween(Long fatturato1,Long fatturato2,Pageable pageable);
	Page<Cliente> findByDataInserimento(LocalDate data,Pageable pageable);
	Page<Cliente> findByDataUltimoContatto(LocalDate data,Pageable pageable);
	Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String nome,Pageable pageable);
	

}
