package com.pastor126.galeriap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastor126.galeriap.entity.PastoresHonorariosEntity;

public interface PastoresHonorariosRepository extends JpaRepository<PastoresHonorariosEntity, Long>{
	Optional<PastoresHonorariosEntity> findByNumero(String numero);
}
