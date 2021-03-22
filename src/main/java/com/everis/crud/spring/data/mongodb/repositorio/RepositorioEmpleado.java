package com.everis.crud.spring.data.mongodb.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.everis.crud.spring.data.mongodb.modelo.Empleado;

public interface RepositorioEmpleado extends MongoRepository<Empleado, String> {
	List<Empleado> findBydni(String dni);  
	List<Empleado> findByNombreContaining(String nombre);
	}