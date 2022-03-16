package it.epicode.be.energy.controller.rest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.service.ClienteService;

@RestController
@SecurityRequirement(name="bearerAuth")
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping(path = "/cliente/lista")
	public ResponseEntity<Page<Cliente>> findAll(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAll(pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/ordinaPerDataInserimento")
	public ResponseEntity<Page<Cliente>> findAllByDataInserimento(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAllByOrderByDataInserimento(pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/ordinaPerDataUltimoContatto")
	public ResponseEntity<Page<Cliente>> findAllByDataUltimoContatto(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAllByOrderByDataUltimoContatto(pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/ordinaPerProvinciaSedeLegale")
	public ResponseEntity<Page<Cliente>> findAllProvinciaSedeLegale(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAllByOrderBySedeLegaleComuneNomeProvincia(pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/ordinaPerFatturatoAnnuale")
	public ResponseEntity<Page<Cliente>> findAllByFatturatoAnnuale(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAllByOrderByFatturatoAnnuale(pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/filtrataPerFatturatoAnnuale/{fatturato1}/{fatturato2}")
	public ResponseEntity<Page<Cliente>> findByFatturatoAnnuale(@PathVariable Long fatturato1,@PathVariable Long fatturato2, Pageable pageable) {
		Page<Cliente> find = clienteService.findByFatturatoAnnuale(fatturato1,fatturato2, pageable);
		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/filtrataPerDataInserimento/{data}")
	public ResponseEntity<Page<Cliente>> findByDataInserimento(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data, Pageable pageable) {
		Page<Cliente> find = clienteService.findByDataInserimento(data, pageable);
		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/filtrataPerDataUltimoContatto/{data}")
	public ResponseEntity<Page<Cliente>> findByDataUltimoContatto(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data, Pageable pageable) {
		Page<Cliente> find = clienteService.findByDataUltimoContatto(data, pageable);
		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/filtrataPerRagioneSociale/{nome}")
	public ResponseEntity<Page<Cliente>> findByRagioneSociale(@PathVariable String nome, Pageable pageable) {
		Page<Cliente> find = clienteService.findByRagioneSociale(nome, pageable);
		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/cliente/aggiungi")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		Cliente save = clienteService.save(cliente);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

	@PutMapping(path = "/cliente/aggiorna/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente save = clienteService.update(cliente, id);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/cliente/elimina/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		clienteService.delete(id);
		return new ResponseEntity<>("Cliente eliminato!", HttpStatus.OK);

	}
}
