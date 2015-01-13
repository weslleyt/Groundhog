import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import javax.xml.bind.ParseConversionEvent;

public class SingleMetricProcessor extends MetricProcessor {

	private String metricName;
	private ArrayList<Integer> metricNumbers;
	private ArrayList<Double> metricNumbersDerived;
	private ArrayList<String> projectNames;
	private ArrayList<Integer> projectVersionsCount;
	private boolean hasMetric;
	private String year = "";
	private boolean proporcionalLoc=false;
	private boolean onlyOneProject=false;
	private boolean evolution = false;
	
	//valor em porcentagem da m�trica
	private ArrayList<Float> metricValuePercent;
	
	public ArrayList<Float> getMetricValuePercent() {
		return metricValuePercent;
	}

	public void setMetricValuePercent(ArrayList<Float> metricValuePercent) {
		this.metricValuePercent = metricValuePercent;
	}

	public SingleMetricProcessor(String metricName, boolean hasTreshold) {
		initialize(metricName, hasTreshold);
	}

	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
	}

	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames, boolean hasMetric) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
		this.setHasMetric(hasMetric);
	}
	
	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames, boolean hasMetric, boolean proporcionalLoc) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
		this.proporcionalLoc = proporcionalLoc;
		this.setHasMetric(hasMetric);
		
	}
	
	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames, boolean hasMetric, String year) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
		this.setHasMetric(hasMetric);
		this.year = year;
	}
	
	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames, boolean hasMetric, String year, boolean proporcionalLoc) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
		this.setHasMetric(hasMetric);
		this.year = year;
		this.proporcionalLoc = proporcionalLoc;
	}
	
	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames, boolean hasMetric, boolean evolution, String year, boolean proporcionalLoc) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
		this.setHasMetric(hasMetric);
		this.evolution = evolution;
		this.year = year;
		this.proporcionalLoc = proporcionalLoc;
	}
	
	public SingleMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames, boolean hasMetric, String year, boolean proporcionalLoc, boolean onlyOneProject) {
		initialize(metricName, hasTreshold);
		this.printProjectName = printProjectNames;
		this.setHasMetric(hasMetric);
		this.year = year;
		this.proporcionalLoc = proporcionalLoc;
		this.onlyOneProject = onlyOneProject;
	}
	
	public SingleMetricProcessor(String metricName, boolean concurrentProjects, boolean printProjectName, String releasedDate, boolean proporcionalLoc) {
		initialize(metricName, concurrentProjects);
		this.printProjectName = printProjectName;
		this.setHasMetric(true);
		this.year = releasedDate;
		this.proporcionalLoc = proporcionalLoc;
	}

	public void initialize(String metricName, boolean hasTreshold) {
		this.setMetricName(metricName);
		setMetricNumbers(new ArrayList<Integer>());
		setMetricNumbersDerived(new ArrayList<Double>());
		setProjectNames(new ArrayList<String>());
		setProjectVersionsCount(new ArrayList<Integer>());
		setMetricValuePercent(new ArrayList<Float>());
		
		this.hasThreshold = hasTreshold;
	}

	private boolean isVersionOfDateGiven(File projectVersion, String year) {	
			
			if (projectVersion.getName().substring(1, 5).equalsIgnoreCase(year)) 
				return true;
			else 
				return false;
		
	}
	
	
	//IMPORTANTE
	
	// LEMBRAR DE ALTERAR O C�DIGO EM SingleMetricProcessFileManager
	// ELE TA PEGANDO OS VALORES DE METRICVALUEPERCENT QUE � FLOAT e o normal �
	// INT ou seja, FOR VOLTAR AO NORMAL E COMENTAR A LINHA ABAIXO � PRECISO ALTERAR A CLASSE
	// SingleMetricProcessFileManager..	
	// m�todo para setar os valores do percentual de crescimento ou decrecimento das m�tricas
	public void setValorMetricaPercent (File firstVersion, File secondVersion) throws IOException	{		
		
		BufferedReader in = new BufferedReader(new FileReader(firstVersion));			
		String str;
		BufferedReader in2 = new BufferedReader(new FileReader(secondVersion));
		String str2;
		
		while ((str = in.readLine()) != null && (str2 = in2.readLine()) != null) {

			String[] splitMetrics = str.split(":");
			String[] splitMetrics2 = str2.split(":");
			String metricNamePercent = getMetricName();
			if (splitMetrics[0].trim().equals(metricNamePercent) && splitMetrics2[0].trim().equals(metricNamePercent)) {				
						
						float valor1 = Float.parseFloat(splitMetrics[1].trim());
						float valor2 = Float.parseFloat(splitMetrics2[1].trim());
						if (valor1 == 0)
							getMetricValuePercent().add(valor2*100);
						else{
							System.out.println("sera que tem float:? " + ((valor2/valor1)-1)*100);
							getMetricValuePercent().add(((valor2/valor1)-1)*100);
						}
						//else
							//getMetricValuePercent().add((valor2-valor1)/valor1*100);
			}
		}
		
		
	}
	
	public void setValorMetricaFirstAndLastVersions (File firstVersion, File secondVersion) throws IOException	{		
		
		BufferedReader in = new BufferedReader(new FileReader(firstVersion));			
		String str;
		BufferedReader in2 = new BufferedReader(new FileReader(secondVersion));
		String str2;
		
		while ((str = in.readLine()) != null && (str2 = in2.readLine()) != null) {

			String[] splitMetrics = str.split(":");
			String[] splitMetrics2 = str2.split(":");
			String metricNamePercent = getMetricName();
			if (splitMetrics[0].trim().equals(metricNamePercent) && splitMetrics2[0].trim().equals(metricNamePercent)) {				
				getProjectVersionsCount().add(Integer.parseInt(splitMetrics[1].trim()));
				getMetricNumbers().add(Integer.parseInt(splitMetrics2[1].trim()));
				
			}
		}
		
		
	}
	
	
	
	@Override
	public void readMetrics() throws IOException {
		
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
	//	if (!year.isEmpty() && proporcionalLoc && !evolution){
	//		System.out.println("Entrou onde deveria");
		//	readFirstYearMetricsPropLOC();
		//}
	//	else
		if (evolution && proporcionalLoc) {
			System.out.println("evolution");
			readMetricsEvolutionPropLOC();
			}
		else if (onlyOneProject==true && proporcionalLoc)
			readMetricsOnlyOneProjectPropLOC();
		else if (onlyOneProject==true && !proporcionalLoc)
			readMetricsOnlyOneProject();
		else if (year.isEmpty() && proporcionalLoc == false){
			
			System.out.println("Sem ser proporcional");

			for (ArrayList<File> projectVersions : getProjectVersions()) {
				int contador=1;
				
				
				while (contador<=projectVersions.size() && contador >0){
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;
					auxiliar = 0;
					auxiliar2=0;


					if (inLimitLOC(1, lastVersion)){

						while ((str = in.readLine()) != null) {
							String[] splitMetrics = str.split(":");
							if (splitMetrics[0].trim().equals(getMetricName())) {
								if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
									getMetricNumbers().add(Integer.parseInt(splitMetrics[1].trim()));
									added = true;
								}
							}
						}  
								
					}									
					if (added) {
						getProjectVersionsCount().add(projectVersions.size());
						getProjectNames().add(getProjectFullName(lastVersion));
						contador=-1;					
					}
					else if (added==false)
						contador++;
					
					in.close();
					
				
			}
				}
		}
		else if (!year.isEmpty() && proporcionalLoc == true )
				readMetricsYearPropLoc();
		else if (!year.isEmpty() && proporcionalLoc == false )
			readMetricsYear();
		else if (year.isEmpty() && proporcionalLoc == true)
			readMetricsPropLOC();
		
	}
	
	public void readMetricsEvolutionPropLOC() throws IOException {
		
		
		double somaMetricas=0, somaLoC=0;
		int contadorProjetos=0;
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
			for (ArrayList<File> projectVersions : getProjectVersions()) {
				//verifica se a primeira versao foi lancada no ano.
				if(VersionYear(Integer.parseInt(year), projectVersions.get(0))){
				
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;
					auxiliar = 0;
					auxiliar2=0;
					
					//if (VersionYear(Integer.parseInt(year), lastVersion)) {
					//if (VersionYearBefore(Integer.parseInt(year), lastVersion)) {
						
						if (inLimitLOC(999, lastVersion)){
							while ((str = in.readLine()) != null) {
								String[] splitMetrics = str.split(":");
								if (splitMetrics[0].trim().equals(getMetricName()))
									if (Integer.parseInt(splitMetrics[1].trim()) > 0){
										//auxiliar = Integer.parseInt(splitMetrics[1].trim());
										somaMetricas = somaMetricas + Integer.parseInt(splitMetrics[1].trim());
										added = true;
									}
								if (added && splitMetrics[0].trim().equals("Lines of Code") ){									
									//auxiliar2 = Integer.parseInt(splitMetrics[1].trim());		
									somaLoC = somaLoC + Integer.parseInt(splitMetrics[1].trim());
								}
							}											
							if (added && somaMetricas>0) { //added && auxiliar>0) {
								//relacaoMetrica = (auxiliar/auxiliar2)*100000;
								//getMetricNumbersDerived().add(relacaoMetrica);
								//getProjectVersionsCount().add(projectVersions.size());
								//getProjectNames().add(getProjectFullName(lastVersion));
								//String nome = getProjectFullName(lastVersion);
								//nomeProjeto.concat("SomaProjetos");
								contadorProjetos++;
								contador=-4;						
							}
						}
					//}
					in.close();
					if (added == false)
						contador++;	
				}	
				}
			}
			
			relacaoMetrica = (somaMetricas/somaLoC)*100000;
			getMetricNumbersDerived().add(relacaoMetrica);
			getProjectVersionsCount().add(contadorProjetos);
			getProjectNames().add("SomaProjetos");
	}

	public void readFirstYearMetricsPropLOC() throws IOException {
		
		System.out.println("parabens ");
		
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
		
			for (ArrayList<File> projectVersions : getProjectVersions()) {
				int contador=0;
				
				if (firstVersionYear(Integer.parseInt(this.year), projectVersions.get(0)) && inLimitLOC(999, projectVersions.get(0)))
				
				while (contador<projectVersions.size() && contador >=0){
					File auxVersion = projectVersions.get(contador);
					BufferedReader in = new BufferedReader(new FileReader(auxVersion));
					String str;
					boolean added = false;
					auxiliar = 0;
					auxiliar2=0;
					
				//	if (firstVersionYear(Integer.parseInt(this.year), projectVersions.get(0))) {
							
						//if (inLimitLOC(999, auxVersion)){
						//	System.out.println(projectVersions.get(0).getName());
							while ((str = in.readLine()) != null) {
								String[] splitMetrics = str.split(":");
								if (splitMetrics[0].trim().equals(getMetricName()))
									if (Integer.parseInt(splitMetrics[1].trim()) > 0){
										auxiliar = Integer.parseInt(splitMetrics[1].trim());
										added = true;
									}
								if (splitMetrics[0].trim().equals("Lines of Code") ){									
									auxiliar2 = Integer.parseInt(splitMetrics[1].trim());		
								}
							}											
							if (added && auxiliar>0 && auxiliar2>0) {
								relacaoMetrica = (auxiliar/auxiliar2)*100000;
								getMetricNumbersDerived().add(relacaoMetrica);
								getProjectVersionsCount().add(projectVersions.size());
								//getProjectNames().add(getProjectFullName(firstVersion));
								getProjectNames().add(auxVersion.getName());
								contador=-4;						
							}
						//}
					//}
					in.close();
					if (added == false)
						contador++;
					
				}
					
			}				
	}
	
	public void readMetricsOnlyOneProjectPropLOC() throws IOException {

		
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
		
		
			for (ArrayList<File> projectVersions : getProjectVersions()) {
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;
					auxiliar = 0;
					auxiliar2=0;
				//	if (inLimitLOC(999, lastVersion)){
						while ((str = in.readLine()) != null) {
							String[] splitMetrics = str.split(":");
							if (splitMetrics[0].trim().equals(getMetricName()))
								if (Integer.parseInt(splitMetrics[1].trim()) > 0){
									auxiliar = Integer.parseInt(splitMetrics[1].trim());
									added = true;
								}
							if (splitMetrics[0].trim().equals("Lines of Code") ){									
									auxiliar2 = Integer.parseInt(splitMetrics[1].trim());
									//if (projectHasMetric(FAZER ESSE M�TODO FUTURO) == hasMetric)
									//added = true;
							}
						}
					//}									
					if (added && auxiliar>0 && auxiliar2>0) {
						
							relacaoMetrica = (auxiliar/auxiliar2)*100000;
							getMetricNumbersDerived().add(relacaoMetrica);
							getProjectVersionsCount().add(projectVersions.size());
							getProjectNames().add(getProjectFullName(lastVersion));
							//contador=-1;						
					}
					//else if (added==false)
						contador++;
						in.close();
				}
			

			}
				
	}
	
	public void readMetricsOnlyOneProject() throws IOException {
		
			for (ArrayList<File> projectVersions : getProjectVersions()) {
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;
					
				//	if (inLimitLOC(999, lastVersion)){
					while ((str = in.readLine()) != null) {
						String[] splitMetrics = str.split(":");
						if (splitMetrics[0].trim().equals(getMetricName())) {
							if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
								getMetricNumbers().add(Integer.parseInt(splitMetrics[1].trim()));
								added = true;
							}
						}
					}  
					//}									
					if (added) {
						
						getProjectVersionsCount().add(projectVersions.size());
						getProjectNames().add(getProjectFullName(lastVersion));
						
					}
					//else if (added==false)
						contador++;
						in.close();
				}
			

			}
				
	}
	
	public void readMetricsPropLOC() throws IOException {
		System.out.println("Geral por tamanho");
		
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
		
		if(getProjectVersions().size()>3){
		
			for (ArrayList<File> projectVersions : getProjectVersions()) {
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;
					auxiliar = 0;
					auxiliar2=0;

					if (inLimitLOC(999, lastVersion)){
						while ((str = in.readLine()) != null) {
							String[] splitMetrics = str.split(":");
							if (splitMetrics[0].trim().equals(getMetricName()))
								if (Integer.parseInt(splitMetrics[1].trim()) > 0){
									auxiliar = Integer.parseInt(splitMetrics[1].trim());
									added = true;
								}
							if (splitMetrics[0].trim().equals("Lines of Code") ){									
									auxiliar2 = Integer.parseInt(splitMetrics[1].trim());
									//if (projectHasMetric(FAZER ESSE M�TODO FUTURO) == hasMetric)
									//added = true;
							}
						}
					}									
					if ( added && auxiliar>0 && auxiliar2>0) {
							relacaoMetrica = (auxiliar/auxiliar2)*100000;
							getMetricNumbersDerived().add(relacaoMetrica);
							getProjectVersionsCount().add(projectVersions.size());
							getProjectNames().add(getProjectFullName(lastVersion));
							contador=-1;					
					}
					else if (added==false)
						contador++;
					
					in.close();
					
				} 

			}
			}
				
	}
	
	public void readMetricsYearPropLoc() throws IOException {
		System.out.println("Lancado em tal ano ultima versao analisada");
		System.out.println("CERTOOO ICSM");
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
		for (ArrayList<File> projectVersions : getProjectVersions()) {
			File firstVersion = projectVersions.get(0);
			
			//ATENCAO - ESCOLHO AQUI SE A PRIMEIRA VERSAO TERA JUC OU NORMAL
			
			//if (firstVersionYear(Integer.parseInt(year), firstVersion, "j.u.c.WITHOUT.imports")){
			if (firstVersionYear(Integer.parseInt(year), firstVersion)){
				
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
					auxiliar = 0;
					auxiliar2 = 0;
					
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;

					if (inLimitLOC(999, lastVersion)){
						while ((str = in.readLine()) != null) {
							String[] splitMetrics = str.split(":");
							if (splitMetrics[0].trim().equals(getMetricName())) 
								if (Integer.parseInt(splitMetrics[1].trim()) > 0){
									auxiliar = Integer.parseInt(splitMetrics[1].trim());
									added = true;
									}
							if (splitMetrics[0].trim().equals("Lines of Code")){									
									auxiliar2 = Integer.parseInt(splitMetrics[1].trim());
							}
							
						}
					
					}
					
					if (added && auxiliar>0 && auxiliar2>0) {
						System.out.println("o auxiliar 1: " +  auxiliar );
						System.out.println("o auxiliar 2: " +  auxiliar2 );
						relacaoMetrica = (auxiliar/auxiliar2)*100000;
						getMetricNumbersDerived().add(relacaoMetrica);
						getProjectVersionsCount().add(projectVersions.size());
						getProjectNames().add(getProjectFullName(lastVersion));
						contador=-1;
					}
					else if (added==false)
						contador++;
				in.close();
				}
				
			}
		}
	}
	
	public void readMetricsYear() throws IOException {
		System.out.println("aqui");
		double auxiliar=0, auxiliar2=0, relacaoMetrica=0;
		for (ArrayList<File> projectVersions : getProjectVersions()) {
			File firstVersion = projectVersions.get(0);
			//if (firstVersionYear(Integer.parseInt(year), firstVersion, "j.u.c")){
			if (firstVersionYear(Integer.parseInt(year), firstVersion)){
				
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){

					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;

					if (inLimitLOC(999, lastVersion)){
						while ((str = in.readLine()) != null) {
							String[] splitMetrics = str.split(":");
							if (splitMetrics[0].trim().equals(getMetricName())) 
								if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
									getMetricNumbers().add(Integer.parseInt(splitMetrics[1].trim()));
									added = true;
								}							
						}
					}
					
					if (added) {
						getProjectVersionsCount().add(projectVersions.size());
						getProjectNames().add(getProjectName(lastVersion));
						contador=-1;
					}
					else if (added==false)
						contador++;
					
					in.close();
				
				}
			}
		}
	}
	
	@Override
	protected String getProjectName(File lastVersion) {
		return lastVersion.getParentFile().getParentFile().getParentFile()
				.getName()
				+ "/"
				+ lastVersion.getParentFile().getParentFile().getName()
				+ "/" + lastVersion.getParentFile().getName();
	}
	
	protected String getProjectFullName(File lastVersion) {
		return lastVersion.getParentFile().getParentFile().getParentFile()
				.getName()
				+ "/"
				+ lastVersion.getParentFile().getParentFile().getName()
				+ "/" + lastVersion.getParentFile().getName()
				+ "/" + lastVersion.getName();
	}

	@Override
	public void writeMetrics() throws IOException {
		SingleMetricProcessFileManager singleMetricProcessFileManager = new SingleMetricProcessFileManager(
				this,this.printProjectName);
		singleMetricProcessFileManager.writeMetricsIntoCSV();
	}

	public void setMetricNumbers(ArrayList<Integer> metricNumbers) {
		this.metricNumbers = metricNumbers;
	}

	public ArrayList<Integer> getMetricNumbers() {
		return metricNumbers;
	}
	
	public ArrayList<Double> getMetricNumbersDerived() {
		return metricNumbersDerived;
	}

	public void setMetricNumbersDerived(ArrayList<Double> metricNumbersDerived) {
		this.metricNumbersDerived = metricNumbersDerived;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setProjectNames(ArrayList<String> projectNames) {
		this.projectNames = projectNames;
	}

	public ArrayList<String> getProjectNames() {
		return projectNames;
	}

	public void setHasMetric(boolean hasMetric) {
		this.hasMetric = hasMetric;
	}

	public boolean getHasMetric() {
		return hasMetric;
	}

	public void setProjectVersionsCount(ArrayList<Integer> projectVersionsCount) {
		this.projectVersionsCount = projectVersionsCount;
	}

	public ArrayList<Integer> getProjectVersionsCount() {
		return projectVersionsCount;
	}

}
