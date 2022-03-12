package com.jagp.app.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jagp.app.modelo.Usuario;
import com.jagp.app.repositorio.IUsuarioRepository;

@Service
public class UsuarioServicesImpl implements IUsuarioServices{
	
	@Autowired
	private IUsuarioRepository usuarioRepositorio;

	@Override
	public Optional<Usuario> findUsuarioById(Integer id) {
		
		return usuarioRepositorio.findById(id);
	}

	
}
