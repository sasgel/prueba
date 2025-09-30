package com.exam.app.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class HuCatMonedaId implements Serializable{
	
	private static final long serialVersionUID = -2820122755900704355L;
	private Integer numCia;
    private String claveMoneda;
    
    public HuCatMonedaId() {
    }

    public HuCatMonedaId(Integer numCia, String claveMoneda) {
        this.numCia = numCia;
        this.claveMoneda = claveMoneda;
    }

}