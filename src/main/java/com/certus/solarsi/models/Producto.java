package com.certus.solarsi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name= "tb_producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private String Nombre_p;
	private double precio_p;
	private String descripcion_p;
	private String uriImagen;
	private int stock_p;
	
	@ManyToOne
	private cliente usuario;
	
	
	public cliente getUsuario() {
		return usuario;
	}
	public void setUsuario(cliente usuario) {
		this.usuario = usuario;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre_p() {
		return Nombre_p;
	}
	public void setNombre_p(String nombre_p) {
		Nombre_p = nombre_p;
	}
	public double getPrecio_p() {
		return precio_p;
	}
	public void setPrecio_p(double precio_p) {
		this.precio_p = precio_p;
	}
	public String getDescripcion_p() {
		return descripcion_p;
	}
	public void setDescripcion_p(String descripcion_p) {
		this.descripcion_p = descripcion_p;
	}

	
	public String getUriImagen() {
		return uriImagen;
	}
	public void setUriImagen(String uriImagen) {
		this.uriImagen = uriImagen;
	}
	public int getStock_p() {
		return stock_p;
	}
	public void setStock_p(int stock_p) {
		this.stock_p = stock_p;
	}
	
	
	
	
}
