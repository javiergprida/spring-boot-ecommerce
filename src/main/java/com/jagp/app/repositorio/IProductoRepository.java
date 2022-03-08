package com.jagp.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jagp.app.modelo.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>{
	
	

}
