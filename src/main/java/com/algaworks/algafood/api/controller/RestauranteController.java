package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
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

import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import com.algaworks.algafood.api.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
		
			
			return service.salvar(restaurante);
		
	}

	@PutMapping("/{id}")
	public Restaurante actualizar(@RequestBody @Valid Restaurante restaurante, @PathVariable long id) {
		
				
				return service.actualizar(restaurante, id);
			
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		
		service.remover(id);
	}

	@PatchMapping("/{id}")
	public Restaurante actualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos, HttpServletRequest servletRequest) {
		Restaurante restauranteactual= service.buscarPorId(id);
		
		merge(campos,restauranteactual,servletRequest);
		
		return actualizar(restauranteactual, id);
	}

	private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest servletRequest) {
		
		ServletServerHttpRequest servletServerHttpRequest= new ServletServerHttpRequest(servletRequest);
		try {
			ObjectMapper objectMapper= new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteorigen=objectMapper.convertValue(campos, Restaurante.class);
			
			campos.forEach((nomePropiedade,valorPropiedade)->{
				Field field =ReflectionUtils.findField(Restaurante.class,nomePropiedade);
				field.setAccessible(true);
				Object novoValor=ReflectionUtils.getField(field, restauranteorigen);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
				System.out.println(nomePropiedade+"="+valorPropiedade);
			});
		} catch (IllegalArgumentException e) {
			
			Throwable rootcouse=ExceptionUtils.getRootCause(e);
			
			throw new HttpMessageNotReadableException(e.getMessage(), rootcouse,servletServerHttpRequest);
		}
		
	}
}
