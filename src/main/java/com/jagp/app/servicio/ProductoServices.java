package com.jagp.app.servicio;

import java.util.Optional;

import com.jagp.app.modelo.Producto;

public interface ProductoServices {

	public Producto saveProducto (Producto producto);

	public Optional<Producto> getProducto (Integer id);

	public void updateProducto (Producto producto);

	public void deleteProducto (Integer id); 

}
