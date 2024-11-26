package com.pastor126.galeriap.controller;


import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastor126.galeriap.dto.AtualSenDTO;
import com.pastor126.galeriap.service.AtualSenhaService;
import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping(value = "/atual")
@CrossOrigin
public class AtualSenhaController {
	
	@Autowired
	private AtualSenhaService atualService;
	
		
	@PostMapping
	public void atualizar(@RequestBody AtualSenDTO atualsenha, HttpServletRequest request) throws IOException, RuntimeException {
		 atualService.atualiza(atualsenha, request);
	}

	@DeleteMapping("/excluir")
	public String excluiconta(HttpServletRequest request) throws IOException {
		atualService.excluiconta(request);

		return "Usuário excluído com sucesso.";
	}
		
	
}
