import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SingleMetricProcessFileManager extends MetricProcessFileManager {

	SingleMetricProcessor singleMetricProcessor;

	public SingleMetricProcessFileManager(SingleMetricProcessor metricProcessor) {
		super(metricProcessor);
		singleMetricProcessor = metricProcessor;
	}

	public SingleMetricProcessFileManager(
			SingleMetricProcessor metricProcessor, boolean printProjectName) {
		super(metricProcessor, printProjectName);
		singleMetricProcessor = metricProcessor;
	}

	@Override
	public String getCsvFileName() {

		String name = singleMetricProcessor.getMetricName();

		if (!singleMetricProcessor.hasThreshold) {
			name += "notThreshold";
		}

		if (singleMetricProcessor.printProjectName) {
			name += "ProjectName";
		}
		if (singleMetricProcessor.getHasMetric()) {
			name += "HasMetric";
		}
		
		if (!singleMetricProcessor.getHasMetric()) {
			name += "Has_NOT_Metric";
		}
		
		if (singleMetricProcessor instanceof JUCContructsMetricProcessor) {
			name += "_JUC_CONSTRUCTS";
		}

		return name;
	}

	@Override
	public void writeMetricNames(BufferedWriter out) throws IOException {
		if (getPrintProjectName()) {
			// Empty Column
			out.write(",");
			out.write("\"nVersions\",");
		}
		out.write("\"" + singleMetricProcessor.getMetricName() + "\",");

	}

	@Override
	public void writeMetricValues(BufferedWriter out) throws IOException {
		System.out.println("chegou");
		ArrayList<Integer> metricsNumbers = new ArrayList<Integer>();;
		ArrayList<Double> metricsNumbersDerived = new ArrayList<Double>();
		

		ArrayList<String> projectNames = singleMetricProcessor.getProjectNames();
		
		ArrayList<Integer> projectVersionsCount = singleMetricProcessor.getProjectVersionsCount();
		
		if(!singleMetricProcessor.getMetricNumbers().isEmpty()){
			metricsNumbers = singleMetricProcessor.getMetricNumbers();
			for (int i = 0; i < metricsNumbers.size(); i++) {
				out.newLine();
				double n = metricsNumbers.get(i);
				
				System.out.println("valor do x = " + n);
				if (getPrintProjectName()) {
					out.write(projectNames.get(i) +","+ projectVersionsCount.get(i)+ "," + Double.toString(n));
				} else {
					out.write(Double.toString(n));
				}
			}
			
			System.out.println("Entrou no if de metric normal");
		}
		if (!singleMetricProcessor.getMetricNumbersDerived().isEmpty()){
			metricsNumbersDerived = singleMetricProcessor.getMetricNumbersDerived();
			for (int i = 0; i < metricsNumbersDerived.size(); i++) {
				out.newLine();
				double n = metricsNumbersDerived.get(i);
				
				System.out.println("valor do y = " + n);
				if (getPrintProjectName()) {
					out.write(projectNames.get(i) +","+ projectVersionsCount.get(i)+ "," + Double.toString(n));
				} else {
					out.write(Double.toString(n));
				}
			}
		}

		//para tirar o c�digo do percento � necess�rio comentar a linha abaixo
		//ArrayList<Float> metricsNumbers = singleMetricProcessor.getMetricValuePercent();
		
		
		
		System.out.println("Terminou");
		//ProjectsCategoriesMetricProcessorFileManager pcmpfm = new ProjectsCategoriesMetricProcessorFileManager(
		//		singleMetricProcessor);
		//pcmpfm.writeMetricsIntoCSV();

	}

}
