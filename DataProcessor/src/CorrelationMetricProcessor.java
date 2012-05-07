import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CorrelationMetricProcessor extends DateDoubleMetricProcessor {

	ArrayList<Integer> recentDates = new ArrayList<Integer>();
	ArrayList<Integer> middleDates = new ArrayList<Integer>();
	ArrayList<Integer> oldDates = new ArrayList<Integer>();
	
	ArrayList<Integer> recentMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> recentMetric2 = new ArrayList<Integer>();
	
	ArrayList<Integer> oldMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> oldMetric2 = new ArrayList<Integer>();
	
	ArrayList<Integer> oldOldMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> oldOldMetric2 = new ArrayList<Integer>();
	
	ArrayList<Double> correlationMetric = new ArrayList<Double>();
	ArrayList<Double> correlationMetric2 = new ArrayList<Double>();
	
	boolean proporcionalLoc = false;
	
	public ArrayList<Integer> getOldOldMetric1() {
		return oldOldMetric1;
	}

	public void setOldOldMetric1(ArrayList<Integer> oldOldMetric1) {
		this.oldOldMetric1 = oldOldMetric1;
	}

	public ArrayList<Integer> getOldOldMetric2() {
		return oldOldMetric2;
	}

	public void setOldOldMetric2(ArrayList<Integer> oldOldMetric2) {
		this.oldOldMetric2 = oldOldMetric2;
	}
	
	public CorrelationMetricProcessor(String metric1, String metric2,
			boolean hasTreshold) {
		super(metric1, metric2, hasTreshold);
		initializeDates();

	}

	public CorrelationMetricProcessor(String metric1, String metric2,
			boolean hasTreshold, boolean printProjectName) {
		super(metric1, metric2, hasTreshold, printProjectName);
		initializeDates();

	}
	
	public CorrelationMetricProcessor(String metric1, String metric2,
			boolean hasTreshold, boolean printProjectName, boolean proporcionalLOC) {
		super(metric1, metric2, hasTreshold, printProjectName);
		this.proporcionalLoc = proporcionalLOC;
		initializeDates();

	}

	private void initializeDates() {
		recentDates.add(2009);
		recentDates.add(2010);
		recentDates.add(2011);

		middleDates.add(2007);
		middleDates.add(2008);

		oldDates.add(2005);
		oldDates.add(2006);
	}

	@Override
	public void readMetrics() throws IOException {
		
		if (proporcionalLoc)
			readMetricsProporcional();
		else {
			for (ArrayList<File> projectVersions : getProjectVersions()) {
				//System.out.println("Entrou aqui 2");
				//System.out.println("numero de vers�es= " + projectVersions.size()  );
				//File firstVersion = projectVersions.get(0);
				//System.out.println("Nome dos projetos: " + firstVersion.getParentFile().getParentFile().getName());
				//if (firstVersionYear(Integer.parseInt("2006"), firstVersion)){				
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					if (isValidProject(1000, lastVersion, getMetricsNames().get(0) , getMetricsNames().get(1))){
						int[] lastRecentMetricValue = getMetricCorrelationValue(lastVersion);
						System.out.println("metrica 0: " + lastRecentMetricValue[0]);
						System.out.println("metrica 1: " + lastRecentMetricValue[1]);
						System.out.println("metrica 2: " + lastRecentMetricValue[2]);

						double auxiliar = lastRecentMetricValue[0];
						double auxiliar2 = lastRecentMetricValue[1];
						double auxiliar3 = lastRecentMetricValue[2];
						double correlacaoMetrica = (auxiliar/auxiliar3)*100000;
						double correlacaoMetrica2 = (auxiliar2/auxiliar3)*100000;
						System.out.println("relacao metrica: " + correlacaoMetrica);

						if (correlacaoMetrica>0 || correlacaoMetrica2>0){
							correlationMetric.add(correlacaoMetrica);
							correlationMetric2.add(correlacaoMetrica2);
							getProjectNames().add(lastVersion.getParentFile().getParentFile().getName()+"/"+lastVersion.getParentFile().getName()+"/"+lastVersion.getName());
							contador=-5;
						}
					}
					contador++;
				}
				System.out.println("Saiu");
			}
		}
			
		//}
//		this.writeMetrics();
//		this.reboot();
	}
	
	public void readMetricsProporcional(){
		
		/*for (ArrayList<File> projectVersions : getProjectVersions()) {		
			
			int contador=1;
			
			//verificar se tem 3 versões ou mais fazer isso aqui para poupar processador 
			
			if(projectVersions.size()>3)
			
			while (contador<=projectVersions.size() && contador >0){
				File lastVersion = projectVersions.get(projectVersions.size()-(contador));
				if (isValidProject(1000, lastVersion, getMetricsNames().get(0) , getMetricsNames().get(1))){
					int[] lastRecentMetricValue = getMetricCorrelationValue(lastVersion);
					System.out.println("metrica 0: " + lastRecentMetricValue[0]);
					System.out.println("metrica 1: " + lastRecentMetricValue[1]);
					System.out.println("metrica 2: " + lastRecentMetricValue[2]);

					double auxiliar = lastRecentMetricValue[0];
					double auxiliar2 = lastRecentMetricValue[1];
					double auxiliar3 = lastRecentMetricValue[2];
					double correlacaoMetrica = (auxiliar/auxiliar3)*100000;
					double correlacaoMetrica2 = (auxiliar2/auxiliar3)*100000;
					System.out.println("relacao metrica: " + correlacaoMetrica);

					if (correlacaoMetrica>0 || correlacaoMetrica2>0){
						correlationMetric.add(correlacaoMetrica);
						correlationMetric2.add(correlacaoMetrica2);
						getProjectNames().add(lastVersion.getParentFile().getParentFile().getName()+"/"+lastVersion.getParentFile().getName()+"/"+lastVersion.getName());
						contador=-5;
					}
				}
				contador++;
			}
			System.out.println("Saiu");}*/
		
	}
	
	public void reboot(){
		correlationMetric.clear();
		correlationMetric2.clear();
		getProjectNames().clear();
	}
		
	@Override
	public void writeMetrics() throws IOException
	{	
		if(correlationMetric.size()>0 && correlationMetric2.size()>0){
			System.out.println("Tamanho da metrica correlacao" + correlationMetric.size());
			System.out.println("Tamanho de projects names" + getProjectNames().size());
		setGroup(GroupMetric.OLD);
		getCorrelationMetrics().addAll(correlationMetric);
		getCorrelationMetrics2().addAll(correlationMetric2);
		//System.out.println("tamanho das metricas + " + getMetricsRelationship().size());
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this,true);
		System.out.println("nome do proejto novoo" + getProjectNames().get(0).replace("/", "-") );
		doubleMetricProcessorFileManager.writeMetricsIntoCSV(getProjectNames().get(0));
		}
		
		else
			System.out.println("n�o e maior que 0");
		
	}
	
	
	private boolean isValidProject(ArrayList<File> recentVersions,
			ArrayList<File> middleVersions, ArrayList<File> oldVersions) {
		
		return !recentVersions.isEmpty();
		
		// DECOMENTAR ABAIXO PARA GERAR A VERS�ES POR GRUPO
		//return !recentVersions.isEmpty() && !middleVersions.isEmpty() && !oldVersions.isEmpty();
	}

	private void fillGroupDateVersion(ArrayList<File> projectVersions,
			ArrayList<File> recentVersions, ArrayList<File> middleVersions,
			ArrayList<File> oldVersions) {
		for (int i = 0; i < projectVersions.size(); i++) {
			File version = projectVersions.get(i);
			String name = version.getName();
			int year = Integer.parseInt(name.substring(1, 5));
			if (isRecentVersion(year)) {
				recentVersions.add(version);
		}
		}
			//DECOMENTAR ABAIXO PARA GERAR A VERS�ES POR GRUPO
			//} else if (isMiddleVersion(year)) {
//				middleVersions.add(version);
			//} else if (isOldVersion(year)) {
//				oldVersions.add(version);
	//		}
		  //}
	}

	private boolean isOldVersion(int year) {
		return year == oldDates.get(0) || year == oldDates.get(1);
	}

	private boolean isMiddleVersion(int year) {
		return year == middleDates.get(0) || year == middleDates.get(1);
	}

	private boolean isRecentVersion(int year) {
		return year == recentDates.get(0) || year == recentDates.get(1)
				|| year == recentDates.get(2);
	}

	//public void setGroup(GroupMetric group) {
	//	this.group = group;
	//}




}
