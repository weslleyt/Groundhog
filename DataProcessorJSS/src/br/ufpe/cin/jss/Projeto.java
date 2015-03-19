package br.ufpe.cin.jss;

import java.util.ArrayList;
import java.util.List;

public class Projeto {
	
	private String nome;
	private List<Category> categories;
	
	private List<SubProject> subProjetos;
	
	public List<SubProject> getSubProjetos() {
		return subProjetos;
	}

	public void setSubProjetos(List<SubProject> subProjetos) {
		this.subProjetos = subProjetos;
	}

	public Projeto (String nome){
		//versoes = new ArrayList<Versao>();
		categories = new ArrayList<Category>();
		subProjetos = new ArrayList<SubProject>();
		this.nome = nome;
		
	}
	
//	public List<Versao> getVersoes() {
//		return versoes;
//	}
//
//	public void setVersoes(List<Versao> versoes) {
//		this.versoes = versoes;
//	}	

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategorie(List<Category> category) {
		this.categories = category;
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
		String projetoString = nome +" " + categories.toString() +" "+ subProjetos.toString();
		return super.toString() +" "+ projetoString;
	}

}
