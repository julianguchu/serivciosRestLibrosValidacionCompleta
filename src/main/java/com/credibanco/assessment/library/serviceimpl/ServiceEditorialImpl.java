package com.credibanco.assessment.library.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credibanco.assessment.library.Repository.EditorialRepository;
import com.credibanco.assessment.library.model.Editorial;
import com.credibanco.assessment.library.model.Libro;
import com.credibanco.assessment.library.service.ServiceEditorial;

@Service
public class ServiceEditorialImpl implements ServiceEditorial {

	
	
	private EditorialRepository  repoEditorial;
	
	@Autowired
	public ServiceEditorialImpl(EditorialRepository  repoEditorial) {
		this.repoEditorial=repoEditorial;
	}
	@Override
	public Editorial crearEditorial(Editorial editorial) {
		// TODO Auto-generated method stub
		return repoEditorial.save(editorial);
	}

	@Override
	public Editorial actualizarEditorial(Editorial editorial) {
		// TODO Auto-generated method stub
		return repoEditorial.save(editorial);
	}

	@Override
	public void eliminarEditorial(Editorial editorial) {
		
		
	for(Libro l : 	editorial.getLibros()) {
		
		l.setEditorial(null);
		
	}
		repoEditorial.delete(editorial);
		
	} 

	@Override
	public List<Editorial> listarEditorial() {
		// TODO Auto-generated method stub
		return (List<Editorial>) repoEditorial.findAll();
	}

	@Override
	public Optional<Editorial> buscarEditorialId(Integer id) {
		// TODO Auto-generated method stub
		return repoEditorial.findById(id);
	}
	@Override
	public Editorial buscarPorNombre(String nombre) {
		
		return repoEditorial.findByNombre(nombre);
	}

}
