package br.ufpe.cin.jss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Versao {

	private static final int NORMALIZING_FACTOR = 100000;
	private File versaoLog;
	private String identificador;	
	private Integer nClasses; 
	private Integer nMetodos;
	private Integer loc;
	
	private LinkedHashMap<String, Integer> metrics;	
	

	public Versao(File versaoLog) throws NumberFormatException, IOException{
		this.versaoLog = versaoLog;
		this.identificador = versaoLog.getName();
		this.metrics = new LinkedHashMap<String, Integer>();
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
	
	/**
	 * @return the metricas
	 */
	public LinkedHashMap<String, Integer> getMetricas() {
		return metrics;
	}	
	
	private void fillVersao() throws NumberFormatException, IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(this.versaoLog));
		
		String str;
		while ((str = in.readLine()) != null) {
			String[] splitMetrics = str.split(":");
			
			Integer value = Integer.parseInt(splitMetrics[1].trim());
			
			String metricName = splitMetrics[0].trim();
			switch (metricName) {
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
				metrics.put(metricName, value);
				break;
			}
		}
		
		in.close();		
	}
	
	public Double getNormalizedMetric(String metricName){
		double value;
		if(metrics.get(metricName)!=null){
			value = (metrics.get(metricName).doubleValue()/this.getLoc())*NORMALIZING_FACTOR;
			return value;
		}
		return null;		
	}
	
}
