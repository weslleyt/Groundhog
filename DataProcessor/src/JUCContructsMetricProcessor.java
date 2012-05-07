import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class JUCContructsMetricProcessor extends SingleMetricProcessor{
	
	public final String futureConstructs = "Future contructs";
	public final String atomicInteger = "AtomicInteger";
	public final String atomicLong = "AtomicLong";
	public final String atomicBoolean = "AtomicBoolean";
	public final String barries = "Barriers";
	public final String executorsConstructs = "Executors contructs";
	public final String concurrentCollections = "Concurrent collections";
	public final String importJuc = "j.u.c";

	public JUCContructsMetricProcessor(String metricName, boolean hasTreshold) {
		super(metricName, hasTreshold);
		
	}
	
	public JUCContructsMetricProcessor(String metricName, boolean hasTreshold,boolean printProjectName ,boolean hasMetric) {
		super(metricName, hasTreshold, printProjectName,hasMetric);
		
	}
	
	@Override
	public void readMetrics() throws IOException 
	{
		for (ArrayList<File> projectVersions : getProjectVersions()) {
			
			
			//System.out.println("É esse READ aqui");
			int contador=1;
			while (contador<=projectVersions.size() && contador >0){
				//System.out.println("contador JUC :" + contador);
				if (inLimitLOC(999, projectVersions.get(projectVersions.size()-(contador)))){
			//		System.out.println("contador juc2 é :" + contador);

					File lastVersion = projectVersions.get(projectVersions.size()-(contador));
					BufferedReader in = new BufferedReader(new FileReader(lastVersion));
					String str;
					boolean added = false;
					int importJucCount =0;	
					int jucCount = 0;
					while ((str = in.readLine()) != null) {

						String[] splitMetrics = str.split(":");

						if(isJucConstructMetric(splitMetrics))
						{
							jucCount += Integer.parseInt(splitMetrics[1].trim());
							if(splitMetrics[0].trim().equals(importJuc))
							{
								importJucCount = Integer.parseInt(splitMetrics[1].trim());
							}
						}				
					}

					if (getHasMetric()) {
						if (jucCount > 0 && importJucCount >0) {
							getMetricNumbers().add(jucCount);
							added = true;
						}
					} else {
						getMetricNumbers().add(jucCount);
						added = true;
					}			

					if (added) {
						getProjectVersionsCount().add(projectVersions.size());
						getProjectNames().add(getProjectName(lastVersion));
					}
					contador=-1;
				}
				contador++;
				
			}		
					
			
			/*
			 
			 File lastVersion = projectVersions.get(projectVersions.size() - 1);
			BufferedReader in = new BufferedReader(new FileReader(lastVersion));
			String str;
			
			 
			 boolean added = false;
			int importJucCount =0;	
			int jucCount = 0;
			while ((str = in.readLine()) != null) {

				String[] splitMetrics = str.split(":");
				
				if(isJucConstructMetric(splitMetrics))
				{
					jucCount += Integer.parseInt(splitMetrics[1].trim());
					if(splitMetrics[0].trim().equals(importJuc))
					{
						importJucCount = Integer.parseInt(splitMetrics[1].trim());
					}
				}				
			}

			if (getHasMetric()) {
				if (jucCount > 0 && importJucCount >0) {
					getMetricNumbers().add(jucCount);
					added = true;
				}
			} else {
				getMetricNumbers().add(jucCount);
				added = true;
			}			

			if (added) {
				getProjectVersionsCount().add(projectVersions.size());
				getProjectNames().add(getProjectName(lastVersion));
			}
*/
		}
	
	}

	private boolean isJucConstructMetric(String[] splitMetrics) {
		return splitMetrics[0].trim().equals(getMetricName())
				|| splitMetrics[0].trim().equals(futureConstructs)
				|| splitMetrics[0].trim().equals(barries)
				|| splitMetrics[0].trim().equals(atomicInteger)
				|| splitMetrics[0].trim().equals(atomicLong)
				|| splitMetrics[0].trim().equals(atomicBoolean)
				|| splitMetrics[0].trim().equals(executorsConstructs)
				|| splitMetrics[0].trim().equals(concurrentCollections)
				|| splitMetrics[0].trim().equals(importJuc);
	}
	
	

}
