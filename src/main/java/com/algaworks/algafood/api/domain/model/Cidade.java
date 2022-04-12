package com.algaworks.algafood.api.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded =true)
@Entity
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;
	
	@NotNull
	private String nome;
	
	@Valid
	@ConvertGroup(from = Default.class,to = Groups.EstadoId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name ="estado_id", nullable = false)
	private Estado estado;
}
