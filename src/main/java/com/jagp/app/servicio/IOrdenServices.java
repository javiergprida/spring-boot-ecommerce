package com.jagp.app.servicio;



import java.util.List;

import com.jagp.app.modelo.Orden;


public interface IOrdenServices {
	
	List<Orden> findAllOrden();

	Orden saveOrden(Orden orden);
	
	String  generarNumeroOrden();
}
