package it.epicode.be.energy.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.energy.security.User;
import it.epicode.be.energy.security.UserService;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/lista")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> find = userService.findAll(pageable);

		if (find.hasContent()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
