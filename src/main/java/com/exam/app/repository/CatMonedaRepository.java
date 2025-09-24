package com.exam.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.app.entity.HuCatMonedaEntity;

public interface CatMonedaRepository extends JpaRepository<HuCatMonedaEntity, Integer>{

}