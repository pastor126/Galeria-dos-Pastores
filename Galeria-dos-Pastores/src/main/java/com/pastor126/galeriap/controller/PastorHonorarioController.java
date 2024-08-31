package com.pastor126.galeriap.controller;

import java.io.IOException;
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

import com.pastor126.galeriap.dto.PastoresHonorariosDTO;
import com.pastor126.galeriap.service.PastoresHonorariosService;


@RestController
@RequestMapping(value = "/pastoresHonorarios")
@CrossOrigin
public class PastorHonorarioController {
	
	@Autowired
	private PastoresHonorariosService pastoresHonorariosService;

	
	@GetMapping
	public List<PastoresHonorariosDTO> listarTodos() throws IOException{
	return pastoresHonorariosService.listarTodos();	
	}
	
	@GetMapping("/{id}")
	public PastoresHonorariosDTO buscarId(@PathVariable("id") Long id) throws IOException{
	return pastoresHonorariosService.buscarPorId(id);
	}
	
	@PostMapping
	public void inserir(@RequestBody PastoresHonorariosDTO pastores) {
		pastoresHonorariosService.inserir(pastores);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PastoresHonorariosDTO> alterar(@PathVariable Long id , @RequestBody PastoresHonorariosDTO pastores) throws IOException {
		PastoresHonorariosDTO atual = pastoresHonorariosService.alterar(id, pastores);
		return ResponseEntity.ok(atual);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) throws IOException{
		pastoresHonorariosService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
