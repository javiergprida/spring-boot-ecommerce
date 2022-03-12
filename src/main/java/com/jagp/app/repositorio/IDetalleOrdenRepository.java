package com.jagp.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jagp.app.modelo.DetalleOrden;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>{

}
