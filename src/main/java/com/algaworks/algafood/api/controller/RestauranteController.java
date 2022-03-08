package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	RestauranteRepository repository;

	@Autowired
	private CadastroRestauranteService service;

	@GetMapping
	public List<Restaurante> listar() {
		return repository.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {

		Restaurante restaurante = repository.buscar(id);
		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = service.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@RequestBody Restaurante restaurante, @PathVariable long id) {
		Restaurante restauranteactual = repository.buscar(id);
		if (restauranteactual == null) {
			return ResponseEntity.notFound().build();
		} else {
			try {
				restaurante = service.actualizar(restaurante, id);
				return ResponseEntity.ok(restaurante);
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> actualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos) {
		Restaurante restauranteactual= repository.buscar(id);
		if (restauranteactual==null) {
			return ResponseEntity.notFound().build();
		}
		merge(campos,restauranteactual);
		
		return actualizar(restauranteactual, id);
	}

	private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
		ObjectMapper objectMapper= new ObjectMapper();
		Restaurante restauranteorigen=objectMapper.convertValue(campos, Restaurante.class);
		
		campos.forEach((nomePropiedade,valorPropiedade)->{
			Field field =ReflectionUtils.findField(Restaurante.class,nomePropiedade);
			field.setAccessible(true);
			Object novoValor=ReflectionUtils.getField(field, restauranteorigen);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
			System.out.println(nomePropiedade+"="+valorPropiedade);
		});
	}
}