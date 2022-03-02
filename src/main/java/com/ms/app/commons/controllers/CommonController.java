package com.ms.app.commons.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms.app.commons.services.CommonService;

public class CommonController<E, S extends CommonService<E>> {

	@Autowired
	protected S _service;

	@GetMapping("/")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok().body(_service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getObject(@PathVariable Long id) {
		Optional<E> optional = _service.findById(id);
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(optional.get());
	}

	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody E object) {
		E objectToDataBase = _service.save(object);
		return ResponseEntity.status(HttpStatus.CREATED).body(objectToDataBase);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteObject(@PathVariable Long id) {
		_service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
