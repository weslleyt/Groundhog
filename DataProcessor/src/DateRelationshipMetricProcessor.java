import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
	
public class DateRelationshipMetricProcessor extends DateDoubleMetricProcessor {

		ArrayList<Integer> recentDates = new ArrayList<Integer>();
		ArrayList<Integer> middleDates = new ArrayList<Integer>();
		ArrayList<Integer> oldDates = new ArrayList<Integer>();
		
		ArrayList<Integer> recentMetric1 = new ArrayList<Integer>();
		ArrayList<Integer> recentMetric2 = new ArrayList<Integer>();
		
		ArrayList<Integer> oldMetric1 = new ArrayList<Integer>();
		ArrayList<Integer> oldMetric2 = new ArrayList<Integer>();
		
		ArrayList<Integer> oldOldMetric1 = new ArrayList<Integer>();
		ArrayList<Integer> oldOldMetric2 = new ArrayList<Integer>();
		
		ArrayList<Double> relationshipMetric = new ArrayList<Double>();
		
		
		
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
		
		public DateRelationshipMetricProcessor(String metric1, String metric2,
				boolean hasTreshold) {
			super(metric1, metric2, hasTreshold);
			initializeDates();

		}

		public DateRelationshipMetricProcessor(String metric1, String metric2,
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
				
			
				int contador=1;
				while (contador<=projectVersions.size() && contador >0){
				
					
					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					
					if (isValidProject(1000, lastVersion, getMetricsNames().get(0) , getMetricsNames().get(1))){
						int[] lastRecentMetricValue = getMetricValue(lastVersion);
						System.out.println("metrica 0: " + lastRecentMetricValue[0]);
						System.out.println("metrica 1: " + lastRecentMetricValue[1]);
						double auxiliar = lastRecentMetricValue[0];
						double auxiliar2 = lastRecentMetricValue[1];
						double relacaoMetrica = (auxiliar/auxiliar2)*100000;
						System.out.println("relacao metrica: " + relacaoMetrica);
						
						if (relacaoMetrica>0){
							relationshipMetric.add(relacaoMetrica);
							getProjectNames().add(lastVersion.getParentFile().getParentFile().getName()+"/"+lastVersion.getParentFile().getName());
							contador=-1;
						}
						
											}
				contador++;
				}
				}
		
		}
			
		@Override
		public void writeMetrics() throws IOException
		{		
			setGroup(GroupMetric.OLD);
			getMetricsRelationship().addAll(relationshipMetric);
			System.out.println("tamanho das metricas + " + getMetricsRelationship().size());
			DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this,true);
			doubleMetricProcessorFileManager.writeMetricsIntoCSV();				
			
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
//					middleVersions.add(version);
				//} else if (isOldVersion(year)) {
//					oldVersions.add(version);
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
