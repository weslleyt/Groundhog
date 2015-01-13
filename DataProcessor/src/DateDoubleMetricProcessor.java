import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DateDoubleMetricProcessor extends DoubleMetricProcessor {


	
	ArrayList<Integer> recentDates = new ArrayList<Integer>();
	ArrayList<Integer> middleDates = new ArrayList<Integer>();
	ArrayList<Integer> oldDates = new ArrayList<Integer>();
	
	ArrayList<Double> recentMetric1 = new ArrayList<Double>();
	ArrayList<Double> recentMetric2 = new ArrayList<Double>();
	
	ArrayList<Double> oldMetric1 = new ArrayList<Double>();
	ArrayList<Double> oldMetric2 = new ArrayList<Double>();
	
	ArrayList<Double> middleMetric1 = new ArrayList<Double>();
	ArrayList<Double> middleMetric2 = new ArrayList<Double>();
	
	boolean isOldRecent = true;
	
	
	private GroupMetric group;
	
	public static enum GroupMetric{
		RECENT, MID, OLD
	}
	
	public DateDoubleMetricProcessor(String metric1, String metric2,
			boolean hasTreshold) {
		super(metric1, metric2, hasTreshold);
		initializeDates();

	}
	
	public DateDoubleMetricProcessor(String metric1, String metric2,
			boolean hasTreshold, boolean printProjectName) {
		super(metric1, metric2, hasTreshold, printProjectName);
		
		initializeDates();

	}

	public DateDoubleMetricProcessor(String metric1, String metric2,
			boolean hasTreshold, boolean printProjectName, boolean isOldRecent) {
		super(metric1, metric2, hasTreshold, printProjectName);
		this.isOldRecent = isOldRecent;
		initializeDates();

	}

	private void initializeDates() {
		recentDates.add(2008);
		recentDates.add(2009);
		recentDates.add(2010);
		recentDates.add(2011);
		recentDates.add(2012);

		//removi o 2007 do grupo do meio para analise msc
		//modificar o método isMiddleVersion e os outros
		middleDates.add(2007);
		middleDates.add(2008);

		
		oldDates.add(2005);
		oldDates.add(2006);
		oldDates.add(2007);
		oldDates.add(2008);
		
	}

	@Override
	public void readMetrics() throws IOException {

		if(!this.isOldRecent){
			this.readMetricsNotOldRecent();
		}else{
		
		for (ArrayList<File> projectVersions : getProjectVersions()) {

			if (projectVersions.size() > 1) {
				
				//File firstVersion = projectVersions.get(0);
				//if (firstVersionYear(2006, firstVersion, "j.u.c"))
					if (inLimit(1, projectVersions, getMetricsNames().get(0))
							&& (inLimit(1, projectVersions, getMetricsNames().get(1)))) {
						
						ArrayList<File> recentVersions = new ArrayList<File>();
						ArrayList<File> middleVersions = new ArrayList<File>();
						ArrayList<File> oldVersions = new ArrayList<File>();

						fillGroupDateVersion(projectVersions, recentVersions,
								middleVersions, oldVersions);

						
						
						if (isValidProject(recentVersions, oldVersions) && inLimitLOC(1000, recentVersions) ) {
						//if (isValidProject(recentVersions, oldVersions) && inLimitLOC(1000, recentVersions.get(recentVersions.size()-1)) ){	
					
							//projeto normal, pegar a versão mais recente do grupo old
							File lastOldVersion = oldVersions.get(oldVersions.size()-1);
							
							File lastRecentVersion = recentVersions.get(recentVersions.size()-1);
							
							
							//vou preencher o lasMiddleVersion so para nao dar problema mas desconciderar 
							File lastMiddleVersion = recentVersions.get(recentVersions.size()-1);
						
							int[] lastRecentMetricValue = getMetricValue(lastRecentVersion);
							int[] lastMiddleMetricValue= getMetricValue(lastMiddleVersion);
							int[] lastOldMetricValue= getMetricValue(lastOldVersion);
					
												//		recentMetric1.add(lastRecentMetricValue[0] - lastOldMetricValue[0]);
												//		recentMetric2.add(lastRecentMetricValue[1] - lastOldMetricValue[1]);														
												//		oldMetric1.add(lastMiddleMetricValue[0] - lastOldMetricValue[0]);
												//		oldMetric2.add(lastMiddleMetricValue[1] - lastOldMetricValue[1]);

							//abaixo é para fazer apenas de uma métrica que tem em no periodo estabelecido
							//utilizado para fazer a resenha do old - recent SEM ser a correlacao
														recentMetric1.add(((double)lastRecentMetricValue[0]/lastRecentMetricValue[2])*100000);
														recentMetric2.add(((double)lastRecentMetricValue[1]/lastRecentMetricValue[2])*100000);
														middleMetric1.add(((double)lastMiddleMetricValue[0]/lastMiddleMetricValue[2])*100000);
														middleMetric2.add(((double)lastMiddleMetricValue[1]/lastMiddleMetricValue[2])*100000);
														oldMetric1.add(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000);
														oldMetric2.add(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000);
							
//aqui faz com 2 métricas, pegando o valor inicial menos final - CORRELACAO
							/*recentMetric1.add((((double)lastRecentMetricValue[0]/lastRecentMetricValue[2])*100000)-(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000));
							recentMetric2.add((((double)lastRecentMetricValue[1]/lastRecentMetricValue[2])*100000)-(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000));
							middleMetric1.add((((double)lastMiddleMetricValue[0]/lastMiddleMetricValue[2])*100000)-(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000));
							middleMetric2.add((((double)lastMiddleMetricValue[1]/lastMiddleMetricValue[2])*100000)-(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000));
							oldMetric1.add(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000);
							oldMetric2.add(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000);*/
							
							
							getProjectNames().add(lastRecentVersion.getParentFile().getParentFile().getName()+"/"+lastRecentVersion.getParentFile().getName());


						}
					}
			}
		}
		}
	}
	
	
	public void readMetricsNotOldRecent() throws IOException {
		
		for (ArrayList<File> projectVersions : getProjectVersions()) {

			if (projectVersions.size() > 1) {
				
				//verifica se possui a metrica
					if (inLimit(1, projectVersions, getMetricsNames().get(0))
							&& (inLimit(1, projectVersions, getMetricsNames().get(1)))) {
						
						
						if (inLimitLOC(1000, projectVersions) ) {
							File lastOldVersion = projectVersions.get(0);
							File lastRecentVersion = projectVersions.get(projectVersions.size()-1);
							
							
							//vou preencher o lasMiddleVersion so para nao dar problema mas desconciderar 
							File lastMiddleVersion = projectVersions.get(projectVersions.size()-1);
						
							int[] lastRecentMetricValue = getMetricValue(lastRecentVersion);
							int[] lastMiddleMetricValue= getMetricValue(lastMiddleVersion);
							int[] lastOldMetricValue= getMetricValue(lastOldVersion);
					
												//		recentMetric1.add(lastRecentMetricValue[0] - lastOldMetricValue[0]);
												//		recentMetric2.add(lastRecentMetricValue[1] - lastOldMetricValue[1]);														
												//		oldMetric1.add(lastMiddleMetricValue[0] - lastOldMetricValue[0]);
												//		oldMetric2.add(lastMiddleMetricValue[1] - lastOldMetricValue[1]);

//abaixo é para fazer apenas de uma métrica que tem em no periodo estabelecido
//utilizado para fazer a resenha do old - recent SEM ser a correlacao
		/*					recentMetric1.add(((double)lastRecentMetricValue[0]/lastRecentMetricValue[2])*100000);
							recentMetric2.add(((double)lastRecentMetricValue[1]/lastRecentMetricValue[2])*100000);
							middleMetric1.add(((double)lastMiddleMetricValue[0]/lastMiddleMetricValue[2])*100000);
							middleMetric2.add(((double)lastMiddleMetricValue[1]/lastMiddleMetricValue[2])*100000);
							oldMetric1.add(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000);
							oldMetric2.add(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000);*/

							
//aqui faz com 2 métricas, pegando o valor inicial menos final - CORRELACAO
							recentMetric1.add((((double)lastRecentMetricValue[0]/lastRecentMetricValue[2])*100000)-(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000));
							recentMetric2.add((((double)lastRecentMetricValue[1]/lastRecentMetricValue[2])*100000)-(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000));
							middleMetric1.add((((double)lastMiddleMetricValue[0]/lastMiddleMetricValue[2])*100000)-(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000));
							middleMetric2.add((((double)lastMiddleMetricValue[1]/lastMiddleMetricValue[2])*100000)-(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000));
							oldMetric1.add(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000);
							oldMetric2.add(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000);
							
							
							getProjectNames().add(lastRecentVersion.getParentFile().getParentFile().getName()+"/"+lastRecentVersion.getParentFile().getName());


						}
					}
			}
		}
	}
	
	



	@Override
	public void writeMetrics() throws IOException
	{		
		setGroup(GroupMetric.RECENT);
		getMetricsNumberNew1().addAll(recentMetric1);
		getMetricsNumberNew2().addAll(recentMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this, true);
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();	
		getMetricsNumberNew1().removeAll(recentMetric1);
		getMetricsNumberNew2().removeAll(recentMetric2);
		
		setGroup(GroupMetric.MID);
		getMetricsNumberNew1().addAll(middleMetric1);
		getMetricsNumberNew2().addAll(middleMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager2 = new DoubleMetricProcessorFileManager(this, true);
		doubleMetricProcessorFileManager2.writeMetricsIntoCSV();				
		getMetricsNumberNew1().removeAll(middleMetric1);
		getMetricsNumberNew2().removeAll(middleMetric2);
		
		setGroup(GroupMetric.OLD);		
		getMetricsNumberNew1().addAll(oldMetric1);
		getMetricsNumberNew2().addAll(oldMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager3 = new DoubleMetricProcessorFileManager(this,true);
		doubleMetricProcessorFileManager3.writeMetricsIntoCSV();
		getMetricsNumberNew1().removeAll(oldMetric1);
		getMetricsNumberNew2().removeAll(oldMetric2);
	}

	private boolean isValidProject(ArrayList<File> recentVersions,
			ArrayList<File> middleVersions, ArrayList<File> oldVersions) {
		return !recentVersions.isEmpty() && !middleVersions.isEmpty()
				&& !oldVersions.isEmpty();
	}

	private boolean isValidProject(ArrayList<File> recentVersions, ArrayList<File> oldVersions) {
		return !recentVersions.isEmpty() && !oldVersions.isEmpty();
	}
	
	private boolean isValidProjectWithMetric(ArrayList<File> recentVersions, ArrayList<File> oldVersions) {
		return !recentVersions.isEmpty() && !oldVersions.isEmpty();
	}
	
	private void fillGroupDateVersion(ArrayList<File> projectVersions,
			ArrayList<File> recentVersions, ArrayList<File> middleVersions,
			ArrayList<File> oldVersions) {
		for (int i = 0; i < projectVersions.size(); i++) {
			File version = projectVersions.get(i);
			String name = version.getName();
			
			if(name.startsWith("[")){
				int year = Integer.parseInt(name.substring(1, 5));
				int month = Integer.parseInt(name.substring(6, 8));
				if (isRecentVersion(year, month)) {
					recentVersions.add(version);
				}// else if (isMiddleVersion(year)) {
				//	middleVersions.add(version);
				//} else
					
				if (isOldVersion(year, month)) {
					oldVersions.add(version);
				}
			}
		}
	}

	private boolean isOldVersion(int year) {
		return year == oldDates.get(0) || year == oldDates.get(1) || year == oldDates.get(2);
	}
	
	private boolean isOldVersion(int year, int month) {
		
		if (year == oldDates.get(0) || year == oldDates.get(1)
				|| year == oldDates.get(2))
			return true;
		else
			if (year == oldDates.get(3) && month <= 6)
				return true;
		
		return false;
	}

	private boolean isMiddleVersion(int year) {
		return year == middleDates.get(0) || year == middleDates.get(1);
	}

	private boolean isRecentVersion(int year) {	
		return year == recentDates.get(0) || year == recentDates.get(1)
				|| year == recentDates.get(2) || year ==recentDates.get(3) ;
	}
	
	//modificacao para adicionar os 6 primeiros meses em old e os 6 ultimos de 2008 em new
	private boolean isRecentVersion(int year, int month) {
		
		if (year == recentDates.get(1) || year == recentDates.get(2)
				|| year == recentDates.get(3) || year == recentDates.get(4))
			return true;
		else
			if (year == recentDates.get(0) && month > 6)
				return true;
		
		return false;
	}

	public void setGroup(GroupMetric group) {
		this.group = group;
	}

	public GroupMetric getGroup() {
		return group;
	}
}
