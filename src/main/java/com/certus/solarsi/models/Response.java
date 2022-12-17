package com.certus.solarsi.models;

import java.util.List;


public class Response<E> {
	private boolean estado;
	private String mensaje;
	private String mensajeError;
	private double p;
	
	private E data;
	private List<E> Listdata;
	
	
	
	
	public double getP() {
		return p;
	}
	public void setP(double p) {
		this.p = p;
	}
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public List<E> getListdata() {
		return Listdata;
	}
	public void setListdata(List<E> listdata) {
		Listdata = listdata;
	}
	
	
	
	
	
}
