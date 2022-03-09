package com.jagp.app.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jagp.app.modelo.Producto;
import com.jagp.app.servicio.ProductoServices;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProductoServices poductoServicio;
	
	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> productos = poductoServicio.findAllProducto();
		model.addAttribute("productos", productos);
		
		return "usuario/home";
	}

}