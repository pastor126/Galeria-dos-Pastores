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
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@PostMapping
	public void inserir(@RequestBody UsuarioDTO usuario) {
		usuarioService.inserirNovoUsuario(usuario);
	}
	
	@GetMapping
	public List<UsuarioDTO> listarTodos(HttpServletRequest request) throws IOException{
		return usuarioService.listarTodos(request);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> alterar(@PathVariable Long id, @RequestBody UsuarioDTO usuario, HttpServletRequest request) throws IOException {
        UsuarioDTO atualizado = usuarioService.alterar(id, usuario, request);
        return ResponseEntity.ok(atualizado);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id, HttpServletRequest request) throws IOException{
		usuarioService.excluir(id, request);
		return ResponseEntity.ok().build();
	}


}
