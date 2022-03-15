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
import it.epicode.be.energy.model.Stato;
import it.epicode.be.energy.service.StatoService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class StatoController {

	@Autowired
	StatoService statoService;

	@GetMapping(path = "/stato/lista")
	public ResponseEntity<Page<Stato>> findAll(Pageable pageable) {
		Page<Stato> findAll = statoService.findAll(pageable);

		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/stato/id/{id}")
	public ResponseEntity<Stato> findById(@PathVariable(required = true) Long id) {
		Optional<Stato> find = statoService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/stato/aggiungi")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Stato> save(@RequestBody Stato stato) {
		Stato save = statoService.save(stato);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@PutMapping(path = "/stato/aggiorna/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Stato> update(@PathVariable Long id, @RequestBody Stato stato) {
		Stato save = statoService.update(stato, id);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/stato/elimina/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		statoService.delete(id);
		return new ResponseEntity<>("Stato eliminato!", HttpStatus.OK);

	}

}
