package com.exam.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.app.entity.HuCatMonedaEntity;
import com.exam.app.entity.HuEmplsEntity;
import com.exam.app.repository.CatMonedaRepository;
import com.exam.app.repository.EmplsRepository;
import com.exam.app.service.EmpleadosService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmpleadosServiceImpl implements EmpleadosService {

	@Autowired
	EmplsRepository empRepo;
	
	@Autowired
	CatMonedaRepository catRepo;
	
    public List<HuEmplsEntity> obtenerTodos() {
        return empRepo.findAll();
    }

    public Optional<HuEmplsEntity> obtenerPorId(Integer numEmp) {
        return empRepo.findById(numEmp);
    }

    public HuEmplsEntity crear(HuEmplsEntity empleado) {
        validarMoneda(empleado.getNumCia().getNumCia());
        return empRepo.save(empleado);
    }

    public HuEmplsEntity actualizar(Integer numEmp, HuEmplsEntity empleadoActualizado) {
        HuEmplsEntity existente = empRepo.findById(numEmp)
            .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        validarMoneda(empleadoActualizado.getNumCia().getNumCia());

        existente.setNombre(empleadoActualizado.getNombre());
        existente.setApellidoPaterno(empleadoActualizado.getApellidoPaterno());
        existente.setApellidoMaterno(empleadoActualizado.getApellidoMaterno());
        existente.setPuesto(empleadoActualizado.getPuesto());
        existente.setClaveMoneda(empleadoActualizado.getClaveMoneda());
        existente.setNumCia(empleadoActualizado.getNumCia());

        return empRepo.save(existente);
    }

    public void eliminar(Integer numEmp) {
        if (!empRepo.existsById(numEmp)) {
            throw new EntityNotFoundException("Empleado no encontrado");
        }
        empRepo.deleteById(numEmp);
    }
    
    public List<HuEmplsEntity> buscarPorNumCia(Integer numCia) {
        return empRepo.findByNumCia_NumCia(numCia);
    }

    private void validarMoneda(Integer numCia) {
        if (!catRepo.existsById(numCia)) {
            throw new IllegalArgumentException("La moneda con numCia " + numCia + " no existe");
        }
    }

    @Override
    public Optional<Map<String, Object>> obtenerDetalleEmpleado(Integer numCia, Integer numEmp) {
        Optional<HuEmplsEntity> empleadoOpt = empRepo.findById(numEmp);

        if (empleadoOpt.isEmpty() || !empleadoOpt.get().getNumCia().getNumCia().equals(numCia)) {
            return Optional.empty();
        }

        HuEmplsEntity empleado = empleadoOpt.get();
        Optional<HuCatMonedaEntity> monedaOpt = catRepo.findById(numCia);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("empleado", empleado);
        resultado.put("moneda", monedaOpt.orElse(null));

        return Optional.of(resultado);
    }
    
    @Override
    public Map<String, Object> obtenerEmpleadosPorMoneda(Integer numCia, String claveMoneda) {
        List<HuEmplsEntity> empleados = empRepo.findByNumCia_NumCiaAndClaveMoneda(numCia, claveMoneda);
        Optional<HuCatMonedaEntity> monedaOpt = catRepo.findById(numCia);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("moneda", monedaOpt.orElse(null));
        resultado.put("empleados", empleados);

        return resultado;
    }
}