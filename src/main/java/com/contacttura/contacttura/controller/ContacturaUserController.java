package com.contacttura.contacttura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contacttura.contacttura.model.ContacturaUser;
import com.contacttura.contacttura.repository.ContacturaUserRepository;

@RestController
@RequestMapping({ "/user" })
public class ContacturaUserController {

	@Autowired
	private ContacturaUserRepository repository;

	ContacturaUserController(ContacturaUserRepository repository) {
		this.repository = repository;
	}

	// List All
	@GetMapping
	public List findAll() {
		return repository.findAll();
	}

	// Find By Id - Busca um usuário específico pelo Id
	@GetMapping(value = "{id}")
	public ResponseEntity findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Create
	@PostMapping
	public ContacturaUser create(@RequestBody ContacturaUser contacturaUser) {
		contacturaUser.setPassword(criptografarSenha(contacturaUser.getPassword()));
		return repository.save(contacturaUser);
	}

	/**
	 * 
	 * Método utilizado para criptografar senha
	 * 
	 * @param password
	 * @return senhaCriptografada
	 */
	private String criptografarSenha(String password) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String senhaCriptografada = passwordEncoder.encode(password);

		return senhaCriptografada;
	}

	// Update
	@PutMapping(value = "{id}")
	public ResponseEntity update(@PathVariable long id, @RequestBody ContacturaUser contacturaUser) {
		return repository.findById(id).map(record -> {
			record.setName(contacturaUser.getName());
			record.setUsername(contacturaUser.getUsername());
			record.setPassword(criptografarSenha(contacturaUser.getPassword()));
			record.setAdmin(contacturaUser.isAdmin());
			ContacturaUser update = repository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}// fim public class ContacturaUserController
