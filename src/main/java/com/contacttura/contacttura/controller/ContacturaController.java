package com.contacttura.contacttura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contacttura.contacttura.model.Contactura;
import com.contacttura.contacttura.repository.ContacturaRepository;

@RestController
@RequestMapping({ "/contactura" })
public class ContacturaController {
	
	@Autowired
	private ContacturaRepository repository;

	ContacturaController(ContacturaRepository contacturaRepository) {
		this.repository = contacturaRepository;
	}
	
	//List All
	@GetMapping
	// http://localhost:8090/contactura
	public List findAll() {
		return repository.findAll();
	}

	// Find by id - Busca um usuário específico pelo id
	@GetMapping(value = "{id}")
	// http://localhost:8090/contactura/1
	public ResponseEntity findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Create
	@PostMapping
	// http://localhost:8090/contactura
	public Contactura create(@RequestBody Contactura contactura) {
		return repository.save(contactura);
	}

	// Update
	@PutMapping(value = "{id}")
	// http://localhost:8090/contactura/2
	// public ResponseEntity update(@PathVariable("id") long id, @RequestBody Contactura contactura) {
	public ResponseEntity update(@PathVariable long id,
			@RequestBody Contactura contactura) {
		return repository.findById(id)
				.map(record -> {
					record.setName(contactura.getName());
					record.setEmail(contactura.getEmail());
					record.setPhone(contactura.getPhone());
					Contactura update = repository.save(record);
					return ResponseEntity.ok().body(update);
				}).orElse(ResponseEntity.notFound().build());
				
	}
	
	
	@DeleteMapping(path = {"/{id}"})
	// http:localhost:8090/contatura/1
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}

}
