package it.epicode.be.energy.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
	private LocalDate dataInserimento;
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
	@ManyToOne
	private Indirizzo sedeLegale;
	@ManyToOne
	private Indirizzo sedeOperativa;
	@OneToMany(mappedBy = "cliente")
	 @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Fattura> fatture;


}
