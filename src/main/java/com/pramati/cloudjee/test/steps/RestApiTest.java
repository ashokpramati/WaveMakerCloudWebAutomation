package com.pramati.cloudjee.test.steps;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.pramati.cloudjee.utils.RestBaseTest;
import com.pramati.cloudjee.utils.RestConfigProperties;
import com.pramati.cloudjee.utils.RestApiCall;



/**
 * Test Case for Rest API Call.Below test case are covered.
 * 
 * <b>Deploy Application</b> </br> <b>Stop Application</b> </br> <b>Start
 * Application</b> </br> <b>List Application</b> </br> <b>Undeploy
 * Applications</b>
 * 
 * 
 * @author krishnakumarnellore
 * 
 */
public class RestApiTest extends RestBaseTest {

	private static final String NODE_TIME_TAKEN = "time-taken";
	private static final String DEPLOY = "deploy";	
	private static final String UNDEPLOY = "undeploy";
	private static final String LIST ="list";


	private static final String NODE_APPSTATE ="appState";	
	private static final String NODE_APPFILENAME ="appFileName";
	private static final String NODE_TYPE = "type";
	private static final String NODE_SERVED_BY =  "served-by";
	private static final String NODE_INSTANCEGROUPNAME= "instanceGroupName";
	private static final String NODE_URL ="url";
	private static final String NODE_SSLENABLED="sslEnabled";

	protected static Logger log = Logger.getLogger(RestApiTest.class);


	@Test(description="Verify whether application is deployed successfully in cloud")
	@Parameters({ "type", "appFileName", "appState", "instanceGroupName",
		"name", "sslEnabled", "url" })
	public void verifyDeployApplicationAPI(String type, String appFileName,
			String appState, String instanceGroupName, String name,
			String sslEnabled, String url) throws ParseException {
		log.info("Started deploying of APP.");
		JsonNode bodyNode = null;
		String JsonResponse = RestApiCall.getJSONResponse(DEPLOY);
		log.info("Completed deployment of APP.");
		JsonNode node = getJsonNode(JsonResponse);
		bodyNode = node.findValue(NODE_TYPE);
		Assert.assertEquals(bodyNode.getTextValue(), "application",
				"type doesn't match");
		bodyNode = node.findValue(NODE_APPFILENAME);
		Assert.assertEquals(bodyNode.getTextValue(), RestConfigProperties.APP_NAME
				+ ".war", "APP Name doesn't match");
		bodyNode = node.findValue("appState");
		Assert.assertEquals(bodyNode.getTextValue(), "STARTED",
				"type doesn't match");
		bodyNode = node.findValue(NODE_INSTANCEGROUPNAME);
		Assert.assertEquals(bodyNode.getTextValue(), "defaultgroup",
				"Default group doesn't match");
		bodyNode = node.findValue("name");
		Assert.assertEquals(bodyNode.getTextValue(), RestConfigProperties.APP_NAME,
				"APP Name doesn't match");
		bodyNode = node.findValue(NODE_SSLENABLED);
		Assert.assertFalse(bodyNode.getBooleanValue(), "SSL is not enabled");
		bodyNode = node.findValue(NODE_URL);
		/*Assert.assertEquals(bodyNode.getTextValue(),
				"http://ashok.apps.wavemakercloud.com/sample/",
				"type doesn't match");*/
		Assert.assertTrue(node.findValue(NODE_TIME_TAKEN).getIntValue() < 10,
				"Time taken is more than 10");
	}
	
