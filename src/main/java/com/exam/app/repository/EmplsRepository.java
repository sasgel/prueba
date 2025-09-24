package com.exam.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.app.entity.HuEmplsEntity;

public interface EmplsRepository extends JpaRepository<HuEmplsEntity, Integer>{
	List<HuEmplsEntity> findByNumCia_NumCia(Integer numCia);
	List<HuEmplsEntity> findByNumCia_NumCiaAndClaveMoneda(Integer numCia, String claveMoneda);
}