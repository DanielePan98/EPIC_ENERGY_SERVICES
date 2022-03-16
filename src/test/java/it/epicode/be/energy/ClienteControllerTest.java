package it.epicode.be.energy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.model.Stato;
import it.epicode.be.energy.model.Tipo;
import it.epicode.be.energy.model.TipoEnum;
import it.epicode.be.energy.repository.IndirizzoRepository;
import it.epicode.be.energy.repository.TipoRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ClienteControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	IndirizzoRepository indirizzoRepository;
	@Autowired
	TipoRepository tipoRepository;

	Cliente c1 = new Cliente();
	Cliente c2 = new Cliente();

	@BeforeEach
	public void contextInit() {
		log.info("Inizio");

		Stato stato1 = new Stato();
		stato1.setNome("Pagato");

		Stato stato2 = new Stato();
		stato2.setNome("Non pagato");

		Tipo tipo = new Tipo();
		tipo.setTipo(TipoEnum.PA);
		
		tipoRepository.save(tipo);

		Indirizzo i1 = new Indirizzo();
		i1.setCap("00152");
		i1.setCivico(12);
		i1.setLocalita("Roma");
		i1.setVia("Via Francesco Massi");

		Indirizzo i2 = new Indirizzo();
		i2.setCap("00199");
		i2.setCivico(57);
		i2.setLocalita("Milano");
		i2.setVia("Via Raffaele Battistini");
		
		indirizzoRepository.save(i1);
		indirizzoRepository.save(i2);
		

		Fattura f1 = new Fattura();
		f1.setAnno(2020);
		f1.setData(Date.valueOf("2020-04-04"));
		f1.setImporto(BigDecimal.valueOf(13450.99));
		f1.setNumero(122);
		f1.setStato(stato1);

		Fattura f2 = new Fattura();
		f2.setAnno(2021);
		f2.setData(Date.valueOf("2021-05-14"));
		f2.setImporto(BigDecimal.valueOf(5999.49));
		f2.setNumero(389);
		f2.setStato(stato2);

		List<Fattura> fatture = new ArrayList<>();
		fatture.add(f2);
		fatture.add(f1);
		
		
		c2.setCognomeContatto("Walker");
		c2.setDataInserimento(LocalDate.parse("2020-07-12"));
		c2.setDataUltimoContatto(LocalDate.parse("2020-10-17"));
		c2.setEmail("patate&co.business@gmail.com");
		c2.setEmailContatto("walker.paul@gmail.com");
		c2.setFatturatoAnnuale(1500000l);
		c2.setNomeContatto("Paul");
		c2.setPartitaIva(45893542384l);
		c2.setPec("patate&co@pec.com");
		c2.setRagioneSociale("Pippo");
		c2.setSedeLegale(i1);
		c2.setSedeOperativa(i2);
		c2.setTelefono(3678869087l);
		c2.setTelefonoContatto(33375911l);
		c2.setTipo(tipo);

		c1.setCognomeContatto("Rossi");
		c1.setDataInserimento(LocalDate.parse("2021-06-07"));
		c1.setDataUltimoContatto(LocalDate.parse("2021-12-24"));
		c1.setEmail("riot.business@gmail.com");
		c1.setEmailContatto("marco.rossi@gmail.com");
		c1.setFatturatoAnnuale(1000000000l);
		c1.setFatture(fatture);
		c1.setNomeContatto("Marco");
		c1.setPartitaIva(12893542384l);
		c1.setPec("riot@pec.com");
		c1.setRagioneSociale("Riot");
		c1.setSedeLegale(i1);
		c1.setSedeOperativa(i2);
		c1.setTelefono(3337869087l);
		c1.setTelefonoContatto(333713921l);
		c1.setTipo(tipo);

		log.info("Fine");
	}

	@Test
	@WithMockUser(username = "m.rossi", password = "test", roles = "ADMIN")
	public void addNewCliente() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(c1);

		@SuppressWarnings("unused")
		MvcResult result = mockMvc
				.perform(post("/api/cliente/aggiungi").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(content().json("{'ragioneSociale':'Riot'}"))
				.andExpect(content().json("{'cognomeContatto':'Rossi'}")).andReturn();
	}

	@Test
	@WithAnonymousUser
	public void getAllClienti() throws Exception {
		this.mockMvc.perform(get("/api/cliente/lista")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void listaClientiWhenUtenteMockIsAuthenticated() throws Exception {
		this.mockMvc.perform(get("/api/cliente/lista")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void deleteStudent() throws Exception {
		this.mockMvc.perform(delete("/api/cliente/elimina/1")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void updateCliente() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(c2);
        this.mockMvc.perform(
                put("/api/cliente/aggiorna/2")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(json)).andExpect(status().isOk()).andExpect(content().json("{'ragioneSociale':'Pippo'}"));	
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void deleteStudent2() throws Exception {
		this.mockMvc.perform(delete("/api/cliente/elimina/1")).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void filtraPerDataInserimento() throws Exception {
		this.mockMvc.perform(get("/api/cliente/ordinaPerDataInserimento")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void filtraPerDataUltimoContatto() throws Exception {
		this.mockMvc.perform(get("/api/cliente/ordinaPerDataUltimoContatto")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void OrdinaPerProvinciaSedeLegale() throws Exception {
		this.mockMvc.perform(get("/api/cliente/ordinaPerProvinciaSedeLegale")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void ordinaPerFatturatoAnnuale() throws Exception {
		this.mockMvc.perform(get("/api/cliente/ordinaPerFatturatoAnnuale")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void filtraPerFatturatoAnnuale() throws Exception {
		this.mockMvc.perform(get("/api/cliente/filtrataPerFatturatoAnnuale/10/100000000")).andExpect(status().isOk());
	}
	
	
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void filtraDataInserimentoNonEsistente() throws Exception {
		this.mockMvc.perform(get("/api/cliente/filtrataPerDataInserimento/1999-02-01")).andExpect(status().isNotFound());
	}
	
	
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void filtraDataUltimoContattoNonEsistente() throws Exception {
		this.mockMvc.perform(get("/api/cliente/filtrataPerDataUltimoContatto/1999-12-24")).andExpect(status().isNotFound());
	}
	
	
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void filtraPerRagioneSocialeNonEsistente() throws Exception {
		this.mockMvc.perform(get("/api/cliente/filtrataPerRagioneSociale/zz")).andExpect(status().isNotFound());
	}
	
	

}
