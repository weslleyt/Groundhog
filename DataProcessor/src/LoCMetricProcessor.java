import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoCMetricProcessor extends MetricProcessor {
	public final String metricName = "Lines of Code";
	private ArrayList<Integer> locProjects;
	private ArrayList<String> projectNames;
	private String year;

	public LoCMetricProcessor(boolean hasThreshold) {
		this.hasThreshold = hasThreshold;
		this.printProjectName = true;
		locProjects = new ArrayList<Integer>();
		projectNames = new ArrayList<String>();
	}

	public LoCMetricProcessor(boolean hasThreshold, String year) {
		this.hasThreshold = hasThreshold;
		this.printProjectName = true;
		this.year = year;
		locProjects = new ArrayList<Integer>();
		projectNames = new ArrayList<String>();
	}
	
	@Override
	public void readMetrics() throws IOException {
		
		if(year==null){
			for (ArrayList<File> projectVersions : getProjectVersions()) {

				getProjectNames().add(getProjectName(projectVersions.get(0)));
				int countLoc = 0;

				for (int i = 0; i < projectVersions.size(); i++) {
					BufferedReader in = new BufferedReader(new FileReader(projectVersions.get(i)));
					String str;
					while ((str = in.readLine()) != null) {
						String[] splitMetrics = str.split(":");
						if (splitMetrics[0].trim().equals(metricName)) {
							int value = Integer.parseInt(splitMetrics[1].trim());
							//comentei a linha abaixo para ele só contar as versoes e projetos que forem menos de 20k
							//para fazer os grupos
							//if(value<20000 && value>=1000)
							//if(value>=20000 && value<=100000)
							//if(value>100000)
							
							//para todos os projetos:
							if(value>999)
								countLoc += value;						
						}
					}
					in.close();
				}
				//if (countLoc>0)
				getLocProjects().add(countLoc);
			}
		}
		if (year!=null)
			rearMetricsYear();
	}

	@Override
	public void writeMetrics() throws IOException {
		LoCMetricProcessorFileManager manager = new LoCMetricProcessorFileManager(this);
		manager.writeMetricsIntoCSV();
	}

	public void rearMetricsYear() throws IOException {
		for (ArrayList<File> projectVersions : getProjectVersions()) {

			File firstVersion = projectVersions.get(0);

			if (firstVersionYear(Integer.parseInt(year), firstVersion) && checkMetric(projectVersions, "j.u.c")){
			//if (firstVersionYear(Integer.parseInt(year), firstVersion)){
				getProjectNames().add(getProjectName(projectVersions.get(0)));
				int countLoc = 0;

				for (int i = 0; i < projectVersions.size(); i++) {
					BufferedReader in = new BufferedReader(new FileReader(projectVersions.get(i)));
					String str;
					while ((str = in.readLine()) != null) {
						String[] splitMetrics = str.split(":");
						if (splitMetrics[0].trim().equals(metricName)) {
							int value = Integer.parseInt(splitMetrics[1].trim());
							if(value>=999)
								countLoc += value;						
						}
					}
				}
				getLocProjects().add(countLoc);
			}
		}
	}
	
	public void setProjectNames(ArrayList<String> projectNames) {
		this.projectNames = projectNames;
	}

	public ArrayList<String> getProjectNames() {
		return projectNames;
	}

	public void setLocProjects(ArrayList<Integer> locProjects) {
		this.locProjects = locProjects;
	}

	public ArrayList<Integer> getLocProjects() {
		return locProjects;
	}

}
