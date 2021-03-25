package com.credibanco.assessment.library.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credibanco.assessment.library.Repository.LibroRepository;
import com.credibanco.assessment.library.model.Autor;
import com.credibanco.assessment.library.model.Libro;
import com.credibanco.assessment.library.service.ServiceLibro;

@Service
public class ServiceLibroImpl  implements ServiceLibro{
	
	private  LibroRepository repoLibro;
	
	@Autowired
	public ServiceLibroImpl ( LibroRepository repoLibro) {
		this.repoLibro=repoLibro;
		
	}

	@Override
	public Libro crearLibro(Libro libro) {
		// TODO Auto-generated method stub
		return repoLibro.save(libro);
	}

	@Override
	public Libro actualizarLibro(Libro libro) {

		return repoLibro.save(libro);
	}

	@Override
	public void eliminarLibro(Libro libro) {
		
		for( Autor a  : libro.getAutores()) {
			
			a.getLibros().remove(libro);
			
			
		}
		
		libro.getEditorial().getLibros().remove(libro);
		libro.setEditorial(null);
		repoLibro.delete(libro);
		
	}

	@Override
	public List<Libro> listarLibros() {
	
		return (List<Libro>) repoLibro.findAll();
	}
	
	@Override
	public Optional<Libro> buscarLibroId(Integer id) {
		// TODO Auto-generated method stub
		return repoLibro.findById(id);
	}

	@Override
	public Integer contarRegXEditorial(String nombre) {
		// TODO Auto-generated method stub
		return repoLibro.contarRegXEditorial(nombre);
	}

	@Override
	public Libro buscarPorNombreLibro(String titulo) {
		// TODO Auto-generated method stub
		return repoLibro.findByNombre(titulo);
	}

}
