package com.everis.crud.spring.data.mongodb.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everis.crud.spring.data.mongodb.modelo.Empleado;
import com.everis.crud.spring.data.mongodb.repositorio.RepositorioEmpleado;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class EmpleadoControl {
	
	@Autowired
	RepositorioEmpleado repositorioEmpleado;

	  @GetMapping("/empleados")
	  public ResponseEntity<List<Empleado>> getAllEmpleados(@RequestParam(required = false) String nombre) {
		  try {
			    List<Empleado> empleados = new ArrayList<Empleado>();

			    if (nombre == null)
			    	repositorioEmpleado.findAll().forEach(empleados::add); //muestra todos los empleados
			    else
			    	repositorioEmpleado.findByNombreContaining(nombre).forEach(empleados::add); //muestra empleados con el nombre buscado

			    if (empleados.isEmpty()) {
			      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			    }

			    return new ResponseEntity<>(empleados, HttpStatus.OK);
			  } catch (Exception e) {
			    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

	  @GetMapping("/empleados/{id}")
	  public ResponseEntity<Empleado> getEmpleadoById(@PathVariable("id") String id) {
		  Optional<Empleado> datosEmpleados = repositorioEmpleado.findById(id);

		  if (datosEmpleados.isPresent()) {
		    return new ResponseEntity<>(datosEmpleados.get(), HttpStatus.OK);
		  } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
	  }

	  @PostMapping("/empleados")
	  public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
		  try {
			    Empleado _empleado = repositorioEmpleado.save(new Empleado(empleado.getDni(), empleado.getNombre(), empleado.getApellidos(), empleado.getPosicion()));
			    return new ResponseEntity<>(_empleado, HttpStatus.CREATED);
			  } catch (Exception e) {
			    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

	  @PutMapping("/empleados/{id}")
	  public ResponseEntity<Empleado> updateEmpleado(@PathVariable("id") String id, @RequestBody Empleado empleado) {
		  Optional<Empleado> datosEmpleados = repositorioEmpleado.findById(id);

		  if (datosEmpleados.isPresent()) {
		    Empleado _empleado = datosEmpleados.get();
		    _empleado.setDni(empleado.getDni());
		    _empleado.setNombre(empleado.getNombre());
		    _empleado.setApellidos(empleado.getApellidos());
		    _empleado.setPosicion(empleado.getPosicion());
		    return new ResponseEntity<>(repositorioEmpleado.save(_empleado), HttpStatus.OK);
		  } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
	  }

	  @DeleteMapping("/empleados/{id}")
	  public ResponseEntity<HttpStatus> deleteEmpleado(@PathVariable("id") String id) {
		  //borramos empleado que coincida con el id
		  try {
			  repositorioEmpleado.deleteById(id);
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			  } catch (Exception e) {
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

	  @DeleteMapping("/empleados")
	  public ResponseEntity<HttpStatus> deleteAllEmpleados() {
		  //borramos todos los empleados
		  try {
			  repositorioEmpleado.deleteAll();
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			  } catch (Exception e) {
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }
}
