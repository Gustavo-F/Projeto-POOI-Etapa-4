package Kindle.entidades;

import java.util.ArrayList;

public class Biblioteca {
	
	private ArrayList<Livro> livros;
	
	public Biblioteca() {
		livros = new ArrayList<Livro>();
	}
	
	public void adicionaLivro(Livro livro){
		for(int indice = 0; indice < livros.size(); indice++) {
			if(livros.get(indice).equals(livro)) {
				System.out.println("Livro já está na biblioteca!");
				return;
			}
		}
		
		livros.add(livro);
	}
	
	public void setLivros(ArrayList<Livro> livros) {
		this.livros = livros;
	}
	
	public ArrayList<Livro> getLivros(){
		return livros;
	}
	
}
