package com.jagp.app.servicio;



import java.util.List;
import java.util.Optional;

import com.jagp.app.modelo.Orden;
import com.jagp.app.modelo.Usuario;


public interface IOrdenServices {
	
	List<Orden> findAllOrden();

	Orden saveOrden(Orden orden);
	
	String  generarNumeroOrden();
	
	List<Orden> findByUsuario(Usuario usuario);
	
	Optional<Orden> findById(Integer id);
}
