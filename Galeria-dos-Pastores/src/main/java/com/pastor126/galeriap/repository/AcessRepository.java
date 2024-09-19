package com.pastor126.galeriap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastor126.galeriap.entity.AcessEntity;

public interface AcessRepository extends JpaRepository<AcessEntity, Long>{
	
	public Optional<AcessEntity>findByUsername(String username);
	
	public default Optional<AcessEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
