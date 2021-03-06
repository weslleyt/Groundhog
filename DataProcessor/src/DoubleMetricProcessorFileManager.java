import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DoubleMetricProcessorFileManager extends MetricProcessFileManager {

	private DoubleMetricProcessor doubleMetricProcessor;
	
	//private DateDoubleMetricProcessor2 ddDoubleMetricProcessor2;

	public DoubleMetricProcessorFileManager(
			DoubleMetricProcessor metricProcessor) {
		super(metricProcessor);
		doubleMetricProcessor = metricProcessor;
	}

	public DoubleMetricProcessorFileManager(DoubleMetricProcessor metricProcessor, boolean printProjectName) {
		super(metricProcessor, printProjectName);
		doubleMetricProcessor = metricProcessor;
	}
	
	public void writeMetricsIntoCSV(String nomeProjeto) throws IOException{
		
		
		File destinyFolderFile = new File(destinyFolder);
		
		if (!destinyFolderFile.exists()) {
			destinyFolderFile.mkdirs();
		}
		
		
		String csvFileName = getCsvFileName(nomeProjeto) + ".csv";
		FileWriter log = new FileWriter(destinyFolderFile.getAbsolutePath() + "\\" + csvFileName);
        BufferedWriter out = new BufferedWriter(log);
        
        
        writeMetricNames(out);
        writeMetricValues(out);       
       
        
        out.flush();
        out.close();
	}
	
	public String getCsvFileName(String nomeProjeto) {
		
		String name = doubleMetricProcessor.getMetricsNames().get(0) + "+"
				+ doubleMetricProcessor.getMetricsNames().get(1);
		if (doubleMetricProcessor instanceof MeanDoubleMetricProcessor) {
			name += "_Mean";
		}
		/*if (doubleMetricProcessor instanceof DateDoubleMetricProcessor2) {
			name += "_Date";
			System.out.println("tipo do grupo: "+((DateDoubleMetricProcessor2) doubleMetricProcessor).getGroup());
			if (((DateDoubleMetricProcessor2) doubleMetricProcessor).getGroup() == DateDoubleMetricProcessor2.GroupMetric.RECENT) {
				
				name += "_LAST_OLD_GROUP";
			}
			else {
				name += "_MID_OLD_GROUP";
			}
		}*/
		if (doubleMetricProcessor instanceof DateRelationshipMetricProcessor){
			name += "Relationship";
		}
		if (doubleMetricProcessor instanceof CorrelationMetricProcessor){
			name += "_Correlation_AllProjects_" ;//+ nomeProjeto.replace("/", "-");
			
		}
			
	
		return name;
	}
	
	@Override
	public String getCsvFileName() {

		String name = doubleMetricProcessor.getMetricsNames().get(0) + "+"
				+ doubleMetricProcessor.getMetricsNames().get(1);
		if (doubleMetricProcessor instanceof MeanDoubleMetricProcessor) {
			name += "_Mean";
		}
		/*if (doubleMetricProcessor instanceof DateDoubleMetricProcessor2) {
			System.out.println("entrou muuito errado 17/10");

			name += "_Date";
			System.out.println("tipo do grupo: "+((DateDoubleMetricProcessor2) doubleMetricProcessor).getGroup());
			if (((DateDoubleMetricProcessor2) doubleMetricProcessor).getGroup() == DateDoubleMetricProcessor2.GroupMetric.RECENT) {
				
				name += "_LAST_OLD_GROUP";
			}
			else {
				name += "_MID_OLD_GROUP";
			}
		}*/
		if (doubleMetricProcessor instanceof DateRelationshipMetricProcessor){
			name += "Relationship";
		}
		if (doubleMetricProcessor instanceof CorrelationMetricProcessor){
			name = "Relationship" + doubleMetricProcessor.getProjectNames().get(1);
		}
			
		//descomentar abaixo para gerar grupo
		if (doubleMetricProcessor instanceof DateDoubleMetricProcessor) {
			
			
			name += "_Date";
			System.out.println("tipo do grupo: "+((DateDoubleMetricProcessor) doubleMetricProcessor).getGroup());
			if (((DateDoubleMetricProcessor) doubleMetricProcessor).getGroup() == DateDoubleMetricProcessor.GroupMetric.RECENT) {		
				name += "_RECENT_OLD_";
			}
			else if (((DateDoubleMetricProcessor) doubleMetricProcessor).getGroup()== DateDoubleMetricProcessor.GroupMetric.OLD) {
				name += "_OLD_";
			}
			else if (((DateDoubleMetricProcessor) doubleMetricProcessor).getGroup()== DateDoubleMetricProcessor.GroupMetric.MID) {
				name += "_MID_OLD_";
			}
		}
		
		return name;
	}

	// Metric Names Columns
	@Override
	public void writeMetricNames(BufferedWriter out) throws IOException {

		if (getPrintProjectName()) {
			// Empty Column
			out.write(",");

		}
		for (int i = 0; i < doubleMetricProcessor.getMetricsNames().size(); i++) {
			out.write("\"" + doubleMetricProcessor.getMetricsNames().get(i)
					+ "\",");
		}

	}

	@Override
	public void writeMetricValues(BufferedWriter out) throws IOException {
		
		/*if (!(doubleMetricProcessor instanceof DateRelationshipMetricProcessor) && !(doubleMetricProcessor instanceof CorrelationMetricProcessor) ){
			System.out.println("ERRADO");
			ArrayList<Integer> metricsNumbers1 = doubleMetricProcessor
			.getMetricsNumber1();
			ArrayList<String> projectNames = doubleMetricProcessor
			.getProjectNames();
			ArrayList<Integer> metricsNumbers2 = doubleMetricProcessor
			.getMetricsNumber2();

			for (int i = 0; i < metricsNumbers1.size(); i++) {
				out.newLine();
				if (getPrintProjectName()) {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)			
						out.write(projectNames.get(i) + "," + metricsNumbers1.get(i)
								+ "," + metricsNumbers2.get(i));
				} else {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)
						out.write(metricsNumbers1.get(i) + "," + metricsNumbers2.get(i));
				}
			}
		}
		else*/
		
		if (doubleMetricProcessor instanceof GrandeDiferencaProjetosProcessor){
			
			ArrayList<Double> metricsNumbers1 = doubleMetricProcessor.getMetricsNumberNew1();
			
			ArrayList<String> projectNames = doubleMetricProcessor.getProjectNames();
			ArrayList<String> projectNamesSecondVersion = doubleMetricProcessor.getProjectNamesSecondVersion();

			ArrayList<Double> metricsNumbers2 = doubleMetricProcessor.getMetricsNumberNew2();

			for (int i = 0, j = 0; j < projectNames.size(); i++, j++) {
				out.newLine();
				if (getPrintProjectName()) {
					//teste
					    if(i+1<metricsNumbers1.size()){                  
						out.write(projectNames.get(j) + "," + metricsNumbers1.get(i)+ "," + metricsNumbers1.get(i+1)+ "," +
								projectNamesSecondVersion.get(j) + "," + metricsNumbers2.get(i) + "," +  metricsNumbers2.get(i+1));
						}
				} else {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)
						out.write(metricsNumbers1.get(i) + "," + metricsNumbers1.get(i++) + ","  + metricsNumbers2.get(i) + "," +metricsNumbers2.get(i++));
				}
				i++;
			}

		}
		
		if (doubleMetricProcessor instanceof DateDoubleMetricProcessor){
			
			ArrayList<Double> metricsNumbers1 = doubleMetricProcessor.getMetricsNumberNew1();

			//System.out.println("Valor é : " +doubleMetricProcessor.getMetricsNumberNew1().get(0));
			
			ArrayList<String> projectNames = doubleMetricProcessor.getProjectNames();

			ArrayList<Double> metricsNumbers2 = doubleMetricProcessor.getMetricsNumberNew2();

			for (int i = 0; i < metricsNumbers1.size(); i++) {
				out.newLine();
				if (getPrintProjectName()) {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)                      
						out.write(projectNames.get(i) + "," + metricsNumbers1.get(i)+ "," + metricsNumbers2.get(i));
				} else {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)
						out.write(metricsNumbers1.get(i) + "," + metricsNumbers2.get(i));
				}
			}

		}
		else if ((doubleMetricProcessor instanceof DateDoubleMetricProcessor) && !(doubleMetricProcessor instanceof CorrelationMetricProcessor)){
			
			ArrayList<Double> metricsNumbers1 = doubleMetricProcessor.getMetricsNumberNew1();

			System.out.println("Valor é : " +doubleMetricProcessor.getMetricsNumberNew1().get(0));
			
			ArrayList<String> projectNames = doubleMetricProcessor.getProjectNames();

			ArrayList<Double> metricsNumbers2 = doubleMetricProcessor.getMetricsNumberNew2();

			for (int i = 0; i < metricsNumbers1.size(); i++) {
				out.newLine();
				if (getPrintProjectName()) {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)                      
						out.write(projectNames.get(i) + "," + metricsNumbers1.get(i)+ "," + metricsNumbers2.get(i));
				} else {
					if (projectNames.size()>i && metricsNumbers1.size()>i && metricsNumbers2.size()>i)
						out.write(metricsNumbers1.get(i) + "," + metricsNumbers2.get(i));
				}
			}

		}
		
		else if (doubleMetricProcessor instanceof DateRelationshipMetricProcessor){
			ArrayList<Double> metricsNumbers1 = doubleMetricProcessor.getMetricsRelationship();
			ArrayList<String> projectNames = doubleMetricProcessor.getProjectNames();
			
			for (int i = 0; i < metricsNumbers1.size(); i++) {
				out.newLine();
				if (getPrintProjectName())
					if (projectNames.size()>i && metricsNumbers1.size()>i)			
						out.write(projectNames.get(i) + "," + metricsNumbers1.get(i));	
			}
		}
		else if (doubleMetricProcessor instanceof CorrelationMetricProcessor){
			ArrayList<Double> metricsNumbers1 = doubleMetricProcessor.getCorrelationMetrics();
			ArrayList<Double> metricsNumbers2 = doubleMetricProcessor.getCorrelationMetrics2();
			ArrayList<String> projectNames = doubleMetricProcessor.getProjectNames();
			
			for (int i = 0; i < metricsNumbers1.size(); i++) {
				out.newLine();
				if (getPrintProjectName())
					if (projectNames.size()>i && metricsNumbers1.size()>i)			
						out.write(projectNames.get(i) + "," + metricsNumbers1.get(i) + "," + metricsNumbers2.get(i));
			}
		}
	}
}
