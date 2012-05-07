import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DoubleMetricProcessor extends MetricProcessor{

	private ArrayList<Double> metricsNumberNew1 = new ArrayList<Double>();
	private ArrayList<Double> metricsNumberNew2 = new ArrayList<Double>();;
	private ArrayList<Integer> metricsNumber1;
	private ArrayList<Integer> metricsNumber2;
	private ArrayList<String> metricsNames;
	private ArrayList<String> projectNames;
	private ArrayList<Double> metricsRelationship = new ArrayList<Double>();
	private ArrayList<Double> metricsCorrelation = new ArrayList<Double>();
	private ArrayList<Double> metricsCorrelation2 = new ArrayList<Double>();
	
	
	public DoubleMetricProcessor(String metric1, String metric2, boolean hasTreshold)
	{
		initialize(metric1,metric2,hasTreshold);
	}
	
	public DoubleMetricProcessor(boolean printProjectName, String metric1, String metric2)
	{
		initialize(metric1,metric2,false);
		this.printProjectName = printProjectName;
	}
	
	public DoubleMetricProcessor(String metric1, String metric2, boolean hasTreshold,boolean printProjectName)
	{
		initialize(metric1,metric2,hasTreshold);
		this.printProjectName = printProjectName;
	}
	
	private void initialize(String metric1, String metric2, boolean hasTreshold)
	{
		setMetricsNumber1(new ArrayList<Integer>());
		setMetricsNumber2(new ArrayList<Integer>());
		metricsNames = new ArrayList<String>();	
		setProjectNames(new ArrayList<String>());
		metricsNames.add(metric1);
		metricsNames.add(metric2);
		this.hasThreshold = hasTreshold;
	}
	//well fez abaixo m�todo
	/*private void initialize(String metric1, String metric2)
	{
		setMetricsNumber1(new ArrayList<Integer>());
		setMetricsNumber2(new ArrayList<Integer>());
		metricsNames = new ArrayList<String>();	
		setProjectNames(new ArrayList<String>());
		metricsNames.add(metric1);
		metricsNames.add(metric2);
	}*/
	
	
	@Override
	public void writeMetrics() throws IOException
	{	
		//DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this, this.printProjectName);
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();
		/*
		  
		  SingleMetricProcessFileManager singleMetricProcessFileManager = new SingleMetricProcessFileManager(
				this,this.printProjectName);
			singleMetricProcessFileManager.writeMetricsIntoCSV();
		  
		  */
		
		//MetricProcessFileManager.writeMetricsIntoCSV(metricsNames, getMetricsNumber1(), getMetricsNumber2(),getProjectNames());		
	}
	//refazer aqui os readmetrics
	@Override
	public void readMetrics() throws IOException{
		
		
		for (ArrayList<File> projectVersions : getProjectVersions()) {
		
				if(inLimit(0, projectVersions, metricsNames.get(0)) &&
						inLimit(0, projectVersions, metricsNames.get(1)))
				{
					
					File endVersion = projectVersions.get(projectVersions.size()-1);
					//BufferedReader in = new BufferedReader(new FileReader(endVersion));
					//String str;
					
					
					int[] metricsEnd = getMetricValue(endVersion);
					int endNumberM1;
					if (metricsEnd[0]!=0 && metricsEnd[1]!=0){
						getMetricsNumber1().add(metricsEnd[0]);
						getMetricsNumber2().add(metricsEnd[1]);
						getProjectNames().add(endVersion.getParentFile().getParentFile().getName()+"/"+endVersion.getParentFile().getName());
						
						
					}				
					
					
				}
			
		}
		System.out.println("tamanho da lista" + getProjectNames().size());

		
		/*
		 
		 for (ArrayList<File> projectVersions : getProjectVersions()) {

			File lastVersion = projectVersions.get(projectVersions.size() - 1);
			BufferedReader in = new BufferedReader(new FileReader(lastVersion));
			String str;

			boolean added = false;

			while ((str = in.readLine()) != null) {

				String[] splitMetrics = str.split(":");
				if (splitMetrics[0].trim().equals(getMetricName())) {

					if (getHasMetric()) {
						if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
							getMetricNumbers().add(
									Integer.parseInt(splitMetrics[1].trim()));
							added = true;
						}
					} else {
						getMetricNumbers().add(
								Integer.parseInt(splitMetrics[1].trim()));
						added = true;
					}
				}
			}

			if (added) {
				getProjectVersionsCount().add(projectVersions.size());
				getProjectNames().add(getProjectName(lastVersion));
			}

		} 
		  
		 * */
		
		
		
		
		
	/*	
		//Subtract metric final version and start version
		for (ArrayList<File> projectVersions : getProjectVersions()) {			
			
			//Project must have more than one version 
			if(projectVersions.size() > 1)
			{
				if(inLimit(0, projectVersions, metricsNames.get(0)) &&
						inLimit(0, projectVersions, metricsNames.get(1)))
				{
					File startVersion = projectVersions.get(0);
					File endVersion = projectVersions.get(projectVersions.size()-1);
		
					int[] metricsStart = getMetricValue(startVersion);
					int[] metricsEnd = getMetricValue(endVersion);
					
					int startNumberM1 = metricsStart[0];
					int endNumberM1 = metricsEnd[0];
					int m1 = endNumberM1 - startNumberM1;
					getMetricsNumber1().add(m1);			
					
					int startNumberM2 = metricsStart[1];
					int endNumberM2 = metricsEnd[1];
					int m2 = endNumberM2 - startNumberM2;
					getMetricsNumber2().add(m2);
					
					getProjectNames().add(endVersion.getParentFile().getParentFile().getName()+"/"+endVersion.getParentFile().getName());
				}
			}
		}*/
	}
	
	protected int[] getMetricValue(File file) throws IOException
	{
		int[] value = new int[3];
		
		BufferedReader in = new BufferedReader(new FileReader(file));			
		String str;
		while ((str = in.readLine()) != null) {
			
			String[] splitMetrics = str.split(":");
			
			if(splitMetrics[0].trim().equals(metricsNames.get(0))){	
				value[0] = Integer.parseInt(splitMetrics[1].trim());					
			}else if (splitMetrics[0].trim().equals(metricsNames.get(1))) {
				value[1] = Integer.parseInt(splitMetrics[1].trim());	
			}else if (splitMetrics[0].trim().equals("Lines of Code")) {
				value[2] = Integer.parseInt(splitMetrics[1].trim());	
			}
			
		}
		
		return value;	
		
	}
	
	protected int[] getMetricCorrelationValue(File file) throws IOException
	{
		int[] value = new int[3];
		
		BufferedReader in = new BufferedReader(new FileReader(file));			
		String str;
		while ((str = in.readLine()) != null) {
			
			String[] splitMetrics = str.split(":");
			
			if(splitMetrics[0].trim().equals(metricsNames.get(0))){	
				value[0] = Integer.parseInt(splitMetrics[1].trim());					
			}else if (splitMetrics[0].trim().equals(metricsNames.get(1))) {
				value[1] = Integer.parseInt(splitMetrics[1].trim());	
			}else if (splitMetrics[0].trim().equals("Lines of Code")) {
				value[2] = Integer.parseInt(splitMetrics[1].trim());	
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
	
	public ArrayList<Double> getMetricsRelationship() {
		return metricsRelationship;
	}
	
	public ArrayList<Double> getCorrelationMetrics() {
		return metricsCorrelation;
	}
	
	public ArrayList<Double> getCorrelationMetrics2() {
		return metricsCorrelation2;
	}
	
	public ArrayList<Double> getMetricsNumberNew1() {
		return metricsNumberNew1;
	}

	public ArrayList<Double> getMetricsNumberNew2() {
		return metricsNumberNew2;
	}
	
	
}
