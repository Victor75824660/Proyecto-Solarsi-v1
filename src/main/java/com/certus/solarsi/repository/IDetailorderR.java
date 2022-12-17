package com.certus.solarsi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.certus.solarsi.models.DetalleOrden;

@Repository
public interface IDetailorderR extends CrudRepository<DetalleOrden, Integer> {

}
