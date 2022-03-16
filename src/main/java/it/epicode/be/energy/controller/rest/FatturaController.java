package it.epicode.be.energy.controller.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

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
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.service.FatturaService;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class FatturaController {

	@Autowired
	FatturaService fatturaService;

	@GetMapping(path = "/fattura/lista")
	public ResponseEntity<Page<Fattura>> findAll(Pageable pageable) {
		Page<Fattura> findAll = fatturaService.findAll(pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/fattura/id/{id}")
	public ResponseEntity<Optional<Fattura>> findById(@PathVariable Long id) {
		Optional<Fattura> findAll = fatturaService.findById(id);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/fattura/filtra/cliente/{id}")
	public ResponseEntity<Page<Fattura>> findByClienteId(@PathVariable Long id, Pageable pageable) {
		Page<Fattura> findAll = fatturaService.findByClienteId(id, pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/fattura/filtra/stato/{nome}")
	public ResponseEntity<Page<Fattura>> findByStato(@PathVariable String nome, Pageable pageable) {
		Page<Fattura> findAll = fatturaService.findByStato(nome, pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/fattura/filtra/data/{data}")
	public ResponseEntity<Page<Fattura>> findByData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date data,
			Pageable pageable) {
		Page<Fattura> findAll = fatturaService.findByData(data, pageable);
		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/fattura/filtra/anno/{anno}")
	public ResponseEntity<Page<Fattura>> findByFatturatoAnnuale(@PathVariable Integer anno, Pageable pageable) {
		Page<Fattura> find = fatturaService.findByAnno(anno, pageable);
		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/fattura/filtra/importo/{importo1}/{importo2}")
	public ResponseEntity<Page<Fattura>> findByDataInserimento(@PathVariable BigDecimal importo1,@PathVariable BigDecimal importo2, Pageable pageable) {
		Page<Fattura> find = fatturaService.findByImportoBetween(importo1,importo2, pageable);
		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "/fattura/aggiungi")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> save(@RequestBody Fattura fattura) {
		Fattura save = fatturaService.save(fattura);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

	@PutMapping(path = "/fattura/aggiorna/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> update(@PathVariable Long id, @RequestBody Fattura fattura) {
		Fattura save = fatturaService.update(fattura, id);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/fattura/elimina/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		fatturaService.deleteById(id);
		return new ResponseEntity<>("Fattura eliminata!", HttpStatus.OK);

	}

}
