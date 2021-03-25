package com.credibanco.assessment.library.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credibanco.assessment.library.model.Autor;
import com.credibanco.assessment.library.model.Editorial;
import com.credibanco.assessment.library.service.ServiceAutor;


@RestController
@RequestMapping(value = "/autor")
public class AutorController {
	
	@Autowired
	private  ServiceAutor servAutor;

	@PostMapping("/crear")
	public ResponseEntity<?> crearAutor( @RequestBody @Valid Autor au, BindingResult resultados){
		
		String mensajes=  null;
		Autor auGuardada= null;
		List<String>erroresFormateados = new ArrayList<String>();
		 if (resultados.hasErrors()) {
				List<ObjectError> errores = resultados.getAllErrors();
				errores.stream().forEach(error ->{
					
					erroresFormateados.add(error.getDefaultMessage());
					
				});
				return   new ResponseEntity<List<String> >(erroresFormateados,HttpStatus.BAD_REQUEST);
			
		 }
		
		try {
			 auGuardada= servAutor.crearAutor(au);
		} catch (DataAccessException e) {
			mensajes = "el autor no  ha sido guardada:"+ " " +  e.getMostSpecificCause() ;
		
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes = "el autor  ha sido guardada";
		
		return   new ResponseEntity<String >(mensajes,HttpStatus.CREATED);
	}
	 
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?>editarAutorl(@RequestBody @Valid Autor autor, @PathVariable Integer  id , BindingResult result){
		String mensajes=  null;
		Autor auBuscada= null;
		

		List<String>  errorsFormat= new ArrayList<String>();
		if(result.hasErrors()) {
			
		List<ObjectError>errores=result.getAllErrors();
			errores.stream().forEach(objeto ->{
				errorsFormat.add(objeto.getDefaultMessage());
				
				
			});
			return   new ResponseEntity<List<String>>(errorsFormat,HttpStatus.BAD_REQUEST);
			
		}
		
		auBuscada= servAutor.buscarAutorId(id).orElse(null);
		
		if(auBuscada==null) {
			mensajes= "el autor no ha sido encontrada";
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			auBuscada.setCiudadProcedencia(autor.getCiudadProcedencia());
			auBuscada.setCorreoElectronico(autor.getCorreoElectronico());
			auBuscada.setFechaNacimiento(autor.getFechaNacimiento());
			auBuscada.setNombreCompleto(autor.getNombreCompleto());
			servAutor.actualizarAutor(auBuscada);

		} catch (DataAccessException e) {
			mensajes="el autor no  ha sido actualizado:" +" " + e.getMostSpecificCause();
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes= "el autor ha sido actualizado";
	
		return   new ResponseEntity<String >(mensajes,HttpStatus.CREATED);
		
		
	}
	
	
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> crearAutor(@PathVariable  Integer id ){
		String mensajes=  null;
		Autor auEncontrada=null;
		try {
			
			 auEncontrada= servAutor.buscarAutorId(id).orElse(null);
			
			
		} catch (DataAccessException e) {
			mensajes="la consulta no ha sido realizada:"+ " "+ e.getMostSpecificCause();
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(auEncontrada==null) {
			
			mensajes="el autor no se ha encontrado en bd";
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return   new ResponseEntity<Autor>(auEncontrada,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarAutorl(@PathVariable("id")  Integer id){
		
		
		String mensajes=  null;
		Autor auEncontrada= servAutor.buscarAutorId(id).orElse(null);
		if(auEncontrada==null) {
			mensajes="el autor no  ha sido encontrada";
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
		}
		try {
			servAutor.eliminarAutor(auEncontrada);
		} catch (DataAccessException e) {
			mensajes="el autor no  ha sido eliminada : " + " "  +   e.getMostSpecificCause();
	
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes="el autor ha sido eliminado";
	
		return   new ResponseEntity<String>(mensajes,HttpStatus.OK);
	}
	
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarAuotores(){
		
		
		
		String mensajes=  null;
		List<Autor> listaAutEncontrada=null; 
		
		
		try {
			listaAutEncontrada=servAutor.listarAutor();
			
		} catch (DataAccessException e) {
			mensajes="no ha podido realizar la consulta" +" "+ e.getMostSpecificCause();
		
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(listaAutEncontrada==null) {
			mensajes="no se ha podido encontrar ningun autor";
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
			
		}
		return   new ResponseEntity<List<Autor>>(listaAutEncontrada,HttpStatus.OK);
	}
	
	
	
	

}
