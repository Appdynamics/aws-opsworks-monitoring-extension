package com.appdynamics.extensions.aws.opsworks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.appdynamics.extensions.aws.config.MetricType;
import com.appdynamics.extensions.aws.metric.NamespaceMetricStatistics;
import com.appdynamics.extensions.aws.metric.StatisticType;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessor;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessorHelper;

/**
 * @author Florencio Sarmiento
 *
 */
public class OpsWorksMetricsProcessor implements MetricsProcessor {
	
	private static final String NAMESPACE = "AWS/OpsWorks";
	
	private static final String[] DIMENSIONS = {"StackId", "LayerId", "InstanceId"};
	
	private List<MetricType> metricTypes;
	
	private Pattern excludeMetricsPattern;
	
	public OpsWorksMetricsProcessor(List<MetricType> metricTypes,
			Set<String> excludeMetrics) {
		this.metricTypes = metricTypes;
		this.excludeMetricsPattern = MetricsProcessorHelper.createPattern(excludeMetrics);
	}

	public List<Metric> getMetrics(AmazonCloudWatch awsCloudWatch) {
		return MetricsProcessorHelper.getFilteredMetrics(awsCloudWatch, 
				NAMESPACE, 
				excludeMetricsPattern);
	}
	
	public StatisticType getStatisticType(Metric metric) {
		return MetricsProcessorHelper.getStatisticType(metric, metricTypes);
	}
	
	public Map<String, Double> createMetricStatsMapForUpload(NamespaceMetricStatistics namespaceMetricStats) {
		Map<String, String> dimensionToMetricPathNameDictionary = new HashMap<String, String>();
		dimensionToMetricPathNameDictionary.put(DIMENSIONS[0], "Stack");
		dimensionToMetricPathNameDictionary.put(DIMENSIONS[1], "Layer");
		dimensionToMetricPathNameDictionary.put(DIMENSIONS[2], "Instance");
		
		return MetricsProcessorHelper.createMetricStatsMapForUpload(namespaceMetricStats, 
				dimensionToMetricPathNameDictionary, false);
	}
	
	public String getNamespace() {
		return NAMESPACE;
	}

}
