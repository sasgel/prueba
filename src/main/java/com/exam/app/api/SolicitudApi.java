package com.exam.app.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.app.service.EmpleadosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class SolicitudApi {

	@Autowired
	private EmpleadosService empService;

	@Operation(summary = "Obtener detalle de empleado por numCia y numEmp", description = "Devuelve la información combinada del empleado y su moneda asociada, filtrando por numCia y numEmp.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Detalle del empleado obtenido exitosamente"),
			@ApiResponse(responseCode = "404", description = "Empleado no encontrado con los parámetros especificados") })

	@GetMapping("detalleFiltro")
	public ResponseEntity<?> obtenerDetalle(@RequestParam Integer numCia, @RequestParam Integer numEmp) {

		Optional<Map<String, Object>> resultado = empService.obtenerDetalleEmpleado(numCia, numEmp);

		if (resultado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Empleado con numEmp " + numEmp + " y numCia " + numCia + " no encontrado");
		}

		return ResponseEntity.ok(resultado.get());
	}

	@Operation(summary = "Filtrar empleados por numCia y claveMoneda", description = "Devuelve los empleados que pertenecen a una compañía específica y usan una clave de moneda determinada, junto con los datos de la moneda.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Empleados filtrados obtenidos exitosamente"),
			@ApiResponse(responseCode = "404", description = "No se encontraron empleados con los filtros especificados") })
	@GetMapping("/filtrarMoneda")
	public ResponseEntity<?> obtenerPorMoneda(@RequestParam Integer numCia, @RequestParam String claveMoneda) {

		Map<String, Object> resultado = empService.obtenerEmpleadosPorMoneda(numCia, claveMoneda);

		if (((List<?>) resultado.get("empleados")).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No se encontraron empleados con claveMoneda '" + claveMoneda + "' en numCia " + numCia);
		}

		return ResponseEntity.ok(resultado);
	}
}