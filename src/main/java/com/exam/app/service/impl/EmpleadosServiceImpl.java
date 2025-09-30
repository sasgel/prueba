package com.exam.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.app.entity.HuCatMonedaEntity;
import com.exam.app.entity.HuCatMonedaId;
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
        Integer numCia = empleado.getMoneda().getId().getNumCia();
        String claveMoneda = empleado.getMoneda().getId().getClaveMoneda();

        validarMoneda(numCia, claveMoneda);

        return empRepo.save(empleado);
    }

    public HuEmplsEntity actualizar(Integer numEmp, HuEmplsEntity empleadoActualizado) {
        HuEmplsEntity existente = empRepo.findById(numEmp)
            .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        HuCatMonedaId monedaId = empleadoActualizado.getMoneda().getId();
        Integer numCia = monedaId.getNumCia();
        String claveMoneda = monedaId.getClaveMoneda();

        validarMoneda(numCia, claveMoneda);

        HuCatMonedaEntity moneda = catRepo.findById(monedaId)
            .orElseThrow(() -> new EntityNotFoundException("Moneda no encontrada"));

        existente.setNombre(empleadoActualizado.getNombre());
        existente.setApellidoPaterno(empleadoActualizado.getApellidoPaterno());
        existente.setApellidoMaterno(empleadoActualizado.getApellidoMaterno());
        existente.setPuesto(empleadoActualizado.getPuesto());
        existente.setMoneda(moneda); 

        return empRepo.save(existente);
    }

    public void eliminar(Integer numEmp) {
        if (!empRepo.existsById(numEmp)) {
            throw new EntityNotFoundException("Empleado no encontrado");
        }
        empRepo.deleteById(numEmp);
    }
    
    public List<HuEmplsEntity> buscarPorNumCia(Integer numCia) {
        return empRepo.findByMoneda_Id_NumCia(numCia);
    }

    private void validarMoneda(Integer numCia, String claveMoneda) {
        HuCatMonedaId monedaId = new HuCatMonedaId(numCia, claveMoneda);

        if (!catRepo.existsById(monedaId)) {
            throw new IllegalArgumentException("La moneda con numCia " + numCia + " y claveMoneda " + claveMoneda + " no existe");
        }
    }

    @Override
    public Optional<Map<String, Object>> obtenerDetalleEmpleado(Integer numCia, Integer numEmp) {
        Optional<HuEmplsEntity> empleadoOpt = empRepo.findById(numEmp);

        if (empleadoOpt.isEmpty() || !empleadoOpt.get().getMoneda().getId().getNumCia().equals(numCia)) {
            return Optional.empty();
        }

        HuEmplsEntity empleado = empleadoOpt.get();

        String claveMoneda = empleado.getMoneda().getId().getClaveMoneda();

        HuCatMonedaId monedaId = new HuCatMonedaId(numCia, claveMoneda);
        Optional<HuCatMonedaEntity> monedaOpt = catRepo.findById(monedaId);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("empleado", empleado);
        resultado.put("moneda", monedaOpt.orElse(null));

        return Optional.of(resultado);
    }
    
    @Override
    public Map<String, Object> obtenerEmpleadosPorMoneda(Integer numCia, String claveMoneda) {
        List<HuEmplsEntity> empleados = empRepo.findByMoneda_Id_NumCiaAndMoneda_Id_ClaveMoneda(numCia, claveMoneda);

        HuCatMonedaId monedaId = new HuCatMonedaId(numCia, claveMoneda);
        Optional<HuCatMonedaEntity> monedaOpt = catRepo.findById(monedaId);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("moneda", monedaOpt.orElse(null));
        resultado.put("empleados", empleados);

        return resultado;
    }

	@Override
	public List<HuCatMonedaEntity> buscarPorClave(String claveMoneda) {
		
		return catRepo.findById_ClaveMonedaContainingIgnoreCase(claveMoneda);
	}
}