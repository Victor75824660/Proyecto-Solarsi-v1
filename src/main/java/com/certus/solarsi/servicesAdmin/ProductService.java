package com.certus.solarsi.servicesAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.certus.solarsi.interfaces.IClienteService;
import com.certus.solarsi.interfaces.IProducto;
import com.certus.solarsi.models.Producto;
import com.certus.solarsi.models.Response;
import com.certus.solarsi.models.cliente;
import com.certus.solarsi.repository.IproductoR;

@Component("ServicioAdmin")
public class ProductService implements IProducto{
	 
	
	@Autowired
	IproductoR InterProduct;
	
	@Autowired
	@Qualifier("Servicio1")
	private IClienteService InterfazCliente;
	
	@Override
	public Response<Producto> crearProducto(Producto producto, int idUsuario){
		
		Response<cliente> response2 = InterfazCliente.buscarUser(idUsuario);
		Response<Producto> response = new Response<>();
		
		try {
			producto.setUsuario(response2.getData());
			Producto pdt = InterProduct.save(producto);
			response.setData(pdt);
			response.setEstado(true);
			response.setMensaje("El producto " + pdt.getNombre_p()+" ah sido creado correctamente");
		} catch (Exception e) {
			response.setEstado(false);
			response.setMensaje("Ocurrió un error pipipi");
			response.setMensajeError(e.getStackTrace().toString());
		}
		
		return response;
	}
	
	@Override
	public Response<Producto>  listarProducto() {
		
		
		Response<Producto> response = new Response<>(); //Para listar a traves de la clase response que anida una entidad generica
		
		try {
			
			response.setListdata((List<Producto>) InterProduct.findAll()); //Se debe castear en una lista de un tipo personaje
			response.setMensaje("Productos obtenidos Correctamente");
			response.setEstado(true);

		} catch (Exception e) {
			response.setEstado(false);
			response.setMensaje("Error al traer los personajes de la BD");
			response.setMensajeError(e.getStackTrace().toString());

		}
		
			return response;
			}
	
	
	@Override
	public Response<Producto> eliminarProducto(Integer ID) {
		Response<Producto> response = new Response<>();
		
		try {

			InterProduct.deleteById(ID);
			response.setEstado(true);
			response.setMensaje("Se ha eliminado el producto");
		} catch (Exception e) {
			response.setEstado(false);
			response.setMensaje(e.getStackTrace().toString());
			response.setMensaje("Error al eliminar el Producto");
		}

		
		return response;
	}
	
	@Override
	public Response<Producto> editarProducto(Integer ID) {//Debe esperar un ID
		Response<Producto> response = new Response<>();
		
		try {
			Optional<Producto> p = InterProduct.findById(ID);//Con esto lo casteamos para que sea del tipo Personaje
			response.setData(p.get());
			response.setEstado(true);
			
		} catch (Exception e) {
			response.setEstado(false);
			response.setMensaje("Error al editar el personaje");
			response.setMensajeError(e.getStackTrace().toString());

		}
		
		return response;
	}
	
	
	
}
