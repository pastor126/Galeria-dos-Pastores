package com.pastor126.galeriap.controller;

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

import com.pastor126.galeriap.dto.RecursosDTO;
import com.pastor126.galeriap.service.RecursosService;

@RestController
@RequestMapping(value = "/recurso")
@CrossOrigin
public class RecursoController {
	
	@Autowired
	private RecursosService recursoService;
	
	@GetMapping
	public List<RecursosDTO> listarTodos(){
		return recursoService.listarTodos();
	}
	
	@PostMapping
	public void inserir(@RequestBody RecursosDTO recurso) {
		recursoService.inserir(recurso);
	}
	
	@PutMapping
	public RecursosDTO alterar(@RequestBody RecursosDTO recurso) {
		return recursoService.alterar(recurso);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		recursoService.excluir(id);
		return ResponseEntity.ok().build();
	}
}
