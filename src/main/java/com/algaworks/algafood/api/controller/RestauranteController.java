package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
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
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {

		Optional<Restaurante> restaurante = repository.findById(id);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
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
		Optional<Restaurante> restauranteactual = repository.findById(id);
		if (restauranteactual.isEmpty()) {
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long id){
		Optional<Restaurante> restaurante= repository.findById(id);
		
		if (restaurante.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> actualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos) {
		Optional<Restaurante> restauranteactual= repository.findById(id);
		if (restauranteactual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		merge(campos,restauranteactual.get());
		
		return actualizar(restauranteactual.get(), id);
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
