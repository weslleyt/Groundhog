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

			if (projectVersions.size() > 2) {
				
				//File firstVersion = projectVersions.get(0);
				//if (firstVersionYear(2006, firstVersion, "j.u.c"))
					if (inLimit(1, projectVersions, getMetricsNames().get(0))
							&& (inLimit(1, projectVersions, getMetricsNames().get(1)))) {
						
						ArrayList<File> recentVersions = new ArrayList<File>();
						ArrayList<File> middleVersions = new ArrayList<File>();
						ArrayList<File> oldVersions = new ArrayList<File>();

						fillGroupDateVersion(projectVersions, recentVersions,
								middleVersions, oldVersions);

						
						
						if (isValidProject(recentVersions, middleVersions, oldVersions) && inLimitLOC(1000, recentVersions.get(recentVersions.size()-1)) ){//inLimitLOC(1000, recentVersions) ) {
							
							File lastRecentVersion = recentVersions.get(recentVersions.size()-1);
							File lastMiddleVersion = middleVersions.get(middleVersions.size() - 1);
							File lastOldVersion = oldVersions.get(oldVersions.size() -1);
						
							int[] lastRecentMetricValue = getMetricValue(lastRecentVersion);
							int[] lastMiddleMetricValue= getMetricValue(lastMiddleVersion);
							int[] lastOldMetricValue= getMetricValue(lastOldVersion);
						
							//							recentMetric1.add(lastRecentMetricValue[0] - lastOldMetricValue[0]);
							//							recentMetric2.add(lastRecentMetricValue[1] - lastOldMetricValue[1]);														
							//							oldMetric1.add(lastMiddleMetricValue[0] - lastOldMetricValue[0]);
							//							oldMetric2.add(lastMiddleMetricValue[1] - lastOldMetricValue[1]);



				/*			recentMetric1.add(((double)lastRecentMetricValue[0]/lastRecentMetricValue[2])*100000);
							recentMetric2.add(((double)lastRecentMetricValue[1]/lastRecentMetricValue[2])*100000);
							middleMetric1.add(((double)lastMiddleMetricValue[0]/lastMiddleMetricValue[2])*100000);
							middleMetric2.add(((double)lastMiddleMetricValue[1]/lastMiddleMetricValue[2])*100000);
							oldMetric1.add(((double)lastOldMetricValue[0]/lastOldMetricValue[2])*100000);
							oldMetric2.add(((double)lastOldMetricValue[1]/lastOldMetricValue[2])*100000);*/

							

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
		System.out.println("Chegou aqui");
		getMetricsNumberNew1().addAll(recentMetric1);
		System.out.println("Chegou aqui2");
		getMetricsNumberNew2().addAll(recentMetric2);
		System.out.println("Chegou aqui3");
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this, true);
		System.out.println("Chegou aqui4");
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();	
		System.out.println("Chegou aqui5");
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

	private void fillGroupDateVersion(ArrayList<File> projectVersions,
			ArrayList<File> recentVersions, ArrayList<File> middleVersions,
			ArrayList<File> oldVersions) {
		for (int i = 0; i < projectVersions.size(); i++) {
			File version = projectVersions.get(i);
			String name = version.getName();
			if(name.startsWith("[")){
				int year = Integer.parseInt(name.substring(1, 5));
				if (isRecentVersion(year)) {
					recentVersions.add(version);
				} else if (isMiddleVersion(year)) {
					middleVersions.add(version);
				} else if (isOldVersion(year)) {
					oldVersions.add(version);
				}
			}
		}
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

	public void setGroup(GroupMetric group) {
		this.group = group;
	}

	public GroupMetric getGroup() {
		return group;
	}
}
