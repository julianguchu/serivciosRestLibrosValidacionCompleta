package com.credibanco.assessment.library.service;

import java.util.List;
import java.util.Optional;

import com.credibanco.assessment.library.model.Autor;



public interface ServiceAutor {
	
	
	Autor crearAutor (Autor  autor);
	Autor actualizarAutor (Autor autor);
	void eliminarAutor(Autor autor);
	List<Autor> listarAutor();
	Optional<Autor> buscarAutorId(Integer id );
	Autor buscarPorNombreAutor(String nombre);

}
