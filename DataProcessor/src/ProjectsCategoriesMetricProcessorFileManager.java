import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ProjectsCategoriesMetricProcessorFileManager extends MetricProcessFileManager{

	SingleMetricProcessor singleMetricProcessor;	
	
	public ProjectsCategoriesMetricProcessorFileManager(
			SingleMetricProcessor metricProcessor) {
		super(metricProcessor);	
		singleMetricProcessor = metricProcessor;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCsvFileName() {
		return singleMetricProcessor.getMetricName() + "_ProjectCategories";
	}

	@Override
	public void writeMetricNames(BufferedWriter out) throws IOException {
		
		out.write("CODE,Category,"+singleMetricProcessor.getMetricName());
	}

	@Override
	public void writeMetricValues(BufferedWriter out) throws IOException {
		
		ArrayList<Integer> metricsNumbers = singleMetricProcessor.getMetricNumbers();
		ArrayList<String> projectNames = singleMetricProcessor.getProjectNames();
		ArrayList<Integer> categoryCount = new ArrayList<Integer>(DataProcessor.categoriesName.size());
		for (int i = 0; i < DataProcessor.categoriesName.size(); i++) {
			categoryCount.add(0);
		}
				
		countCategories(metricsNumbers, projectNames, categoryCount);		
		writeCategoriesCount(out, categoryCount);
    	
	}

	private void writeCategoriesCount(BufferedWriter out,
			ArrayList<Integer> categoryCount) throws IOException {
		for (int j = 0; j < categoryCount.size(); j++) {
			out.newLine();
			out.write("C"+j+","+DataProcessor.categoriesName.get(j)+","+categoryCount.get(j).intValue());
		}
	}

	private void countCategories(ArrayList<Integer> metricsNumbers,
			ArrayList<String> projectNames, ArrayList<Integer> categoryCount) {
		for (int i = 0; i <  metricsNumbers.size(); i++) {
	        	int n= metricsNumbers.get(i);
	        	
	        	if(n > 0)
	        	{
	        		String mainName = getProjecMainName(projectNames.get(i));
	        		ArrayList<Integer> categoriCountIndex = getCategoriCountIndex(mainName);
	        		for (int j = 0; j < categoriCountIndex.size(); j++) {
	        			Integer integer = categoryCount.get(categoriCountIndex.get(j));
	        			integer += 1;
	        			categoryCount.set(categoriCountIndex.get(j),integer);
					}	        		
	        	}
		}
	}
	
	private String getProjecMainName(String projectName)
	{
		return projectName.split("/")[1];		
	}
	
	private ArrayList<Integer> getCategoriCountIndex(String mainName){
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		
		for (int i = 0; i < DataProcessor.categoriesName.size(); i++) {
			ArrayList<String> projects = DataProcessor.projectsByCategory.get(i);
			for (int j = 0; j < projects.size(); j++) {
				if(projects.get(j).equals(mainName))
				{
					indexs.add(i);
					break;
				}
			}
		}
		
		return indexs;
	}
	
	public void writeAllProjectCategories()
	{
		
	}

}
