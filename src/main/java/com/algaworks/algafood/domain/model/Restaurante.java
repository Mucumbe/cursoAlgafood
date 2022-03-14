package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	@Column(name = "tx_frete")
	private BigDecimal taxaFrete;
	
	@JsonIgnore
	@ManyToOne
	private Cozinha cozinha;

	

}
