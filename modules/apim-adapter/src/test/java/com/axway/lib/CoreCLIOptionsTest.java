package com.axway.lib;

import org.apache.commons.cli.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.axway.apim.lib.CLIOptions;
import com.axway.apim.lib.CoreCLIOptions;
import com.axway.apim.lib.CoreParameters;
import com.axway.apim.lib.Parameters;
import com.axway.apim.lib.errorHandling.AppException;
import com.axway.lib.utils.SampleCLIOptions;

public class CoreCLIOptionsTest {
	@Test
	public void testCoreParameters() throws AppException {
		String[] args = {"-h", "api-env", "-u", "apiadmin", "-p", "changeme", "-port", "8888", "-apimCLIHome", "My-home-is-my-castle", "-clearCache", "ALL", "-returnCodeMapping", "10:0", "-rollback", "false", "-force", "-ignoreCache", "-ignoreAdminAccount"};
		CLIOptions options = SampleCLIOptions.create(args);
		CoreParameters params = (CoreParameters) options.getParams();
		
		Assert.assertEquals(params.getHostname(), "api-env");
		Assert.assertEquals(params.getUsername(), "apiadmin");
		Assert.assertEquals(params.getPassword(), "changeme");
		Assert.assertEquals(params.getPort(), 8888);
		Assert.assertEquals(params.getApimCLIHome(), "My-home-is-my-castle");
		Assert.assertEquals(params.getClearCache(), "ALL");
		Assert.assertEquals(params.getReturnCodeMapping(), "10:0");
		Assert.assertTrue(params.isForce());
		Assert.assertFalse(params.isRollback());
		Assert.assertTrue(params.isIgnoreCache());
		Assert.assertTrue(params.isIgnoreAdminAccount());
	}
	
	@Test
	public void testOldForceParameter() throws AppException {
		String[] args = {"-s", "api-env", "-f", "true"};
		CLIOptions options = SampleCLIOptions.create(args);
		CoreParameters params = (CoreParameters) options.getParams();
		Assert.assertTrue(params.isForce());
	}
	
	@Test
	public void testStagePropertyFiles() throws ParseException, AppException {
		String[] args = {"-s", "yetAnotherStage"};
		CLIOptions options = SampleCLIOptions.create(args);
		CoreParameters params = (CoreParameters) options.getParams();
		
		Assert.assertEquals(params.getAdminUsername(), "yetanotherUser");
		Assert.assertEquals(params.getAdminPassword(), "yetanotherPassword");
		Assert.assertEquals(params.getProperties().get("yetAnotherProperty"), "HellImHere"); // from env.yetAnotherStage.properties
		Assert.assertEquals(params.getProperties().get("myTestVariable"), "resolvedToSomething"); // from env.properties
	}
	
	@Test
	public void testPropertyFileWithoutStage() throws ParseException, AppException {
		String[] args = {};
		CLIOptions options = SampleCLIOptions.create(args);
		CoreParameters params = (CoreParameters) options.getParams();
		
		Assert.assertEquals(params.getHostname(), "localhost");
		Assert.assertEquals(params.getProperties().get("myTestVariable"), "resolvedToSomething"); // from env.properties
	}
}
