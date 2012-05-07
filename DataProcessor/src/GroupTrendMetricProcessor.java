import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class GroupTrendMetricProcessor extends MetricProcessor {

	private String metricName;
	private Hashtable<TrendType, Integer> groupCount;
	private int noneCount;
	private int crescentCount;
	private int decrescentCount;
	private int constantCount;

	public GroupTrendMetricProcessor(String metricName, boolean hasTreshold) {
		this.setMetricName(metricName);
		setGroupCount(new Hashtable<TrendType, Integer>());
		this.hasThreshold = hasTreshold;
	}

	@Override
	public void readMetrics() throws IOException {

		for (ArrayList<File> projectVersions : getProjectVersions()) {
			if (projectVersions.size() > 1) {
				TrendType type = null;
				TrendType lastType = null;
				Integer lastValue = null;

				for (File version : projectVersions) {

					Integer value = getMetricValue(version);

					if (lastValue != null) {
						type = setTrendType(lastValue, value);
						type = checkNoneType(type, lastType);
						lastType = type;
						

						if (type.equals(TrendType.NONE)) {
							break;
						}
					}
					lastValue = value;
				}

				groupCount(type);
			}
		}
	}

	private void groupCount(TrendType type) {
		if (getGroupCount().containsKey(type)) {
			Integer i = getGroupCount().get(type) + 1;
			getGroupCount().put(type,i);
		} else {
			getGroupCount().put(type, 1);
		}
	}

	private Integer getMetricValue(File file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		Integer value = null;

		while ((str = in.readLine()) != null) {

			String[] splitMetrics = str.split(":");
			if (splitMetrics[0].trim().equals(getMetricName())) {

				value = Integer.parseInt(splitMetrics[1].trim());
				break;
			}
		}

		return value;
	}

	private TrendType checkNoneType(TrendType type, TrendType lastType) {

		if (lastType !=null && lastType != type) {
			type = TrendType.NONE;
		} 
		return type;
	}

	private TrendType setTrendType(int lastValue, int value) {
		TrendType type;
		if (value > lastValue) {
			type = TrendType.CRESCENT;
		} else if (value < lastValue) {
			type = TrendType.DECRESCENT;
		} else {
			type = TrendType.CONSTANT;
		}
		return type;
	}

	@Override
	public void writeMetrics() throws IOException {
		GroupTrendMetricProcessFileManager groupMetricProcessFileManager = new GroupTrendMetricProcessFileManager(
				this);
		groupMetricProcessFileManager.writeMetricsIntoCSV();

	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setGroupCount(Hashtable<TrendType, Integer> groupCount) {
		this.groupCount = groupCount;
	}

	public Hashtable<TrendType, Integer> getGroupCount() {
		return groupCount;
	}

	enum TrendType {
		CRESCENT, DECRESCENT, CONSTANT, NONE
	}
}
