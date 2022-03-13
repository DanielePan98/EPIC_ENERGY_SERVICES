package it.epicode.be.energy.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Fattura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer anno;
	private Date data;
	private BigDecimal importo;
	private Integer numero;
	@ManyToOne
	private Stato stato;
	@ManyToOne
	private Cliente cliente;

}
