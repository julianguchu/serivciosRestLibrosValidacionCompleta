package com.credibanco.assessment.library.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credibanco.assessment.library.Repository.AutorRepository;
import com.credibanco.assessment.library.model.Autor;
import com.credibanco.assessment.library.model.Libro;
import com.credibanco.assessment.library.service.ServiceAutor;

@Service
public class ServiceAutorImpl  implements  ServiceAutor{

	
	private  AutorRepository repoAutor;
	@Autowired
	public ServiceAutorImpl (AutorRepository repoAutor) {
		this.repoAutor=repoAutor;
		
	}
	
	
	@Override
	public Autor crearAutor(Autor autor) {
		
	return repoAutor.save(autor);
	}

	@Override
	public Autor actualizarAutor(Autor autor) {
		// TODO Auto-generated method stub
		return repoAutor.save(autor);
	}

	@Override
	public void eliminarAutor(Autor autor) {
		
	for(Libro l :	autor.getLibros()) {
		
		l.getAutores().remove(autor);
		
	} 
		repoAutor.delete(autor);
		
	}

	@Override
	public List<Autor> listarAutor() {
		return (List<Autor>)repoAutor.findAll();
	}

	@Override
	public Optional<Autor> buscarAutorId(Integer id) {
		// TODO Auto-generated method stub
		return repoAutor.findById(id);
	}


	@Override
	public Autor buscarPorNombreAutor(String nombre) {
		// TODO Auto-generated method stub
		return repoAutor.findByAutorNombre(nombre);
	}

}
