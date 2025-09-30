package com.exam.app.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.app.entity.HuCatMonedaEntity;
import com.exam.app.entity.HuCatMonedaId;

public interface CatMonedaRepository extends JpaRepository<HuCatMonedaEntity, HuCatMonedaId>{
	List<HuCatMonedaEntity> findByDescripcion(String descripcion);
	List<HuCatMonedaEntity> findById_ClaveMonedaContainingIgnoreCase(String claveMoneda);
	
}