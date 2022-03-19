package com.jagp.app.controlador;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jagp.app.modelo.Usuario;
import com.jagp.app.servicio.IOrdenServices;
import com.jagp.app.servicio.IProductoServices;
import com.jagp.app.servicio.IUsuarioServices;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private IProductoServices poductoServicio;
	
	@Autowired
	private IUsuarioServices usuarioServicio;
	
	@Autowired
	private IOrdenServices ordenServicio;
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		 Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
			
			model.addAttribute("usuario", usuario);
		
		model.addAttribute("productos", poductoServicio.findAllProducto());
		
		return "administrador/home";
	}
	
	@GetMapping("/perfil")
	public String verAdmin(HttpSession session, Model model) {
     Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		
		model.addAttribute("usuario", usuario);
		
		return "administrador/perfil";
	}
	
	@GetMapping("/usuarios")
	public String verUsuario(HttpSession session, Model model) {
     Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("perfiles", usuarioServicio.findAllUser());
		
		return "administrador/usuarios";
	}
	
	@GetMapping("/ordenes")
	public String verOrdenes(HttpSession session, Model model) {
		 Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
			
			model.addAttribute("usuario", usuario);
			model.addAttribute("ordenes", ordenServicio.findAllOrden());
		
		return "administrador/ordenes";
	}

}
