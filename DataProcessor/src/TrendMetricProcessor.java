import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrendMetricProcessor extends DoubleMetricProcessor {

	public TrendMetricProcessor(String metricName, boolean hasTreshold) {
		super(metricName, metricName, hasTreshold);
	}

	public TrendMetricProcessor(String metricName, boolean hasTreshold,
			boolean printProjectNames) {
		super(metricName, metricName, hasTreshold, printProjectNames);
	}

	@Override
	public void readMetrics() throws IOException {

		for (ArrayList<File> projectVersions : getProjectVersions()) {

			// Project must have more than one version
			if (projectVersions.size() > 1) {
				File startVersion = projectVersions.get(0);
				File endVersion = projectVersions
						.get(projectVersions.size() - 1);

				int metricStart = getTrendMetricValue(startVersion);
				int metricEnd = getTrendMetricValue(endVersion);

				getMetricsNumber1().add(metricStart);
				getMetricsNumber2().add(metricEnd);

				getProjectNames().add(
						endVersion.getParentFile().getParentFile().getName()
								+ "/" + endVersion.getParentFile().getName());
			}
		}
	}

	private int getTrendMetricValue(File file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		while ((str = in.readLine()) != null) {

			String[] splitMetrics = str.split(":");

			if (splitMetrics[0].trim().equals(getMetricsNames().get(0))) {
				return Integer.parseInt(splitMetrics[1].trim());
			}

		}
		return 0;
	}
}
