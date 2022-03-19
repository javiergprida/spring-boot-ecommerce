package com.jagp.app.servicio;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jagp.app.modelo.Usuario;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private IUsuarioServices usuarioServicio;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("esto es el username: ");
		
		Optional<Usuario> optionalUsuario = usuarioServicio.findByUsername(username);
		if(optionalUsuario.isPresent()) {
			log.info("esto es el id del usuario: {}",optionalUsuario.get().getId());
			session.setAttribute("id_usuario", optionalUsuario.get().getId());
			Usuario usuario = optionalUsuario.get();
			return User.builder().username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getTipo()).build();
		}else {
			
			throw new UsernameNotFoundException("usuario no encontrado");
		}
		
		
		
	}

	

}
