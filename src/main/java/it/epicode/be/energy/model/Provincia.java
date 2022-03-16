package it.epicode.be.energy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sigla;
	private String regione;
	@OneToMany(mappedBy = "provincia")
	@JsonIgnoreProperties({ "nomeProvincia", "provincia", "indirizzi" })
	private List<Comune> comuni;

	public Provincia(String sigla, String nome, String regione) {
		this.nome = nome;
		this.sigla = sigla;
		this.regione = regione;
	}

}
