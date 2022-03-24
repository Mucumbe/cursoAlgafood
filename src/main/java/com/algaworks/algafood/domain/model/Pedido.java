package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;

	@Column(nullable = false)
	private BigDecimal subtTotal;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@Column(nullable = false)
	private BigDecimal valorTotal;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDate dataCriacao;
	
	private LocalDate dataConfirmacao;
	private LocalDate dataCancelamento;
	private LocalDate dataEntrega;
	private String statusPedido;
	
	
	@ManyToOne
	private Restaurante restaurante;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private FormaPagamento formaPagamento;
	
	@Embedded
	private Endereco endereco;
	
	@OneToMany
	private List<ItemPedido>  itemPedido;
	

}
