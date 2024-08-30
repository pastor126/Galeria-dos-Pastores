package com.pastor126.galeriap.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastor126.galeriap.dto.FalecomigoDTO;
import com.pastor126.galeriap.service.FalecomigoService;


@RestController
@RequestMapping(value = "/falecomigo")
@CrossOrigin
public class FalecomigoController {
	
	@Autowired
	private FalecomigoService falecomigoService;
	

	
	@PostMapping
	public void contatoU(@RequestBody FalecomigoDTO mensagem) {
		falecomigoService.contato(mensagem);
	}
	
	
}
