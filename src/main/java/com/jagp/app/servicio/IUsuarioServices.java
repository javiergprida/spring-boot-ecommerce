package com.jagp.app.servicio;

import java.util.List;
import java.util.Optional;

import com.jagp.app.modelo.Usuario;

public interface IUsuarioServices {

	Optional<Usuario> findUsuarioById(Integer id);
	
	Usuario saveUsuario(Usuario usuario);
	
	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findByUsername(String username);
	
	List<Usuario> findAllUser();
}
