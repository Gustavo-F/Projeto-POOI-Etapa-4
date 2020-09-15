package Kindle.entidades;

import java.util.ArrayList;

public class Livro {
	
	private int idLivro;
	private String titulo;
	private int paginas;
	private ArrayList<Escritor> escritores;
	private ArrayList<Genero> generos;
	private Editora editora;
	
	public Livro() {
		this.escritores = new ArrayList<Escritor>();
		this.generos = new ArrayList<Genero>();
	}

	public Livro(int idLivro, String titulo, int paginas, ArrayList<Escritor> escritores, ArrayList<Genero> genero, Editora editora) {	
		this.idLivro = idLivro;
		this.titulo = titulo;
		this.paginas = paginas;
		this.escritores = escritores;
		this.generos = genero;
		this.editora = editora;
	}

	public int getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public ArrayList<Escritor> getEscritores() {
		return escritores;
	}

	public void setEscritores(ArrayList<Escritor> escritores) {
		this.escritores = escritores;
	}

	public ArrayList<Genero> getGeneros() {
		return generos;
	}

	public void setGenero(ArrayList<Genero> genero) {
		this.generos = genero;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	
	
}