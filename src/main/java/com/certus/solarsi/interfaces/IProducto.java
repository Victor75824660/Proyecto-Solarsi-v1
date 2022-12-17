package com.certus.solarsi.interfaces;

import com.certus.solarsi.models.Producto;
import com.certus.solarsi.models.Response;

public interface IProducto {
	
	public Response<Producto> crearProducto(Producto producto, int idUsuario);
	
	public Response<Producto>  listarProducto();
	
	public Response<Producto> eliminarProducto(Integer ID);
	
	public Response<Producto> editarProducto(Integer ID);
	
	
	
}
