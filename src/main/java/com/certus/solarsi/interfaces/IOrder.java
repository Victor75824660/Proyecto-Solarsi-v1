package com.certus.solarsi.interfaces;

import java.util.List;
import java.util.Optional;

import com.certus.solarsi.models.Orden;
import com.certus.solarsi.models.cliente;


public interface IOrder {
	
	Orden guardarOrden (Orden orden);
	
	List<Orden>  listarOrdenes();
	
	Optional<Orden> findById(Integer id);
	
	String generarNumeroOrden();
	
	List<Orden> findByUsuario(cliente cliente);
}
