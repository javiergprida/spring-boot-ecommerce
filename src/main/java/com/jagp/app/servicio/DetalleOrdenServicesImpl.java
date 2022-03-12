package com.jagp.app.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jagp.app.modelo.DetalleOrden;
import com.jagp.app.repositorio.IDetalleOrdenRepository;

@Service
public class DetalleOrdenServicesImpl implements IDetalleOrdenServices{

	@Autowired
	private IDetalleOrdenRepository detalleOrdenRepositorio;
	
	@Override
	public DetalleOrden saveDetalleOrden(DetalleOrden detalleOrden) {
		
		return detalleOrdenRepositorio.save(detalleOrden);
	}

}
