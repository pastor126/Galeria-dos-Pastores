package com.pastor126.galeriap.dto;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.entity.FalecomigoEntity;


public class FalecomigoDTO {
	
	private String nome;
	
	private String email;
	
	private int telefone;
	
	private String mensagem;

	
	public FalecomigoDTO(FalecomigoEntity falecomigo) {
		BeanUtils.copyProperties(falecomigo, this);
	}
	
	public FalecomigoDTO() {
		
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
