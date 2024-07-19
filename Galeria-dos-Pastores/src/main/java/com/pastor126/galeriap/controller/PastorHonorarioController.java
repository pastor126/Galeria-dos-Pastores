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
	
	@PostMapping
	public void inserir(@RequestBody PastoresHonorariosDTO pastores) {
		pastoresHonorariosService.inserir(pastores);
	}
	
	@PutMapping
	public PastoresHonorariosDTO alterar(@RequestBody PastoresHonorariosDTO pastores) {
		return pastoresHonorariosService.alterar(pastores);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
		pastoresHonorariosService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
