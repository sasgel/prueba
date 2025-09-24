package com.exam.app.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "HU_EMPLS")
public class HuEmplsEntity implements Serializable {

	private static final long serialVersionUID = 3045756127969712434L;
	@Id
	private Integer numEmp;
	private String claveMoneda;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String puesto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numCia", referencedColumnName = "numCia")
	private HuCatMonedaEntity numCia;

}