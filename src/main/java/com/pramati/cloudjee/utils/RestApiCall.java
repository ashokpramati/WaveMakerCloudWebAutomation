package com.pramati.cloudjee.utils;

import org.apache.log4j.Logger;

/**
 * Rest API Call . This will make call to all rest request.
 * 
 * @author krishnakumarnellore
 * 
 */
public class RestApiCall {

	static DeployHelper deployHelper = null;
	protected static Logger log = Logger.getLogger(RestApiCall.class);

	public static String getJSONResponse(String command) {
		log.info("Started getting Json Response for command " + command);
		deployHelper = new DeployHelper();

		String response = null;
		try {
			response = deployHelper.executeCommand(command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Got response as " + response);
		System.out.println(response);
		return response;
	}

	public static String getJSONResponseWithSpecificUserPassword(String command,String username,String password,String param) {
		log.info("Started getting Json Response for command " + command);		
		deployHelper = new DeployHelper(username, password);
		//deployHelper = new DeployHelper(username, password);
		String response = null;
		try {
			if(param.length()>0){
				deployHelper.undeployindividualapp(param);
			}
			else{
				response = deployHelper.executeCommand(command);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Got response as " + response);
		System.out.println(response);
		return response;
	}

}
