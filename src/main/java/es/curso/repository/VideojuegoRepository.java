package es.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.curso.entity.Videojuego;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {
}
