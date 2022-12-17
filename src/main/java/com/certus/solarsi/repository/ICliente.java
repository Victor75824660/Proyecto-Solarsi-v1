package com.certus.solarsi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.certus.solarsi.models.cliente;


@Repository
public interface ICliente extends CrudRepository<cliente, Integer>{
		Optional<cliente> findByEmail(String email);
}
