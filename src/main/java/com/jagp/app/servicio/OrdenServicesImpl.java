package com.jagp.app.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jagp.app.modelo.Orden;
import com.jagp.app.repositorio.IOrdenRepository;

@Service
public class OrdenServicesImpl implements IOrdenServices{

	@Autowired
	private IOrdenRepository ordenRepositorio;
	
	@Override
	public Orden saveOrden(Orden orden) {
		
		return ordenRepositorio.save(orden);
	}

}
