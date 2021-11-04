package es.curso.service;

import java.util.List;

import es.curso.entity.Videojuego;

public interface VideojuegoService {

	List<Videojuego> getAllVideojuegos();
	Videojuego findVideojuegoById(int id);
	void añadirVideojuego(Videojuego videojuego);
	void eliminarVideojuego(int id);
}
