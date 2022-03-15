package it.epicode.be.energy.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.service.IndirizzoService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class IndirizzoController {
	
	@Autowired
	IndirizzoService indirizzoService;
	
	@GetMapping(path = "/indirizzo/lista")
	public ResponseEntity<Page<Indirizzo>> findAll(Pageable pageable) {
		Page<Indirizzo> findAll = indirizzoService.findAll(pageable);

		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/indirizzo/id/{id}")
	public ResponseEntity<Indirizzo> findById(@PathVariable(required = true) Long id) {
		Optional<Indirizzo> find = indirizzoService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/indirizzo/aggiungi")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> save(@RequestBody Indirizzo indirizzo) {
		Indirizzo save = indirizzoService.save(indirizzo);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@PutMapping(path = "/indirizzo/aggiorna/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> update(@PathVariable Long id, @RequestBody Indirizzo indirizzo) {
		Indirizzo save = indirizzoService.update(indirizzo, id);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/indirizzo/elimina/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		indirizzoService.delete(id);
		return new ResponseEntity<>("Indirizzo eliminato!", HttpStatus.OK);

	}

}
