package com.jagp.app.controlador;

import java.util.Optional;

import javax.servlet.http.HttpSession;

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
	
	@GetMapping("/perfil")
	public String verUsuario() {
		
		return "usuario/perfil";
	}
	

	@PostMapping("/save_usuario")
	public String saveUsuario(Usuario usuario) {
		
		log.info("Usuario registrado: {}", usuario);
		usuario.setTipo("USER");
		
		usuarioServicio.saveUsuario(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginUsuario() {
		
		return"usuario/login";
	}
	
	@PostMapping("/acceder")
	public String accesoUsuario(Usuario usuario, HttpSession session) {
		
		log.info("accesos: {}", usuario);
		
		Optional<Usuario> username = usuarioServicio.findByUsername(usuario.getUsername());
		//log.info("usuario obtenido de la ddbb: {}", username.get());
		
		//Optional<Usuario> userEmail = usuarioServicio.findByEmail(usuario.getEmail());
		//log.info("usuario obtenido de la ddbb: {}", userEmail.get());
		
		if(username.isPresent()) {
			
			session.setAttribute("id_usuario", username.get().getId());
			if(username.get().getTipo().equals("ADMIN")) {
				return"redirect:/administrador";
			}else {
				return"redirect:/";
			}
			
		}else {
			
			log.info("usuario no encontrado");
			
		}
		
		return "redirect:/";
	}
}
