package com.pastor126.galeriap.entity;


import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.dto.FalecomigoDTO;
import com.pastor126.galeriap.dto.UsuarioDTO;
import com.pastor126.galeriap.entity.enums.SituacaoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class FalecomigoEntity {
	
	
	
	private String nome;
	
	private String email;
	
	private int telefone;
	
	private String mensagem;

	
	public FalecomigoEntity(FalecomigoDTO falecomigo) {
		BeanUtils.copyProperties(falecomigo, this);
	}
	
	public FalecomigoEntity() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	


	

}
