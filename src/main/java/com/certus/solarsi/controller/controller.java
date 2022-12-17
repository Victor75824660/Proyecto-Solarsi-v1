package com.certus.solarsi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.solarsi.interfaces.IClienteService;
import com.certus.solarsi.interfaces.IDetailOrder;
import com.certus.solarsi.interfaces.IOrder;
import com.certus.solarsi.interfaces.IProducto;
import com.certus.solarsi.models.DetalleOrden;
import com.certus.solarsi.models.Orden;
import com.certus.solarsi.models.Producto;
import com.certus.solarsi.models.Response;
import com.certus.solarsi.models.cliente;

@Controller
@RequestMapping("/App")
public class controller {
	
	private final Logger log = LoggerFactory.getLogger(controller.class);
	
	@Value("${title.generic}") //Llamamos al valor de Properties y lo seteamos en la clase
	private String titlePage;
	
	@Autowired
	@Qualifier("Servicio1")
	private IClienteService InterfazCliente;
	
	@Autowired
	@Qualifier("ServicioAdmin")
	private IProducto IntProducto;
	
	@Autowired
	@Qualifier("Orden")
	private IOrder IntOrder;
	
	@Autowired
	@Qualifier("DetalleO")
	private IDetailOrder IntDetOrder;
	
	
	List<DetalleOrden> detalles = new ArrayList<>();
	
	Orden orden = new Orden();
	
	@GetMapping({"/inicio","/","/Inicio"})
	public String Entrada(Model model, HttpSession session) {
	
		log.info("Sesion del usuario : {}", session.getAttribute("idUsuario"));
		
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		
		model.addAttribute("TituloPagina", titlePage); //Inyectar datos a la vista a través del controlador 

		
	return "Inicio";	
	
	}
	
	@GetMapping("/Integrantes")
	public String Integrante(Model model) {
		
		model.addAttribute("prueba", InterfazCliente.prueba1().getMensaje());
		
		return "Integrantes";
	}
	
	@GetMapping("/MisCompras")
	public String mostrarCompras(Model model, HttpSession session) {
		
		Response<cliente> clt = InterfazCliente.buscarUser(Integer.parseInt(session.getAttribute("idUsuario").toString()));
		List<Orden> ordenes = IntOrder.findByUsuario(clt.getData());
		
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		
		model.addAttribute("ordenes", ordenes);
		
		
		
		return "resumenCompras";
	}
	
	
	@GetMapping("/detalleCompra/{id}")
	public String MostrarDetalledeLaComra(@PathVariable Integer id, Model model, HttpSession session) {
		log.info("Id de la orden {}", id);
		Optional<Orden> orden = IntOrder.findById(id);
		
		
		model.addAttribute("detalles", orden.get().getDetalle());
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		
		return "detalleCompra";
	}
	
	@GetMapping("/Productos")
	public String Productos(Model model, HttpSession session) {
		
		Response<Producto> rspta = IntProducto.listarProducto();
		
		
		if(rspta.getEstado()){
			model.addAttribute("Mensaje", rspta.getMensaje());
			model.addAttribute("productos", rspta.getListdata());
			model.addAttribute("sesion", session.getAttribute("idUsuario"));
			return "Productos";

			
		}else {
			model.addAttribute("Mensaje", rspta.getMensaje());
			model.addAttribute("mensajeError", rspta.getMensajeError());

			return "errores";	
		}
		
	
		
	}
	
	@GetMapping("/detailProducto/{id}")
	public String mostrarDetalle(@PathVariable int id ,Model model) {
		
		Response<Producto> rspta = IntProducto.editarProducto(id); //Le enviamos el ID a la interfaz y luego al metodo
		model.addAttribute("producto", rspta.getData());// Enviamos un el producto
		
		return "detalleProducto";
	}
	
	
	
	@GetMapping("/CarritodeCompras")
	public String CarritodeCompras(Model model, HttpSession session) {
	
		model.addAttribute("cartera", detalles);
		model.addAttribute("TotalF", orden);
		
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		
		return "CarritodeCompras";
	}
	
	
	@PostMapping("/AddCar")
	public String añadirAlCarrito(@RequestParam Integer id, @RequestParam int cantidad, Model model) {
		log.info(id.toString());
		log.info("{}", cantidad);
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Response<Producto> rspta = IntProducto.editarProducto(id);
		producto = rspta.getData();
		
		detalleOrden.setId(id);
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio_p());
		detalleOrden.setNombre(producto.getNombre_p());
		detalleOrden.setTotal(producto.getPrecio_p()*cantidad);
		detalleOrden.setProducto(producto);
		
		//Validamos que el producto no se repita 2 veces
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == id);
		
		if (!ingresado) {
			detalles.add(detalleOrden);
		}
		
		
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		
		model.addAttribute("cartera", detalles);
		model.addAttribute("TotalF", orden);
		
		
		 
		 
		return "CarritodeCompras";

		
	}
	
	
	//Quitar un producto del array del carrito
	@GetMapping("/delete/{id}")
	public String eliminarunProductodelCarrito(@PathVariable Integer id, Model model) {
		//lita de productos
		List<DetalleOrden> nuevaOrden = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleOrden : detalles) {
			if(detalleOrden.getProducto().getId() != id) {
				nuevaOrden.add(detalleOrden);
			}
		}
		
		//Actualizar la nueva lista
		detalles = nuevaOrden;
		
		double sumaTotal = 0;
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		
		
		model.addAttribute("cartera", detalles);
		model.addAttribute("TotalF", orden);
		
		
		
		return "CarritodeCompras";
	}
	
	@GetMapping("/OrdenDetail")
	public String mostrarResumendeLaOrden(Model model, HttpSession session) {
		Response<cliente> rspta = InterfazCliente.buscarUser(Integer.parseInt(session.getAttribute("idUsuario").toString()));
		
		model.addAttribute("user", rspta.getData());
		model.addAttribute("cartera", detalles);
		model.addAttribute("TotalF", orden);
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		
		return "resumenOrden";
	}
	
	
	@GetMapping("/saveOrder")
	public String GuardarOrden( HttpSession session) {
		orden.setNumero(IntOrder.generarNumeroOrden());
		
		Response<cliente> rspta = InterfazCliente.buscarUser(Integer.parseInt(session.getAttribute("idUsuario").toString()));
		
		orden.setUsuario(rspta.getData());
		IntOrder.guardarOrden(orden);
		
		for(DetalleOrden dt:detalles) {
			dt.setOrden(orden);
			IntDetOrder.guardarDetalleOrden(dt);
		}
		
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/App/";
	}
	
	@PostMapping("/buscarProducto")
	public String buscarProducto (@RequestParam String nombre, Model model) {
	

		List<Producto> producto = IntProducto.listarProducto().getListdata().stream().filter(p -> p.getNombre_p().contains(nombre)).collect(Collectors.toList());
		
		model.addAttribute("productos", producto);
		
		
		return "Productos";
	}
	
}
