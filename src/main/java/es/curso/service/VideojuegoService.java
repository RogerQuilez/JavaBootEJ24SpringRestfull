package es.curso.service;

import java.util.List;

import es.curso.entity.Videojuego;

public interface VideojuegoService {

	List<Videojuego> getAllVideojuegos();
	List<Videojuego> findByCompania(String compania);
	List<Videojuego> findByNombre(String nombre);
	Videojuego findVideojuegoById(int id);
	void añadirVideojuego(Videojuego videojuego);
	void eliminarVideojuego(int id);
	double calcularTotal();
}
