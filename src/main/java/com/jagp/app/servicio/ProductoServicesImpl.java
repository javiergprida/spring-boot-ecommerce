package com.jagp.app.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jagp.app.modelo.Producto;
import com.jagp.app.repositorio.IProductoRepository;

@Service
public class ProductoServicesImpl implements ProductoServices{
	
	@Autowired
	private IProductoRepository ProductoRepositorio;

	@Override
	public Producto saveProducto(Producto producto) {
		
		return ProductoRepositorio.save(producto);
	}

	@Override
	public Optional<Producto> getProducto(Integer id) {
		
		return ProductoRepositorio.findById(id);
	}

	@Override
	public void updateProducto(Producto producto) {
		ProductoRepositorio.save(producto);
		
	}

	@Override
	public void deleteProducto(Integer id) {
		ProductoRepositorio.deleteById(id);
		
	}

}
