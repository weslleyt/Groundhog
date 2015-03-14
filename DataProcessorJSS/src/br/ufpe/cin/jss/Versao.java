package br.ufpe.cin.jss;

public class Versao {

	private String identificador;	
	private Integer nClasses; 
	private Integer nMetodos;
	private Integer loc;
	
	
	public Versao(String identificador, Integer nClasses, Integer nMetodos,
			Integer loc) {
		super();
		this.identificador = identificador;
		this.nClasses = nClasses;
		this.nMetodos = nMetodos;
		this.loc = loc;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Integer getnClasses() {
		return nClasses;
	}
	public void setnClasses(Integer nClasses) {
		this.nClasses = nClasses;
	}
	public Integer getnMetodos() {
		return nMetodos;
	}
	public void setnMetodos(Integer nMetodos) {
		this.nMetodos = nMetodos;
	}
	public Integer getLoc() {
		return loc;
	}
	public void setLoc(Integer loc) {
		this.loc = loc;
	}
	
}
