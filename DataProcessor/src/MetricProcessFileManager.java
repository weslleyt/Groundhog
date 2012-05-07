import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class MetricProcessFileManager {

	static String destinyFolder;
	static String sourceForlder;
	private MetricProcessor metricProcessor;
	private boolean printProjectName;
	
	
	public MetricProcessFileManager(MetricProcessor metricProcessor)
	{
		this.setMetricProcessor(metricProcessor);
	}
	
	public MetricProcessFileManager(MetricProcessor metricProcessor, boolean printProjectName)
	{
		this.setMetricProcessor(metricProcessor);
		this.setPrintProjectName(printProjectName);
	}
	
	public void writeMetricsIntoCSV() throws IOException{
	
		File destinyFolderFile = new File(destinyFolder);
		
		if (!destinyFolderFile.exists()) {
			destinyFolderFile.mkdirs();
		}
		
		String csvFileName = getCsvFileName() + ".csv";
		//FileWriter log = new FileWriter(destinyFolderFile.getAbsolutePath() + "\\" + csvFileName);
		FileWriter log = new FileWriter(destinyFolderFile.getAbsolutePath() + "//" + csvFileName);
        BufferedWriter out = new BufferedWriter(log);
        
        writeMetricNames(out);
        writeMetricValues(out);       
       
        
        out.flush();
        out.close();
	}

	public abstract String getCsvFileName();
	
	public abstract void writeMetricNames(BufferedWriter out)  throws IOException ;
	
	public abstract void writeMetricValues(BufferedWriter out)  throws IOException ;

	public void setSourceForlder(String sourceForlder) {
		MetricProcessFileManager.sourceForlder = sourceForlder;
	}

	public String getSourceForlder() {
		return sourceForlder;
	}

	public void setDestinyFolder(String destinyFolder) {
		MetricProcessFileManager.destinyFolder = destinyFolder;
	}

	public String getDestinyFolder() {
		return destinyFolder;
	}
	

	public void setMetricProcessor(MetricProcessor metricProcessor) {
		this.metricProcessor = metricProcessor;
	}

	public MetricProcessor getMetricProcessor() {
		return metricProcessor;
	}

	public void setPrintProjectName(boolean printProjectName) {
		this.printProjectName = printProjectName;
	}

	public boolean getPrintProjectName() {
		return printProjectName;
	}
	
}
