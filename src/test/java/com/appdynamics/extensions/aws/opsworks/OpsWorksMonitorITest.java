package com.appdynamics.extensions.aws.opsworks;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.appdynamics.extensions.aws.opsworks.OpsWorksMonitor;
import com.google.common.collect.Maps;
import com.singularity.ee.agent.systemagent.api.TaskOutput;

public class OpsWorksMonitorITest {
	
	private OpsWorksMonitor classUnderTest = new OpsWorksMonitor();
	
	@Test
	public void testMetricsCollectionCredentialsEncrypted() throws Exception {
		Map<String, String> args = Maps.newHashMap();
		args.put("config-file","src/test/resources/conf/itest-encrypted-config.yaml");
		
		TaskOutput result = classUnderTest.execute(args, null);
		assertTrue(result.getStatusMessage().contains("successfully completed"));
	}
	
	@Test
	public void testMetricsCoyllectionWithProxy() throws Exception {
		Map<String, String> args = Maps.newHashMap();
		args.put("config-file","src/test/resources/conf/itest-proxy-config.yaml");
		
		TaskOutput result = classUnderTest.execute(args, null);
		assertTrue(result.getStatusMessage().contains("successfully completed"));
	}	
}
