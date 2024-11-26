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
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/pastoresHonorarios")
@CrossOrigin
public class PastorHonorarioController {
	
	@Autowired
	private PastoresHonorariosService pastoresHonorariosService;
	
	@GetMapping
	public List<PastoresHonorariosDTO> listarTodos(HttpServletRequest request) throws IOException{
	return pastoresHonorariosService.listarTodos(request);	
	}
	
	@GetMapping("/{id}")
	public PastoresHonorariosDTO buscarId(@PathVariable Long id, HttpServletRequest request) throws IOException{
	return pastoresHonorariosService.buscarPorId(id, request);
	}
	
	@PostMapping
	public void inserir(@RequestBody PastoresHonorariosDTO pastores) {
		pastoresHonorariosService.inserir(pastores);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PastoresHonorariosDTO> alterar(@PathVariable Long id , @RequestBody PastoresHonorariosDTO pastores, HttpServletRequest request) throws IOException {
		PastoresHonorariosDTO atual = pastoresHonorariosService.alterar(id, pastores, request);
		return ResponseEntity.ok(atual);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id, HttpServletRequest request) throws IOException{
		pastoresHonorariosService.excluir(id, request);
		return ResponseEntity.ok().build();
	}

}
