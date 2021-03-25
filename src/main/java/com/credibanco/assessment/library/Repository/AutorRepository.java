package com.credibanco.assessment.library.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.credibanco.assessment.library.model.Autor;
import com.credibanco.assessment.library.model.Editorial;




@Repository
public interface AutorRepository  extends CrudRepository<Autor, Integer> {

	
	@Query("SELECT aut  FROM   Autor aut   where aut.nombreCompleto=?1")
	Autor  findByAutorNombre(String nombreCompleto);
}
