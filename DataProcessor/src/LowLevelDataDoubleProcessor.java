import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LowLevelDataDoubleProcessor  extends DoubleMetricProcessor {

	ArrayList<Integer> recentDates = new ArrayList<Integer>();
	ArrayList<Integer> middleDates = new ArrayList<Integer>();
	ArrayList<Integer> oldDates = new ArrayList<Integer>();
	
	ArrayList<Integer> recentMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> recentMetric2 = new ArrayList<Integer>();
	
	ArrayList<Integer> oldMetric1 = new ArrayList<Integer>();
	ArrayList<Integer> oldMetric2 = new ArrayList<Integer>();
	
	
	ArrayList<Integer> coisa1a = new ArrayList<Integer>();
	ArrayList<Integer> coisa2a = new ArrayList<Integer>();
	ArrayList<Integer> coisa3a = new ArrayList<Integer>();
	ArrayList<Integer> coisa1b = new ArrayList<Integer>();
	ArrayList<Integer> coisa2b = new ArrayList<Integer>();
	ArrayList<Integer> coisa3b = new ArrayList<Integer>();
	
	
	private GroupMetric group;
	
	public static enum GroupMetric{
		LAST_OLD, MID_OLD
	}
	
	public LowLevelDataDoubleProcessor(String metric1, String metric2,
			boolean hasTreshold) {
		super(metric1, metric2, hasTreshold);
		initializeDates();

	}

	public LowLevelDataDoubleProcessor(String metric1, String metric2,
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
				if (inLimit(0, projectVersions, getMetricsNames().get(0))
						&& inLimit(0, projectVersions, getMetricsNames().get(1))) {

					ArrayList<File> recentVersions = new ArrayList<File>();
					ArrayList<File> middleVersions = new ArrayList<File>();
					ArrayList<File> oldVersions = new ArrayList<File>();

					fillGroupDateVersion(projectVersions, recentVersions,
							middleVersions, oldVersions);

					if (isValidProject(recentVersions, middleVersions, oldVersions)) {
						//	System.out.println("Aqui é o tamanho do recent version"+ recentVersions.size());
							//System.out.println("Aqui é o tamanho do middle version"+ middleVersions.size());
							//System.out.println("Aqui é o tamanho do old version"+ oldVersions.size());
							File lastRecentVersion = recentVersions.get(recentVersions.size()-1);
							File lastMiddleVersion = middleVersions.get(middleVersions.size() - 1);
							File lastOldVersion = oldVersions.get(oldVersions.size() -1);
							
							int[] lastRecentMetricValue = getMetricValue(lastRecentVersion);
							int[] lastMiddleMetricValue= getMetricValue(lastMiddleVersion);
							int[] lastOldMetricValue= getMetricValue(lastOldVersion);
							
							recentMetric1.add(lastRecentMetricValue[0] - lastOldMetricValue[0]);
							recentMetric2.add(lastRecentMetricValue[1] - lastOldMetricValue[1]);
														
							oldMetric1.add(lastMiddleMetricValue[0] - lastOldMetricValue[0]);
							oldMetric2.add(lastMiddleMetricValue[1] - lastOldMetricValue[1]);
							
							coisa1a.add(lastRecentMetricValue[0]);
							coisa1b.add(lastRecentMetricValue[1]);
							coisa2a.add(lastMiddleMetricValue[0]);
							coisa2b.add(lastMiddleMetricValue[1]);
							coisa3a.add(lastOldMetricValue[0]);
							coisa3b.add(lastOldMetricValue[1]);
							
							getProjectNames().add(lastRecentVersion.getParentFile().getParentFile().getName()+"/"+lastRecentVersion.getParentFile().getName());							
							
					}
				}
			}
		}
	}
	
	@Override
	public void writeMetrics() throws IOException
	{		
		setGroup(GroupMetric.LAST_OLD);
		
		getMetricsNumber1().addAll(recentMetric1);
		getMetricsNumber2().addAll(recentMetric2);
		
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this);
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();				
		
		getMetricsNumber1().removeAll(recentMetric1);
		getMetricsNumber2().removeAll(recentMetric2);
		
		setGroup(GroupMetric.MID_OLD);		
		getMetricsNumber1().addAll(oldMetric1);
		getMetricsNumber2().addAll(oldMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager2 = new DoubleMetricProcessorFileManager(this);
		doubleMetricProcessorFileManager2.writeMetricsIntoCSV();
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



