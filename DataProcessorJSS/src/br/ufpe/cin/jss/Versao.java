package br.ufpe.cin.jss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Versao {

	private File versaoLog;
	private String identificador;	
	private Integer nClasses; 
	private Integer nMetodos;
	private Integer loc;
	
	
	public Versao(File versaoLog) throws NumberFormatException, IOException{
		this.versaoLog = versaoLog;
		this.identificador = versaoLog.getName();	
		fillVersao();
	}

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

	public File getVersaoLog() {
		return versaoLog;
	}

	public void setVersaoLog(File versaoLog) {
		this.versaoLog = versaoLog;
	}
	
	private void fillVersao() throws NumberFormatException, IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(this.versaoLog));
		
		String str;
		while ((str = in.readLine()) != null) {
			String[] splitMetrics = str.split(":");
			
			Integer value = Integer.parseInt(splitMetrics[1].trim());
			
			switch (splitMetrics[0].trim()) {
			case "classes":
				this.nClasses = value;				
				break;
			case "methods":
				this.nMetodos = value;
				break;
			case "Lines of Code":
				this.loc = value;
				break;

			default:
				break;
			}
		}
		
		in.close();
		
	}
	
}
