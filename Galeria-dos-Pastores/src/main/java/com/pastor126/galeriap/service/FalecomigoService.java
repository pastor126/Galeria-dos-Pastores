package com.pastor126.galeriap.service;


import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.FalecomigoDTO;
import com.pastor126.galeriap.entity.FalecomigoEntity;


@Service
public class FalecomigoService {
    
    EmailService emailService = new EmailService();

    public void contato(FalecomigoDTO falecomigo) {
        FalecomigoEntity falecomigoEntity = new FalecomigoEntity(falecomigo);
        
        String mensagem = "Nome: " + falecomigoEntity.getNome() + "\n" +
                          "E-mail: " + falecomigoEntity.getEmail() + "\n" +
                          "Telefone: " + falecomigoEntity.getTelefone() + "\n" +
                          "Mensagem: " + falecomigoEntity.getMensagem();

        emailService.enviarEmailTexto("pastor126ptg@gmail.com",
                "Falecomigo da Galeria dos Pastores", 
                mensagem);
    }
}
