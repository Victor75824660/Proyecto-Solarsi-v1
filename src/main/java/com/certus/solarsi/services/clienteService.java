package com.certus.solarsi.services;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.certus.solarsi.interfaces.IClienteService;
import com.certus.solarsi.models.Response;
import com.certus.solarsi.models.cliente;
import com.certus.solarsi.repository.ICliente;
import com.certus.solarsi.repository.IproductoR;

@Component("Servicio1") //Notación importante para conectar con la interfaz
public class clienteService implements IClienteService{
	
	@Autowired
	ICliente clienteDataRepository;
	
	@Autowired
	IproductoR IProductoI;

	
	BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
	public Response<cliente> prueba1(){ //primera prueba xdd
		
		Response<cliente> response = new Response<>();
		
		
		response.setMensaje("Esto es una prueba");
		
		return response;
	}
	
	public Response<cliente> crearUsuario(cliente clienteRecibido){
		
		Response<cliente> response = new Response<>();
		
		try {
			clienteRecibido.setPassword(passEncoder.encode(clienteRecibido.getPassword()));
			clienteRecibido.setTipo("USER");
			cliente custom = clienteDataRepository.save(clienteRecibido);		
			response.setData(custom);
			response.setEstado(true);
			response.setMensaje("El usuario " + custom.getNombres()+" ah sido creado correctamente");
		} catch (Exception e) {
			response.setEstado(false);
			response.setMensaje("Ocurrió un error pipipi");
			response.setMensajeError(e.getStackTrace().toString());
		}
		
		return response;
	}
	
	public Response<cliente> buscarUser(Integer id){
		Response<cliente> response = new Response<>();
		
		try {
			Optional<cliente> p = clienteDataRepository.findById(id);//Con esto lo casteamos para que sea del tipo Personaje
			response.setData(p.get());
			response.setEstado(true);
			
		} catch (Exception e) {
			response.setEstado(false);
			response.setMensaje("Error al traer el personaje");
			response.setMensajeError(e.getStackTrace().toString());

		}
		
		return response;
		
	}

	@Override
	public Optional<cliente> findByEmail(String email) {
		
		
		return clienteDataRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<cliente> cliente = clienteDataRepository.findByEmail(username);
		cliente custom = cliente.get();
		
		if (custom == null) {
				throw new UsernameNotFoundException("Usuario o Password Invalidos");
		}
		
		return new User(custom.getEmail(),custom.getPassword(), null);
	}
	
	
	
}
