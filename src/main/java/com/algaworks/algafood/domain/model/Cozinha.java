package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;
	private String nome;
	
	//@JsonIgnore
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes=new ArrayList<>();
	
	
	
}
