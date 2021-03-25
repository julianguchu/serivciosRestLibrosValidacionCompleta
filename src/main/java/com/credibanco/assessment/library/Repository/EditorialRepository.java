package com.credibanco.assessment.library.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.credibanco.assessment.library.model.Autor;
import com.credibanco.assessment.library.model.Editorial;


@Repository
public interface EditorialRepository  extends CrudRepository<Editorial, Integer> {

	
	@Query("SELECT edito   from  Editorial edito  where  edito.nombre=?1")
	Editorial  findByNombre (String  nombre);	
	
	

	
}
