package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhaPorNome(@RequestParam String nome){
		
		return cozinhaRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/frete")
	public List<Restaurante> restauranteComFreteGratis(String nome){
		
		return restauranteRepository.findComFreteGrates(nome);
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro(){
		
		return restauranteRepository.buscarPrimrito();
	}
	
	@GetMapping("/restaurantes/taxaFrete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantePorTaxaFrete(String nome, Long id){
		
		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, id);
	}
	
	@GetMapping("/restaurantes/taxa")
	public List<Restaurante> restaurantePorTaxaFreteNome(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		return restauranteRepository.find(nome,taxaInicial, taxaFinal);
	}
}
