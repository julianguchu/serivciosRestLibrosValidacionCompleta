package com.credibanco.assessment.library.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LibroDTO {
	 
	private  String ano;
	@NotNull(message = "no puede ir nulo el genero")
	@NotEmpty(message = "no debe ir vacio el genero")
	@NotBlank(message = "no puede ir vacio   el genero b")
	@Size(min=4, message ="debe ingresar al menos 4 caracteres en el genero" )
	private String genero; 
	
	@NotNull(message = "no puede ir nulo el titulo")
	@Size(min=7, message ="debe ingresar al menos 7 caracteres  en el titulo" )
	private String  titulo;
	
	@NotNull(message = "no puede ir nulo o vacio  el numero de paginas")
	@NotBlank(message = "no puede ir vacio el numero de paginas b")
	private Integer noPagina; 
	
	private  String editorial;
	
	@Size(min = 1 , message = "debe haber al menos un autors")
	@NotEmpty(message = "no debe ir vacio autors")
	@NotBlank(message = "no puede ir vacio el numero de paginas b")
	private ArrayList<String> autor;
	
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Integer getNoPagina() {
		return noPagina;
	}
	public void setNoPagina(Integer noPagina) {
		this.noPagina = noPagina;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public ArrayList<String> getAutor() {
		return autor;
	}
	public void setAutor(ArrayList<String> autor) {
		this.autor = autor;
	}
	
	

}
