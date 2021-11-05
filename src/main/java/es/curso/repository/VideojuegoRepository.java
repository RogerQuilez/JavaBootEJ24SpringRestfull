package es.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.curso.entity.Videojuego;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {
	
	public List<Videojuego> findByCompaniaContaining(String compania);
	public List<Videojuego> findByNombreContaining(String nombre);
}
