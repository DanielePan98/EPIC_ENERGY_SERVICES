package it.epicode.be.energy.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.model.Stato;
import it.epicode.be.energy.model.Tipo;
import it.epicode.be.energy.model.TipoEnum;
import it.epicode.be.energy.repository.ClienteRepository;
import it.epicode.be.energy.repository.ComuneRepository;
import it.epicode.be.energy.repository.FatturaRepository;
import it.epicode.be.energy.repository.IndirizzoRepository;
import it.epicode.be.energy.repository.ProvinciaRepository;
import it.epicode.be.energy.repository.StatoRepository;
import it.epicode.be.energy.repository.TipoRepository;
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
	@Autowired
	TipoRepository tipoRepository;
	@Autowired
	StatoRepository statoRepository;
	@Autowired
	IndirizzoRepository indirizzoRepository;
	@Autowired
	FatturaRepository fatturaRepository;
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public void run(String... args) throws Exception {
		log.info("*** Inizio caricamento dati ***");
		initProvincia();
		initComune();
		initUtente();
		initTipo();
		initStato();
		initIndirizzo();
		initCliente();
		initFattura();
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
				provincia = provinciaRepository.findByNomeIgnoreCase(values[3]);
				if (provincia.isPresent()) {
					comune = new Comune(values[2], values[3]);
					comune.setProvincia(provincia.get());
					comuneRepository.save(comune);
				}
			}
		}
	}

	private void initStato() throws Exception {
		Stato stato1 = new Stato();
		Stato stato2 = new Stato();

		stato1.setNome("Pagato");
		stato2.setNome("Non pagato");

		statoRepository.save(stato1);
		statoRepository.save(stato2);

	}

	private void initTipo() throws Exception {
		Tipo tipo1 = new Tipo();
		Tipo tipo2 = new Tipo();
		Tipo tipo3 = new Tipo();
		Tipo tipo4 = new Tipo();

		tipo1.setTipo(TipoEnum.PA);
		tipo2.setTipo(TipoEnum.SAS);
		tipo3.setTipo(TipoEnum.SPA);
		tipo4.setTipo(TipoEnum.SRL);

		tipoRepository.save(tipo1);
		tipoRepository.save(tipo2);
		tipoRepository.save(tipo3);
		tipoRepository.save(tipo4);

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

	private void initIndirizzo() throws Exception {
		Indirizzo i1 = new Indirizzo();
		i1.setCap("00152");
		i1.setCivico(12);
		i1.setLocalita("Roma");
		i1.setVia("Via Francesco Massi");
		i1.setComune(comuneRepository.findById(4756l).get());

		Indirizzo i2 = new Indirizzo();
		i2.setCap("00199");
		i2.setCivico(57);
		i2.setLocalita("Milano");
		i2.setVia("Via Raffaele Battistini");
		i2.setComune(comuneRepository.findById(1542l).get());

		Indirizzo i3 = new Indirizzo();
		i3.setCap("50489");
		i3.setCivico(180);
		i3.setLocalita("Agliè");
		i3.setVia("Via Giuseppe Verdi");
		i3.setComune(comuneRepository.findById(1l).get());

		indirizzoRepository.save(i1);
		indirizzoRepository.save(i2);
		indirizzoRepository.save(i3);
	}

	private void initFattura() throws Exception {
		Fattura f1 = new Fattura();
		f1.setAnno(2020);
		f1.setData(Date.valueOf("2020-04-04"));
		f1.setImporto(BigDecimal.valueOf(13450.99));
		f1.setNumero(122);
		f1.setStato(statoRepository.findById(1l).get());
		f1.setCliente(clienteRepository.findById(1l).get());

		Fattura f2 = new Fattura();
		f2.setAnno(2021);
		f2.setData(Date.valueOf("2021-05-14"));
		f2.setImporto(BigDecimal.valueOf(5999.49));
		f2.setNumero(389);
		f2.setStato(statoRepository.findById(2l).get());
		f2.setCliente(clienteRepository.findById(1l).get());

		Fattura f3 = new Fattura();
		f3.setAnno(2022);
		f3.setData(Date.valueOf("2022-06-24"));
		f3.setImporto(BigDecimal.valueOf(11500.34));
		f3.setNumero(567);
		f3.setStato(statoRepository.findById(2l).get());
		f3.setCliente(clienteRepository.findById(2l).get());

		Fattura f4 = new Fattura();
		f4.setAnno(2019);
		f4.setData(Date.valueOf("2019-09-20"));
		f4.setImporto(BigDecimal.valueOf(10000));
		f4.setNumero(61);
		f4.setStato(statoRepository.findById(1l).get());
		f4.setCliente(clienteRepository.findById(2l).get());

		fatturaRepository.save(f1);
		fatturaRepository.save(f2);
		fatturaRepository.save(f3);
		fatturaRepository.save(f4);
	}

	private void initCliente() throws Exception {

		Cliente c1 = new Cliente();
		c1.setCognomeContatto("Rossi");
		c1.setDataInserimento(LocalDate.parse("2021-06-07"));
		c1.setDataUltimoContatto(LocalDate.parse("2021-12-24"));
		c1.setEmail("riot.business@gmail.com");
		c1.setEmailContatto("marco.rossi@gmail.com");
		c1.setFatturatoAnnuale(1000000000l);
		c1.setNomeContatto("Marco");
		c1.setPartitaIva(12893542384l);
		c1.setPec("riot@pec.com");
		c1.setRagioneSociale("Riot");
		c1.setSedeLegale(indirizzoRepository.findById(1l).get());
		c1.setSedeOperativa(indirizzoRepository.findById(2l).get());
		c1.setTelefono(3337869087l);
		c1.setTelefonoContatto(333713921l);
		c1.setTipo(tipoRepository.findById(5l).get());

		Cliente c2 = new Cliente();
		c2.setCognomeContatto("Walker");
		c2.setDataInserimento(LocalDate.parse("2020-07-12"));
		c2.setDataUltimoContatto(LocalDate.parse("2020-10-17"));
		c2.setEmail("patate&co.business@gmail.com");
		c2.setEmailContatto("walker.paul@gmail.com");
		c2.setFatturatoAnnuale(1500000l);
		c2.setNomeContatto("Paul");
		c2.setPartitaIva(45893542384l);
		c2.setPec("patate&co@pec.com");
		c2.setRagioneSociale("Patate&co");
		c2.setSedeLegale(indirizzoRepository.findById(3l).get());
		c2.setSedeOperativa(indirizzoRepository.findById(3l).get());
		c2.setTelefono(3678869087l);
		c2.setTelefonoContatto(33375911l);
		c2.setTipo(tipoRepository.findById(6l).get());

		clienteRepository.save(c1);
		clienteRepository.save(c2);
	}
}
