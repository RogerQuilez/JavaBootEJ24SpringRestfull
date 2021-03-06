package es.curso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.curso.entity.Videojuego;
import es.curso.service.VideojuegoService;

@RestController
@RequestMapping("/api")
public class VideojuegoController {

	@Autowired
	private VideojuegoService videoService;
	
	@GetMapping("/videojuegos")
	public ResponseEntity<?> getClientes() {
		
		Map<String, Object> response = new HashMap<>();
		List<Videojuego> videojuegos = new LinkedList<Videojuego>();
		
		try {
			videojuegos = videoService.getAllVideojuegos();
			
		} catch (Exception e) {
			
			response.put("mensaje", "Error al realizar la consula a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Videojuego>>(videojuegos, HttpStatus.OK);
		
	}
	
	@GetMapping("/videojuegos/compania/{compania}")
	public ResponseEntity<?> getVideojuegosByCompania(@PathVariable("compania") String compania) {
		
		Map<String, Object> response = new HashMap<>();
		List<Videojuego> videojuegos = new LinkedList<Videojuego>();
		
		try {
			videojuegos = videoService.findByCompania(compania);
			
		} catch (Exception e) {
			
			response.put("mensaje", "Error al realizar la consula a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (videojuegos.isEmpty()) {
			response.put("mensaje", "No hay videojuegos con esa compa??ia en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Videojuego>>(videojuegos, HttpStatus.OK);
	}
	
	@GetMapping("/videojuegos/precioTotal")
	public ResponseEntity<?> getTotalPrice() {
		
		Map<String, Object> response = new HashMap<>();
		List<Videojuego> videojuegos = new LinkedList<Videojuego>();
		List<String> listPrices = new ArrayList<>();
		double precioTotal = 0;
		
		try {
			videojuegos = videoService.getAllVideojuegos();
			precioTotal = videoService.calcularTotal();
			
		} catch (Exception e) {
			
			response.put("mensaje", "Error al realizar la consula a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (videojuegos.isEmpty()) {
			response.put("mensaje", "No hay videojuegos con esa compa??ia en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		for (Videojuego v: videojuegos) {
			listPrices.add(v.getNombre() + " Precio: " + v.getPrice());
		}
		
		listPrices.add("Precio Total: " + precioTotal);
		
		return new ResponseEntity<List<String>>(listPrices, HttpStatus.OK);
	}
	
	@GetMapping("/videojuegos/nombre/{nombre}")
	public ResponseEntity<?> getVideojuegosByNombre(@PathVariable("nombre") String nombre) {
		
		Map<String, Object> response = new HashMap<>();
		List<Videojuego> videojuegos = new LinkedList<Videojuego>();
		
		try {
			videojuegos = videoService.findByNombre(nombre);
			
		} catch (Exception e) {
			
			response.put("mensaje", "Error al realizar la consula a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (videojuegos.isEmpty()) {
			response.put("mensaje", "No hay videojuegos con ese nombre en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Videojuego>>(videojuegos, HttpStatus.OK);
	}
	
	@GetMapping("/videojuegos/{id}")
	public ResponseEntity<?> getCliente(@PathVariable("id") Integer id) {
		
		Videojuego videojuego = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			videojuego = videoService.findVideojuegoById(id);
			
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consula a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		if (videojuego == null) {
			response.put("mensaje", "El Videojuego no se encuentra en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Videojuego>(videojuego, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/videojuegos")
	public ResponseEntity<?> crearVideojuego(@Valid @RequestBody Videojuego videojuego, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			
			List<String> listErrors = new ArrayList<>();
			for (FieldError err: result.getFieldErrors()) {
				listErrors.add(err.getDefaultMessage());
			}
				
			response.put("errors", listErrors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			videoService.a??adirVideojuego(videojuego);
			
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Videojuego>(videojuego, HttpStatus.CREATED);
	}
	
	@PutMapping("/videojuegos/{id}")
	public ResponseEntity<?> modificarVideojuego(@Valid @RequestBody Videojuego videojuego, 
			@PathVariable("id") Integer id, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (videoService.findVideojuegoById(id) == null) {
			response.put("mensaje", "El Videojuego con ID: " + id.toString() + " no existe en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (result.hasErrors()) {
			
			List<String> listErrors = new ArrayList<>();
			for (FieldError err: result.getFieldErrors()) {
				listErrors.add(err.getDefaultMessage());
			}
				
			response.put("errors", listErrors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		videojuego.setId(id);
		
		try {
			videoService.a??adirVideojuego(videojuego);
			
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return new ResponseEntity<Videojuego>(videojuego, HttpStatus.OK);
	}
	
	@DeleteMapping("/videojuegos/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			videoService.eliminarVideojuego(id);
			
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El videojuego ha sido eliminado con ??xito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
