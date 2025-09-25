package com.exam.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.app.entity.HuEmplsEntity;

public interface EmplsRepository extends JpaRepository<HuEmplsEntity, Integer>{
	List<HuEmplsEntity> findByMoneda_Id_NumCia(Integer numCia);
	List<HuEmplsEntity> findByMoneda_Id_NumCiaAndMoneda_Id_ClaveMoneda(Integer numCia, String claveMoneda);
}