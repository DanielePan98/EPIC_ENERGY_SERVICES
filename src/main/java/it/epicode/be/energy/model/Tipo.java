package it.epicode.be.energy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Data
public class Tipo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(name = "nome")
	private TipoEnum tipo;
	@OneToMany(mappedBy = "tipo")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Cliente> clienti;

}
