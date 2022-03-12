package com.jagp.app.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jagp.app.modelo.Usuario;
import com.jagp.app.servicio.IUsuarioServices;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioServices usuarioServicio;
	
	@GetMapping("/registro")
	public String CrearUsuario() {
		
		return "usuario/registro";
	}

	@PostMapping("/save_usuario")
	public String saveUsuario(Usuario usuario) {
		
		log.info("Usuario registrado: {}", usuario);
		usuario.setTipo("USER");
		
		usuarioServicio.saveUsuario(usuario);
		
		return "redirect:/";
	}
}
