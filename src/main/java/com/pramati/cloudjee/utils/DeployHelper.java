package com.pramati.cloudjee.utils;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeployHelper extends RestBaseTest {

	private final String auth;

	public DeployHelper() {
		if (authCookie == null) {
			authenticate();
		}
		this.auth = getAuthCookie().getName() + "="
				+ getAuthCookie().getValue() + "; " + getjsessionId().getName()
				+ "=" + getjsessionId().getValue();
	}

	public DeployHelper(String username,String password) {
		if (authCookie == null) {
			authenticate(username,password);
		}
		this.auth = getAuthCookie().getName() + "="
				+ getAuthCookie().getValue() + "; " + getjsessionId().getName()
				+ "=" + getjsessionId().getValue();
	}

	static {
		javax.net.ssl.HttpsURLConnection
		.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
			public boolean verify(String hostname,
					javax.net.ssl.SSLSession sslSession) {
				if (hostname.equals(RestConfigProperties.HOST_NAME)) {
					return true;
				}
				return false;
			}
		});
	}

	static {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection
			.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String deploy(File file, String appName) throws Exception {

		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httppost = new HttpPost(RestConfigProperties.DEPLOY + appName);
		httppost.setHeader("Cookie", auth);

		FileBody uploadFilePart = new FileBody(file);
		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("file", uploadFilePart);
		httppost.setEntity(reqEntity);
		System.out.println("Request Header :"+httppost);
		HttpResponse response = httpclient.execute(httppost);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());

		return readResponse(response);		
	}

	private String start() throws Exception {

		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(RestConfigProperties.START
				+ RestConfigProperties.APP_NAME);
		httpget.setHeader("Cookie", auth);
		System.out.println("Start Of APP : " + httpget.getURI());
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());

		return readResponse(response);

	}

	private String stop(String appName) throws Exception {

		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(RestConfigProperties.STOP + appName);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());

		return readResponse(response);

	}

	private String list() throws Exception {
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpGet httpget = new HttpGet(RestConfigProperties.LIST);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		return readResponse(response);
	}
	/**
	 * Signout all session
	 * 
	 * @return
	 * @throws Exception
	 */
	private String signout() throws Exception {
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpGet httpget = new HttpGet(RestConfigProperties.SIGNOUT);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		return readResponse(response);
	}

	public String undeploy() throws Exception {
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(RestConfigProperties.UNDEPLOY
				+ RestConfigProperties.APP_NAME);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		return readResponse(response);
	}

	/**
	 * To undeploy app individually from cloud
	 * 
	 * @param appName
	 * @return
	 * @throws Exception
	 */
	public String undeployindividualapp(String appName) throws Exception {
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(RestConfigProperties.UNDEPLOY
				+ appName);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		return readResponse(response);
	}

	/**
	 * To be used to undeploy Maximum application from cloud
	 * 
	 * @return
	 * @throws Exception
	 */
	private String maxUndeploy() throws Exception {
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		String undeployMsg = "";
		String appName = RestConfigProperties.MAX_APP_NAME; //All APP Name are retrieved from property file

		String[] maxAppName =appName.split(",");

		for (String aName : maxAppName) {
			HttpPost httpget = new HttpPost(RestConfigProperties.UNDEPLOY
					+ aName);
			httpget.setHeader("Cookie", auth);
			HttpResponse response = httpclient.execute(httpget);
			System.out.println("ResponseCode: "
					+ response.getStatusLine().getStatusCode());
			undeployMsg += readResponse(response);
		}
		return undeployMsg;
	}



	protected String executeCommand(String cmd) throws Exception {
		String response = null;
		if (cmd.equals("deploy")) {
			File warPath = new File(RestConfigProperties.APP_PATH);
			response = deploy(warPath, RestConfigProperties.APP_NAME);
			signout();
			return response;
		} else if (cmd.equals("start")) {
			response = start();
			signout();
			return response;
		} else if (cmd.equals("stop")) {
			response= stop(RestConfigProperties.APP_NAME);
			signout();
			return response;
		} else if (cmd.equals("list")) {
			response= list();
			signout();
			return response;
		} else if (cmd.equals("undeploy")) {
			response= undeploy();
			signout();
			return response;
		}
		else if (cmd.equals("maxdeploy")) {          //To deploy more than one war file 
			String deployRes = "";
			File warPath = null;

			String appName = RestConfigProperties.MAX_APP_NAME; //All APP Name are retrieved from property file
			String appPath = RestConfigProperties.MAX_APP_PATH; //All APP Path are retrieved from property file

			String[] maxAppName =appName.split(",");
			String[] maxAppPath =appPath.split(",");

			int appIndex = 0;
			for (String path : maxAppPath) {
				warPath = new File(path);
				deployRes = deploy(warPath, maxAppName[appIndex]);
				appIndex++;
			}

			response= deployRes;
			signout();
			return response;
		}
		else if (cmd.equals("maxundeploy")) {  
			response= maxUndeploy();
			signout();
			return response;

		}
		return null;
	}

	
	/**
	 * Sample example below for usage.
	 * 
	 */
	
	/*public static void main(String[] args) {
		DeployHelper dh = new DeployHelper("ashok.c@imaginea.com", "pramati123");
		
		
		try {
			JsonNode node = new RestBaseTest().getJsonNode(dh.executeCommand("list"));
			List<JsonNode> appFileList = node.findValues("name");
			int i =0;
			for (JsonNode jsonNode : appFileList) {
				System.out.println(jsonNode.getTextValue() +" ------ " +node.findValues("appState").get(i) +" ------ " +node.findValues("createdAt").get(i));
				
				i++;
			}
			dh.signout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}