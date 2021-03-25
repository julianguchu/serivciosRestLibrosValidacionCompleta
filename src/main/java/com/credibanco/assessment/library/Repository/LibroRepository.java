package com.credibanco.assessment.library.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.credibanco.assessment.library.model.Editorial;
import com.credibanco.assessment.library.model.Libro;

@Repository
public interface LibroRepository  extends CrudRepository<Libro, Integer>{
	
	@Query("SELECT  COUNT(li)    FROM Libro  li where  li.editorial.nombre=?1 ")
	Integer contarRegXEditorial(String nombre);

	@Query("SELECT li   from  Libro li where  li.titulo=?1")
	Libro  findByNombre (String  titulo);	
	
	
}
   