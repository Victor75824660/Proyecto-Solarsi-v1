package com.certus.solarsi.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.certus.solarsi.interfaces.IClienteService;
import com.certus.solarsi.interfaces.IOrder;
import com.certus.solarsi.interfaces.IProducto;
import com.certus.solarsi.models.Orden;
import com.certus.solarsi.models.Producto;
import com.certus.solarsi.models.Response;
import com.certus.solarsi.models.cliente;




@Controller
@RequestMapping("/App")
@SessionAttributes("producto")//con esto hago la sesion del editar
public class controladorAdminProductos {
	
	private final Logger log = LoggerFactory.getLogger(controladorAdminProductos.class);
	
	@Autowired
	@Qualifier("ServicioAdmin")
	private IProducto IntProducto;
	
	@Autowired
	@Qualifier("Servicio1")
	private IClienteService InterfazCliente;
	
	@Autowired
	@Qualifier("Orden")
	private IOrder IntOrder;
	
	
	@GetMapping("/AddProducto")
	public String VistadeAñadir(Model model) {
		
		
		Producto producto = new Producto();
		Response<Producto> rspta = IntProducto.listarProducto();
		
		
		if(rspta.getEstado()){
			model.addAttribute("Mensaje", rspta.getMensaje());
			model.addAttribute("productos", rspta.getListdata());
			model.addAttribute("producto", producto); //Le enviamos un producto en blacon al formulario
			model.addAttribute("btn", "Añadir Producto");
			
			return "añadirProducto";

		}else {
			model.addAttribute("Mensaje", rspta.getMensaje());
			model.addAttribute("mensajeError", rspta.getMensajeError());

			return "errores";	
		}
		
		

	}
	
	@PostMapping("/añadirProducto")//POST
	public String crearunUsuario (Producto producto,SessionStatus eStatus ,Model model, HttpSession session) {//Con la sessionStatus vamos a poder editar 
		
		int codUser = Integer.parseInt(session.getAttribute("idUsuario").toString());
		Response<Producto> rspta = IntProducto.crearProducto(producto, codUser);

		if(rspta.getEstado()) {
			
			
			model.addAttribute("Mensaje", rspta.getMensaje());
			eStatus.setComplete();
			
			return "redirect:/App/AddProducto";
		}else {
			model.addAttribute("Error", rspta.getMensajeError());
			return "redirect: /App/Error";
		}
		
	}
	
	@GetMapping("/Eliminar/{id}")//para editar tenemos que enviar el ID
	public String EliminarPersonaje(@PathVariable int id, Model model) {
	
	
		Response<Producto> rspta = IntProducto.eliminarProducto(id); //Le enviamos el ID a la interfaz y luego al metodo
		
		if(rspta.getEstado()) {
			
		}else {
			model.addAttribute("Mensaje", rspta.getMensaje());
			model.addAttribute("mensajeError", rspta.getMensajeError());
			
			return "errores";				
		}
		return "redirect:/App/AddProducto";
		
	}
	
	@GetMapping("/Editar/{id}")//para editar tenemos que enviar el ID
	public String EditarProducto(@PathVariable int id  ,Model model) {
			

		Response<Producto> rspta = IntProducto.editarProducto(id); //Le enviamos el ID a la interfaz y luego al metodo
		model.addAttribute("producto", rspta.getData());// Enviamos un el producto
		model.addAttribute("btn", "Actualizar Producto");
		
		return "añadirProducto";//RECORDAR QUE ESTE ES EL NOMBRE DEL HTML
	}
	
	
	
	@GetMapping("/OrdenesUser")
	public String mostrarOrdenesDeUsuario(Model model) {
		
		model.addAttribute("listOrder", IntOrder.listarOrdenes());
		
		
		return "adminViewOrders";
	}
	
	@GetMapping("/detalleOrdenAdmin/{id}")
	public String mostrarDetalleOrdenUser(@PathVariable Integer id,Model model) {
		
		log.info("aea : {}", id);
		Orden orden = IntOrder.findById(id).get();
		Response<cliente> rspta = InterfazCliente.buscarUser(IntOrder.findById(id).get().getUsuario().getIdCliente());
		
		model.addAttribute("detail", orden.getDetalle());
		model.addAttribute("dataUser", rspta.getData());
		
		return "adminDetalleOrden";
	}
	
	
}
