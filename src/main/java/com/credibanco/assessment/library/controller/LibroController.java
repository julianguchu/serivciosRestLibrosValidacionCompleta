package com.credibanco.assessment.library.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.credibanco.assessment.library.dto.LibroDTO;
import com.credibanco.assessment.library.model.Autor;
import com.credibanco.assessment.library.model.Editorial;
import com.credibanco.assessment.library.model.Libro;
import com.credibanco.assessment.library.service.ServiceAutor;
import com.credibanco.assessment.library.service.ServiceEditorial;
import com.credibanco.assessment.library.service.ServiceLibro;

@RestController
@RequestMapping(value = "/libro")
public class LibroController {

	@Autowired
	private ServiceLibro  servLibro;
	@Autowired
	private  ServiceEditorial servEditorial;
	@Autowired
	private  ServiceAutor  servAutor;
	
	
	
	@InitBinder
	public void validacionVacio(WebDataBinder binder) {
		
		StringTrimmerEditor  edicion = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, edicion);
	}

	@PostMapping("/guardar")
	public ResponseEntity<?>  guardarLibro(@RequestBody  @Valid  LibroDTO libro, BindingResult resultado ){
		Map<String, Object> mensajes = new HashMap<>();
		List<String> erroresFormateados = new  ArrayList<>();
		 if (resultado.hasErrors()) {
			List<ObjectError> errores = resultado.getAllErrors();
			errores.stream().forEach(error ->{
				
				erroresFormateados.add(error.getDefaultMessage());
				
			});
			mensajes.put("mensajes", "los errores en entrada de datos son los siguientes");
			mensajes.put("errores", erroresFormateados);
			
		
			
			 
			 
			 return  new  ResponseEntity<List<String>>(erroresFormateados,  HttpStatus.OK);
			 
		 }
	
		 List<String> noGuardados=  new ArrayList<String>();
		 List<Autor> listAutRegistradosBd= new ArrayList<>();
			int indice=0;
			boolean autorClienteRepetido=false;
		 for( String nombreAutor : libro.getAutor() ) {
			 indice++;

			 for(int ind=indice ; ind< libro.getAutor().size(); ind++) {
				 
				 if(nombreAutor.equals(libro.getAutor().get(ind))) {
					 
					 autorClienteRepetido=true;
					 break;
					 
				 }
				 
			 }
			 
			 if(autorClienteRepetido==true) {
				 
				 break;
				 
			 }
			 
			Autor  autorValidacion =servAutor.buscarPorNombreAutor(nombreAutor);
			 
			if (autorValidacion== null) {
				
				noGuardados.add(nombreAutor);
			}
			else {
				
				listAutRegistradosBd.add(autorValidacion);
				
			}
				
			
		 }
		if(autorClienteRepetido==true) {
			
			
			
			return  new ResponseEntity<String>("Ingresaste  autores con el mismo nombre, se requieren disintos autores",HttpStatus.EXPECTATION_FAILED);
		}
		
		
		if (noGuardados.size()>=1) {
			mensajes.put("error", "Los siguientes usuarios no estan en la bd");
			mensajes.put("lista", noGuardados);
			 
					 return  new  ResponseEntity<Map<String, Object>> (mensajes,  HttpStatus.NOT_FOUND);
			
			
		}
		
		 
		Editorial editEncontradaBd  =  servEditorial.buscarPorNombre(libro.getEditorial());
		 
		if (editEncontradaBd ==null) {
			
			mensajes.put("error", "la editorial ".concat("  no esta regisrada en la bd"));
			 return  new  ResponseEntity<Map<String, Object>> (mensajes,  HttpStatus.NOT_FOUND);
			
		}
		
	
		
		Integer  i =editEncontradaBd.getMaxLibrosReg().equals(-1)? 0 : servLibro.contarRegXEditorial(editEncontradaBd.getNombre());
		
	
 if (i.equals(editEncontradaBd.getMaxLibrosReg()) && ( !editEncontradaBd.getMaxLibrosReg().equals(-1)) ) {
	 
	 return  new  ResponseEntity<String> ("No es posible registrar el libro, se alcanzó el máximo permitido",  HttpStatus.NOT_FOUND);
	 
 }
 

		 
		
	
		 Libro  libroAGuardar= new  Libro();
	try {
		libroAGuardar.setAno(libro.getAno());
		 libroAGuardar.setGenero(libro.getGenero());
		 libroAGuardar.setNoPaginas(libro.getNoPagina());
		 libroAGuardar.setTitulo(libro.getTitulo());
		
		
		for(  Autor aut:   listAutRegistradosBd) {
			aut.getLibros().add(libroAGuardar);
	
		}
		editEncontradaBd.getLibros().add(libroAGuardar);
		 libroAGuardar.setAutores(listAutRegistradosBd);
		libroAGuardar.setEditorial(editEncontradaBd);
		
		 
		
		 libroAGuardar.setAno(libro.getAno());
		 libroAGuardar.setGenero(libro.getGenero());
		 libroAGuardar.setNoPaginas(libro.getNoPagina());
		 libroAGuardar.setTitulo(libro.getTitulo());
		 
		 
		servLibro.crearLibro(libroAGuardar);
		 
	} catch (DataAccessException e) {
		mensajes.put("mensaje", "no se pudo  crear  el libro por : "+ " "+ e.getMostSpecificCause());
		
		
	}
		mensajes.put("mensaje", "el libro:".concat(libroAGuardar.getTitulo()).concat(" ha sido creado"));
		mensajes.put("libro", libroAGuardar);
		
		return new ResponseEntity<Map<String, Object>>(mensajes,HttpStatus.CREATED);

		 
	}
	
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?>editarLibro(@PathVariable Integer  id , @RequestBody @Valid LibroDTO libro, BindingResult result){
		String mensajes=  null;
		Libro liBuscada= null;
		
		
	
		List<String>  errorsFormat= new ArrayList<String>();
		if(result.hasErrors()) {
			
		List<ObjectError>errores=result.getAllErrors();
			errores.stream().forEach(objeto ->{
				errorsFormat.add(objeto.getDefaultMessage());
				
				
			});
			return   new ResponseEntity<List<String>>(errorsFormat,HttpStatus.BAD_REQUEST);
			
		}
		
		
		liBuscada= servLibro.buscarLibroId(id).orElse(null);
		
		
		if(liBuscada==null) {
			mensajes= "la libro no ha sido encontrada";
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
		}
		
		Editorial   editorialRecuperada= servEditorial.buscarPorNombre(libro.getEditorial());
		if( editorialRecuperada == null) {
			
	mensajes= "la editorial que ingresaste no esta en la base de datos ";
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
			
			 
		}
		boolean  noEncotradoUsuarioenBd= false;
		List<Autor>  autoresEncontrados= new ArrayList<>();
		
		for (String  autores : libro.getAutor()) {
			
		Autor   autor= servAutor.buscarPorNombreAutor(autores);	
			
			if(autor==null) {
				noEncotradoUsuarioenBd=true;
				break ;
			}else {
			
			autoresEncontrados.add(autor);
			}
		}
		if(noEncotradoUsuarioenBd==true) {
			
			mensajes= "el usuario que pretendes actualizar no esta en la base de datos ";
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
			
			
			
		}
		
		
		try {
			for(  Autor autor :liBuscada.getAutores()) {
				autor.getLibros().clear();
				 
			}

			
		for(  Autor autor :autoresEncontrados) {
				autor.getLibros().add(liBuscada);
				
			}

		
			liBuscada.setAno(libro.getAno());
			liBuscada.setGenero(libro.getGenero());
			liBuscada.setNoPaginas(libro.getNoPagina());
			liBuscada.setTitulo(libro.getTitulo());
			liBuscada.setEditorial(editorialRecuperada);
			liBuscada.setAutores(autoresEncontrados);
			
			servLibro.actualizarLibro(liBuscada);

		} catch (DataAccessException e) {
			mensajes="la libro no  ha sido actualizado:" +" " + e.getMostSpecificCause();
			
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes= "la libro ha sido actualizado";
	
		return   new ResponseEntity<String >(mensajes,HttpStatus.CREATED);
		
		
	}
	
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarLibro(@PathVariable("id")  Integer id){
		
		 
		String mensajes=  null;
		Libro liEncontrada= servLibro.buscarLibroId(id).orElse(null);
		if(liEncontrada==null) {
			mensajes="la libro no  ha sido encontrado";
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
		}
		try {
			servLibro.eliminarLibro(liEncontrada);
		} catch (DataAccessException e) {
			mensajes="la libro no  ha sido eliminado : " + " "  +   e.getMostSpecificCause();
	
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes="el libro  ha sido eliminado";
	
		return   new ResponseEntity<String>(mensajes,HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarLibros(){
		
		
		
		String mensajes=  null;
		List<Libro> listaLiEncontrada=null; 
		
		
		try {
			listaLiEncontrada=servLibro.listarLibros();
			
		} catch (DataAccessException e) {
			mensajes="no ha podido realizar la consulta" +" "+ e.getMostSpecificCause();
		
			return   new ResponseEntity<String>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(listaLiEncontrada==null) {
			mensajes="no se ha podido encontrar ningun libro";
			return   new ResponseEntity<String>(mensajes,HttpStatus.NOT_FOUND);
			
		}
		return   new ResponseEntity<List<Libro>>(listaLiEncontrada,HttpStatus.OK);
	}
	
	
	
	
	
}
