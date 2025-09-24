package com.exam.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.exam.app.entity.HuEmplsEntity;

public interface EmpleadosService {

	List<HuEmplsEntity> obtenerTodos();
	Optional<HuEmplsEntity> obtenerPorId(Integer numEmp);
	HuEmplsEntity crear(HuEmplsEntity empleado);
	HuEmplsEntity actualizar(Integer numEmp, HuEmplsEntity empleadoActualizado);
	void eliminar(Integer numEmp);
	public Optional<Map<String, Object>> obtenerDetalleEmpleado(Integer numCia, Integer numEmp);
	public Map<String, Object> obtenerEmpleadosPorMoneda(Integer numCia, String claveMoneda);
}