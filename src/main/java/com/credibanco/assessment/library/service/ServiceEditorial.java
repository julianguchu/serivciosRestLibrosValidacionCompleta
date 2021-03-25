package com.credibanco.assessment.library.service;

import java.util.List;
import java.util.Optional;

import com.credibanco.assessment.library.model.Editorial;


public interface ServiceEditorial{

	
	Editorial crearEditorial(Editorial editorial);
	Editorial actualizarEditorial(Editorial editorial);
	void eliminarEditorial(Editorial editorial);
	List<Editorial> listarEditorial();
	Optional<Editorial> buscarEditorialId(Integer id );
	Editorial  buscarPorNombre(String nombre);

}
