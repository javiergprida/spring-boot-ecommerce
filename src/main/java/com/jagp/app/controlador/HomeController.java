package com.jagp.app.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.jagp.app.modelo.DetalleOrden;
import com.jagp.app.modelo.Orden;
import com.jagp.app.modelo.Producto;
import com.jagp.app.modelo.Usuario;
import com.jagp.app.servicio.IDetalleOrdenServices;
import com.jagp.app.servicio.IOrdenServices;
import com.jagp.app.servicio.IProductoServices;
import com.jagp.app.servicio.IUsuarioServices;

@Controller
@RequestMapping(value={"", "/"})
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IProductoServices poductoServicio;
	
	@Autowired
	private IUsuarioServices usuarioServices;
	
	@Autowired
	private IOrdenServices ordenServices;
	
	@Autowired
	private IDetalleOrdenServices detalleOrdenServices;
	
	@Autowired
	private IUsuarioServices usuarioServicio;
	
	//para almacenar los detales de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	
	//datos de la orden
	Orden orden = new Orden();
		
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		log.info("Session del usuario: {}", session.getAttribute("id_usuario"));
		
			model.addAttribute("productos", poductoServicio.findAllProducto());
			
			//session
			model.addAttribute("sesion", session.getAttribute("id_usuario"));
			
			
			return "usuario/home";
		
		
	}
	
	
	@GetMapping("producto_home/{id}")
	public String productoHome(@PathVariable Integer id, Model model, HttpSession session) {
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		log.info("id enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = poductoServicio.getProducto(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		model.addAttribute("usuario", usuario);
		
		return "usuario/producto_home";
	}
	
	@PostMapping("/carrito")
	public String addCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model, HttpSession session) {
		Usuario usuario = usuarioServicio.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		
		
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
		
		//validar que el producto no se añada repetidas veces
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		if(!ingresado) {
			detalles.add(detalleOrden);
		}
		
		
		
		
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

	@GetMapping("/getCarrito")
	public String getCarrito(Model model, HttpSession session) {
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		//session
		model.addAttribute("session", session.getAttribute("id_usuario"));
		
		return "/usuario/carrito";
	}
	
	@GetMapping("/orden")
	public String orden(Model model, HttpSession session) {
		
		Usuario usuario = usuarioServices.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		
		return "usuario/resumen_orden";
	}
	
	//guardar orden
	@GetMapping("/saveOrden")
	public String saveOrden(HttpSession session) {
		
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenServices.generarNumeroOrden());
		
		//usuario
		Usuario usuario = usuarioServices.findUsuarioById(Integer.parseInt(session.getAttribute("id_usuario").toString())).get();
		
		orden.setUsuario(usuario);
		ordenServices.saveOrden(orden);
		
		//guardar detalles
		for (DetalleOrden detalleOrden:detalles) {
			
			detalleOrden.setOrden(orden);
			detalleOrdenServices.saveDetalleOrden(detalleOrden);
			
		}
		
		//limpiar lista y orden
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/buscar")
	public String buscarProducto(@RequestParam String busqueda, Model model) {
		log.info("nombre del producto : {}", busqueda);
		
		List<Producto> productos = poductoServicio.findAllProducto().stream().filter(p -> p.getNombre().contains(busqueda)).collect(Collectors.toList());
		
		model.addAttribute("productos",productos);
		
		return "usuario/home";
	}
	
}