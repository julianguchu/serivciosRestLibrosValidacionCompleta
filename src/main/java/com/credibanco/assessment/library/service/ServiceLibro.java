package com.credibanco.assessment.library.service;

import java.util.List;
import java.util.Optional;

import com.credibanco.assessment.library.model.Libro;

public interface ServiceLibro {
	
	Libro crearLibro(Libro libro);
	Libro actualizarLibro(Libro libro);
	void eliminarLibro(Libro libro);
	List<Libro> listarLibros();
	Optional<Libro> buscarLibroId(Integer id );
	Integer contarRegXEditorial(String nombre);
	Libro  buscarPorNombreLibro  (String titulo);

}
