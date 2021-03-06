package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.repository.EstadoRepository;
import com.algaworks.algafood.api.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	EstadoRepository repository;

	@Autowired
	private CadastroEstadoService service;

	@GetMapping
	public List<Estado> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Estado buscar(@PathVariable long id) {

		return service.buscarPorId(id);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid Estado estado) {
		return repository.save(estado);

	}

	@PutMapping("/{id}")
	public Estado actualizar(@RequestBody @Valid Estado estado, @PathVariable long id) {

		Estado estadoActual = service.buscarPorId(id);

		BeanUtils.copyProperties(estado, estadoActual, "id");

		return repository.save(estadoActual);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable long id) {
		service.excluir(id);

	}
}
