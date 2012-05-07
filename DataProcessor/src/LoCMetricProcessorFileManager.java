import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;


public class LoCMetricProcessorFileManager extends MetricProcessFileManager {

	LoCMetricProcessor locMetricProcessor;
	
	public LoCMetricProcessorFileManager(LoCMetricProcessor metricProcessor) {
		super(metricProcessor);
		locMetricProcessor = metricProcessor;
	}

	@Override
	public String getCsvFileName() {
		
		String name = "LOCNumbers";
		
		if(!locMetricProcessor.hasThreshold)
		{
			name+= "_notThreshold";
		}
		return name;
	}

	@Override
	public void writeMetricNames(BufferedWriter out) throws IOException {
		out.write(";");
		out.write("\"" + locMetricProcessor.metricName + "\",");
		
	}

	@Override
	public void writeMetricValues(BufferedWriter out) throws IOException {

		ArrayList<Integer> metricsNumbers = locMetricProcessor.getLocProjects();
		ArrayList<String> projectNames = locMetricProcessor.getProjectNames();

		//acho que não precisa disso não
		//ArrayList<Integer> projectVersionsCount = singleMetricProcessor.getProjectVersionsCount();
		
		
		for (int i = 0; i < metricsNumbers.size(); i++) {
			out.newLine();
			int n = metricsNumbers.get(i);
			//if (getPrintProjectName())
				out.write(projectNames.get(i) + "," + Integer.toString(n));
			//else
				//out.write(Integer.toString(n));
		}
			
			
	}
			
}
