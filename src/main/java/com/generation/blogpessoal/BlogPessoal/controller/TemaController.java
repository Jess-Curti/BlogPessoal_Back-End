package com.generation.blogpessoal.BlogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.BlogPessoal.model.Tema;
import com.generation.blogpessoal.BlogPessoal.repository.TemaRepository;

/**
 * Implementação dos endpoints relacionados com a model Tema
 * 
 * @author Jessica Curti
 * @date 08/02/2022
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	private @Autowired TemaRepository repository;

	@GetMapping("/all")
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByTemas(@PathVariable String descricao) {
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
	}

	@PostMapping("/new")
	public ResponseEntity<Tema> newTemas(@Valid @RequestBody Tema newTemas) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(newTemas));
	}

	@PutMapping("/edit")
	public ResponseEntity<Tema> editTemas(@Valid @RequestBody Tema editTemas) {
		return repository.findById(editTemas.getId())
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(repository.save(editTemas)))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void deleteTemas(@PathVariable long id) {
		Optional<Tema> tema = repository.findById(id);

		if(tema.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
		repository.deleteById(id);
	}
}
