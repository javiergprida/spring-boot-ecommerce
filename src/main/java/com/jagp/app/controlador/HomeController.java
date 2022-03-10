package com.jagp.app.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jagp.app.modelo.DetalleOrden;
import com.jagp.app.modelo.Orden;
import com.jagp.app.modelo.Producto;
import com.jagp.app.servicio.ProductoServices;

@Controller
@RequestMapping(value={"", "/", "/home"})
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoServices poductoServicio;
	
	//para almacenar los detales de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	
	//datos de la orden
	Orden orden = new Orden();
	
	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> productos = poductoServicio.findAllProducto();
		model.addAttribute("productos", productos);
		
		return "usuario/home";
	}
	
	@GetMapping("producto_home/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("id enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = poductoServicio.getProducto(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		
		return "usuario/producto_home";
	}
	
	@PostMapping("/cart")
	public String addCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> optionalProducto = poductoServicio.getProducto(id);
		log.info("producto añadido: {}", optionalProducto.get());
		log.info("cantidad: {}", cantidad);
		
		producto = optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setImagen(producto.getImagen());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProducto(producto);
		
		detalles.add(detalleOrden);
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		
		orden.setTotal(sumaTotal);
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	} 
	
	//quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCarrito(@PathVariable Integer id, Model model) {
		
		//lista nueva de productos
		List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleOrden: detalles) {
		
			if(detalleOrden.getProducto().getId() != id) {
				ordenesNuevas.add(detalleOrden);
			}
			
		}
		
		//nueva lista con los prodcutos restantes
		detalles = ordenesNuevas;
		
		double sumaTotal = 0;
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		
		orden.setTotal(sumaTotal);
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		
		return "usuario/carrito";
	}

}