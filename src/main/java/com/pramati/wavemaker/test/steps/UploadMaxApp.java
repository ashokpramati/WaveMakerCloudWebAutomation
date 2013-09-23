package com.pramati.wavemaker.test.steps;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pramati.cloudjee.test.steps.RestApiTest;
import com.pramati.cloudjee.utils.RestApiCall;
import com.pramati.cloudjee.utils.RestBaseTest;

/**
 * This class upload max war in cloud. So that we can get Exception in UI
 * 
 * @author krishnakumarnellore
 *
 */
public class UploadMaxApp extends RestBaseTest {
	

	protected static Logger log = Logger.getLogger(RestApiTest.class);

	private static final String MAX_DEPLOY = "maxdeploy";
	
	private static final String NODE_CAUSE = "message";

	private static final String EXPECTED_MAXERROR = "Maximun of 5 applications already deployed.";

	@Test(description="Deploy maximum of 5 application in cloud, So that WaveMakerMaxWarUploadError test case get error in alert")	
	public void uploadMaxApplicationAPI() throws ParseException {
		log.info("Started running maximum APP deployment.");		
		String JsonResponse = RestApiCall.getJSONResponse(MAX_DEPLOY);
		log.info("Completed max app deployment.");
		JsonNode node = getJsonNode(JsonResponse);
		Assert.assertEquals(node.findValue(NODE_CAUSE).getTextValue(), EXPECTED_MAXERROR, "Mismatch in displayed cause, displayed cause is "+node.findValue(NODE_CAUSE));
	}

}
