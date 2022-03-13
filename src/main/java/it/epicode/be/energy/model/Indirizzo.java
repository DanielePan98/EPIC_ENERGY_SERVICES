package it.epicode.be.energy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Indirizzo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String via;
	private int civico;
	private String localita;
	private int cap;
	@ManyToOne
	private Comune comune;
	@OneToMany(mappedBy = "sedeLegale")
	private List<Cliente> clientiSedeLegale;
	@OneToMany(mappedBy = "sedeOperativa")
	private List<Cliente> clientiSedeOperativa;

}
