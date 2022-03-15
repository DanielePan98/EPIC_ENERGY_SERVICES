package it.epicode.be.energy.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.service.ProvinciaService;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ProvinciaController {
	
	@Autowired ProvinciaService provinciaService;
	
	@GetMapping(path = "/provincia/nome/{nome}")
	public ResponseEntity<Provincia> findByNome(@PathVariable(required = true) String nome) {
		Optional<Provincia> find = provinciaService.findByNome(nome);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/provincia/id/{id}")
	public ResponseEntity<Provincia> findById(@PathVariable(required = true) Long id) {
		Optional<Provincia> find = provinciaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/provincia/lista")
	public ResponseEntity<Page<Provincia>> findAll(Pageable pageable) {
		Page<Provincia> findAll = provinciaService.findAll(pageable);

		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}
