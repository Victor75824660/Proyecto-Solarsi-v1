package com.certus.solarsi.interfaces;



import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.certus.solarsi.models.Response;
import com.certus.solarsi.models.cliente;

public interface IClienteService extends UserDetailsService{

	
	Response<cliente> prueba1();
	Response<cliente> crearUsuario(cliente clienteRecibido);
	Response<cliente> buscarUser(Integer id);
	Optional<cliente> findByEmail(String email);
}
