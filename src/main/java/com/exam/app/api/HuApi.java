package com.exam.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.app.entity.HuCatMonedaEntity;
import com.exam.app.entity.HuEmplsEntity;
import com.exam.app.repository.CatMonedaRepository;
import com.exam.app.service.EmpleadosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin
public class HuApi {

	@Autowired
	EmpleadosService emplService;

	@Operation(summary = "Obtener todos los empleados", description = "Devuelve una lista completa de empleados registrados.")
	@ApiResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente")
	@GetMapping
	public ResponseEntity<List<HuEmplsEntity>> obtenerTodos() {
		return ResponseEntity.ok(emplService.obtenerTodos());
	}
	
	
	@Operation(summary = "Obtener por Clave Monedas", description = "Devuelve una lista de todas las monedas registradas.")
	@ApiResponse(responseCode = "200", description = "Lista completa de monedas obtenida exitosamente")
	 @GetMapping("/clave/{claveMoneda}")
    public ResponseEntity<List<HuCatMonedaEntity>> buscarPorClave(@PathVariable String claveMoneda) {
        return ResponseEntity.ok(emplService.buscarPorClave(claveMoneda));
    }
	

	@Operation(summary = "Obtener empleado por ID", description = "Devuelve los datos de un empleado específico según su ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
			@ApiResponse(responseCode = "404", description = "Empleado no encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<HuEmplsEntity> obtenerPorId(@PathVariable Integer id) {
		return emplService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Crear nuevo empleado", description = "Registra un nuevo empleado en el sistema.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos") })
	@PostMapping
	public ResponseEntity<HuEmplsEntity> crear(@RequestBody HuEmplsEntity empleado) {
		HuEmplsEntity creado = emplService.crear(empleado);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	@Operation(summary = "Actualizar empleado", description = "Modifica los datos de un empleado existente.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente"),
			@ApiResponse(responseCode = "404", description = "Empleado no encontrado") })
	@PutMapping("/{id}")
	public ResponseEntity<HuEmplsEntity> actualizar(@PathVariable Integer id, @RequestBody HuEmplsEntity empleado) {
		HuEmplsEntity actualizado = emplService.actualizar(id, empleado);
		return ResponseEntity.ok(actualizado);
	}

	@Operation(summary = "Eliminar empleado", description = "Elimina un empleado del sistema por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
		emplService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}