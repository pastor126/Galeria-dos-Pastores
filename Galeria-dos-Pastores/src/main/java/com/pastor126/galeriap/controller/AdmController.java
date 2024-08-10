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

import com.pastor126.galeriap.dto.UsuarioDTO;
import com.pastor126.galeriap.service.UsuarioService;

@RestController
@RequestMapping(value = "/adm")
@CrossOrigin
public class AdmController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping
	public List<UsuarioDTO> listarTodos() throws IOException{
		return usuarioService.listarTodos();
	}
	
	@PostMapping
	public void inserir(@RequestBody UsuarioDTO usuario) {
		usuarioService.inserirNovoUsuario(usuario);
	}
	
	
	
	@PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> alterar(@PathVariable Long id, @RequestBody UsuarioDTO usuario) throws IOException {
        UsuarioDTO atualizado = usuarioService.alterar(id, usuario);
        return ResponseEntity.ok(atualizado);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) throws IOException{
		
		usuarioService.excluir(id);
		
		return ResponseEntity.ok().build();
	}

}
