package es.curso.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class Videojuego {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String imagen;

	@NotEmpty(message="El campo nombre no puede estar vacío")
	private String nombre;
	
	@NotEmpty(message="El campo compañia no puede estar vacío")
	private String compania;
	
	@Min(0)
	@Max(10)
	private double nota;
	
	@NotEmpty(message="El campo autor no puede estar vacío")
	private String author;
	
	@Min(0)
	private int price;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCompania() {
		return compania;
	}
	
	public void setCompania(String compania) {
		this.compania = compania;
	}
	
	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Videojuego [id=" + id + ", imagen=" + imagen + ", nombre=" + nombre + ", compania=" + compania
				+ ", nota=" + nota + ", author=" + author + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, compania, id, imagen, nombre, nota, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Videojuego other = (Videojuego) obj;
		return Objects.equals(author, other.author) && Objects.equals(compania, other.compania) && id == other.id
				&& Objects.equals(imagen, other.imagen) && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(nota) == Double.doubleToLongBits(other.nota) && price == other.price;
	}

}
