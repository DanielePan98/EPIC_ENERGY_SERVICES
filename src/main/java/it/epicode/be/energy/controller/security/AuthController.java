package it.epicode.be.energy.controller.security;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.epicode.be.energy.exception.EnergyException;
import it.epicode.be.energy.security.LoginRequest;
import it.epicode.be.energy.security.LoginResponse;
import it.epicode.be.energy.security.RequestRegisterUser;
import it.epicode.be.energy.security.Role;
import it.epicode.be.energy.security.RoleRepository;
import it.epicode.be.energy.security.Roles;
import it.epicode.be.energy.security.User;
import it.epicode.be.energy.security.UserDetailsImpl;
import it.epicode.be.energy.security.UserRepository;
import it.epicode.be.energy.security.UserService;
import it.epicode.be.energy.util.security.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.setToken(token);
		loginResponse.setRoles(roles);
		
		return ResponseEntity.ok(loginResponse);
	}
	
	@PostMapping("/signup")
	@Operation(summary="Se non si specifica il ruolo di default è user,si possono avere più ruoli")
    public ResponseEntity<?> registraUser(@RequestBody RequestRegisterUser registraUser) {

        if (userRepository.existsByEmail(registraUser.getEmail())) {
            return new ResponseEntity<String>("email già in uso!", HttpStatus.BAD_REQUEST);
        } else if (userRepository.existsByUserName(registraUser.getUserName())) {
            return new ResponseEntity<String>("username già in uso!", HttpStatus.BAD_REQUEST);
        }

        User userRegistrato = new User();
        userRegistrato.setUserName(registraUser.getUserName());
        userRegistrato.setEmail(registraUser.getEmail());
        userRegistrato.setPassword(encoder.encode(registraUser.getPassword()));
        userRegistrato.setNome(registraUser.getNome());
        userRegistrato.setCognome(registraUser.getCognome());
        if (registraUser.getRoles().isEmpty()) {
            Optional<Role> ruolo = roleRepository.findByRoleName(Roles.ROLE_USER);
            Set<Role> ruoli = new HashSet<>();
            ruoli.add(ruolo.get());
            userRegistrato.setRoles(ruoli);
        } else {
            Set<Role> ruoli = new HashSet<>();
            for (String set : registraUser.getRoles()) {
                switch (set.toUpperCase()) {
                case "ADMIN":
                    Optional<Role> ruoloA = roleRepository.findByRoleName(Roles.ROLE_ADMIN);
                    ruoli.add(ruoloA.get());
                    break;
                case "USER":
                    Optional<Role> ruoloB = roleRepository.findByRoleName(Roles.ROLE_USER);
                    ruoli.add(ruoloB.get());
                    break;
                default:
                    throw new EnergyException("Ruolo non trovato!");

                }

            }
            userRegistrato.setRoles(ruoli);

        }
        userRepository.save(userRegistrato);
        return new ResponseEntity<>("Utente inserito con successo: " + userRegistrato.toString(), HttpStatus.CREATED);

    }
	


}
