package br.ufpe.cin.jss;

import java.util.ArrayList;

public class Category {
	
	private String name;
	private Integer concurrentTimes;
	private Integer nonConcurrentTimes;
	private ArrayList<Projeto> listaProjetos = new ArrayList<Projeto>();
	
	
	public Category(String categoryName) {
		this.name = categoryName;
		this.concurrentTimes = 0;
		this.nonConcurrentTimes = 0;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the concurrentTimes
	 */
	public Integer getConcurrentTimes() {
		return concurrentTimes;
	}
	/**
	 * @param concurrentTimes the concurrentTimes to set
	 */
	public void setConcurrentTimes(Integer concurrentTimes) {
		this.concurrentTimes = concurrentTimes;
	}
	/**
	 * @return the nonConcurrentTimes
	 */
	public Integer getNonConcurrentTimes() {
		return nonConcurrentTimes;
	}
	/**
	 * @param nonConcurrentTimes the nonConcurrentTimes to set
	 */
	public void setNonConcurrentTimes(Integer nonConcurrentTimes) {
		this.nonConcurrentTimes = nonConcurrentTimes;
	}
	
	
	
	public ArrayList<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(ArrayList<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}
	
	public void adicionarProjeto(Projeto projeto) {
		listaProjetos.add(projeto);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		return this.name.equals(((Category)obj).name);
	}
	
	@Override
	public String toString() {
		
		return this.getName();
	}

}
