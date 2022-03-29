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
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public Restaurante buscar(@PathVariable Long id) {

		return service.buscarPorId(id);

	}

	@PostMapping
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		
			
			return service.salvar(restaurante);
		
	}

	@PutMapping("/{id}")
	public Restaurante actualizar(@RequestBody Restaurante restaurante, @PathVariable long id) {
		
				
				return service.actualizar(restaurante, id);
			
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		
		service.remover(id);
	}

	@PatchMapping("/{id}")
	public Restaurante actualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos) {
		Restaurante restauranteactual= service.buscarPorId(id);
		
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
