package com.pastor126.galeriap.dto;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.entity.AcessEntity;


public class AcessDTO {
	private Long id;
	private String token;
	private String username;
	
	public AcessDTO(AcessEntity acess) {
		BeanUtils.copyProperties(acess, this);
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AcessDTO() {
		super();
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	
}
