package com.algaworks.algafood.api.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.exception.EstadoNaoEncontradoExceprion;
import com.algaworks.algafood.api.domain.model.exception.NegocioException;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;
import com.algaworks.algafood.api.domain.service.CadastroCidadeService;
import com.algaworks.algafood.api.exceptionHandler.Problem;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroCidadeService service;

	@GetMapping
	public List<Cidade> listar() {

		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Cidade porId(@PathVariable long id) {

		return service.buscaPorIdValidada(id);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade cidade) {

		try {
			return service.salvar(cidade);
		} catch (EstadoNaoEncontradoExceprion e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable long id) {
		service.excluir(id);
	}

	@PutMapping("/{id}")
	public Cidade actualizar(@PathVariable long id, @RequestBody @Valid Cidade cidade) {

		return service.actualizar(cidade, id);
	}

	

}
