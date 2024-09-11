package com.pastor126.galeriap.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.FalecomigoDTO;



@Service
public class FalecomigoService {
    
	@Autowired
    EmailService emailService = new EmailService();

    public void contato(FalecomigoDTO falecomigo) {
//        FalecomigoEntity falecomigoEntity = new FalecomigoEntity(falecomigo);
        
        String mensagem = "Nome: " + falecomigo.getNome() + "\n" +
                          "E-mail: " + falecomigo.getEmail() + "\n" +
                          "Telefone: " + falecomigo.getTelefone() + "\n" +
                          "Mensagem: " + falecomigo.getMensagem();
        
        System.out.println("Mensagem: "+ mensagem);

        emailService.enviarEmailTexto("pastor126@gmail.com",
                "Falecomigo da Galeria dos Pastores", 
                mensagem);
    }
}
