package com.generation.blogpessoal.BlogPessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.BlogPessoal.model.Temas;
import com.generation.blogpessoal.BlogPessoal.repository.TemasRepository;

@RestController
@RequestMapping("/tema")
@CrossOrigin("*")
public class TemasController {

	@Autowired
	private TemasRepository repository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Temas>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Temas> GetById(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{tema}") 
	public ResponseEntity<List<Temas>> GetByTemas (@PathVariable String tema)  {
		return ResponseEntity.ok(repository.findAllByTemasContainingIgnoreCase(tema));
	}     

	@PostMapping("/new")
	public ResponseEntity <Temas> newTemas (@RequestBody Temas newTemas) {
		return ResponseEntity.status(201).body(repository.save(newTemas));
	}

	@PutMapping("/edit")
	public ResponseEntity <Temas> editTemas (@RequestBody Temas editTemas) {
		return ResponseEntity.status (200).body(repository.save(editTemas));
	}

	@DeleteMapping ("/delete/{id}")
	public void deleteTemas (@PathVariable long id) {
		repository.deleteById(id);
	}
}
