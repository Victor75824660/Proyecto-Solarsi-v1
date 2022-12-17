package com.certus.solarsi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.certus.solarsi.models.Orden;
import com.certus.solarsi.models.cliente;

@Repository
public interface IOrderR extends CrudRepository<Orden, Integer>{
	List<Orden> findByUsuario(cliente cliente);
}
