import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MetricProcessor2 extends MetricProcessor{
	
	private ArrayList<Integer> metricsNumber1;
	private ArrayList<Integer> metricsNumber2;
	private ArrayList<String> metricsNames;
	private ArrayList<String> projectNames;
	
	public MetricProcessor2(String metric1, boolean hasTreshold)
	{
		initialize(metric1,hasTreshold);
	}
	
	public MetricProcessor2(String metric1, boolean hasTreshold,boolean printProjectName)
	{
		initialize(metric1,hasTreshold);
		this.printProjectName = printProjectName;
	}
	
	private void initialize(String metric1, boolean hasTreshold)
	{
		setMetricsNumber1(new ArrayList<Integer>());
		metricsNames = new ArrayList<String>();	
		setProjectNames(new ArrayList<String>());
		metricsNames.add(metric1);
		this.hasThreshold = hasTreshold;
	}
	
	
	@Override
	public void writeMetrics() throws IOException
	{	
	//	DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this);
	//	doubleMetricProcessorFileManager.writeMetricsIntoCSV();
		//MetricProcessFileManager.writeMetricsIntoCSV(metricsNames, getMetricsNumber1(), getMetricsNumber2(),getProjectNames());		
	}

	@Override
	public void readMetrics() throws IOException{
		
		//Subtract metric final version and start version
		for (ArrayList<File> projectVersions : getProjectVersions()) {			
			
			//Project must have more than one version 
			if(projectVersions.size() > 1)
			{
				if(inLimit(0, projectVersions, metricsNames.get(0)) &&
						inLimit(0, projectVersions, metricsNames.get(1)))
				{
					File startVersion = projectVersions.get(projectVersions.size()-1);
					
		
				//	int[] metricsStart = getMetricsNumber1()(startVersion);
										
			//		int startNumberM1 = metricsStart[0];
			//		getMetricsNumber1().add(startNumberM1);			
				//	if()
					getProjectNames().add("teste"+startVersion.getParentFile().getParentFile().getName());
				}
			}
		}
	}
	
	protected int[] getMetricValue(File file) throws IOException
	{
		int[] value = new int[2];
		
		BufferedReader in = new BufferedReader(new FileReader(file));			
		String str;
		while ((str = in.readLine()) != null) {
			
			String[] splitMetrics = str.split(":");
			
			if(splitMetrics[0].trim().equals(metricsNames.get(0))){	
				value[0] = Integer.parseInt(splitMetrics[1].trim());					
			}else if (splitMetrics[0].trim().equals(metricsNames.get(1))) {
				value[1] = Integer.parseInt(splitMetrics[1].trim());	
			}
			
		}
		
		return value;	
		
	}

	public ArrayList<String> getMetricsNames() {
		return metricsNames;
	}


	public void setProjectNames(ArrayList<String> projectNames) {
		this.projectNames = projectNames;
	}


	public ArrayList<String> getProjectNames() {
		return projectNames;
	}


	public void setMetricsNumber2(ArrayList<Integer> metricsNumber2) {
		this.metricsNumber2 = metricsNumber2;
	}


	public ArrayList<Integer> getMetricsNumber2() {
		return metricsNumber2;
	}


	public void setMetricsNumber1(ArrayList<Integer> metricsNumber1) {
		this.metricsNumber1 = metricsNumber1;
	}


	public ArrayList<Integer> getMetricsNumber1() {
		return metricsNumber1;
	}
	
	
}