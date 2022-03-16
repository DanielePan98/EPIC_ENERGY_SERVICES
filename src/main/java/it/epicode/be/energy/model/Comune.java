package it.epicode.be.energy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Comune {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeComune;
	private String nomeProvincia;
	@ManyToOne
	@JsonIgnoreProperties({ "regione", "sigla", "comuni" })
	private Provincia provincia;
	@OneToMany(mappedBy = "comune")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Indirizzo> indirizzi;

	public Comune(String nomeComune, String nomeProvincia) {
		this.nomeComune = nomeComune;
		this.nomeProvincia = nomeProvincia;
	}

}
