package com.algaworks.algafood.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;


@ValorZeroIncluiDescricao(valorField = "taxaFrete",descricaoField= "nome", descricaoObrigatoria="Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	
	
	@NotBlank
	private String nome;
	
	//@DecimalMin("1")
	//@TaxaFrete

	@Column(name = "tx_frete")
	private BigDecimal taxaFrete;
	
	//@JsonIgnore
	//@JsonIgnoreProperties(value = "hibernateLazyInitializer")
	@Valid
	@ConvertGroup(from = Default.class,to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name ="cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos= new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany//(fetch = FetchType.EAGER)
	//@JoinTable(name = "restaurante_formas_pagamento",joinColumns = @JoinColumn(name="restaurantes_id"),
	//inverseJoinColumns = @JoinColumn(name="forma_pagamentos_id"))
	private List<FormaPagamento> formaPagamento= new ArrayList<>();
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataActualizacao;

}
