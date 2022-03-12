package com.jagp.app.servicio;

import java.util.List;
import java.util.Optional;

import com.jagp.app.modelo.Producto;

public interface IProductoServices {

	public Producto saveProducto (Producto producto);

	public Optional<Producto> getProducto (Integer id);

	public void updateProducto (Producto producto);

	public void deleteProducto (Integer id); 
	
	public List<Producto> findAllProducto();

}
