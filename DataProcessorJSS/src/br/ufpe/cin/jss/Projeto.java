package br.ufpe.cin.jss;

import java.util.ArrayList;
import java.util.List;

public class Projeto {
	
	private String nome;
	private List<String> categorias;
	private List<Versao> versoes;
	private List<Projeto> subProjetos;
	
	public List<Projeto> getSubProjetos() {
		return subProjetos;
	}

	public void setSubProjetos(List<Projeto> subProjetos) {
		this.subProjetos = subProjetos;
	}

	public Projeto (String nome){
		versoes = new ArrayList<Versao>();
		categorias = new ArrayList<String>();
		subProjetos = new ArrayList<Projeto>();
		this.nome = nome;
		
	}
	
	public List<Versao> getVersoes() {
		return versoes;
	}

	public void setVersoes(List<Versao> versoes) {
		this.versoes = versoes;
	}	

	public List<String> getCategoria() {
		return categorias;
	}

	public void setCategoria(List<String> categoria) {
		this.categorias = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return this.nome.equals(((Projeto)obj).nome);
	}
	
	@Override
	public String toString() {
		String projetoString = nome +" " + categorias.toString() +" "+ versoes.toString();
		return super.toString() +" "+ projetoString;
	}

}
