package com.certus.solarsi.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.solarsi.interfaces.IClienteService;
import com.certus.solarsi.models.Response;
import com.certus.solarsi.models.cliente;

@Controller
@RequestMapping("/App")
public class controladorUsuario {
	
	private final Logger log = LoggerFactory.getLogger(controladorUsuario.class);
	
	@Autowired
	@Qualifier("Servicio1")
	private IClienteService InterfazCliente;
	
	
	@GetMapping("/Login")
	public String Log() {
	
		return "Login";
	}
	
	@GetMapping("/Register")
	public String Registrarse(Model model) {
		
		cliente clt = new cliente();
		
		model.addAttribute("custom", clt);
		return "Register";
	}
	
	@PostMapping("/crearUsuario")
	public String crearunUsuario(@Valid cliente custom,BindingResult result ,Model model) {
		
		if(result.hasErrors()) {
			
			return "Register";
		}
		
		Response<cliente> rspta = InterfazCliente.crearUsuario(custom);

		if(rspta.getEstado()) {
			model.addAttribute("Mensaje", rspta.getMensaje());
			
			return "redirect:/App/Login";
		}else {
			model.addAttribute("Error", rspta.getMensajeError());
			return "redirect: /App/Error";
		}
		
	
 
	}
	
	@GetMapping("/acceder")
	public String acceder(@RequestParam String username ,cliente cliente, HttpSession session) {
		log.info("Acceso : {}", cliente.getEmail());
		log.info("Acceso : {}", cliente.getPassword());
		
		Optional<cliente> user = InterfazCliente.findByEmail(username);
		log.info("Acceso : {}", user.get().getEmail());
		
		
		
		if(user.isPresent()) {
			session.setAttribute("idUsuario", user.get().getIdCliente());
			if (user.get().getTipo() == "ADMIN") {
				return "redirect:/App/AddProducto";
			}else {
				return "redirect:/App/Inicio";
			}
		}else {
			log.info("No existe la cuenta");
		}
		
		return "redirect:/App/Inicio";
		
		
		}
	
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idUsuario");
		
		
		return "redirect:/App/Inicio";
	}
	
}
