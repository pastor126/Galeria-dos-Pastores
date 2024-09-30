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
import com.pastor126.galeriap.dto.PastoresDTO;
import com.pastor126.galeriap.service.PastoresService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/pastores")
@CrossOrigin
public class PastorController {
	
	@Autowired
	private PastoresService pastoresService;
	
	@GetMapping
	public List<PastoresDTO> listarTodos(HttpServletRequest request) throws IOException{
	return pastoresService.listarTodos(request);	
	}
	
	@GetMapping("/{id}")
	public PastoresDTO buscarId(@PathVariable("id") Long id, HttpServletRequest request) throws IOException{
	return pastoresService.buscarPorId(id, request);
	}
	
	@PostMapping
	public void inserir(@RequestBody PastoresDTO pastores) {
		pastoresService.inserir(pastores);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PastoresDTO> alterar(@PathVariable Long id ,@RequestBody PastoresDTO pastores, HttpServletRequest request) throws IOException {
        PastoresDTO atualizado = pastoresService.alterar(id, pastores, request);
        return ResponseEntity.ok(atualizado);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id, HttpServletRequest request) throws IOException{
		pastoresService.excluir(id, request);
		return ResponseEntity.ok().build();
	}

}
