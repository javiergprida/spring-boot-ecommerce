package com.jagp.app.controlador;


import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jagp.app.modelo.Producto;
import com.jagp.app.modelo.Usuario;
import com.jagp.app.servicio.IProductoServices;
import com.jagp.app.servicio.IUsuarioServices;
import com.jagp.app.servicio.UploadFileServices;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private IProductoServices productoServicio;
	
	@Autowired
	private UploadFileServices upload;
	
	@Autowired
	private IUsuarioServices usuarioServicio;
	
	@GetMapping("")
	public String show(HttpSession session, Model model) {
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		model.addAttribute("usuario", usuario);
		model.addAttribute("productos", productoServicio.findAllProducto());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create(HttpSession session, Model model) {
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		model.addAttribute("usuario", usuario);
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String saveProducto(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session,  Model model) throws IOException {
		//LOGGER.info("este es el objeto producto {}",producto);
		
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		model.addAttribute("usuario", usuario);
		producto.setUsuario(usuario);
		
		//imagen
		if(producto.getId() == null) {
			//esta validacion se cera cuando el producto es nuevo
			String nombreImagen = upload.saveImagen(file);
			producto.setImagen(nombreImagen);
			
		}
		
		productoServicio.saveProducto(producto);
		return "redirect:/productos";
	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, HttpSession session) {
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		Producto producto = new Producto();
		Optional<Producto> optionalProducto = productoServicio.getProducto(id);
		producto = optionalProducto.get();
		
		//LOGGER.info("producto buscado {}",producto);
		
		model.addAttribute("producto", producto);
		model.addAttribute("usuario", usuario);
		
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String updateProducto(Producto producto,  @RequestParam("img") MultipartFile file) throws IOException {
		
		Producto p = new Producto();
		p = productoServicio.getProducto(producto.getId()).get();
		
		
		if(file.isEmpty()) {//es cuando editamos el producto pero no cambiamos la imagen
			
			producto.setImagen(p.getImagen());
		}else {
			//cuando se edita tambien la imagen
						
			//metodo eliminar mientars no sea la imagen por defecto
			if(!p.getImagen().equals("placeholder.png")) {
				
				upload.deleteImagen(p.getImagen());
			}
			
			producto.setUsuario(p.getUsuario());
			String nombreImagen = upload.saveImagen(file);
			producto.setImagen(nombreImagen);
			
		}
		
		productoServicio.updateProducto(producto);
		
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProducto(@PathVariable Integer id) {
		
		Producto p = new Producto();
		p = productoServicio.getProducto(id).get();
		
		//metodo eliminar mientars no sea la imagen por defecto
		if(!p.getImagen().equals("placeholder.png")) {
			
			upload.deleteImagen(p.getImagen());
			
		}
		
		productoServicio.deleteProducto(id);
		
		return "redirect:/productos";
	}
}
