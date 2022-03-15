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
import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.service.ComuneService;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ComuneController {

	@Autowired
	ComuneService comuneService;

	@GetMapping(path = "/comune/id/{id}")
	public ResponseEntity<Comune> findById(@PathVariable(required = true) Long id) {
		Optional<Comune> find = comuneService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/comune/nome/{nome}")
	public ResponseEntity<Page<Comune>> findByNomeComune(@PathVariable String nome,Pageable pageable) {
		Page<Comune> findAll = comuneService.findByNomeComune(nome,pageable);

		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/comune/lista")
	public ResponseEntity<Page<Comune>> findAll(Pageable pageable) {
		Page<Comune> findAll = comuneService.findAll(pageable);

		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}
