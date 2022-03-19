package com.jagp.app.controlador;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jagp.app.modelo.Orden;
import com.jagp.app.modelo.Usuario;
import com.jagp.app.servicio.IOrdenServices;
import com.jagp.app.servicio.IUsuarioServices;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioServices usuarioServicio;
	
	@Autowired
	private IOrdenServices ordenServicio;
	
	@GetMapping("/registro")
	public String CrearUsuario() {
		
		return "usuario/registro";
	}
	
	@GetMapping("/perfil")
	public String verUsuario(HttpSession session, Model model) {
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		
		model.addAttribute("usuario", usuario);
		
		return "usuario/perfil";
	}
	

	@PostMapping("/save_usuario")
	public String saveUsuario(Usuario usuario) {
		
		//log.info("Usuario registrado: {}", usuario);
		usuario.setTipo("USER");
		
		usuarioServicio.saveUsuario(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginUsuario() {
		
		return"usuario/login";
	}
	
	@PostMapping("/acceder")
	public String accesoUsuario(Usuario usuario, HttpSession session,Model model) {
		
		//log.info("accesos: {}", usuario);
		
		Optional<Usuario> username = usuarioServicio.findByUsername(usuario.getUsername());
		//log.info("usuario obtenido de la ddbb: {}", username.get());
		
		//Optional<Usuario> userEmail = usuarioServicio.findByEmail(usuario.getEmail());
		//log.info("usuario obtenido de la ddbb: {}", userEmail.get());
		
		if(username.isPresent()) {
			
			session.setAttribute("id_usuario", username.get().getId());
			if(username.get().getTipo().equals("ADMIN")) {
				return"redirect:/administrador";
			}else {
				
				model.addAttribute("usuario", usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get());
				return"redirect:/";
			}
			
		}else {
			
			log.info("usuario no encontrado");
			
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(HttpSession session, Model model) {
		
		model.addAttribute("sesion", session.getAttribute("id_usuario"));
		
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		List<Orden> ordenes = ordenServicio.findByUsuario(usuario);
		
		model.addAttribute("ordenes",ordenes);
		model.addAttribute("usuario", usuario);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle-compra/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		//log.info("id de la irden: {}", id);
		
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		Optional <Orden> orden = ordenServicio.findById(id);
		
		//session
		model.addAttribute("session", session.getAttribute("id_usuario"));
		model.addAttribute("usuario", usuario);
		model.addAttribute("detalles", orden.get().getDetalle());
		
		
		return "usuario/detalle_compra";
	}
	
	@GetMapping("/log-out")
	public String cerrarSession(HttpSession session) {
		
		session.removeAttribute("id_usuario");
		
		return "redirect:/usuario/login";
	}
	
}
