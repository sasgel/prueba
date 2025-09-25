package com.exam.app.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@Table(name = "HU_CAT_MONEDA")
public class HuCatMonedaEntity implements Serializable{
	private static final long serialVersionUID = -8197963205964432848L;
	
	@EmbeddedId
    private HuCatMonedaId id;
	private String descripcion;
	private String simbolo;
	private String estatus;
}
