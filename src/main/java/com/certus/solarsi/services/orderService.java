package com.certus.solarsi.services;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


import com.certus.solarsi.interfaces.IOrder;
import com.certus.solarsi.models.Orden;
import com.certus.solarsi.models.cliente;
import com.certus.solarsi.repository.IOrderR;


@Component("Orden")
public class orderService implements IOrder{
	
	@Autowired
	private IOrderR repoOrder;
	

	
	@Override
	public Orden guardarOrden (Orden orden){
		
		Date fechaCrea = new Date();
		orden.setFechaCreacion(fechaCrea);
		
		return repoOrder.save(orden);
	}
	
	@Override
	public List<Orden>  listarOrdenes() {
		
		return (List<Orden>) repoOrder.findAll();
		
		
			}
	
	
	
	@Override
	public String generarNumeroOrden() {
		int numero=0;
		String numeroConcatenado="";
		
		List<Orden> ordenes = listarOrdenes();
		
		List<Integer> numeros= new ArrayList<Integer>();
		
		ordenes.stream().forEach(o -> numeros.add( Integer.parseInt( o.getNumero())));
		
		if (ordenes.isEmpty()) {
			numero=1;
		}else {
			numero= numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if (numero<10) { //0000001000
			numeroConcatenado="000000000"+String.valueOf(numero);
		}else if(numero<100) {
			numeroConcatenado="00000000"+String.valueOf(numero);
		}else if(numero<1000) {
			numeroConcatenado="0000000"+String.valueOf(numero);
		}else if(numero<10000) {
			numeroConcatenado="0000000"+String.valueOf(numero);
		}		
		
		return numeroConcatenado;
	}

	@Override
	public List<Orden> findByUsuario(cliente cliente) {
		
		return repoOrder.findByUsuario(cliente);
	}

	@Override
	public Optional<Orden> findById(Integer id) {
		
		
		return repoOrder.findById(id);
	}
	
	
}
