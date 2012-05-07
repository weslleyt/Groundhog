import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DateDoubleMetricProcessor2 extends DateDoubleMetricProcessor{

	ArrayList<Integer> recentDates = new ArrayList<Integer>();
	ArrayList<Integer> middleDates = new ArrayList<Integer>();
	ArrayList<Integer> oldDates = new ArrayList<Integer>();
	
	ArrayList<Integer> recentMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> recentMetric2 = new ArrayList<Integer>();
	
	ArrayList<Integer> oldMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> oldMetric2 = new ArrayList<Integer>();
	
	ArrayList<Integer> oldOldMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> oldOldMetric2 = new ArrayList<Integer>();
	
	
	
	
	
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

	/*
	private GroupMetric group;
	
	public static enum GroupMetric{
		LAST_OLD, MID_OLD, OLD_OLD
	}*/
	
	public DateDoubleMetricProcessor2(String metric1, String metric2,
			boolean hasTreshold) {
		super(metric1, metric2, hasTreshold);
		initializeDates();

	}

	public DateDoubleMetricProcessor2(String metric1, String metric2,
			boolean hasTreshold, boolean printProjectName) {
		super(metric1, metric2, hasTreshold, printProjectName);
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
		
		for (ArrayList<File> projectVersions : getProjectVersions()) {
			
			//O contador serve para ele passar por todas as versões do projeto
			int contador=1;
			//if (projectVersions.size() > 2) {
				//if (inLimit(0, projectVersions, getMetricsNames().get(0))
					//	&& inLimit(0, projectVersions, getMetricsNames().get(1))) {
			while (contador<=projectVersions.size() && contador >0){
			
				
				File lastRecentVersion = projectVersions.get(projectVersions.size()-(contador));
				//comentei abaixo para gerar os projetos que tem determinada métrica e não tem outra
				//if (isValidProject(999, lastRecentVersion, getMetricsNames().get(0) , getMetricsNames().get(1))){
				if (isValidUniqueProject(9999, lastRecentVersion, getMetricsNames().get(0) , getMetricsNames().get(1))){
				
//					ArrayList<File> recentVersions = new ArrayList<File>();
	//				ArrayList<File> middleVersions = new ArrayList<File>();
		//			ArrayList<File> oldVersions = new ArrayList<File>();
					
													
					int[] lastRecentMetricValue = getMetricValue(lastRecentVersion);
					
					
					if (lastRecentMetricValue[0]>0 && lastRecentMetricValue[1]<1 ){ 
						recentMetric1.add(lastRecentMetricValue[0]);
						recentMetric2.add(lastRecentMetricValue[1]);
						getProjectNames().add(lastRecentVersion.getParentFile().getParentFile().getName()+"/"+lastRecentVersion.getParentFile().getName()+"/"+lastRecentVersion.getName());
						contador=-1;
					}
					
					
					
					// DESCOMENTAR ABAIXO PARA GERAR GRUPOS
			/*	//	fillGroupDateVersion(projectVersions, recentVersions,
					//		middleVersions, oldVersions);

					//if (isValidProject(recentVersions, middleVersions, oldVersions)) {
						
							File lastRecentVersion = recentVersions.get(recentVersions.size()-1);
							File lastMiddleVersion = middleVersions.get(middleVersions.size() - 1);
							File lastOldVersion = oldVersions.get(oldVersions.size() -1);
							
							int[] lastRecentMetricValue = getMetricValue(lastRecentVersion);
							int[] lastMiddleMetricValue= getMetricValue(lastMiddleVersion);
							int[] lastOldMetricValue= getMetricValue(lastOldVersion);
							
							if (lastRecentMetricValue[0]>0 && lastRecentMetricValue[1]<1 ){ 
								recentMetric1.add(lastRecentMetricValue[0]);
								recentMetric2.add(lastRecentMetricValue[1]);
							}
							
							if (lastMiddleMetricValue[0]>0 && lastMiddleMetricValue[1]<1){
								oldMetric1.add(lastMiddleMetricValue[0]);
								oldMetric2.add(lastMiddleMetricValue[1]);
							}
							
							if (lastOldMetricValue[0]>0 && lastOldMetricValue[1]<1){
								oldOldMetric1.add(lastOldMetricValue[0]);
								oldOldMetric2.add(lastOldMetricValue[1]);
							}
							
							
							*/
							
					//}
				}
			contador++;
			}
			}
		//}
	}
	
	@Override
	public void writeMetrics() throws IOException
	{		
		setGroup(GroupMetric.LAST_OLD);
		System.out.println("GRUPO novo é: " + getGroup());
		getMetricsNumber1().addAll(recentMetric1);
		getMetricsNumber2().addAll(recentMetric2);
		
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this,true);
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();				
		//System.out.println("tamanho das métricas LAST old " + getMetricsNumber1().size() + "e 2:" + getMetricsNumber2().size() );
				
		
		/*setGroup(GroupMetric.LAST_OLD);
		System.out.println("GRUPO lo é: " + getGroup());
		getMetricsNumber1().addAll(recentMetric1);
		getMetricsNumber2().addAll(recentMetric2);
		
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this,true);
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();				
		System.out.println("tamanho das métricas LAST old " + getMetricsNumber1().size() + "e 2:" + getMetricsNumber2().size() );
		getMetricsNumber1().removeAll(recentMetric1);
		getMetricsNumber2().removeAll(recentMetric2);
		
		setGroup(GroupMetric.MID_OLD);
		System.out.println("GRUPO MI é: "+getGroup());
		getMetricsNumber1().addAll(oldMetric1);
		getMetricsNumber2().addAll(oldMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager2 = new DoubleMetricProcessorFileManager(this,true);
		doubleMetricProcessorFileManager2.writeMetricsIntoCSV();
		System.out.println("tamanho das métricas MID_OLD old " + getMetricsNumber1().size() + "e 2:" + getMetricsNumber2().size() );
		getMetricsNumber1().removeAll(oldMetric1);
		getMetricsNumber2().removeAll(oldMetric2);
		
		setGroup(GroupMetric.OLD_OLD);		
		System.out.println("GRUPO oo é: "+getGroup());
		getMetricsNumber1().addAll(oldOldMetric1);
		getMetricsNumber2().addAll(oldOldMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager3 = new DoubleMetricProcessorFileManager(this,true);
		doubleMetricProcessorFileManager3.writeMetricsIntoCSV();
		System.out.println("tamanho das métricas OLD old " + getMetricsNumber1().size() + "e 2:" + getMetricsNumber2().size() );*/
	}

	private boolean isValidProject(ArrayList<File> recentVersions,
			ArrayList<File> middleVersions, ArrayList<File> oldVersions) {
		
		return !recentVersions.isEmpty();
		
		// DECOMENTAR ABAIXO PARA GERAR A VERSÕES POR GRUPO
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
			//DECOMENTAR ABAIXO PARA GERAR A VERSÕES POR GRUPO
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
