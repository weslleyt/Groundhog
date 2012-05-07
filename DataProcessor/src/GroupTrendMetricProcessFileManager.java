import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;


public class GroupTrendMetricProcessFileManager extends
		MetricProcessFileManager {

	GroupTrendMetricProcessor groupTrendMetricProcessor;

	public GroupTrendMetricProcessFileManager(
			GroupTrendMetricProcessor metricProcessor) {
		super(metricProcessor);
		groupTrendMetricProcessor = metricProcessor;
	}

	@Override
	public String getCsvFileName() {
		String name = groupTrendMetricProcessor.getMetricName() + "_GroupTrend";
		return name;
	}

	@Override
	public void writeMetricNames(BufferedWriter out) throws IOException {

		Enumeration<GroupTrendMetricProcessor.TrendType> keys = groupTrendMetricProcessor
				.getGroupCount().keys();
		while (keys.hasMoreElements()) {
			out.write("\"" + keys.nextElement().toString() + "\";");
		}
	}

	@Override
	public void writeMetricValues(BufferedWriter out) throws IOException {

		out.newLine();
		Hashtable<GroupTrendMetricProcessor.TrendType, Integer> group = groupTrendMetricProcessor
				.getGroupCount();

		Enumeration<GroupTrendMetricProcessor.TrendType> keys = groupTrendMetricProcessor
				.getGroupCount().keys();
		while (keys.hasMoreElements()) {
			out.write(group.get(keys.nextElement()) + ";");
		}
	}

}
