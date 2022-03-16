package it.epicode.be.energy.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ragioneSociale;
	private Long partitaIva;
	private String email;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInserimento;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataUltimoContatto;
	private Long fatturatoAnnuale;
	private String pec;
	private Long telefono;
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	private Long telefonoContatto;
	@ManyToOne
	private Tipo tipo;
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Indirizzo sedeLegale;
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Indirizzo sedeOperativa;
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Fattura> fatture;

}
