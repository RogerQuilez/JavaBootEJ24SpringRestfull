package es.curso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.entity.Videojuego;
import es.curso.repository.VideojuegoRepository;
import es.curso.service.VideojuegoService;

@Service
public class VideojuegoServiceImpl implements VideojuegoService {
	
	@Autowired
	private VideojuegoRepository videoRepo;

	@Override
	public List<Videojuego> getAllVideojuegos() {
		return videoRepo.findAll();
	}

	@Override
	public Videojuego findVideojuegoById(int id) {
		return videoRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void añadirVideojuego(Videojuego videojuego) {
		videoRepo.save(videojuego);
	}

	@Override
	@Transactional
	public void eliminarVideojuego(int id) {
		videoRepo.deleteById(id);
	}

	@Override
	public List<Videojuego> findByCompania(String compania) {
		return videoRepo.findByCompaniaContaining(compania);
	}

	@Override
	public List<Videojuego> findByNombre(String nombre) {
		return videoRepo.findByNombreContaining(nombre);
	}

	@Override
	public double calcularTotal() {
		double total = 0;
		
		for (Videojuego v: videoRepo.findAll()) {
			total += v.getPrice();
		}
		
		return total;
	}

}
