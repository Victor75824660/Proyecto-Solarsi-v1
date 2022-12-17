package com.certus.solarsi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certus.solarsi.interfaces.IDetailOrder;
import com.certus.solarsi.models.DetalleOrden;


import com.certus.solarsi.repository.IDetailorderR;

@Component("DetalleO")
public class detailOrderService implements IDetailOrder{
	
	
	@Autowired
	private IDetailorderR detailRepository;
	
	
	@Override
	public DetalleOrden guardarDetalleOrden (DetalleOrden orderD){
		
		
		
		
		
		
		return detailRepository.save(orderD);
		
	}
}
