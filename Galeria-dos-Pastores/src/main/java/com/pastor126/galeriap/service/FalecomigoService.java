package com.pastor126.galeriap.service;


import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.FalecomigoDTO;



@Service
public class FalecomigoService {
    
    EmailService emailService = new EmailService();

    public void contato(FalecomigoDTO falecomigo) {
//        FalecomigoEntity falecomigoEntity = new FalecomigoEntity(falecomigo);
        
        String mensagem = "Nome: " + falecomigo.getNome() + "\n" +
                          "E-mail: " + falecomigo.getEmail() + "\n" +
                          "Telefone: " + falecomigo.getTelefone() + "\n" +
                          "Mensagem: " + falecomigo.getMensagem();

        emailService.enviarEmailTexto("pastor126ptg@gmail.com",
                "Falecomigo da Galeria dos Pastores", 
                mensagem);
    }
}
