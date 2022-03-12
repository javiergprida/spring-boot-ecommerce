package com.jagp.app.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jagp.app.modelo.Orden;
import com.jagp.app.repositorio.IOrdenRepository;

@Service
public class OrdenServicesImpl implements IOrdenServices{

	@Autowired
	private IOrdenRepository ordenRepositorio;
	
	@Override
	public Orden saveOrden(Orden orden) {
		
		return ordenRepositorio.save(orden);
	}

	@Override
	public List<Orden> findAllOrden() {
		
		return ordenRepositorio.findAll();
	}
	
	public String generarNumeroOrden() {
		
		int numero = 0;
		String numeroConcatenado = "";
		
		List<Orden> ordenes = findAllOrden();
		List<Integer> numeros = new ArrayList<Integer>();
		
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		
		if(ordenes.isEmpty()) {
			
			numero = 1;
			
		}else {
			
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if(numero < 10) {
			
			numeroConcatenado = "000000000" + String.valueOf(numero);
			
		}else if(numero < 100) {
			
			numeroConcatenado = "00000000" + String.valueOf(numero);
			
		}else if(numero < 1000) {
			
			numeroConcatenado = "0000000" + String.valueOf(numero);
			
		}else if(numero < 10000) {
			
			numeroConcatenado = "000000" + String.valueOf(numero);
			
		}else if(numero < 100000) {
			
			numeroConcatenado = "00000" + String.valueOf(numero);
		}else if(numero < 1000000) {
			
			
			numeroConcatenado = "0000" + String.valueOf(numero);
			
		}else if(numero < 10000000) {
			
			numeroConcatenado = "000" + String.valueOf(numero);
			
		}else if(numero < 100000000) {
			
			numeroConcatenado = "00" + String.valueOf(numero);
			
		}else if(numero < 1000000000) {
			
			numeroConcatenado = "0" + String.valueOf(numero);
			
		}
		
		
		
		return numeroConcatenado;
	}

}
