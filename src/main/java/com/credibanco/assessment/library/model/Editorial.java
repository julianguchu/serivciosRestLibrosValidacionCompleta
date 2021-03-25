package com.credibanco.assessment.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;


import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Editorial implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Integer id;
	
	@Column(nullable = false)
	@NotNull(message="el nombre es oligatorio")
	@NotEmpty(message = "nombre no puede estar en blanco")
	@Size(min = 4 , message="nombre debe tener minimo 4 caracteres ")
	private String nombre;
	
	@Column(nullable = false)
	@NotNull(message="la direccion es oligatorio")
	@Size(min = 12 , message="la direccion debe tener minimo 12 caracteres ")
	private String direccion; 
	
	@Column(nullable = false)
	@NotNull(message="el telefono es obligatorio")
	@Size(min = 7 , message="el telefono debe tener minimo 7 caracteres ")
	private  String telefono;
	
	@Column
	private String correoElect;
	
	@Column(nullable = false)
	@NotNull(message="el maximo de libros es obligatorio")
	private  Integer maxLibrosReg;
	

	 
	@OneToMany(mappedBy = "editorial" , cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER) 
	private List<Libro> libros= new  ArrayList<Libro>();   
	
	 
	 
	 
	 
	public Editorial() {
		super();
	}
	//@JsonManagedReference
	public List<Libro> getLibros() {
		return this.libros;
	}
	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreoElect() {
		return correoElect;
	}
	public void setCorreoElect(String correoElect) {
		this.correoElect = correoElect;
	}
	public Integer getMaxLibrosReg() {
		return maxLibrosReg;
	}
	public void setMaxLibrosReg(Integer maxLibrosReg) {
		this.maxLibrosReg = maxLibrosReg;
	}

}
