package com.pastor126.galeriap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastor126.galeriap.entity.PastoresEntity;

public interface PastoresRepository extends JpaRepository<PastoresEntity, Long>{
	Optional<PastoresEntity> findByNumero(String numero);
	
}
