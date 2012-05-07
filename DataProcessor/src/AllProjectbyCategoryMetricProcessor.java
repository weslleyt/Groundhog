import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class AllProjectbyCategoryMetricProcessor extends MetricProcessor{

	@Override
	public void readMetrics() throws IOException {
		
		ArrayList<Integer> categoryCount = new ArrayList<Integer>(DataProcessor.categoriesName.size());		
		
		for (int i = 0; i < DataProcessor.categoriesName.size(); i++) {
			categoryCount.add(0);
		}
		
		for (ArrayList<File> projectVersions : getProjectVersions()) {
			
			ArrayList<Integer> indexs = new ArrayList<Integer>();
			String mainName = projectVersions.get(0).getParentFile().getParentFile().getName();
			
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
			
			for (int j = 0; j < indexs.size(); j++) {
    			Integer integer = categoryCount.get(indexs.get(j));
    			integer += 1;
    			categoryCount.set(indexs.get(j),integer);
			}

		}
		
		for (int k = 0; k < +categoryCount.size(); k++) {
			System.out.println("C"+k+","+DataProcessor.categoriesName.get(k)+","+categoryCount.get(k).intValue());
		}
		
	}

	@Override
	public void writeMetrics() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
