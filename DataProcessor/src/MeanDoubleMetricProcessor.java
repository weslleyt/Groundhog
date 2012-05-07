import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MeanDoubleMetricProcessor extends DoubleMetricProcessor {

	public MeanDoubleMetricProcessor(String metric1, String metric2,
			boolean hasTreshold) {
		super(metric1, metric2, hasTreshold);
	}

	public MeanDoubleMetricProcessor(String metric1, String metric2,
			boolean hasTreshold, boolean printProjecNames) {
		super(metric1, metric2, hasTreshold, printProjecNames);
	}

	@Override
	public void readMetrics() throws IOException {

		for (ArrayList<File> projectVersions : getProjectVersions()) {

			// Project must have more than three version
			if (projectVersions.size() > 2) {
				if (inLimit(0, projectVersions, getMetricsNames().get(0))
						&& inLimit(0, projectVersions, getMetricsNames().get(1))) {
					File startVersion = projectVersions.get(0);
					File mediumVersion = projectVersions.get(projectVersions
							.size() / 2);
					File endVersion = projectVersions.get(projectVersions
							.size() - 1);

					int[] metricsStart = getMetricValue(startVersion);
					int[] metricsMedium = getMetricValue(mediumVersion);
					int[] metricsEnd = getMetricValue(endVersion);

					int startNumberM1 = metricsStart[0];
					int mediumNumberM1 = metricsMedium[0];
					int endNumberM1 = metricsEnd[0];
					int m1 = meanMetric(startNumberM1, mediumNumberM1,
							endNumberM1);
					getMetricsNumber1().add(m1);

					int startNumberM2 = metricsStart[1];
					int mediumNumberM2 = metricsMedium[1];
					int endNumberM2 = metricsEnd[1];
					int m2 = meanMetric(startNumberM2, mediumNumberM2,
							endNumberM2);
					getMetricsNumber2().add(m2);

					getProjectNames().add(
							endVersion.getParentFile().getParentFile()
									.getName()
									+ "/"
									+ endVersion.getParentFile().getName());
				}
			}
		}
	}

	// value = mean (LV-MV,MV-FV)
	// LV = last version; MV = medium version; FV = first version
	private int meanMetric(int startNumberM1, int mediumNumberM1,
			int endNumberM1) {
		return ((endNumberM1 - mediumNumberM1) + (mediumNumberM1 - startNumberM1)) / 2;
	}

}