	@Test(description="Verify list of application avalibale in cloud")	
	public void verifyListApplicationsAPI() throws ParseException {
		log.info("Listing APP.");
		List<JsonNode> bodyNode = null;
		String JsonResponse = RestApiCall.getJSONResponse(LIST);
		log.info("APP's are listed.");
		JsonNode node = getJsonNode(JsonResponse);		
/*		bodyNode = node.findValues(NODE_TYPE);
		Assert.assertTrue(bodyNode.toString().contains("application"),
				"Type doesn't match");
		bodyNode = node.findValues(NODE_APPFILENAME);
		Assert.assertTrue(
				bodyNode.toString()
				.contains(RestConfigProperties.APP_NAME + ".war"),
				"APP Name doesn't match");
		bodyNode = node.findValues(NODE_APPSTATE);
		Assert.assertTrue(bodyNode.toString().contains("STARTED"),
				"type doesn't match");
		bodyNode = node.findValues(NODE_INSTANCEGROUPNAME);
		Assert.assertTrue(bodyNode.toString().contains("defaultgroup"),
				"Default group doesn't match");
		bodyNode = node.findValues("name");
		Assert.assertTrue(
				bodyNode.toString().contains(RestConfigProperties.APP_NAME),
				"APP Name doesn't match");
		bodyNode = node.findValues(NODE_SSLENABLED);
		System.out.println(bodyNode);
		Assert.assertFalse(bodyNode.contains(false), "SSL is not enabled");
		bodyNode = node.findValues(NODE_URL);
		Assert.assertTrue(
				bodyNode.toString().contains(
						"http://ashok.apps.wavemakercloud.com/sample/"),
				"type doesn't match");
		Assert.assertTrue(node.findValue(NODE_TIME_TAKEN).getIntValue() < 10,
				"Time taken is more than 10");
*/
	}

	/*@Test(dependsOnMethods = "verifyDeployApplicationAPI",description="Verify whether application is stopped succesfully")
	public void verifyStopApplicationAPI() throws ParseException {
		log.info("Beginning to stop APP.");
		String JsonResponse = RestApiCall.getJSONResponse("stop");
		log.info("Stopped APP.");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue(NODE_SERVED_BY);
		Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		Assert.assertTrue(node.findValue(NODE_TIME_TAKEN).getIntValue() < 10,
				"Time taken is more than 10");
	}

	@Test(dependsOnMethods = "verifyStopApplicationAPI" , description="Verify whether Application is started succesfully")
	public void verifyStartApplicationAPI() throws ParseException {
		log.info("Starting APP.");
		String JsonResponse = RestApiCall.getJSONResponse("start");
		log.info("Started APP.");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue(NODE_SERVED_BY);
		Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		Assert.assertTrue(node.findValue(NODE_TIME_TAKEN).getIntValue() < 10,
				"Time taken is more than 10");
	}


	@Test(dependsOnMethods = "verifyStartApplicationAPI", description="Verify list of application avalibale in cloud")	
	public void verifyListApplicationsAPI() throws ParseException {
		log.info("Listing APP.");
		List<JsonNode> bodyNode = null;
		String JsonResponse = RestApiCall.getJSONResponse(LIST);
		log.info("APP's are listed.");
		JsonNode node = getJsonNode(JsonResponse);		
		bodyNode = node.findValues(NODE_TYPE);
		Assert.assertTrue(bodyNode.toString().contains("application"),
				"Type doesn't match");
		bodyNode = node.findValues(NODE_APPFILENAME);
		Assert.assertTrue(
				bodyNode.toString()
				.contains(RestConfigProperties.APP_NAME + ".war"),
				"APP Name doesn't match");
		bodyNode = node.findValues(NODE_APPSTATE);
		Assert.assertTrue(bodyNode.toString().contains("STARTED"),
				"type doesn't match");
		bodyNode = node.findValues(NODE_INSTANCEGROUPNAME);
		Assert.assertTrue(bodyNode.toString().contains("defaultgroup"),
				"Default group doesn't match");
		bodyNode = node.findValues("name");
		Assert.assertTrue(
				bodyNode.toString().contains(RestConfigProperties.APP_NAME),
				"APP Name doesn't match");
		bodyNode = node.findValues(NODE_SSLENABLED);
		System.out.println(bodyNode);
		Assert.assertFalse(bodyNode.contains(false), "SSL is not enabled");
		bodyNode = node.findValues(NODE_URL);
		Assert.assertTrue(
				bodyNode.toString().contains(
						"http://ashok.apps.wavemakercloud.com/sample/"),
				"type doesn't match");
		Assert.assertTrue(node.findValue(NODE_TIME_TAKEN).getIntValue() < 10,
				"Time taken is more than 10");

	}



	@Test(dependsOnMethods = "verifyListApplicationsAPI",description="Verify whether application is undeployed succesfully from cloud")	
	public void verifyUndeployApplicationAPI() throws ParseException {
		log.info("Undeploying APP.");
		String JsonResponse = RestApiCall.getJSONResponse(UNDEPLOY);
		log.info("Completed undeployment of APP.");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue(NODE_SERVED_BY);
		Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		Assert.assertTrue(node.findValue(NODE_TIME_TAKEN).getIntValue() < 10,
				"Time taken is more than 10");

	}*/

}
