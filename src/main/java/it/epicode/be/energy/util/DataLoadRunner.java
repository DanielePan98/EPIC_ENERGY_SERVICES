package it.epicode.be.energy.util;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.repository.ComuneRepository;
import it.epicode.be.energy.repository.ProvinciaRepository;
import it.epicode.be.energy.security.Role;
import it.epicode.be.energy.security.RoleRepository;
import it.epicode.be.energy.security.Roles;
import it.epicode.be.energy.security.User;
import it.epicode.be.energy.security.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataLoadRunner implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ComuneRepository comuneRepository;
	@Autowired
	ProvinciaRepository provinciaRepository;

	@Override
	public void run(String... args) throws Exception {
		log.info("*** Inizio caricamento dati ***");
		initProvincia();
		initComune();
		initUtente();
		log.info("*** Fine caricamento dati ***");

	}

	private void initProvincia() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane.csv"));) {
			String[] values = null;
			csvReader.readNext();
			while ((values = csvReader.readNext()) != null) {
				provinciaRepository.save(new Provincia(values[0], values[1], values[2]));
			}
		}

	}

	private void initComune() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
			String[] values = null;
			csvReader.readNext();
			Optional<Provincia> provincia;
			Comune comune;
			while ((values = csvReader.readNext()) != null) {
				provincia=provinciaRepository.findByNomeIgnoreCase(values[3]);
				if(provincia.isPresent()) {
					comune = new Comune(values[2], values[3]);
					comune.setProvincia(provincia.get());
					comuneRepository.save(comune);
				}			
			}
		}
	}

	private void initUtente() throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		Role role = new Role();
		Role role2 = new Role();

		role2.setRoleName(Roles.ROLE_USER);
		role.setRoleName(Roles.ROLE_ADMIN);

		User user = new User();
		User user2 = new User();
		Set<Role> rolesAdmin = new HashSet<>();
		Set<Role> rolesUser = new HashSet<>();
		rolesAdmin.add(role);
		rolesUser.add(role2);

		user2.setUserName("user");
		user2.setPassword(bCrypt.encode("user"));
		user2.setEmail("user@gmail.com");
		user2.setRoles(rolesUser);
		user2.setActive(true);

		user.setUserName("admin");
		user.setPassword(bCrypt.encode("admin"));
		user.setEmail("admin@domain.com");
		user.setRoles(rolesAdmin);
		user.setActive(true);

		roleRepository.save(role2);
		roleRepository.save(role);

		userRepository.save(user);
		userRepository.save(user2);
	}
}
