package com.jagp.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jagp.app.modelo.Orden;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{

}
