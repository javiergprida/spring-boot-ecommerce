package com.jagp.app.servicio;

import java.util.Optional;

import com.jagp.app.modelo.Usuario;

public interface IUsuarioServices {

	Optional<Usuario> findUsuarioById(Integer id);
	
	Usuario saveUsuario(Usuario usuario);
}
