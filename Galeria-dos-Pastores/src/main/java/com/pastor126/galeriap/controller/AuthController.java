package com.pastor126.galeriap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastor126.galeriap.dto.AcessDTO;
import com.pastor126.galeriap.dto.AuthenticationDTO;
import com.pastor126.galeriap.dto.UsuarioDTO;
import com.pastor126.galeriap.service.AuthService;
import com.pastor126.galeriap.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<AcessDTO> login(@RequestBody AuthenticationDTO authDto){
		AcessDTO acessDTO = authService.login(authDto);
		
        return ResponseEntity.ok(acessDTO);
	}
	
	@PostMapping(value = "/novoUsuario")
	public void inserirNovoUsuario(@RequestBody UsuarioDTO novoUsuario){
		usuarioService.inserirNovoUsuario(novoUsuario);
	}
	
	@GetMapping(value = "/verificaCadastro/{uuid}")
	public String verificaCadastro(@PathVariable String uuid) {
		return usuarioService.verificaCadastro(uuid);
		
	}
}