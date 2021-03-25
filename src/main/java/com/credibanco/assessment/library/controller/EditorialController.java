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

import com.credibanco.assessment.library.model.Editorial;
import com.credibanco.assessment.library.service.ServiceEditorial;

@RestController
@RequestMapping(value = "/editorial")
public class EditorialController {
	
	@Autowired
	private  ServiceEditorial servEditorial;

	@PostMapping("/crear")
	public ResponseEntity<?> crearEditorial(@Valid @RequestBody Editorial ed, BindingResult result){
		
		String mensajes=  null;
		Editorial edGuardada= null;
		List<String>  errorsFormat= new ArrayList<String>();
		if(result.hasErrors()) {
			
		List<ObjectError>errores=result.getAllErrors();
			errores.stream().forEach(objeto ->{
				errorsFormat.add(objeto.getDefaultMessage());
				
				
			});
			return   new ResponseEntity<List<String>>(errorsFormat,HttpStatus.BAD_REQUEST);
			
		}
		
		try {
			 edGuardada= servEditorial.crearEditorial(ed);
		} catch (DataAccessException e) {
			mensajes = "la editorial no  ha sido guardada:"+ " " +  e.getMostSpecificCause() ;
		
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes = "la editorial  ha sido guardada";
		
		return   new ResponseEntity<String >(mensajes,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?>editarEditorial(@PathVariable Integer  id , @RequestBody @Valid Editorial editorial, BindingResult result){
		String mensajes=  null;
		Editorial edBuscada= null;
		
		
	
		List<String>  errorsFormat= new ArrayList<String>();
		if(result.hasErrors()) {
			
		List<ObjectError>errores=result.getAllErrors();
			errores.stream().forEach(objeto ->{
				errorsFormat.add(objeto.getDefaultMessage());
				
				
			});
			return   new ResponseEntity<List<String>>(errorsFormat,HttpStatus.BAD_REQUEST);
			
		}
		
		edBuscada= servEditorial.buscarEditorialId(id).orElse(null);
		
		if(edBuscada==null) {
			mensajes= "la editorial no ha sido encontrada";
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			edBuscada.setCorreoElect(editorial.getCorreoElect());
			edBuscada.setDireccion(editorial.getDireccion());
			edBuscada.setMaxLibrosReg(editorial.getMaxLibrosReg());
			edBuscada.setNombre(editorial.getNombre());
			edBuscada.setTelefono(editorial.getTelefono());
			servEditorial.actualizarEditorial(edBuscada);

		} catch (DataAccessException e) {
			mensajes="la editorial no  ha sido actualizado:" +" " + e.getMostSpecificCause();
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes= "la editorial ha sido actualizado";
	
		return   new ResponseEntity<String >(mensajes,HttpStatus.CREATED);
		
		
	}
	
	
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> crearEditorial(@PathVariable  Integer id ){
		String mensajes=  null;
		Editorial edEncontrada=null;
		try {
			
			 edEncontrada= servEditorial.buscarEditorialId(id).orElse(null);
			
			
		} catch (DataAccessException e) {
			mensajes="la consulta no ha sido realizada:"+ " "+ e.getMostSpecificCause();
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(edEncontrada==null) {
			
			mensajes="la editorial no se ha encontrado en bd";
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return   new ResponseEntity<Editorial>(edEncontrada,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarEditorial(@PathVariable("id")  Integer id){
		
		 
		String mensajes=  null;
		Editorial edEncontrada= servEditorial.buscarEditorialId(id).orElse(null);
		if(edEncontrada==null) {
			mensajes="la editorial no  ha sido encontrada";
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
		}
		try {
			 servEditorial.eliminarEditorial(edEncontrada);
		} catch (DataAccessException e) {
			mensajes="la editorial no  ha sido eliminada : " + " "  +   e.getMostSpecificCause();
	
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes="la editorial ha sido eliminada";
	
		return   new ResponseEntity<String>(mensajes,HttpStatus.OK);
	}
	
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarEditoriales(){
		
		
		
		String mensajes=  null;
		List<Editorial> listaEdiEncontrada=null; 
		
		
		try {
			listaEdiEncontrada=servEditorial.listarEditorial();
			
		} catch (DataAccessException e) {
			mensajes="no ha podido realizar la consulta" +" "+ e.getMostSpecificCause();
		
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(listaEdiEncontrada==null) {
			mensajes="no se ha podido encontrar ninguna editorial";
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
			
		}
		return   new ResponseEntity<List<Editorial>>(listaEdiEncontrada,HttpStatus.OK);
	}
	
	
}
